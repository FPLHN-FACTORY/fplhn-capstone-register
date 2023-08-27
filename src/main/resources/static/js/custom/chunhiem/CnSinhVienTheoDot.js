const sinhVienApi = '/api/chu-nhiem/dot-dang-ky/sinh-vien';
const sinhVienTheoDotDangKyView = '/chu-nhiem/dot-dang-ky/sinh-vien';
const CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function openModalAddSinhVien(maSinhVien, count) {
    $("#idNhom_error").html('')
    $("#modal_create_nhom").modal('show');
    $("#addSinhVienTheoNhom").val(maSinhVien);
    $("#currentPaging").val(count)
}

function clearBoLoc() {
    $("#tenSinhVien").val("")
    $("#dotDangKyId").val("")
    $("#monChuongTrinh").val("")
    $("#monDatn").val("")
    $("#tenGvhd").val("")
    $("#maNhomSearch").val("")
    $("#trangThai").val("")
}

function openModalThemSinhVienVaoNhom() {
    $("#modal_create_nhom").modal('show')
}

function createSinhVienVaoNhom() {
    let maNhom = $("#maNhom").val().trim();
    const parts = maNhom.split("_");
    let desiredResult = ""
    if (parts.length >= 2) {
        desiredResult = parts.slice(0, 2).join("_");
    } else {
        $("#idNhom_error").html('Nhóm không hợp lệ')
    }
    let maSinhVien = $("#addSinhVienTheoNhom").val().trim();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?id=" + desiredResult + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien, maNhom),
        success: function (responseData) {
            $("#modal_create_nhom").modal('hide');
            bootbox.alert({
                message: 'Thêm thành công',
                backdrop: true,
                callback: function () {
                    loadDataToTable(currentPage)
                }
            });
        },
        error: function (e) {
            if (e.responseJSON.message.trim() == 'nhom khong ton tai') {
                $("#idNhom_error").html('Nhóm không tồn tại')
            }
            if (e.responseJSON.message.trim() == 'nhom da du so luong thanh vien') {
                $("#idNhom_error").html('Nhóm đã đủ số lượng thành viên')
            }
        }
    });
}

genPagzing()

function changeInput() {
    if ($("#valueInput").val().length != 0) {
        let value = parseInt($("#valueInput").val());
        if (Number(value) <= 0) {
            value = 1;
        }
        if (Number(value) > totalPages) {
            value = totalPages;
        }
        loadDataToTable(value - 1);
    }
}

