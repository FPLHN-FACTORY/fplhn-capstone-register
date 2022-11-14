const giangVienHuongDanApi = '/api/dao-tao/giang-vien-huong-dan';
const giangVienHuongDanView = '/dao-tao/giang-vien-huong-dan';
const regexFE = new RegExp('[a-z0-9]+@fe.edu.vn');
const regexFPT = new RegExp('[a-z0-9]+@fpt.edu.vn');
const regexSDT = new RegExp('(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}');
const regexSoNhom = new RegExp('[0-9]+');
$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
})
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$(document).ready(function () {
    $("#giang_vien_error").text("");
});


function clearData() {
    $("#tenGiangVien").val("");
    $("#search_dot_dang_ky").val("")
}

function clearErrorUpdate() {
    $("#errorTenGiangVienUpdate").text("");
    $("#errorSoNhomHDUpdate").text("");
    $("#errorTenTaiKhoanUpdate").text("");
    $("#errorSDTUpdate").text("");
    $("#errorEmailFptUpdate").text("");
    $("#errorEmailFeUpdate").text("");
    $("#errorDotDangKyUpdate").text("");
}

function openModalAddGVHD() {
    $("#errorTenGiangVien").text('');
    $("#errorSoNhomHD").text('');
    $("#errorTenTaiKhoan").text('');
    $("#errorSDT").text('');
    $("#errorEmailFpt").text('');
    $("#errorEmailFe").text('');
    $("#tenGvhd").val('');
    $("#soNhomHuongDanToiDa").val('');
    $("#tenTaiKhoan").val('');
    $("#soDienThoai").val('');
    $("#emailFpt").val('');
    $("#emailFe").val('');
    $("#errorDotDangKy").text('');
    $("#modal_create").modal('show')
}

