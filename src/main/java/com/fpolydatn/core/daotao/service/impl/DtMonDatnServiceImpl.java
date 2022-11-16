package com.fpolydatn.core.daotao.service.impl;

import com.fpolydatn.core.common.base.PageableObject;
import com.fpolydatn.core.daotao.excel.DTMonDatnImport;
import com.fpolydatn.core.daotao.model.request.DtCreateImportMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtCreateMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtFindMonDatnRequest;
import com.fpolydatn.core.daotao.model.request.DtUpdateMonDatnRequest;
import com.fpolydatn.core.daotao.model.response.DtMessMonDatnReponse;
import com.fpolydatn.core.daotao.model.response.DtMonDatnResponse;
import com.fpolydatn.core.daotao.repository.DtChuyenNganhRepository;
import com.fpolydatn.core.daotao.repository.DtMonDatnRepository;
import com.fpolydatn.core.daotao.service.DtMonDatnService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hungpv
 */
@Service
@Validated
public class DtMonDatnServiceImpl implements DtMonDatnService {
    @Autowired
    private DtMonDatnRepository monDatnRepository;

    @Autowired
    private DtChuyenNganhRepository chuyenNganhRepository;

    private FormUtils formUtils = new FormUtils();

    private ConcurrentHashMap<String, MonDatn> monDatnHashMap;

    private ConcurrentHashMap<String, ChuyenNganh> chuyenNganhHashMap;

    private ConcurrentHashMap<String, ChuyenNganh> chuyenNganhById;

    private ConcurrentHashMap<String, MonDatn> nhomMonDatnHashMap;

    private DTMonDatnImport monDatnImport;

    @Override
    public PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtMonDatnResponse> res = monDatnRepository.getAllByCoSo(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(final String id, String coSoId) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        Integer countInPhanCongHuongDan = monDatnRepository.countInPhanCongHuongDan(id);
        Integer countInSinhVien = monDatnRepository.countInSinhVien(id, coSoId);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        if (countInPhanCongHuongDan != 0) {
            throw new RestApiException(Message.KHONG_DUOC_XOA_MON);
        }
        if (countInSinhVien != 0) {
            throw new RestApiException(Message.KHONG_DUOC_XOA_MON);
        }
        monDatnRepository.delete(monCheck.get());
        return true;
    }