function genPagzing() {

    $("#page").html(``)
    let templatePageMid = ``;
    let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataToTable(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
    templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
    let templatePageAfter = `
                              <li style="color: #0a8cf0" class="page-item">
                                   <a style="border: none" class="page-link" onclick="loadDataToTable(${currentPage + 1})"><img src="/img/next.svg"></a>
                              </li><li style="color: #0a8cf0" class="page-item">
                `;
    let templateHtml = ``;
    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
    if (totalPages == 1) {
        templateHtml = ``;
    }
    $("#page").html(templateHtml);
}

function loadDataToTable(count) {
    let tenSinhVien = $("#tenSinhVien").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    let monChuongTrinh = $("#monChuongTrinh").val().trim();
    let monDatn = $("#monDatn").val().trim();
    let tenGvhd = $("#tenGvhd").val().trim();
    let maNhomSearch = $("#maNhomSearch").val().trim();
    let trangThai = $("#trangThai").val().trim();
    let sizeSinhVien = parseInt($("#sizeSinhVien").val().trim());
    if (count < 0 || count > totalPages - 1) {
        return;
    }
    $("#listSV").html("");
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/search" + "?tenSinhVien=" + tenSinhVien
            + "&monDatn=" + monDatn + "&monChuongTrinh=" + monChuongTrinh + "&tenGvhd=" + tenGvhd
            + "&maNhomSearch=" + maNhomSearch + "&trangThai=" + trangThai
            + "&idDotDangKy=" + dotDangKyId + "&page=" + count + "&size=" + sizeSinhVien,
        success: function (responseData) {
            let array = responseData.data.data
            var text = "";
            var text1 = "";
            var text2 = "";
            var text3 = "";
            if (array.length == 0) {
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
                $("#listSV").html(``)
                $("#page").html(``)
            } else {
                $("#empty").html(``)
                totalPages = responseData.data.totalPages
                array.map(function (sinhVien) {
                    var tenDeTai = sinhVien.tenDeTai == null ? 'Chưa có' : sinhVien.tenDeTai;
                    var tenGvhd = sinhVien.tenGvhd == null ? 'Chưa có' : sinhVien.tenGvhd;
                    var maSinhVien = sinhVien.maSinhVien;
                    text1 = `
                      <tr class="table-light">
                            <td class="text-center align-middle">${sinhVien.stt}</td>
                            <td class="text-center align-middle"> ${sinhVien.maSinhVien}</td>
                            <td class="text-center align-middle"> ${sinhVien.tenSinhVien}</td>
                            <td class="text-center align-middle"> ${sinhVien.tenChuyenNganh}</td>
                            <td class="text-center align-middle"> ${sinhVien.maMonChuongTrinh == null ? 'Chưa có' : sinhVien.maMonChuongTrinh}</td>
                            <td class="text-center align-middle"> ${sinhVien.maMonDuAn == null ? 'Chưa có' : sinhVien.maMonDuAn}</td>
                                                        `
                    text2 = sinhVien.maNhom == null ? `
                <td class="text-center align-middle">
                <div class="d-flex justify-content-center align-items-center">
                  <button onclick="openModalAddSinhVien('${maSinhVien}','${count}')"
                      class="btn_pencil_square">
                       <img src="/img/pencil-square.svg">  
                        </button>
                        </div>
                      </td>
                ` : `
                <td class="text-center align-middle">
                    <p>${sinhVien.maNhom}</p>
                </td>
                `;
                    text3 = `
                    <td class="text-center align-middle">${tenDeTai}</td>
                    <td class="text-center align-middle">${tenGvhd}</td>
                    </tr >
                `;
                    text += text1 + text2 + text3;
                });
                $("#listSV").html(text);
                $("#page").html(``)
                genPagzing()
            }
        },
        error: function (responseData) {
        }
    });
}

function importExcel() {

    var form = $('#form_import_excel')[0];
    let typeExcel = $("#inputGroupFile01").val();
    let fileName = typeExcel.split(".");
    let typeFile = fileName[fileName.length - 1];
    if (typeExcel.length <= 0) {
        $("#import_excel_error").text("Hãy chọn file excel cần import");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        $.ajax({
                type: 'POST',
                url: CNNhomDatnApi + '/import',
                enctype: 'multipart/form-data',
                processData: false,
                data: new FormData(form),
                contentType: false,
                cache: false,
                success: function (response) {
                    if (response.data.status == true) {
                        bootbox.alert({
                            message: 'Import file excel thành công',
                            backdrop: true,
                            callback: function () {
                                loadDataToTable(0);
                            }
                        });
                    } else {
                        bootbox.alert({
                            message: response.data.message,
                            backdrop: true,
                            callback: function () {
                                loadDataToTable(0);
                            }
                        });
                    }
                    $('#modal_import_excel').modal("hide");
                },
                error: function (e) {
                }
            }
        );
    } else {
        $("#import_excel_error").text("Hãy chọn đúng file excel");
    }
}

function openModalExport() {
    $("#type_excel_error").text("");
    $("#modal_excel").modal('show')
}

function openModalExportThongTinSinhVien() {
    $("#type_excel_error_thong_tin_sinh_vien").text("");
    $("#modal_excel_thong_tin_sinh_vien").modal('show')
}

function openModalImport() {
    $("#import_excel_error").text("");
    $("#labelFile").text('Choose file');
    $("#inputGroupFile01").val('');
    $("#modal_import_excel").modal('show')
}

