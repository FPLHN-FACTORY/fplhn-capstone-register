package com.fpolydatn.core.sinhvien.service.impl;

import com.fpolydatn.core.sinhvien.model.response.SvSinhVienExportWordResponse;
import com.fpolydatn.core.sinhvien.repository.SvNhomDatnRepository;
import com.fpolydatn.core.sinhvien.repository.SvSinhVienRepository;
import com.fpolydatn.core.sinhvien.service.SvExportFileWordService;
import com.fpolydatn.entity.ChuyenNganh;
import com.fpolydatn.entity.CoSo;
import com.fpolydatn.entity.DotDangKy;
import com.fpolydatn.entity.GiangVienHuongDan;
import com.fpolydatn.entity.HocKy;
import com.fpolydatn.entity.NhomDatn;
import com.fpolydatn.entity.SinhVien;
import com.fpolydatn.infrastructure.constant.Message;
import com.fpolydatn.infrastructure.constant.TrangThaiNhom;
import com.fpolydatn.infrastructure.exception.rest.RestApiException;
import com.fpolydatn.repository.ChuyenNganhRepository;
import com.fpolydatn.repository.CoSoRepository;
import com.fpolydatn.repository.DotDangKyRepository;
import com.fpolydatn.repository.GiangVienHuongDanRepository;
import com.fpolydatn.repository.HocKyRepository;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabTlc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
public class SvExportFileWordServiceImpl implements SvExportFileWordService {

    @Autowired
    @Qualifier(GiangVienHuongDanRepository.NAME)
    private GiangVienHuongDanRepository giangVienHuongDanRepository;

    @Autowired
    private SvNhomDatnRepository svNhomDatnRepository;

    @Autowired
    private SvSinhVienRepository svSinhVienRepository;

    @Autowired
    @Qualifier(ChuyenNganhRepository.NAME)
    private ChuyenNganhRepository chuyenNganhRepository;

    @Autowired
    @Qualifier(CoSoRepository.NAME)
    private CoSoRepository coSoRepository;

    @Autowired
    @Qualifier(HocKyRepository.NAME)
    private HocKyRepository hocKyRepository;

    @Autowired
    @Qualifier(DotDangKyRepository.NAME)
    private DotDangKyRepository dotDangKyRepository;

