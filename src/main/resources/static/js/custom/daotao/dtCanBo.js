const canBoApi = '/api/dao-tao/can-bo';
const canBoView = '/dao-tao/can-bo';
const regexFE = new RegExp('[a-z0-9]+@fe.edu.vn');
const regexFPT = new RegExp('[a-z0-9]+@fpt.edu.vn');
const regexSoDienThoai = /(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b/;

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(canBoView);
});

function clearData() {
    $("#keyword").val("");
    $("#ten_tai_khoan").val("");
    $("#vai_tro").val("");
}

let totalPages = $("#totalPages").val();
let currentPage = $("#currentPage").val();

function changeInput() {
    if ($("#valueInput").val().length != 0) {
        let value = parseInt($("#valueInput").val());
        if (Number(value) <= 0) {
            value = 1;
        }
        if (Number(value) > totalPages) {
            value = totalPages;
        }
        loadTable(value - 1);
    }
}

genPaging();

function genPaging() {
    if (totalPages <= 1) {
        $("#page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadTable(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadTable(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    }
}

function loadTable(count) {
    if (count == -1) {
        count = totalPages - 1;
    }
    if (count == totalPages) {
        count = 0;
    }
    let tenTaiKhoan = $("#ten_tai_khoan").val();
    let vaiTro = $("#vai_tro").val();
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: canBoApi + "/search" + "?tenTaiKhoan=" + tenTaiKhoan + "&vaiTro=" + vaiTro + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            totalPages = responseData.data.totalPages;
            if (responseData.data.data.length == 0) {
                $("#searchListCanBo").html(``);
                $("#page").html(``);
                $("#empty").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#empty").html(``)
                $("#searchListCanBo").html(responseData.data.data.map(function (canbo) {
                    return `
                <tr class="table-light">                 
                      <td class="text-center align-middle">${canbo.stt}</td>
                      <td class="text-center align-middle">${canbo.emailFE}</td>
                      <td class="text-center align-middle">${canbo.soDienThoai}</td>
                      <td class="text-center align-middle">${canbo.tenCanBo} - ${canbo.tenTaiKhoan}</td>
                      <td class="text-center align-middle">${canbo.vaiTro === 0 ? 'Đào Tạo' : 'CNBM - ' + canbo.tenChuyenNganh}</td>
                      <td class="text-center align-middle">
                        <div class="d-flex justify-content-center align-items-center">
                          <button
                            type="button"
                            class="btn_pencil_square"
                            data-toggle="tooltip"
                            title="Sửa thông tin cán bộ"
                            onclick="openModalUpdateCanBo('${canbo.id}','${count}')">
                            <img src="/img/pencil-square.svg">
                         </button>
                         <button
                            type="button"
                            class="btn_trash"
                            data-toggle="tooltip"
                            title="Xóa cán bộ"
                            onclick="openModalRemoveCanBo('${canbo.id}','${count})">
                            <img src="/img/trash.svg">
                        </button>
                        </div>
                    </td>
                </tr>
               `
                }));
                genPaging();
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


function openModalCreate() {
    $("#emailfe").val('');
    $("#emailfpt").val('');
    $("#sodienthoai").val('');
    $("#tenCanBo").val('');
    $("#tentaikhoan").val('');
    $('#email_fe_error').text('');
    $('#email_fpt_error').text('');
    $('#so_dien_thoai_error').text('');
    $('#ten_can_bo_error').text('');
    $('#ten_tai_khoan_error').text('');
    $("#modal_create").modal('show')
}

function openModalImport() {
    $("#import_excel_error").text('');
    $("#labelFile").text('Choose file');
    $("#inputGroupFile01").val('');
    $("#import").modal('show')
}

function openModalTaiMau() {
    $("#mau_excel_error").text('');
    $("#modal_excel_mau").modal('show')
}

function clearUpdate() {
    $('#email_fe_error_update').text("");
    $('#email_fpt_error_update').text("");
    $('#so_dien_thoai_error_update').text("");
    $('#ten_tai_khoan_error_update').text("");
    $('#ten_tai_khoan_error_update').text("");
}

function save() {
    let emailFe = $("#emailfe").val().trim();
    let emailFpt = $("#emailfpt").val().trim();
    let soDienThoai = $("#sodienthoai").val().trim();
    let tenCanBo = $("#tenCanBo").val().trim();
    let tenTaiKhoan = $("#tentaikhoan").val().trim();
    let vaitro = $("#vaiTro").val().trim();

    let canBoRequest = {};

    canBoRequest["emailFe"] = emailFe;
    canBoRequest["emailFpt"] = emailFpt;
    canBoRequest["soDienThoai"] = soDienThoai;
    canBoRequest["tenCanBo"] = tenCanBo;
    canBoRequest["tenTaiKhoan"] = tenTaiKhoan;
    canBoRequest["vaiTro"] = vaitro;
    let check = true;

    if (emailFe.length === 0) {
        $("#email_fe_error").text("Email Fe Không Được Để Trống");
        check = false;
    } else if (emailFe.includes(" ")) {
        $("#email_fe_error").text("Email Fe không được để dấu cách");
        check = false;
    } else {
        $("#email_fe_error").text(" ");
    }
    if (emailFpt.length == 0) {
        $("#email_fpt_error").text("Email Fpt Không Được Để Trống");
        check = false;
    } else if (emailFpt.includes(" ")) {
        $("#email_fpt_error").text("Email Fe không được để dấu cách");
        check = false;
    } else {
        $("#email_fpt_error").text(" ");
    }

    if (soDienThoai.length == 0) {
        $("#so_dien_thoai_error").text("Số Điện Thoại Không Được Để Trống");
        check = false;
    } else {
        $("#so_dien_thoai_error").text("");
    }

    if (tenCanBo.length == 0) {
        $("#ten_can_bo_error").text("Tên Cán Bộ Không Được Để Trống");
        check = false;
    } else {
        $("#ten_can_bo_error").text(" ");
    }

    if (tenTaiKhoan.length == 0) {
        $("#ten_tai_khoan_error").text("Tên Tài Khoản Không Được Để Trống");
        check = false;
    } else if (tenTaiKhoan.includes(" ")) {
        $("#ten_tai_khoan_error").text("Tên Tài Khoản Không Được Được Chứa Khoảng Trắng");
        check = false;
    } else {
        $("#ten_tai_khoan_error").text(" ");
    }
    if (check == true) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: canBoApi,
            data: JSON.stringify(canBoRequest),
            dataType: 'json',
            success: function () {
                window.open(canBoView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                if (e.responseJSON.message.trim() == 'email fpt da ton tai') {
                    $("#email_fpt_error").text("Email fpt đã tồn tại");
                } else if (e.responseJSON.message.trim() == 'email fe da ton tai') {
                    $("#email_fe_error").text("Email fe đã tồn tại");
                } else if (e.responseJSON.message.trim() == 'Ten tai khoan cua can bo da ton tai') {
                    $("#ten_tai_khoan_error").text("Tên tài khoản đã tồn tại");
                }
            }
        });
    }
}

function openModalUpdateCanBo(canBoId) {
    clearUpdate();
    $("#modal_update_can_bo").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: canBoApi + "/" + canBoId,
        data: JSON.stringify(canBoId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_can_bo_update").val(responseData.data.id);
            $("#email_fe_update").val(responseData.data.emailFe);
            $("#email_fpt_update").val(responseData.data.emailFpt);
            $("#so_dien_thoai_Update").val(responseData.data.soDienThoai);
            $("#ten_Can_Bo_Update").val(responseData.data.tenCanBo);
            $("#ten_tai_khoan_Update").val(responseData.data.tenTaiKhoan);
            $("#co_So_Id_Update").val(responseData.data.coSoId);
            $("#vai_Tro_Update").val(responseData.data.vaiTro);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let emailFpt = $("#email_fpt_update").val().trim();
    let emailFe = $("#email_fe_update").val().trim();
    let soDienThoai = $("#so_dien_thoai_Update").val().trim();
    let tenCanBo = $("#ten_Can_Bo_Update").val().trim();
    let tenTaiKhoan = $("#ten_tai_khoan_Update").val().trim();
    let vaiTro = $("#vai_Tro_Update").val().trim();
    let canBoId = $("#id_can_bo_update").val().trim();
    let canBoRequest = {};

    canBoRequest["emailFpt"] = emailFpt;
    canBoRequest["emailFe"] = emailFe;
    canBoRequest["soDienThoai"] = soDienThoai
    canBoRequest["tenTaiKhoan"] = tenTaiKhoan;
    canBoRequest["tenCanBo"] = tenCanBo;
    canBoRequest["vaiTro"] = vaiTro;

    let check = true;

    if (emailFe.length === 0) {
        $("#email_fe_error_update").text("Email Fe Không Được Để Trống");
        check = false;
    } else if (emailFe.includes(" ")) {
        $("#email_fe_error_update").text("Email Fe không được để dấu cách");
        check = false;
    } else {
        $("#email_fe_error_update").text(" ");
    }

    if (emailFpt.length == 0) {
        $("#email_fpt_error_update").text("Email Fpt Không Được Để Trống");
        check = false;
    } else if (emailFpt.includes(" ")) {
        $("#email_fpt_error_update").text("Email Fpt không được để dấu cách");
        check = false;
    } else {
        $("#email_fpt_error_update").text(" ");
    }

    if (soDienThoai.length == 0) {
        $("#so_dien_thoai_error_update").text("Số Điện Thoại Không Được Để Trống");
        check = false;
    } else {
        $("#so_dien_thoai_error_update").text("");
    }

    if (tenCanBo.length == 0) {
        $("#ten_can_bo_error_update").text("Tên Cán Bộ Không Được Để Trống");
        check = false;
    } else {
        $("#ten_can_bo_error_update").text(" ");
    }

    if (tenTaiKhoan.length == 0) {
        $("#ten_tai_khoan_error_update").text("Tên Tài Khoản Không Được Để Trống");
        check = false;
    } else if (tenTaiKhoan.includes(" ")) {
        $("#ten_tai_khoan_error_update").text("Tên Tài Khoản Không Được Chứa Khoảng Trắng");
        check = false;
    } else {
        $("#ten_tai_khoan_error_update").text(" ");
    }

    if (check == true) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: canBoApi + "/" + canBoId,
            data: JSON.stringify(canBoRequest),
            dataType: 'json',
            success: function () {
                window.open(canBoView, '_self');
                $("#modal_update_can_bo").modal("hide");
            },
            error: function (e) {
                if (e.responseJSON.message.trim() == 'email fpt da ton tai') {
                    $("#email_fpt_error_update").text("Email fpt đã tồn tại");
                } else if (e.responseJSON.message.trim() == 'email fe da ton tai') {
                    $("#email_fe_error_update").text("Email fe đã tồn tại");
                } else if (e.responseJSON.message.trim() == 'Ten tai khoan cua can bo da ton tai') {
                    $("#ten_tai_khoan_error_update").text("Tên tài khoản đã tồn tại");
                }
            }
        });
    }
};

