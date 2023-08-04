package com.fpolydatn_tool;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.entity.CanBo;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.MonDatn;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.PhanCongHuongDan;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.repository.CanBoRepository;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.HocKyRepository;
import com.fpolydatn.repository.MonDatnRepository;
import com.fpolydatn.repository.NhomDatnRepository;
import com.fpolydatn.repository.PhanCongHuongDanRepository;
import com.fpolydatn.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

/**
 * @author phongtt35
 */

@SpringBootApplication
@EnableJpaRepositories(
        basePackages = "com.fpolydatn.repository"
)
public class DBGenerator implements CommandLineRunner {

    @Autowired
    private CoSoRepository coSoRepository;
    @Autowired
    private HocKyRepository hocKyRepository;
    @Autowired
    private CanBoRepository canBoRepository;
    @Autowired
    private ChuyenNganhRepository chuyenNganhRepository;
    @Autowired
    private MonDatnRepository monDatnRepository;
    @Autowired
    private DotDangKyRepository dotDangKyRepository;
    @Autowired
    private GiangVienHuongDanRepository giangVienHuongDanRepository;
    @Autowired
    private PhanCongHuongDanRepository phanCongHuongDanRepository;
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private NhomDatnRepository nhomDatnRepository;

    private final boolean IS_RELEASE = false;

