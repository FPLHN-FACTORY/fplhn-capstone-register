package com.fpolydatn_tool;

import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.constant.VaiTro;
import com.fpolydatn.model.entity.CanBoEntity;
import com.fpolydatn.model.entity.ChuyenNganhEntity;
import com.fpolydatn.model.entity.CoSoEntity;
import com.fpolydatn.model.entity.DotDangKyEntity;
import com.fpolydatn.model.entity.GiangVienHuongDanEntity;
import com.fpolydatn.model.entity.HocKyEntity;
import com.fpolydatn.model.entity.MonDatnEntity;
import com.fpolydatn.model.entity.NhomDatnEntity;
import com.fpolydatn.model.entity.PhanCongHuongDanEntity;
import com.fpolydatn.model.entity.SinhVienEntity;
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

    @Override
    public void run(String... args) throws Exception {
        CoSoEntity csHN = new CoSoEntity();
        csHN.setMaCoSo("HN");
        csHN.setTenCoSo("Hà Nội");
        csHN.setId(coSoRepository.save(csHN).getId());

        HocKyEntity su22 = new HocKyEntity();
        su22.setTenHocKy("Summer 2022");
        su22.setId(hocKyRepository.save(su22).getId());

        CanBoEntity yenlh5 = new CanBoEntity();
        yenlh5.setCoSoId(csHN.getId());
        yenlh5.setEmailFe("yenlh5@fe.edu.vn");
        yenlh5.setEmailFpt("yenlh5@fpt.edu.vn");
        yenlh5.setSoDienThoai("0912345678");
        yenlh5.setTenCanBo("Lê Hải Yến");
        yenlh5.setTenTaiKhoan("yenlh5");
        yenlh5.setVaiTro(VaiTro.DAO_TAO);
        yenlh5.setId(canBoRepository.save(yenlh5).getId());
        CanBoEntity minhdq8 = new CanBoEntity();
        minhdq8.setCoSoId(csHN.getId());
        minhdq8.setEmailFe("minhdq8@fe.edu.vn");
        minhdq8.setEmailFpt("minhdq8@fpt.edu.vn");
        minhdq8.setSoDienThoai("0912345678");
        minhdq8.setTenCanBo("Đặng Quang Minh");
        minhdq8.setTenTaiKhoan("minhdq8");
        minhdq8.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
        minhdq8.setId(canBoRepository.save(minhdq8).getId());
        CanBoEntity thienth3 = new CanBoEntity();
        thienth3.setCoSoId(csHN.getId());
        thienth3.setEmailFe("thienth3@fe.edu.vn");
        thienth3.setEmailFpt("thienth3@fpt.edu.vn");
        thienth3.setSoDienThoai("0912345678");
        thienth3.setTenCanBo("Trần Hữu Thiện");
        thienth3.setTenTaiKhoan("thienth3");
        thienth3.setVaiTro(VaiTro.CHU_NHIEM_BO_MON);
        thienth3.setId(canBoRepository.save(thienth3).getId());

        ChuyenNganhEntity ptpm = new ChuyenNganhEntity();
        ptpm.setCoSoId(csHN.getId());
        ptpm.setChuNhiemBoMon(minhdq8.getId());
        ptpm.setTenChuyenNganh("Phát triển phần mềm");
        ptpm.setId(chuyenNganhRepository.save(ptpm).getId());
        ChuyenNganhEntity cntt = new ChuyenNganhEntity();
        cntt.setCoSoId(csHN.getId());
        cntt.setChuNhiemBoMon(thienth3.getId());
        cntt.setTenChuyenNganh("Công nghệ thông tin");
        cntt.setId(chuyenNganhRepository.save(cntt).getId());

        MonDatnEntity java = new MonDatnEntity();
        java.setCoSoId(csHN.getId());
        java.setMaMon("PRO2112");
        java.setChuyenNganhId(ptpm.getId());
        java.setTenMonDatn("Dự án tốt nghiệp (UDPM-Spring Boot)");
        java.setId(monDatnRepository.save(java).getId());
        MonDatnEntity cSharp = new MonDatnEntity();
        cSharp.setCoSoId(csHN.getId());
        cSharp.setMaMon("PRO2113");
        cSharp.setChuyenNganhId(ptpm.getId());
        cSharp.setTenMonDatn("Dự án tốt nghiệp C#");
        cSharp.setId(monDatnRepository.save(cSharp).getId());

        DotDangKyEntity dot1 = new DotDangKyEntity();
        dot1.setCoSoId(csHN.getId());
        dot1.setHocKyId(su22.getId());
        dot1.setNgayBatDau(1652115600000L);
        dot1.setNgayKetThuc(1652979600000L);
        dot1.setTenDotDangKy("Đợt đăng ký kỳ Summer 2022");
        dot1.setId(dotDangKyRepository.save(dot1).getId());

        GiangVienHuongDanEntity tiennh21 = new GiangVienHuongDanEntity();
        tiennh21.setEmailFe("tiennh21@fe.edu.vn");
        tiennh21.setSoDienThoai("09123445678");
        tiennh21.setTenTaiKhoan("tiennh21");
        tiennh21.setEmailFpt("tiennh21@fpt.edu.vn");
        tiennh21.setCoSoId(csHN.getId());
        tiennh21.setDotDangKyId(dot1.getId());
        tiennh21.setSoNhomHuongDanToiDa((short)7);
        tiennh21.setTenTaiKhoan("tiennh21");
        tiennh21.setTenGvhd("Nguyễn Hoàng Tiến");
        tiennh21.setId(giangVienHuongDanRepository.save(tiennh21).getId());

        GiangVienHuongDanEntity dungna29 = new GiangVienHuongDanEntity();
        dungna29.setEmailFe("dungna29@fe.edu.vn");
        dungna29.setSoDienThoai("09123445678");
        dungna29.setTenTaiKhoan("dungna29");
        dungna29.setEmailFpt("dungna29@fpt.edu.vn");
        dungna29.setCoSoId(csHN.getId());
        dungna29.setDotDangKyId(dot1.getId());
        dungna29.setSoNhomHuongDanToiDa((short)5);
        dungna29.setTenTaiKhoan("dungna29");
        dungna29.setTenGvhd("Nguyễn Anh Dũng");
        dungna29.setId(giangVienHuongDanRepository.save(dungna29).getId());

        PhanCongHuongDanEntity tienNHJava = new PhanCongHuongDanEntity();
        tienNHJava.setGiangVienId(tiennh21.getId());
        tienNHJava.setMonDatnId(java.getId());
        phanCongHuongDanRepository.save(tienNHJava);
        PhanCongHuongDanEntity dungNACSharp = new PhanCongHuongDanEntity();
        dungNACSharp.setMonDatnId(cSharp.getId());
        dungNACSharp.setGiangVienId(cSharp.getId());
        phanCongHuongDanRepository.save(dungNACSharp);

        SinhVienEntity phongtt35 =  new SinhVienEntity();
        phongtt35.setChuyenNganhId(ptpm.getId());
        phongtt35.setDotDangKyId(dot1.getId());
        phongtt35.setMonDatnId(java.getId());
        phongtt35.setSoDienThoai("0912345667");
        phongtt35.setCoSoId(csHN.getId());
        phongtt35.setEmail("phongtt35@fpt.edu.vn");
        phongtt35.setKhoa("K12");
        phongtt35.setTenSinhVien("Trần Tuấn Phong");
        phongtt35.setMaSinhVien("PH12345");
        phongtt35.setId(sinhVienRepository.save(phongtt35).getId());

        NhomDatnEntity nhom1 = new NhomDatnEntity();
        nhom1.setMaNhom("HN0001");
        nhom1.setDotDangKyId(dot1.getId());
        nhom1.setGiangVienId(tiennh21.getId());
        nhom1.setTruongNhomId(phongtt35.getId());
        nhom1.setCoSoId(csHN.getId());
        nhom1.setMatKhau("123456");
        nhom1.setTrangThai(TrangThaiNhom.CHUA_CHOT_DE_TAI);
        nhom1.setTenDeTai("Tool Đăng ký DATN");
        nhom1.setTenNhom("Thích cho tạch");
        nhom1.setId(nhomDatnRepository.save(nhom1).getId());

        phongtt35.setNhomId(nhom1.getId());
        sinhVienRepository.flush();
        sinhVienRepository.save(phongtt35);

        SinhVienEntity nguyenvv4 =  new SinhVienEntity();
        nguyenvv4.setChuyenNganhId(ptpm.getId());
        nguyenvv4.setDotDangKyId(dot1.getId());
        nguyenvv4.setMonDatnId(java.getId());
        nguyenvv4.setSoDienThoai("0912345667");
        nguyenvv4.setCoSoId(csHN.getId());
        nguyenvv4.setEmail("nguyenvv4@fpt.edu.vn");
        nguyenvv4.setKhoa("K12");
        nguyenvv4.setTenSinhVien("Vũ Văn Nguyên");
        nguyenvv4.setMaSinhVien("PH23456");
        nguyenvv4.setNhomId(nhom1.getId());
        nguyenvv4.setId(sinhVienRepository.save(nguyenvv4).getId());

        SinhVienEntity hangnt169 =  new SinhVienEntity();
        hangnt169.setChuyenNganhId(ptpm.getId());
        hangnt169.setDotDangKyId(dot1.getId());
        hangnt169.setMonDatnId(java.getId());
        hangnt169.setSoDienThoai("0912345667");
        hangnt169.setCoSoId(csHN.getId());
        hangnt169.setEmail("hangnt169@fpt.edu.vn");
        hangnt169.setKhoa("K12");
        hangnt169.setTenSinhVien("Nguyễn Thuý Hằng");
        hangnt169.setMaSinhVien("PH34567");
        hangnt169.setNhomId(nhom1.getId());
        hangnt169.setId(sinhVienRepository.save(hangnt169).getId());

        SinhVienEntity khanhpg =  new SinhVienEntity();
        khanhpg.setChuyenNganhId(ptpm.getId());
        khanhpg.setDotDangKyId(dot1.getId());
        khanhpg.setMonDatnId(cSharp.getId());
        khanhpg.setSoDienThoai("0912345667");
        khanhpg.setCoSoId(csHN.getId());
        khanhpg.setEmail("khanhpg@fpt.edu.vn");
        khanhpg.setKhoa("K12");
        khanhpg.setTenSinhVien("Phạm Gia Khánh");
        khanhpg.setMaSinhVien("PH45678");
        khanhpg.setNhomId(null);
        khanhpg.setId(sinhVienRepository.save(khanhpg).getId());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DBGenerator.class);
        ctx.close();
    }

}