function importCanBo() {
    let typeExcel = $("#inputGroupFile01").val();
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length)
    if (typeExcel.trim().length <= 0) {
        $("#import_excel_error").text("Hãy chọn file excel cần import");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        var form = $('#form_import_excel')[0];
        $.ajax({
                type: 'POST',
                url: canBoApi + '/import',
                enctype: 'multipart/form-data',
                processData: false,
                data: new FormData(form),
                contentType: false,
                cache: false,
                success: function (response) {
                    if (response.data.status == true) {
                        bootbox.alert({
                            message: "Import file excel thành công",
                            backdrop: true,
                            callback: function () {
                                window.open(canBoView, '_self');
                            }
                        });
                    } else {
                        bootbox.alert({
                            message: response.data.message,
                            backdrop: true,
                            callback: function () {
                                window.open(canBoView, '_self');
                            }
                        });
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

function openModalRemoveCanBo(canBoId) {
    bootbox.confirm({
        title: "Xóa nhóm môn",
        message: "Bạn muốn xóa cán bộ này?",
        backdrop: true,
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
                    url: canBoApi + "/" + canBoId,
                    dataType: 'json',
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                window.open(canBoView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        if (responseData.responseJSON.message.trim() === 'Can Bo not exist') {
                            bootbox.alert({
                                message: 'Cán Bộ Không Tồn Tại',
                                backdrop: true,
                                callback: function () {
                                    window.open(canBoView, '_self');
                                }
                            });
                        } else if (responseData.responseJSON.message.trim() === 'Chu nhiem nay dang chu nhiem mot hoac nhieu chuyen nganh') {
                            bootbox.alert({
                                message: 'Chủ nhiệm này đang chủ nhiệm một hoặc nhiều chuyên ngành',
                                backdrop: true,
                                callback: function () {
                                    window.open(canBoView, '_self');
                                }
                            });
                        }
                    }
                });
            }
        }
    });
}

function exportExcelMau() {
    let typeExcel = $("#type_mau_excel").val();
    if (typeExcel == '') {
        $("#mau_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/can-bo/export-mau?typeExcel=" + typeExcel,
            success: function () {
                window.open("/api/dao-tao/can-bo/export-mau?typeExcel=" + typeExcel, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}