    @Override
    public void run(String... args) throws Exception {
        HocKy fa22 = new HocKy();
        fa22.setTenHocKy("Fall 2022");
        fa22.setNgayBatDau(1668103963000L);
        fa22.setNgayKetThuc(1671646363000L);
        fa22.setId(hocKyRepository.save(fa22).getId());

        CoSo csHN = new CoSo();
        csHN.setMaCoSo("HN");
        csHN.setTenCoSo("Hà Nội");
        csHN.setId(coSoRepository.save(csHN).getId());
        CanBo yenlh5 = new CanBo();
        yenlh5.setCoSoId(csHN.getId());
        yenlh5.setEmailFe("yenlh5@fe.edu.vn");
        yenlh5.setEmailFpt("yenlh@fpt.edu.vn");
        yenlh5.setSoDienThoai("0912345678");
        yenlh5.setTenCanBo("Lê Hải Yến");
        yenlh5.setTenTaiKhoan("yenlh5");
        yenlh5.setVaiTro(VaiTro.DAO_TAO);
        yenlh5.setId(canBoRepository.save(yenlh5).getId());
        CanBo huongnt166 = new CanBo();
        huongnt166.setCoSoId(csHN.getId());
        huongnt166.setEmailFe("huongnt166@fe.edu.vn");
        huongnt166.setEmailFpt("huongnt166@fpt.edu.vn");
        huongnt166.setSoDienThoai("0912345678");
        huongnt166.setTenCanBo("Nguyễn Thị Hương");
        huongnt166.setTenTaiKhoan("huongnt166");
        huongnt166.setVaiTro(VaiTro.DAO_TAO);
        huongnt166.setId(canBoRepository.save(huongnt166).getId());
        CanBo quyetnv19 = new CanBo();
        quyetnv19.setCoSoId(csHN.getId());
        quyetnv19.setEmailFe("quyetnv19@fe.edu.vn");
        quyetnv19.setEmailFpt("quyetnv19@fpt.edu.vn");
        quyetnv19.setSoDienThoai("0912345678");
        quyetnv19.setTenCanBo("Nguyễn Văn Quyết");
        quyetnv19.setTenTaiKhoan("quyetnv19");
        quyetnv19.setVaiTro(VaiTro.DAO_TAO);
        quyetnv19.setId(canBoRepository.save(quyetnv19).getId());

        CanBo cnbm = new CanBo();
        cnbm.setCoSoId(csHN.getId());
        cnbm.setEmailFe("chunhiem.fcr@gmail.com");
        cnbm.setEmailFpt("chunhiem.fcr@gmail.com");
        cnbm.setSoDienThoai("0912345678");
        cnbm.setTenCanBo("Chủ nhiệm");
        cnbm.setTenTaiKhoan("chunhiem");
        cnbm.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
        cnbm.setId(canBoRepository.save(cnbm).getId());

        CanBo daotao = new CanBo();
        daotao.setCoSoId(csHN.getId());
        daotao.setEmailFe("daotao.fcr@gmail.com");
        daotao.setEmailFpt("daotao.fcr@gmail.com");
        daotao.setSoDienThoai("0912345678");
        daotao.setTenCanBo("Đào tạo");
        daotao.setTenTaiKhoan("daotao");
        daotao.setVaiTro(VaiTro.DAO_TAO);
        daotao.setId(canBoRepository.save(daotao).getId());

        CoSo csHCM = new CoSo();
        csHCM.setMaCoSo("HCM");
        csHCM.setTenCoSo("Hồ Chí Minh");
        csHCM.setId(coSoRepository.save(csHCM).getId());
        CanBo nhulh = new CanBo();
        nhulh.setCoSoId(csHCM.getId());
        nhulh.setEmailFe("nhulh@fe.edu.vn");
        nhulh.setEmailFpt("nhulh@fpt.edu.vn");
        nhulh.setSoDienThoai("0912345678");
        nhulh.setTenCanBo("nhulh");
        nhulh.setTenTaiKhoan("nhulh");
        nhulh.setVaiTro(VaiTro.DAO_TAO);
        nhulh.setId(canBoRepository.save(nhulh).getId());
        CanBo nhuntq20 = new CanBo();
        nhuntq20.setCoSoId(csHCM.getId());
        nhuntq20.setEmailFe("nhuntq20@fe.edu.vn");
        nhuntq20.setEmailFpt("nhuntq20@fpt.edu.vn");
        nhuntq20.setSoDienThoai("0912345678");
        nhuntq20.setTenCanBo("nhuntq20");
        nhuntq20.setTenTaiKhoan("nhuntq20");
        nhuntq20.setVaiTro(VaiTro.DAO_TAO);
        nhuntq20.setId(canBoRepository.save(nhuntq20).getId());

        CoSo csCT = new CoSo();
        csCT.setMaCoSo("CT");
        csCT.setTenCoSo("Cần Thơ");
        csCT.setId(coSoRepository.save(csCT).getId());
        CanBo nguyenpnx = new CanBo();
        nguyenpnx.setCoSoId(csCT.getId());
        nguyenpnx.setEmailFe("nguyenpnx@fe.edu.vn");
        nguyenpnx.setEmailFpt("nguyenpnx@fpt.edu.vn");
        nguyenpnx.setSoDienThoai("0912345678");
        nguyenpnx.setTenCanBo("nguyenpnx");
        nguyenpnx.setTenTaiKhoan("nguyenpnx");
        nguyenpnx.setVaiTro(VaiTro.DAO_TAO);
        nguyenpnx.setId(canBoRepository.save(nguyenpnx).getId());
        CanBo hungtm26 = new CanBo();
        hungtm26.setCoSoId(csCT.getId());
        hungtm26.setEmailFe("hungtm26@fe.edu.vn");
        hungtm26.setEmailFpt("hungtm26@fpt.edu.vn");
        hungtm26.setSoDienThoai("0912345678");
        hungtm26.setTenCanBo("hungtm26");
        hungtm26.setTenTaiKhoan("hungtm26");
        hungtm26.setVaiTro(VaiTro.DAO_TAO);
        hungtm26.setId(canBoRepository.save(hungtm26).getId());
        CanBo khoata17 = new CanBo();
        khoata17.setCoSoId(csCT.getId());
        khoata17.setEmailFe("khoata17@fe.edu.vn");
        khoata17.setEmailFpt("khoata17@fpt.edu.vn");
        khoata17.setSoDienThoai("0912345678");
        khoata17.setTenCanBo("khoata17");
        khoata17.setTenTaiKhoan("khoata17");
        khoata17.setVaiTro(VaiTro.DAO_TAO);
        khoata17.setId(canBoRepository.save(khoata17).getId());

        CoSo csDN = new CoSo();
        csDN.setMaCoSo("DN");
        csDN.setTenCoSo("Đà Nẵng");
        csDN.setId(coSoRepository.save(csDN).getId());
        CanBo tronglt = new CanBo();
        tronglt.setCoSoId(csDN.getId());
        tronglt.setEmailFe("tronglt@fe.edu.vn");
        tronglt.setEmailFpt("tronglt@fpt.edu.vn");
        tronglt.setSoDienThoai("0912345678");
        tronglt.setTenCanBo("tronglt");
        tronglt.setTenTaiKhoan("tronglt");
        tronglt.setVaiTro(VaiTro.DAO_TAO);
        tronglt.setId(canBoRepository.save(tronglt).getId());
        CanBo thaoltb6 = new CanBo();
        thaoltb6.setCoSoId(csDN.getId());
        thaoltb6.setEmailFe("thaoltb6@fe.edu.vn");
        thaoltb6.setEmailFpt("thaoltb6@fpt.edu.vn");
        thaoltb6.setSoDienThoai("0912345678");
        thaoltb6.setTenCanBo("thaoltb6");
        thaoltb6.setTenTaiKhoan("thaoltb6");
        thaoltb6.setVaiTro(VaiTro.DAO_TAO);
        thaoltb6.setId(canBoRepository.save(thaoltb6).getId());

        CoSo csTN = new CoSo();
        csTN.setMaCoSo("TN");
        csTN.setTenCoSo("Tây Nguyên");
        csTN.setId(coSoRepository.save(csTN).getId());
        CanBo HoaLT48 = new CanBo();
        HoaLT48.setCoSoId(csTN.getId());
        HoaLT48.setEmailFe("HoaLT48@fe.edu.vn");
        HoaLT48.setEmailFpt("HoaLT48@fpt.edu.vn");
        HoaLT48.setSoDienThoai("0912345678");
        HoaLT48.setTenCanBo("HoaLT48");
        HoaLT48.setTenTaiKhoan("HoaLT48");
        HoaLT48.setVaiTro(VaiTro.DAO_TAO);
        HoaLT48.setId(canBoRepository.save(HoaLT48).getId());
        CanBo LinhNTT66 = new CanBo();
        LinhNTT66.setCoSoId(csTN.getId());
        LinhNTT66.setEmailFe("LinhNTT66@fe.edu.vn");
        LinhNTT66.setEmailFpt("LinhNTT66@fpt.edu.vn");
        LinhNTT66.setSoDienThoai("0912345678");
        LinhNTT66.setTenCanBo("LinhNTT66");
        LinhNTT66.setTenTaiKhoan("LinhNTT66");
        LinhNTT66.setVaiTro(VaiTro.DAO_TAO);
        LinhNTT66.setId(canBoRepository.save(LinhNTT66).getId());

        if (IS_RELEASE) return;

        CanBo minhdq8 = new CanBo();
        minhdq8.setCoSoId(csHN.getId());
        minhdq8.setEmailFe("minhdq8@fe.edu.vn");
        minhdq8.setEmailFpt("minhdq8@fpt.edu.vn");
        minhdq8.setSoDienThoai("0912345678");
        minhdq8.setTenCanBo("Đặng Quang Minh");
        minhdq8.setTenTaiKhoan("minhdq8");
        minhdq8.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
        minhdq8.setId(canBoRepository.save(minhdq8).getId());
        CanBo thienth3 = new CanBo();
        thienth3.setCoSoId(csHN.getId());
        thienth3.setEmailFe("thienth3@fe.edu.vn");
        thienth3.setEmailFpt("thienth3@fpt.edu.vn");
        thienth3.setSoDienThoai("0912345678");
        thienth3.setTenCanBo("Trần Hữu Thiện");
        thienth3.setTenTaiKhoan("thienth3");
        thienth3.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
        thienth3.setId(canBoRepository.save(thienth3).getId());

        ChuyenNganh ptpm = new ChuyenNganh();
        ptpm.setCoSoId(csHN.getId());
        ptpm.setChuNhiemBoMon(cnbm.getId());
        ptpm.setTenChuyenNganh("Phát triển phần mềm");
        ptpm.setId(chuyenNganhRepository.save(ptpm).getId());
        ChuyenNganh cntt = new ChuyenNganh();
        cntt.setCoSoId(csHN.getId());
        cntt.setChuNhiemBoMon(thienth3.getId());
        cntt.setTenChuyenNganh("Công nghệ thông tin");
        cntt.setId(chuyenNganhRepository.save(cntt).getId());

        MonDatn monPtpm = new MonDatn();
        monPtpm.setCoSoId(csHN.getId());
        monPtpm.setMaMon("PRO");
        monPtpm.setChuyenNganhId(ptpm.getId());
        monPtpm.setTenMonDatn("Môn dự án tốt nghiệp PTPM");
        monPtpm.setId(monDatnRepository.save(monPtpm).getId());
        MonDatn java = new MonDatn();
        java.setCoSoId(csHN.getId());
        java.setMaMon("PRO2112");
        java.setChuyenNganhId(ptpm.getId());
        java.setNhomMonDatnId(monPtpm.getId());
        java.setTenMonDatn("Dự án tốt nghiệp (UDPM-Spring Boot)");
        java.setId(monDatnRepository.save(java).getId());
        MonDatn cSharp = new MonDatn();
        cSharp.setCoSoId(csHN.getId());
        cSharp.setMaMon("PRO2113");
        cSharp.setChuyenNganhId(ptpm.getId());
        cSharp.setNhomMonDatnId(monPtpm.getId());
        cSharp.setTenMonDatn("Dự án tốt nghiệp C#");
        cSharp.setId(monDatnRepository.save(cSharp).getId());

        DotDangKy dot1 = new DotDangKy();
        dot1.setCoSoId(csHN.getId());
        dot1.setHocKyId(fa22.getId());
        Date now = new Date();
        now.setSeconds(0);
        dot1.setNgayBatDau(now.getTime());
        dot1.setHanSinhVien(dot1.getNgayBatDau() + 1296000000L);
        dot1.setHanGiangVien(dot1.getNgayBatDau() + 1900800000L);
        dot1.setHanChuNhiemBoMon(dot1.getNgayBatDau() + 2629800000L);
        dot1.setNgayKetThuc(dot1.getNgayBatDau() + 2629800000L);
        dot1.setTenDotDangKy("Đợt đăng ký kỳ 11");
        dot1.setId(dotDangKyRepository.save(dot1).getId());

        GiangVienHuongDan tiennh21 = new GiangVienHuongDan();
        tiennh21.setEmailFe("tiennh21@fe.edu.vn");
        tiennh21.setSoDienThoai("0912344567");
        tiennh21.setTenTaiKhoan("tiennh21");
        tiennh21.setEmailFpt("tiennh21@fpt.edu.vn");
        tiennh21.setCoSoId(csHN.getId());
        tiennh21.setDotDangKyId(dot1.getId());
        tiennh21.setSoNhomHuongDanToiDa((short)7);
        tiennh21.setTenTaiKhoan("tiennh21");
        tiennh21.setTenGvhd("Nguyễn Hoàng Tiến");
        tiennh21.setId(giangVienHuongDanRepository.save(tiennh21).getId());

        GiangVienHuongDan dungna29 = new GiangVienHuongDan();
        dungna29.setEmailFe("dungna29@fe.edu.vn");
        dungna29.setSoDienThoai("0912344567");
        dungna29.setTenTaiKhoan("dungna29");
        dungna29.setEmailFpt("dungna29@fpt.edu.vn");
        dungna29.setCoSoId(csHN.getId());
        dungna29.setDotDangKyId(dot1.getId());
        dungna29.setSoNhomHuongDanToiDa((short)5);
        dungna29.setTenGvhd("Nguyễn Anh Dũng");
        dungna29.setId(giangVienHuongDanRepository.save(dungna29).getId());

        GiangVienHuongDan gvhd = new GiangVienHuongDan();
        gvhd.setEmailFe("giangvien.fcr@gmail.com");
        gvhd.setSoDienThoai("0912344567");
        gvhd.setTenTaiKhoan("giangvien");
        gvhd.setEmailFpt("giangvien.fcr@gmail.com");
        gvhd.setCoSoId(csHN.getId());
        gvhd.setDotDangKyId(dot1.getId());
        gvhd.setSoNhomHuongDanToiDa((short)7);
        gvhd.setTenGvhd("Giảng Viên");
        gvhd.setId(giangVienHuongDanRepository.save(gvhd).getId());

        PhanCongHuongDan tienNHJava = new PhanCongHuongDan();
        tienNHJava.setGiangVienId(tiennh21.getId());
        tienNHJava.setMonDatnId(java.getId());
        phanCongHuongDanRepository.save(tienNHJava);
        PhanCongHuongDan dungNACSharp = new PhanCongHuongDan();
        dungNACSharp.setMonDatnId(cSharp.getId());
        dungNACSharp.setGiangVienId(dungna29.getId());
        phanCongHuongDanRepository.save(dungNACSharp);
        PhanCongHuongDan gvhdJava = new PhanCongHuongDan();
        gvhdJava.setMonDatnId(java.getId());
        gvhdJava.setGiangVienId(gvhd.getId());
        phanCongHuongDanRepository.save(gvhdJava);
        PhanCongHuongDan gvhdCS = new PhanCongHuongDan();
        gvhdCS.setMonDatnId(cSharp.getId());
        gvhdCS.setGiangVienId(gvhd.getId());
        phanCongHuongDanRepository.save(gvhdCS);

        SinhVien phongtt35 =  new SinhVien();
        phongtt35.setChuyenNganhId(ptpm.getId());
        phongtt35.setDotDangKyId(dot1.getId());
        phongtt35.setMonChuongTrinhId(java.getId());
        phongtt35.setSoDienThoai("0912345667");
        phongtt35.setCoSoId(csHN.getId());
        phongtt35.setEmail("phongtt35@fpt.edu.vn");
        phongtt35.setKhoa("K12");
        phongtt35.setTenSinhVien("Trần Tuấn Phong");
        phongtt35.setMaSinhVien("PH12345");
        phongtt35.setMonDatnId(java.getId());
        phongtt35.setId(sinhVienRepository.save(phongtt35).getId());

        NhomDatn nhom1 = new NhomDatn();
        nhom1.setMaNhom("HN_1");
        nhom1.setDotDangKyId(dot1.getId());
        nhom1.setTruongNhomId(phongtt35.getId());
        nhom1.setCoSoId(csHN.getId());
        nhom1.setMatKhau("123456");
        nhom1.setTrangThai(TrangThaiNhom.MOI_THANH_LAP);
        nhom1.setTenDeTai("Tool Đăng ký DATN");
        nhom1.setTenNhom("Thích cho tạch");
        nhom1.setId(nhomDatnRepository.save(nhom1).getId());

        phongtt35.setNhomId(nhom1.getId());
        sinhVienRepository.flush();
        sinhVienRepository.save(phongtt35);

        SinhVien nguyenvv4 =  new SinhVien();
        nguyenvv4.setChuyenNganhId(ptpm.getId());
        nguyenvv4.setDotDangKyId(dot1.getId());
        nguyenvv4.setMonChuongTrinhId(java.getId());
        nguyenvv4.setSoDienThoai("0912345667");
        nguyenvv4.setCoSoId(csHN.getId());
        nguyenvv4.setEmail("nguyenvv4@fpt.edu.vn");
        nguyenvv4.setKhoa("K12");
        nguyenvv4.setTenSinhVien("Vũ Văn Nguyên");
        nguyenvv4.setMaSinhVien("PH23456");
        nguyenvv4.setNhomId(nhom1.getId());
        nguyenvv4.setMonDatnId(java.getId());
        nguyenvv4.setId(sinhVienRepository.save(nguyenvv4).getId());

        SinhVien hangnt169 =  new SinhVien();
        hangnt169.setChuyenNganhId(ptpm.getId());
        hangnt169.setDotDangKyId(dot1.getId());
        hangnt169.setMonChuongTrinhId(java.getId());
        hangnt169.setSoDienThoai("0912345667");
        hangnt169.setCoSoId(csHN.getId());
        hangnt169.setEmail("hangnt169@fpt.edu.vn");
        hangnt169.setKhoa("K12");
        hangnt169.setTenSinhVien("Nguyễn Thuý Hằng");
        hangnt169.setMaSinhVien("PH34567");
        hangnt169.setNhomId(nhom1.getId());
        hangnt169.setMonDatnId(java.getId());
        hangnt169.setId(sinhVienRepository.save(hangnt169).getId());

        SinhVien khanhpg =  new SinhVien();
        khanhpg.setChuyenNganhId(ptpm.getId());
        khanhpg.setDotDangKyId(dot1.getId());
        khanhpg.setMonChuongTrinhId(cSharp.getId());
        khanhpg.setSoDienThoai("0912345667");
        khanhpg.setCoSoId(csHN.getId());
        khanhpg.setEmail("khanhpg@fpt.edu.vn");
        khanhpg.setKhoa("K12");
        khanhpg.setTenSinhVien("Phạm Gia Khánh");
        khanhpg.setMaSinhVien("PH45678");
        khanhpg.setNhomId(null);
        khanhpg.setId(sinhVienRepository.save(khanhpg).getId());

        SinhVien sinhVien =  new SinhVien();
        sinhVien.setChuyenNganhId(ptpm.getId());
        sinhVien.setDotDangKyId(dot1.getId());
        sinhVien.setMonChuongTrinhId(cSharp.getId());
        sinhVien.setSoDienThoai("0912345667");
        sinhVien.setCoSoId(csHN.getId());
        sinhVien.setEmail("sinhvien.fcr@gmail.com");
        sinhVien.setKhoa("K12");
        sinhVien.setTenSinhVien("Sinh Viên");
        sinhVien.setMaSinhVien("PH00001");
        sinhVien.setNhomId(null);
        sinhVien.setId(sinhVienRepository.save(sinhVien).getId());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}