package com.fpolydatn.core.chunhiem.controller;

import com.fpolydatn.core.chunhiem.service.CnSinhVienTheoNhomService;
import com.fpolydatn.core.common.base.BaseController;
import com.fpolydatn.core.common.base.ResponseObject;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chu-nhiem/sinh-vien")
public class CnSinhVienTheoNhomRestController extends BaseController {
    @Autowired
    private CnSinhVienTheoNhomService sinhVienTheoNhomService;

    @DeleteMapping("/{maSinhVien}")
    public ResponseObject updateSinhVienByNhom(@PathVariable("maSinhVien") String maSinhVien) {
        Boolean remove = false;
        try {
            remove = sinhVienTheoNhomService.deleteFromIdSinhVien(maSinhVien);
            return new ResponseObject(remove);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
    @PostMapping("/addSinhVien")
    public ResponseObject addSinhVienByNhom(@RequestParam("id") String idNhom, @RequestParam("maSinhVien") String maSinhVien) {
        boolean add = false;
        try {
            add = sinhVienTheoNhomService.addSinhVienTheoNhom(idNhom, maSinhVien);
            return new ResponseObject(add);
        } catch (Exception e) {
            return new ResponseObject(e);
        }
    }
    @PostMapping("/addGvhd")
    public ResponseObject addGvhd(@RequestParam("giangVienId")String giangVienId, @RequestParam("idNhom")String idNhom){
        boolean add = false;
        try {
            add = sinhVienTheoNhomService.addGvhd(giangVienId,idNhom);
            return new ResponseObject(true);
        }catch (Exception e){
            return  new ResponseObject(e);
        }
    }

    @PutMapping("/changeCaptain")
    public ResponseObject changeCaptain(@RequestParam("idCaptain")String idCaptain,@RequestParam("idGroup")String idGroup){
        boolean update = false;
        try {
            update = sinhVienTheoNhomService.changeCaptain(idCaptain,idGroup);
            return new ResponseObject(update);
        }catch (Exception e){
            return new ResponseObject(e);
        }
    }

    @PutMapping("/changeCaptainToOther")
    public ResponseObject changeCaptainToOther( @RequestParam("idCaptain")String idCaptain, @RequestParam("idGroup")String idGroup){
        boolean update = false;
        try {
            update = sinhVienTheoNhomService.changeCaptainToOther(idCaptain,idGroup);

            return new ResponseObject(update);
        }catch (Exception e){
            return new ResponseObject(e);
        }
    }

    @GetMapping("/listStudentNotCaptain")
    public ResponseObject getListStudentNotCaptain(@RequestParam("idGroup")String idGroup){
        SinhVien captain = sinhVienTheoNhomService.getOldCaptain(idGroup);
        return new ResponseObject(sinhVienTheoNhomService.getStudentNotCaptain(captain.getId(),idGroup));
    }
    @GetMapping("/oldCaptain")
    public ResponseObject getOldCaptain(@RequestParam("idGroup")String idGroup){
        return new ResponseObject(sinhVienTheoNhomService.getOldCaptain(idGroup));
    }
    @GetMapping("/tickCaptain")
    public ResponseObject getTickCaptain(@RequestParam("maSinhVien")String maSinhVien,@RequestParam("idGroup")String idGroup){
        return new ResponseObject(sinhVienTheoNhomService.tickCapTain(maSinhVien, idGroup));
    }
}