function exportFile() {
    $("#type_excel_error").text("");
    let typeExcel = $("#type_excel").val()
    let tenSinhVien = $("#tenSinhVien").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    let monChuongTrinh = $("#monChuongTrinh").val().trim();
    let monDatn = $("#monDatn").val().trim();
    let tenGvhd = $("#tenGvhd").val().trim();
    let maNhomSearch = $("#maNhomSearch").val().trim();
    let trangThai = $("#trangThai").val().trim();

    if (typeExcel == '') {
        $("#type_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: CNNhomDatnApi + "/export?typeExcel=" + typeExcel + "&idDotDangKy=" + dotDangKyId
                + "&tenSinhVien=" + tenSinhVien + "&monDatn=" + monDatn
                + "&tenGvhd=" + tenGvhd + "&monChuongTrinh=" + monChuongTrinh
                + "&maNhomSearch=" + maNhomSearch + "&trangThai=" + trangThai
            ,
            success: function () {
                window.open(CNNhomDatnApi + "/export?typeExcel=" + typeExcel + "&idDotDangKy=" + dotDangKyId
                    + "&tenSinhVien=" + tenSinhVien + "&monDatn=" + monDatn + "&tenGvhd=" + tenGvhd
                    + "&monChuongTrinh=" + monChuongTrinh
                    + "&maNhomSearch=" + maNhomSearch + "&trangThai=" + trangThai
                    , '_self');
                $("#modal_excel").modal('hide')
            },
            error: function (e) {
            }
        });
    }
}

function exportFileThongTinSinhVien() {
    $("#type_excel_error_thong_tin_sinh_vien").text("");
    let typeExcel = $("#type_excel_thong_tin_sinh_vien").val()
    let tenSinhVien = $("#tenSinhVien").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    let monChuongTrinh = $("#monChuongTrinh").val().trim();
    let monDatn = $("#monDatn").val().trim();
    let tenGvhd = $("#tenGvhd").val().trim();
    let maNhomSearch = $("#maNhomSearch").val().trim();
    let trangThai = $("#trangThai").val().trim();

    if (typeExcel == '') {
        $("#type_excel_error_thong_tin_sinh_vien").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: CNNhomDatnApi + "/export/sinh-vien?typeExcel=" + typeExcel + "&idDotDangKy=" + dotDangKyId
                + "&tenSinhVien=" + tenSinhVien + "&monDatn=" + monDatn + "&monChuongTrinh=" + monChuongTrinh
                + "&tenGvhd=" + tenGvhd + "&maNhomSearch=" + maNhomSearch + "&trangThai=" + trangThai
            ,
            success: function () {
                window.open(CNNhomDatnApi + "/export/sinh-vien?typeExcel=" + typeExcel + "&idDotDangKy=" + dotDangKyId
                    + "&tenSinhVien=" + tenSinhVien + "&monDatn=" + monDatn
                    + "&tenGvhd=" + tenGvhd + "&monChuongTrinh=" + monChuongTrinh
                    + "&maNhomSearch=" + maNhomSearch + "&trangThai=" + trangThai
                    , '_self');
                $("#modal_excel_thong_tin_sinh_vien").modal('hide')
            },
            error: function (e) {
            }
        });
    }
}

function searchMaNhom() {
    let text = $("#maNhom").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search-list-nhom" + "?maNhom=" + text,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data
            $("#maNhom").autocomplete({
                source: array
            });
        },
        error: function (e) {
        }
    });
}

function openModalRandomNhomDatn() {
    $("#modal_random_nhomdatn").modal('show')
}

function randomNhomDatn() {
    let soThanhVien = parseInt($("#so_thanh_vien").val());

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/random-nhom-datn" + "?number=" + soThanhVien,
        dataType: 'json',
        success: function (responseData) {
            bootbox.alert({
                message: 'Random tạo nhóm thành công',
                backdrop: true,
                callback: function () {
                    loadDataToTable(0);
                    $("#modal_random_nhomdatn").modal('hide')
                }
            });
        },
        error: function (e) {
            if (e.responseJSON.message == 'Tao nhom that bai') {
                bootbox.alert({
                    message: "Tạo nhóm thất bại",
                    backdrop: true,
                    callback: function () {
                    }
                });
            }
        }
    });
}