function addGVHD () {
    let tenGvhd = $("#tenGvhd").val().trim();
    let soNhomHuongDanToiDa = $("#soNhomHuongDanToiDa").val().trim();
    let tenTaiKhoan = $("#tenTaiKhoan").val().trim();
    let soDienThoai = $("#soDienThoai").val().trim();
    let emailFpt = $("#emailFpt").val().trim();
    let emailFe = $("#emailFe").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    let giangVienRequest = {};
    giangVienRequest["tenGvhd"] = tenGvhd;
    giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
    giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
    giangVienRequest["soDienThoai"] = soDienThoai;
    giangVienRequest["emailFpt"] = emailFpt;
    giangVienRequest["emailFe"] = emailFe;
    giangVienRequest["dotDangKyId"] = dotDangKyId;

    let index = 0;

    if (tenGvhd.length === 0) {
        $("#errorTenGiangVien").text(" Tên giảng viên không được để trống");
        index++;
    } else {
        $("#errorTenGiangVien").text("");
    }
    if (soNhomHuongDanToiDa.length === 0) {
        $("#errorSoNhomHD").text(" Số nhóm không được để trống");
        index++;
    } else if (regexSoNhom.test(soNhomHuongDanToiDa) == false) {
        $("#errorSoNhomHD").text(" Số nhóm phải là số");
        index++;
    } else if (Number.parseInt(soNhomHuongDanToiDa) <= 0) {
        $("#errorSoNhomHD").text("Số nhóm phải lớn hơn 0");
        index++;
    } else if (Number.parseInt(soNhomHuongDanToiDa) < 1 || Number.parseInt(soNhomHuongDanToiDa) > 7) {
        $("#errorSoNhomHD").text("Số nhóm chỉ từ 1 đến 7");
        index++;
    } else {
        $("#errorSoNhomHD").text("");
    }

    if (tenTaiKhoan.length === 0) {
        $("#errorTenTaiKhoan").text(" Tên tài khoản không được để trống");
        index++;
    } else {
        $("#errorTenTaiKhoan").text("");
    }

    if (soDienThoai.length === 0) {
        $("#errorSDT").text(" Số điện thoại không được để trống");
        index++;
    } else if (regexSDT.test(soDienThoai) == false) {
        $("#errorSDT").text(" Số điện thoại không đúng định dạng");
        index++;
    } else if (soDienThoai.length != 10) {
        $("#errorSDT").text(" Số điện thoại không đúng định dạng");
        index++;
    } else {
        $("#errorSDT").text("");
    }

    if (emailFpt.length === 0) {
        $("#errorEmailFpt").text(" EmailFpt không được để trống");
        index++;
    } else if (emailFpt.trim().includes(" ")) {
        $("#errorEmailFpt").text("Email Fpt Phải Đúng Định Dạng");
        index++;
    } else if (regexFPT.test(emailFpt) == false) {
        $("#errorEmailFpt").text("Email Fpt Phải Đúng Định Dạng");
        index++;
    } else {
        $("#errorEmailFpt").text("");
    }

    if (emailFe.length === 0) {
        $("#errorEmailFe").text(" EmailFe không được để trống");
        index++;
    } else if (regexFE.test(emailFe) == false) {
        $("#errorEmailFe").text("Email Fe Phải Đúng Định Dạng");
        index++;
    } else if (emailFpt.trim().includes(" ")) {
        $("#errorEmailFe").text("Email Fe Phải Đúng Định Dạng");
        index++;
    } else {
        $("#errorEmailFe").text("");
    }

    if (dotDangKyId == "Chọn đợt đăng kí") {
        $("#errorDotDangKy").text(" Đợt đăng ký không được để trống");
        index++;
    } else {
        $("#errorDotDangKy").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: giangVienHuongDanApi,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function (responseData) {
                console.log(responseData)
                if (responseData.data.message == null) {
                    window.open(giangVienHuongDanView, '_self');
                    $("#modal_create").modal("hide");
                } else {
                    if (responseData.data.message == 'email fpt da ton tai') {
                        $("#errorEmailFpt").text("Email Fpt đã tồn tại");
                    } else if (responseData.data.message == 'email fe da ton tai') {
                        $("#errorEmailFe").text("Email Fe đã tồn tại");
                    } else if (responseData.data.message == 'ten tai khoan da ton tai') {
                        $("#errorTenTaiKhoan").text("Tài khoản giảng viên bị trùng")
                    }
                }
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function openModalUpdateGiangVien(giangVienId, item) {
    $("#modal_update_giang_vien").modal('show');
    $("#update_giang_vien_item").val(item);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/" + giangVienId,
        data: JSON.stringify(giangVienId),
        dataType: 'json',
        success: function (responseData) {
            clearErrorUpdate();
            $("#giangVienId").val(responseData.data.id);
            $("#ten_giang_vien_update").val(responseData.data.tenGvhd);
            $("#so_nhom_update").val(responseData.data.soNhomHuongDanToiDa);
            $("#ten_tai_khoan_update").val(responseData.data.tenTaiKhoan);
            $("#so_dien_thoai_update").val(responseData.data.soDienThoai);
            $("#emailFpt_update").val(responseData.data.emailFpt);
            $("#emailFe_update").val(responseData.data.emailFe);
            $("#dot_dang_ky_update").val(responseData.data.dotDangKyId);
            $("#co_so_update").val(responseData.data.coSoId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function updateGVHD () {
    let tenGvhd = $("#ten_giang_vien_update").val().trim();
    let soNhomHuongDanToiDa = $("#so_nhom_update").val().trim();
    let tenTaiKhoan = $("#ten_tai_khoan_update").val().trim();
    let soDienThoai = $("#so_dien_thoai_update").val().trim();
    let emailFpt = $("#emailFpt_update").val().trim();
    let emailFe = $("#emailFe_update").val().trim();
    let dotDangKyId = $("#dot_dang_ky_update").val().trim();
    let giangVienId = $("#giangVienId").val().trim();
    let item = $("#update_giang_vien_item").val().trim();
    let giangVienRequest = {};
    giangVienRequest["tenGvhd"] = tenGvhd;
    giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
    giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
    giangVienRequest["soDienThoai"] = soDienThoai;
    giangVienRequest["emailFpt"] = emailFpt;
    giangVienRequest["emailFe"] = emailFe;
    giangVienRequest["dotDangKyId"] = dotDangKyId;
    let index = 0;
    if (tenGvhd.length === 0) {
        $("#errorTenGiangVienUpdate").text(" Tên giang viên không được để trống");
        index++;
    } else {
        $("#errorTenGiangVienUpdate").text("");
    }

    if (soNhomHuongDanToiDa.length === 0) {
        $("#errorSoNhomHDUpdate").text(" Số nhóm không được để trống");
        index++;
    } else if (regexSoNhom.test(soNhomHuongDanToiDa) == false) {
        $("#errorSoNhomHDUpdate").text(" Số nhóm phải là số");
        index++;
    } else if (Number.parseInt(soNhomHuongDanToiDa) < 1 || Number.parseInt(soNhomHuongDanToiDa) > 7) {
        $("#errorSoNhomHDUpdate").text("Số nhóm chỉ từ 1 đến 7");
        index++;
    } else {
        $("#errorSoNhomHDUpdate").text("");
    }

    if (tenTaiKhoan.length === 0) {
        $("#errorTenTaiKhoanUpdate").text(" Tên tài khoản không được để trống");
        index++;
    } else {
        $("#errorTenTaiKhoanUpdate").text("");
    }

    if (soDienThoai.length === 0) {
        $("#errorSDTUpdate").text(" Số điện thoại không được để trống");
        index++;
    } else if (regexSDT.test(soDienThoai) == false) {
        $("#errorSDTUpdate").text(" Số điện thoại không đúng định dạng");
        index++;
    } else if (soDienThoai.length != 10) {
        $("#errorSDTUpdate").text(" Số điện thoại không đúng định dạng");
        index++;
    } else {
        $("#errorSDTUpdate").text("");
    }

    if (emailFpt.length === 0) {
        $("#errorEmailFptUpdate").text(" EmailFpt không được để trống");
        index++;
    }  else if (emailFpt.trim().includes(" ")) {
        $("#errorEmailFptUpdate").text("Email Fpt Phải Đúng Định Dạng");
        index++;
    } else if (regexFPT.test(emailFpt) == false) {
        $("#errorEmailFptUpdate").text("Email Fpt Phải Đúng Định Dạng");
        index++;
    } else {
        $("#errorEmailFptUpdate").text("");
    }

    if (emailFe.length === 0) {
        $("#errorEmailFeUpdate").text(" EmailFe không được để trống");
        index++;
    } else if (regexFE.test(emailFe) == false) {
        $("#errorEmailFeUpdate").text("Email Fe Phải Đúng Định Dạng");
        index++;
    } else if (emailFe.trim().includes(" ")) {
        $("#errorEmailFeUpdate").text("Email Fe Phải Đúng Định Dạng");
        index++;
    } else {
        $("#errorEmailFeUpdate").text("");
    }

    if (dotDangKyId == "Chọn đợt đăng kí") {
        $("#errorDotDangKyUpdate").text(" Đợt đăng ký không được để trống");
        index++;
    } else {
        $("#errorDotDangKyUpdate").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: giangVienHuongDanApi + "/" + giangVienId,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function (responseData) {
                if (responseData.data.message == null) {
                    window.open(giangVienHuongDanView, '_self');
                    $("#modal_create").modal("hide");
                } else {
                    if (responseData.data.message == 'email fpt da ton tai') {
                        $("#errorEmailFptUpdate").text("Email Fpt đã tồn tại");
                    } else if (responseData.data.message == 'email fe da ton tai') {
                        $("#errorEmailFeUpdate").text("Email Fe đã tồn tại");
                    } else if (responseData.data.message == 'ten tai khoan da ton tai') {
                        $("#errorTenTaiKhoanUpdate").text("Tài khoản giảng viên bị trùng")
                    }
                }

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function openModalRemoveGiangVien(giangVienId, item) {
    bootbox.confirm({
        title: "Xóa",
        message: "Bạn chắc chắn xóa không?",
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: giangVienHuongDanApi + "/" + giangVienId,
                    data: JSON.stringify(giangVienId),
                    dataType: 'json',
                    success: function (responseData) {
                        if (responseData.data.message != null) {
                            bootbox.alert({
                                message: 'Giảng viên đang có nhóm hướng dẫn không thể xóa',
                                backdrop: true,
                            });
                        } else {
                            bootbox.alert({
                                message: 'Xóa thành công',
                                backdrop: true,
                                callback: function () {
                                    loadTable(item);
                                }
                            });
                        }
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                    }
                });
            }
        }
    });
}

function openModalExport() {
    $("#type_excel_error").text('');
    $("#modal_excel").modal('show')
}

function openModalImportMau() {
    $("#labelFile").text('Choose file');
    $("#mau_import_error").text('');
    $("#modal_import_excel").modal('hide')
    $("#modal_import_mau").modal('show')
}
function openModalImport() {
    $("#labelFile").text('Choose file');
    $("#inputGroupFile01").val('');
    $("#import_excel_error").text('');
    $("#modal_import_excel").modal('show')
}
function exportFile() {
    let typeExcel = $("#type_excel").val()
    if (typeExcel == '') {
        $("#type_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/giang-vien-huong-dan/export?typeExcel=" + typeExcel,
            success: function () {
                window.open("/api/dao-tao/giang-vien-huong-dan/export?typeExcel=" + typeExcel, '_self');
                $("#modal_excel").modal('hide')
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function importExcel() {
    let typeExcel = $("#inputGroupFile01").val();
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length)
    if (typeExcel.trim().length <= 0) {
        $("#import_excel_error").text("Hãy chọn file excel cần import");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        var form = $('#form_import_excel')[0];
        $.ajax({
                type: 'POST',
                url: giangVienHuongDanApi + '/import',
                enctype: 'multipart/form-data',
                processData: false,
                data: new FormData(form),
                contentType: false,
                cache: false,
                success: function (response) {
                    if (response.data.status == true) {
                        alert("Import file excel thành công");
                        window.open(giangVienHuongDanView, '_self');
                    } else {
                        alert(response.data.message);
                        $("#inputGroupFile01").val("");
                        $("#inputGroupFile01").clear()
                    }
                },
                error: function (e) {
                }
            }
        );
    } else {
        $("#import_excel_error").text("Hãy chọn đúng file excel");
    }
}

function importFileMau() {
    let typeExcel = $("#type_mau_import").val();
    if (typeExcel == '') {
        $("#mau_import_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/giang-vien-huong-dan/export/mau?typeExcel=" + typeExcel,

            success: function () {
                window.open("/api/dao-tao/giang-vien-huong-dan/export/mau?typeExcel=" + typeExcel, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

genPagingAndFillColor();

function genPagingAndFillColor() {
    genPageZing()
    let colorPage = document.querySelectorAll("#pageColor");
    colorPage[currentPage].style.backgroundColor = '#0a8cf0';
    colorPage[currentPage].style.color = 'white';
}

function genPageZing() {
    $("#page").html(``)
    let templateHtml = ``;
    let templatePageMid = ``;
    let templatePageBefore = `
                           <li style="color: #0a8cf0" class="page-item">
                                <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadTable(currentPage - 1)">Back</a>
                           </li>
                           `;
    let templatePageAfter = `
                            <li style="color: #0a8cf0" class="page-item">
                                 <a style="${currentPage == totalPages - 1 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadTable(Number(currentPage) + 1)">Next</a>
                            </li>
                            `;
    for (let i = 0; i < totalPages; i++) {
        templatePageMid += `<li class="page-item" >
                                         <a id="pageColor" class="page-link" onclick="loadTable(${i})">${i + 1}</a>
                                     </li>`
    }
    templateHtml = templatePageBefore + templatePageMid + templatePageAfter
    if (totalPages == 1) {
        templateHtml = ``;
    }
    $("#page").html(templateHtml);
}

function loadTable(count) {
    let tenGiangVien = $("#tenGiangVien").val();
    let dotDangKyId = $("#search_dot_dang_ky").val();
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/search" + "?tenGiangVien=" + tenGiangVien + "&page=" + count + "&dotDangKyId=" +dotDangKyId,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.data.length == 0) {
                $("#searchListGvhd").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
                if (responseData.data.totalPages == 1) {
                    loadTable(0);
                    return;
                }
                if (count == responseData.data.totalPages && count != 0) {
                    loadTable(count - 1);
                    return;
                }
            } else {
                totalPages = responseData.data.totalPages
                $("#empty").html(``)
                $("#searchListGvhd").html(responseData.data.data.map(function (item) {
                    return `
               <tr>
                   <td >${item.stt}</td>
                   <td >${item.tenGvhd + ' - ' + item.tenTaiKhoan}</td>
                   <td >${item.soNhomDangHuongDan + '/' + item.soNhomHuongDanToiDa}</td>
                   <td >${item.soDienThoai}</td>
                   <td >${item.emailFe}</td>
                   <td>
                       <button
                           type="button"
                           class="btn_pencil_square"
                           data-toggle="tooltip"
                           title="Sửa "
                           onclick="openModalUpdateGiangVien('${item.id}','${count}')">
                           <img src="/img/pencil-square.svg">
                        </button>
                        </button>
                        <button
                            type="button"
                            class="btn_trash"
                            data-toggle="tooltip"
                            title="Xóa "
                            onclick="openModalRemoveGiangVien('${item.id}','${count}')">
                            <img src="/img/trash.svg">
                        </button>
                       <a type="button"
                            class="btn_clipboard_user"
                            data-toggle="tooltip"
                            href="/dao-tao/giang-vien-huong-dan/${item.id}">
                            <img src="/img/clipboard-user.svg">
                        </a>
                   </td>
               </tr>
               `
                }))
                genPagingAndFillColor()
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