    @Override
    public byte[] exportWord(String idSinhVien) {
        Optional<SinhVien> sinhVienFind = svSinhVienRepository.findById(idSinhVien);
        if (!sinhVienFind.isPresent()) {
            throw new RestApiException(Message.SINH_VIEN_NOT_EXISTS);
        }
        if (sinhVienFind.get().getNhomId() == null) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_CO_NHOM);
        }
        Optional<NhomDatn> nhomDatnFind = svNhomDatnRepository.findById(sinhVienFind.get().getNhomId());
        if (!nhomDatnFind.isPresent()) {
            throw new RestApiException(Message.NHOM_DATN_NOT_EXIST);
        }
        if (!sinhVienFind.get().getId().equals(nhomDatnFind.get().getTruongNhomId())) {
            throw new RestApiException(Message.SINH_VIEN_KHONG_PHAI_TRUONG_NHOM);
        }
        if (!nhomDatnFind.get().getTrangThai().equals(TrangThaiNhom.CNBM_CHOT)) {
            throw new RestApiException(Message.TRANG_THAI_KHONG_THOA_MAN);
        }
        return handleExportWord(sinhVienFind.get(), nhomDatnFind.get());
    }

    public byte[] handleExportWord(SinhVien sinhVien, NhomDatn nhomDatn) {
        try {
            Optional<ChuyenNganh> chuyenNganhFind = chuyenNganhRepository.findById(sinhVien.getChuyenNganhId());
            if (!chuyenNganhFind.isPresent()) {
                throw new RestApiException(Message.CHUYEN_NGANH_NOT_EXIST);
            }

            XWPFDocument document = new XWPFDocument();

            XWPFParagraph imageParagraph = document.createParagraph();

            XWPFRun imageRun = imageParagraph.createRun();
            try (InputStream imgStream = getClass().getResourceAsStream("/static/assets/images/logo.png")) {
                imageRun.addPicture(imgStream, XWPFDocument.PICTURE_TYPE_PNG, "image.png", Units.toEMU(130), Units.toEMU(45));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            imageParagraph.setAlignment(ParagraphAlignment.CENTER);

            XWPFParagraph infoParagraphTitle = document.createParagraph();

            XWPFRun infoRunTitle = infoParagraphTitle.createRun();
            infoRunTitle.setText("ĐĂNG KÝ DỰ ÁN TỐT NGHIỆP");
            infoRunTitle.setBold(true);
            infoRunTitle.setFontSize(16);
            infoParagraphTitle.setAlignment(ParagraphAlignment.CENTER);
            infoRunTitle.addBreak();

            Optional<DotDangKy> dotDangKyFind = dotDangKyRepository.findById(sinhVien.getDotDangKyId());
            if (!dotDangKyFind.isPresent()) {
                throw new RestApiException(Message.DOT_DANG_KY_NOT_EXIST);
            }
            Optional<HocKy> hocKyFind = hocKyRepository.findById(dotDangKyFind.get().getHocKyId());
            if (!hocKyFind.isPresent()) {
                throw new RestApiException(Message.HOC_KY_NOT_EXIST);
            }

            XWPFParagraph infoParagraph = document.createParagraph();
            XWPFRun infoRun = infoParagraph.createRun();
            infoRun.setText("Kỳ: " + hocKyFind.get().getTenHocKy() + "       Thời gian:  Từ ... đến ...");
            infoRun.addBreak();
            infoRun.setText("Chuyên ngành: " + chuyenNganhFind.get().getTenChuyenNganh());
            infoRun.addBreak();
            infoRun.setText("Họ tên người đăng ký: " + sinhVien.getTenSinhVien());
            infoRun.addBreak();
            infoRun.setText("Số điện thoại: " + sinhVien.getSoDienThoai() + "                Vai trò trong nhóm: Trưởng nhóm");
            infoRun.addBreak();
            infoRun.setText("Mã sinh viên: " + sinhVien.getMaSinhVien() + ".     Mã số nhóm: " + nhomDatn.getMaNhom());
            infoRun.setFontSize(14);

            XWPFParagraph infoGVHD = document.createParagraph();
            XWPFRun infoRunGVHD = infoGVHD.createRun();
            infoRunGVHD.setText("1. Thông tin giảng viên hướng dẫn:");
            infoRunGVHD.setBold(true);
            infoRunGVHD.setFontSize(14);

            Optional<GiangVienHuongDan> giangVienHuongDanFind = giangVienHuongDanRepository.findById(nhomDatn.getGiangVienId());

            XWPFTable tableGVHD = document.createTable();
            CTTblWidth widthGiangVien = tableGVHD.getCTTbl().addNewTblPr().addNewTblW();
            widthGiangVien.setType(STTblWidth.PCT);
            widthGiangVien.setW(BigInteger.valueOf(5300));

            XWPFTableRow rowGiangVien = tableGVHD.getRow(0);
            rowGiangVien.getCell(0).setText("Họ và tên GVHD");
            rowGiangVien.addNewTableCell().setText("SĐT");
            rowGiangVien.addNewTableCell().setText("E-Mail");
            rowGiangVien.addNewTableCell().setText("Ghi chú");

            rowGiangVien = tableGVHD.createRow();
            rowGiangVien.getCell(0).setText(!giangVienHuongDanFind.isPresent() ? "Chưa có" : giangVienHuongDanFind.get().getTenGvhd());
            rowGiangVien.getCell(1).setText(!giangVienHuongDanFind.isPresent() ? "Chưa có" : giangVienHuongDanFind.get().getSoDienThoai());
            rowGiangVien.getCell(2).setText(!giangVienHuongDanFind.isPresent() ? "Chưa có" : giangVienHuongDanFind.get().getEmailFpt());
            rowGiangVien.getCell(3).setText("");

            setTextAlignCenterAndCellAlignCenterBoth(tableGVHD);

            XWPFParagraph infoSinhVien = document.createParagraph();
            XWPFRun infoRunSinhVien = infoSinhVien.createRun();
            infoRunSinhVien.setText("2. Thông tin sinh viên đăng ký:");
            infoRunSinhVien.setBold(true);
            infoRunSinhVien.setFontSize(14);

            XWPFTable tableSinhVien = document.createTable();
            CTTblWidth widthSinhVien = tableSinhVien.getCTTbl().addNewTblPr().addNewTblW();
            widthSinhVien.setType(STTblWidth.PCT);
            widthSinhVien.setW(BigInteger.valueOf(5300));

            List<SvSinhVienExportWordResponse> listSinhVienTrongNhom = svSinhVienRepository.getThanhVienTheoNhom(nhomDatn.getId(), sinhVien.getCoSoId(), sinhVien.getDotDangKyId());

            XWPFTableRow rowSinhVien = tableSinhVien.getRow(0);
            rowSinhVien.getCell(0).setText("STT");
            rowSinhVien.addNewTableCell().setText("Họ và tên");
            rowSinhVien.addNewTableCell().setText("MSSV");
            rowSinhVien.addNewTableCell().setText("SĐT");
            rowSinhVien.addNewTableCell().setText("E-mail");
            rowSinhVien.addNewTableCell().setText("Chữ ký");

            for (int i = 0; i < listSinhVienTrongNhom.size(); i++) {
                SvSinhVienExportWordResponse inforSinhVien = listSinhVienTrongNhom.get(i);
                rowSinhVien = tableSinhVien.createRow();
                rowSinhVien.getCell(0).setText(i + 1 + "");
                rowSinhVien.getCell(1).setText(inforSinhVien.getTenSinhVien());
                rowSinhVien.getCell(2).setText(inforSinhVien.getMaSinhVien());
                rowSinhVien.getCell(3).setText(inforSinhVien.getSoDienThoai());
                rowSinhVien.getCell(4).setText(inforSinhVien.getEmail());
                rowSinhVien.getCell(5).setText("");
            }

            setTextAlignCenterAndCellAlignCenterBoth(tableSinhVien);

            XWPFParagraph infoNhomDatn = document.createParagraph();
            XWPFRun infoRunNhomDatn = infoNhomDatn.createRun();
            infoRunNhomDatn.setText("3. Đăng ký nội dung chính của Dự án tốt nghiệp:");
            infoRunNhomDatn.setBold(true);
            infoRunNhomDatn.setFontSize(14);

            XWPFParagraph paragraphDATN = document.createParagraph();
            XWPFRun runDATN = paragraphDATN.createRun();

            runDATN.setText("(*) 3.1. Tên Dự án tốt nghiệp");
            runDATN.addBreak();

            runDATN.setText("Tên Tiếng Việt: " + nhomDatn.getTenDeTai());
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("Tên Tiếng Anh:");
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("Viết tắt (nếu có):");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("(*) 3.2. Nội dung chính (Bao gồm kết quả và sản phẩm)  ");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("a) Lý thuyết và thực hành (Tài liệu): ");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("b) Chương trình:");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("c) Các sản phẩm khác:");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();

            runDATN.setText("4. Ý kiến khác (Những đề xuất nếu có)");
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            setTabStop(paragraphDATN, STTabJc.LEFT);
            runDATN.addTab();
            runDATN.addBreak();
            runDATN.setFontSize(13);

            XWPFTable table = document.createTable(1, 2);
            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.PCT);
            width.setW(BigInteger.valueOf(5300));

            table.getCTTbl().getTblPr().unsetTblBorders();

            XWPFTableRow row = table.getRow(0);

            XWPFTableCell cellLeft = row.getCell(0);
            XWPFParagraph paragraphLeft = cellLeft.addParagraph();
            paragraphLeft.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runLeft = paragraphLeft.createRun();
            runLeft.setText("Giảng viên hướng dẫn");
            runLeft.addBreak();
            runLeft.setText("(Ký ghi rõ họ tên)");
            runLeft.addBreak();
            runLeft.addBreak();
            runLeft.addBreak();
            runLeft.addBreak();
            runLeft.setText(giangVienHuongDanFind.get().getTenGvhd());
            runLeft.setFontSize(13);

            XWPFTableCell cellRight = row.getCell(1);
            XWPFParagraph paragraphRight = cellRight.addParagraph();
            paragraphRight.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun runRight = paragraphRight.createRun();

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd 'tháng' MM 'năm' yyyy");
            String formattedDate = currentDate.format(dateFormatter);

            Optional<CoSo> coSoFind = coSoRepository.findById(sinhVien.getCoSoId());
            if (!coSoFind.isPresent()) {
                throw new RestApiException(Message.CO_SO_NOT_EXIST);
            }

            runRight.setText(coSoFind.get().getTenCoSo() + ", ngày " + formattedDate);
            runRight.addBreak();
            runRight.setText("Người đăng ký");
            runRight.addBreak();
            runRight.setText("(Ký, ghi rõ họ tên)");
            runRight.setFontSize(13);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RestApiException("Lỗi trong quá trình xuất file");
        }
    }

    private void setTextAlignCenterAndCellAlignCenterBoth(XWPFTable xwpfTable) {
        for (int rowIndex = 0; rowIndex < xwpfTable.getRows().size(); rowIndex++) {
            XWPFTableRow tableRow = xwpfTable.getRow(rowIndex);

            for (int cellIndex = 0; cellIndex < tableRow.getTableCells().size(); cellIndex++) {
                XWPFTableCell cell = tableRow.getCell(cellIndex);
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                paragraph.setAlignment(ParagraphAlignment.CENTER);

                XWPFRun run;
                if (paragraph.getRuns() != null && paragraph.getRuns().size() > 0) {
                    run = paragraph.getRuns().get(0);
                } else {
                    run = paragraph.createRun();
                }

                run.setFontSize(12);
                if (rowIndex == 0) {
                    run.setColor("000000");
                    run.setBold(true);
                } else {
                    run.setColor("FF0000");
                }
            }
        }
    }

    public static void setTabStop(XWPFParagraph paragraph, STTabJc.Enum tabAlignment) {
        CTP ctp = paragraph.getCTP();
        CTPPr ppr = (ctp.isSetPPr()) ? ctp.getPPr() : ctp.addNewPPr();
        CTTabs tabs = (ppr.isSetTabs()) ? ppr.getTabs() : ppr.addNewTabs();
        CTTabStop tabStop = tabs.addNewTab();
        tabStop.setVal(tabAlignment);
        tabStop.setLeader(STTabTlc.DOT);
        tabStop.setPos(new BigInteger("10000"));
    }

}
