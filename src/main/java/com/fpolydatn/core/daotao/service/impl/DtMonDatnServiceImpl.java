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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    private HashMap<String, MonDatn> monDatnHashMap;

    private HashMap<String, ChuyenNganh> chuyenNganhHashMap;

    private HashMap<String, MonDatn> nhomMonDatnHashMap;

    private DTMonDatnImport monDatnImport;

    @Override
    public PageableObject<DtMonDatnResponse> search(@Valid final DtFindMonDatnRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<DtMonDatnResponse> res = monDatnRepository.findByName(req, pageable);
        return new PageableObject(res);
    }

    @Override
    public Boolean delete(final String id, String coSoId, String dotDangKyId) {
        Optional<MonDatn> monCheck = monDatnRepository.findById(id);
        List<MonDatn> checkIdMon = monDatnRepository.findByIdMon(id, coSoId, dotDangKyId);
        if (Objects.isNull(monCheck)) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        if (checkIdMon.size() != 0) {
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
    public List<MonDatn> findByTenNhomMon() {
        return monDatnRepository.findByTenNhomMon();
    }

    @Override
    public MonDatn update(@Valid final DtUpdateMonDatnRequest command) {
        Optional<MonDatn> monDatnCheck = monDatnRepository.findById(command.getId());
        MonDatn monDatnTr = formUtils.convertToObject(MonDatn.class, command);
        String monDatnInDb = monDatnRepository.findByMaMonUpdate(monDatnTr.getMaMon(), monDatnTr.getId(), monDatnTr.getCoSoId());
        if (!monDatnCheck.isPresent()) {
            throw new RestApiException(Message.MON_DATN_NOT_EXIST);
        }
        if (!Objects.isNull(monDatnInDb)) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        }
        MonDatn monDatn = monDatnCheck.get();
        monDatn.setMaMon(command.getMaMon());
        monDatn.setTenMonDatn(command.getTenMonDatn());
        return monDatnRepository.save(monDatn);
    }

    @Override
    public Boolean add(@Valid final DtCreateMonDatnRequest command) {
        MonDatn monDatn = formUtils.convertToObject(MonDatn.class, command);
        MonDatn monDatnInDb = monDatnRepository.findByMaMon(monDatn.getMaMon());
        if (!Objects.isNull(monDatnInDb)) {
            throw new RestApiException(Message.MON_DATN_ALREADY_EXIST);
        } else {
            MonDatn mon = monDatnRepository.save(monDatn);
            if (mon.getNhomMonDatnId().trim().length() == 0) {
                mon.setNhomMonDatnId(mon.getId());
                monDatnRepository.save(mon);
            }
            return true;
        }
    }

    @Override
    public DtMessMonDatnReponse importExcel(MultipartFile file, String coSoId) {
        monDatnHashMap = new HashMap<>();
        chuyenNganhHashMap = new HashMap<>();
        nhomMonDatnHashMap = new HashMap<>();
        monDatnImport = new DTMonDatnImport();
        boNhoDem(coSoId);

        DtMessMonDatnReponse mess = new DtMessMonDatnReponse();
        List<MonDatn> list = new ArrayList<>();
        if (!checkTypeFile(file)) {
            return new DtMessMonDatnReponse(false, "file không đúng định dạng XLSX");
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
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã đã tồn tại"));
                    return;
                }
                if (createMonDant.getMaMon().trim().length() > 10) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Mã không được quá 10 ký tự"));
                    return;

                }
                if (createMonDant.getMaMon().indexOf(" ") > 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("B" + (createMonDant.getStt() + 1), "Không được để khoảng trắng"));
                    return;
                }

                if (createMonDant.getTenMonDatn().trim().length() == 0) {
                    System.out.println(createMonDant.getMaMon());
                    mess.setStatus(false);
                    mess.setMessage(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn không được để trống"));
                    return;
                }
                if (createMonDant.getTenMonDatn().trim().length() > 100) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("C" + (createMonDant.getStt() + 1), "Tên môn không được quá 100 ký tự"));
                    return;
                }

                if (createMonDant.getChuyenNganhId().trim().length() == 0) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành không được để trống"));
                    return;
                }
                if (!chuyenNganhHashMap.containsKey(createMonDant.getChuyenNganhId())) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("D" + (createMonDant.getStt() + 1), "Không tìm thấy tên chuyên ngành"));
                    return;
                }
                if (createMonDant.getChuyenNganhId().trim().length() > 36) {
                    mess.setStatus(false);
                    mess.setMessage(formErrors("D" + (createMonDant.getStt() + 1), "Tên chuyên ngành không được quá 36 ký tự"));
                    return;
                }

                if (createMonDant.getNhomMonDatnId().trim().length() != 0) {
                    if (createMonDant.getNhomMonDatnId().trim().length() > 10) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("F" + (createMonDant.getStt() + 1), "Mã nhóm không được quá 10 ký tự"));
                        return;
                    }
                    if (!nhomMonDatnHashMap.containsKey(createMonDant.getNhomMonDatnId()) && !createMonDant.getMaMon().equals(createMonDant.getNhomMonDatnId())) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("F" + (createMonDant.getStt() + 1), "Không tìm thấy mã nhóm môn"));
                        return;
                    }
                    if (createMonDant.getNhomMonDatnId().indexOf(" ") > 0) {
                        mess.setStatus(false);
                        mess.setMessage(formErrors("F" + (createMonDant.getStt() + 1), "Không được để khoảng trắng"));
                        return;
                    }
                }

                MonDatn monDatn = new MonDatn();
                monDatn.setMaMon(createMonDant.getMaMon());
                monDatn.setTenMonDatn(createMonDant.getTenMonDatn());
                monDatn.setCoSoId(coSoId);
                monDatn.setChuyenNganhId(chuyenNganhHashMap.get(createMonDant.getChuyenNganhId()).getId());
                if (createMonDant.getNhomMonDatnId().trim().length() == 0) {
                    monDatn.setNhomMonDatnId(createMonDant.getMaMon());
                } else {
                    monDatn.setNhomMonDatnId(nhomMonDatnHashMap.get(createMonDant.getNhomMonDatnId()).getId());
                }
                monDatnHashMap.put(monDatn.getMaMon(), monDatn);
                list.add(monDatn);
            });

            if (list.size() != 0 && list.size() == importMonDatnRequests.size()) {
                mess.setStatus(true);
                List<MonDatn> datnList = monDatnRepository.saveAll(list);
                datnList.parallelStream().forEach(el -> {
                    if (el.getMaMon().equalsIgnoreCase(el.getNhomMonDatnId())) {
                        el.setNhomMonDatnId(el.getId());
                        monDatnRepository.save(el);
                    }
                });
            }

        } catch (Exception e) {
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
            chuyenNganhHashMap.put(x.getTenChuyenNganh(), x);
        }

        for (MonDatn e : monDatnRepository.getListNhomMonDatn(coSoId)) {
            nhomMonDatnHashMap.put(e.getMaMon(), e);
        }

    }


}