    @Override
    public MonDatn findById(String id) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        return monCheck.get();
    }

    @Override
    public List<MonDatn> getNhomMonByChuyeNganh(String coSoid, String chuyenNganhId) {
        return monDatnRepository.getNhomMonDatnByCoSo(coSoid, chuyenNganhId);
    }

    @Override
    public MonDatn update(@Valid final DtUpdateMonDatnRequest command) {
        Optional<MonDatn> monDatn = monDatnRepository.findById(command.getId());
        if (!monDatn.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        String maMonReq = command.getMaMon().toUpperCase();
        MonDatn monDatnCheck = monDatnRepository.findByMaMon(maMonReq, command.getCoSoId());
        if (monDatnCheck != null) {
            if (!monDatnCheck.getId().equals(command.getId())) {
                throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
            }
        }
        Optional<ChuyenNganh> chuyenNganh = chuyenNganhRepository.findById(command.getChuyenNganhId());
        if (!chuyenNganh.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        monDatn.get().setCoSoId(command.getCoSoId());
        monDatn.get().setMaMon(maMonReq);
        monDatn.get().setTenMonDatn(command.getTenMonDatn());
        String nhomMonId = "";
        try {
            nhomMonId = command.getNhomMonDatnId().trim();
        } catch (Exception e) {
            return null;
        }
        if (nhomMonId.length() == 0) {
            monDatn.get().setNhomMonDatnId(monDatn.get().getId());
        } else {
            monDatn.get().setNhomMonDatnId(command.getNhomMonDatnId());
        }
        monDatn.get().setChuyenNganhId(command.getChuyenNganhId());
        monDatnRepository.save(monDatn.get());
        return monDatn.get();
    }

    @Override
    public Boolean add(@Valid final DtCreateMonDatnRequest command) {
        String maMon = "";
        try {
            maMon = command.getMaMon().toUpperCase();
        } catch (Exception e) {
            return false;
        }
        MonDatn monDatnCheck = monDatnRepository.findByMaMon(maMon, command.getCoSoId());
        if (monDatnCheck != null) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        }
        Optional<ChuyenNganh> chuyenNganh = chuyenNganhRepository.findById(command.getChuyenNganhId());
        if (!chuyenNganh.isPresent()) {
            throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
        }
        String nhomMonId = "";
        try {
            nhomMonId = command.getNhomMonDatnId().trim();
        } catch (Exception e) {
            return false;
        }
        MonDatn monDatn = new MonDatn();
        monDatn.setCoSoId(command.getCoSoId());
        monDatn.setChuyenNganhId(command.getChuyenNganhId());
        monDatn.setMaMon(maMon);
        monDatn.setTenMonDatn(command.getTenMonDatn());
        monDatn.setNhomMonDatnId(command.getNhomMonDatnId());
        monDatnRepository.save(monDatn);
        if ("".equals(nhomMonId)) {
            monDatn.setNhomMonDatnId(monDatn.getId());
            monDatnRepository.save(monDatn);
        }
        return true;
    }

    @Override
    public DtMessMonDatnReponse importExcel(MultipartFile file, String coSoId) {
        monDatnHashMap = new ConcurrentHashMap<>();
        chuyenNganhHashMap = new ConcurrentHashMap<>();
        nhomMonDatnHashMap = new ConcurrentHashMap<>();
        chuyenNganhById = new ConcurrentHashMap<>();
        monDatnImport = new DTMonDatnImport();
        boNhoDem(coSoId);
        DtMessMonDatnReponse mess = new DtMessMonDatnReponse();
        ConcurrentMap<String, MonDatn> list = new ConcurrentHashMap<>();
        ConcurrentMap<String, ChuyenNganh> chuyenNganhs = new ConcurrentHashMap<>();
        ConcurrentMap<String, MonDatn> nhomMons = new ConcurrentHashMap<>();
        if (!checkTypeFile(file)) {
            return new DtMessMonDatnReponse(false, "File không đúng định dạng XLSX");
        }
        List<DtCreateImportMonDatnRequest> importMonDatnRequests = null;
        try {
            importMonDatnRequests = monDatnImport.importExcelList(file);
            importMonDatnRequests.parallelStream().forEach(createMonDant -> {
                if (createMonDant.getMaMon().trim().length() == 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã không được để trống"));
                    return;
                }
                if (monDatnHashMap.containsKey(createMonDant.getMaMon())) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã " + createMonDant.getMaMon() + " đã tồn tại"));
                    return;
                }

                if (nhomMonDatnHashMap.containsKey(createMonDant.getMaMon())) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã " + createMonDant.getMaMon() + " bị trùng với mã nhóm môn"));
                    return;
                }

                if (createMonDant.getMaMon().trim().length() > 10) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã " + createMonDant.getMaMon() + " không được quá 10 ký tự"));
                    return;
                }
                if (createMonDant.getMaMon().indexOf(" ") > 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Không được có khoảng trắng"));
                    return;
                }
                if (createMonDant.getTenMonDatn().trim().length() == 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn " + createMonDant.getTenMonDatn() + " không được để trống"));
                    return;
                }
                if (createMonDant.getTenMonDatn().trim().length() > 100) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn " + createMonDant.getTenMonDatn() + " không được quá 100 ký tự"));
                    return;
                }
                if (createMonDant.getChuyenNganhId().trim().length() == 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành không được để trống"));
                    return;
                }
                if (createMonDant.getChuyenNganhId().trim().length() > 36) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành " + createMonDant.getChuyenNganhId() + " không được quá 36 ký tự"));
                    return;
                }
                if (createMonDant.getNhomMonDatnId().trim().length() != 0) {
                    if (createMonDant.getNhomMonDatnId().trim().length() > 10) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("E" + (createMonDant.getStt() + 1), "Mã nhóm " + createMonDant.getNhomMonDatnId() + " không được quá 10 ký tự"));
                        return;
                    }
                    if (monDatnHashMap.containsKey(createMonDant.getNhomMonDatnId())) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("E" + (createMonDant.getStt() + 1), "Mã nhóm " + createMonDant.getNhomMonDatnId() + " bị trùng với mã môn"));
                        return;
                    }
                    if (createMonDant.getNhomMonDatnId().indexOf(" ") > 0) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("E" + (createMonDant.getStt() + 1), "Không được có khoảng trắng"));
                        return;
                    }
                }

                if (!chuyenNganhHashMap.containsKey(createMonDant.getChuyenNganhId().toUpperCase())) {
                    ChuyenNganh chuyenNganh = new ChuyenNganh();
                    chuyenNganh.setTenChuyenNganh(createMonDant.getChuyenNganhId());
                    chuyenNganh.setCoSoId(coSoId);
                    chuyenNganhHashMap.put(createMonDant.getChuyenNganhId(), chuyenNganh);
                    chuyenNganhs.put(chuyenNganh.getTenChuyenNganh(), chuyenNganh);
                }

                if (nhomMonDatnHashMap.containsKey(createMonDant.getNhomMonDatnId())
                        && createMonDant.getNhomMonDatnId().trim().length() != 0) {
                    System.out.println(nhomMonDatnHashMap.get(createMonDant.getNhomMonDatnId()).getChuyenNganhId());
                    if (!nhomMonDatnHashMap.get(createMonDant.getNhomMonDatnId()).getChuyenNganhId().equalsIgnoreCase(createMonDant.getChuyenNganhId())) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("E" + (createMonDant.getStt() + 1), "Nhóm môn " + createMonDant.getNhomMonDatnId() + " không trùng chuyên ngành"));
                        return;
                    }
                }

                if (!nhomMonDatnHashMap.containsKey(createMonDant.getNhomMonDatnId())
                        && createMonDant.getNhomMonDatnId().trim().length() != 0) {
                    MonDatn nhomMon = new MonDatn();
                    nhomMon.setMaMon(createMonDant.getNhomMonDatnId());
                    nhomMon.setCoSoId(coSoId);
                    nhomMon.setChuyenNganhId(createMonDant.getChuyenNganhId());
                    nhomMon.setTenMonDatn(createMonDant.getNhomMonDatnId());
                    nhomMon.setNhomMonDatnId(null);
                    nhomMonDatnHashMap.put(nhomMon.getMaMon(), nhomMon);
                    nhomMons.put(nhomMon.getMaMon(), nhomMon);
                }

                MonDatn monDatn = new MonDatn();
                monDatn.setMaMon(createMonDant.getMaMon());
                monDatn.setTenMonDatn(createMonDant.getTenMonDatn());
                monDatn.setCoSoId(coSoId);
                monDatn.setChuyenNganhId(createMonDant.getChuyenNganhId().toUpperCase());
                monDatn.setNhomMonDatnId(createMonDant.getNhomMonDatnId());
                monDatnHashMap.put(monDatn.getMaMon(), monDatn);
                list.put(monDatn.getMaMon(), monDatn);
            });
            if (list.size() != 0 && list.size() == importMonDatnRequests.size()) {

                if (chuyenNganhs.values().size() != 0) {
                    List<ChuyenNganh> chuyenNganhList = chuyenNganhRepository.saveAll(chuyenNganhs.values());
                    chuyenNganhList.parallelStream().forEach(el -> {
                        chuyenNganhHashMap.put(el.getTenChuyenNganh().toUpperCase(), el);
                    });
                }

                if (nhomMons.values().size() != 0) {
                    List<MonDatn> nhoMonDatnList = monDatnRepository.saveAll(nhomMons.values());
                    nhoMonDatnList.parallelStream().forEach(el -> {
                        el.setChuyenNganhId(chuyenNganhHashMap.get(el.getChuyenNganhId()).getId());
                        nhomMonDatnHashMap.put(el.getMaMon(), el);
                        monDatnRepository.save(el);
                    });
                }


                List<MonDatn> datnList = monDatnRepository.saveAll(list.values());
                datnList.parallelStream().forEach(el -> {
                    el.setChuyenNganhId(chuyenNganhHashMap.get(el.getChuyenNganhId()).getId());
                    if (el.getNhomMonDatnId().trim().length() == 0) {
                        el.setNhomMonDatnId(el.getId());
                        monDatnRepository.save(el);
                    } else {
                        el.setNhomMonDatnId(nhomMonDatnHashMap.get(el.getNhomMonDatnId()).getId());
                        monDatnRepository.save(el);
                    }
                });
                mess.setStatus(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new DtMessMonDatnReponse(false, "Lỗi hệ thống");
        }
        return mess;
    }

    private String formErrors(String vtr, String err) {
        return "Lỗi tại vị trí " + vtr + " . Lỗi -> " + err;
    }

    private boolean checkTypeFile(MultipartFile file) {
        String filename = file.getResource().getFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

        String excel = "xlsx";
        if (!extension.equals(excel)) {
            return false;
        }
        return true;
    }

    private void boNhoDem(String coSoId) {
        for (MonDatn x : monDatnRepository.getListMonDatnByIdCoSo(coSoId)) {
            monDatnHashMap.put(x.getMaMon(), x);
        }
        for (ChuyenNganh x : chuyenNganhRepository.getAllByIdCoSo(coSoId)) {
            chuyenNganhHashMap.put(x.getTenChuyenNganh().toUpperCase(), x);
        }
        for (ChuyenNganh x : chuyenNganhRepository.getAllByIdCoSo(coSoId)) {
            chuyenNganhById.put(x.getId(), x);
        }
        for (MonDatn e : monDatnRepository.getListNhomMonDatn(coSoId)) {
            e.setChuyenNganhId(chuyenNganhById.get(e.getChuyenNganhId()).getTenChuyenNganh());
            nhomMonDatnHashMap.put(e.getMaMon(), e);
        }
    }

}
