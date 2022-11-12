const canBoApi = '/api/dao-tao/can-bo';
const canBoView = '/dao-tao/can-bo';
const regexFE = new RegExp('[a-z0-9]+@fe.edu.vn');
const regexFPT = new RegExp('[a-z0-9]+@fpt.edu.vn');
const regexSoDienThoai = /(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b/;

$(document).ready(function () {
    $("#hoc_ky_error").text("");
    clear();
});


function clearData() {
    $("#keyword").val("");
}

function clear() {
    $('#email_fe_error').text("");
    $('#email_fpt_error').text("");
    $('#so_dien_thoai_error').text("");
    $('#ten_can_bo_error').text("");
    $('#ten_tai_khoan_error').text("");

}

function clearUpdate() {
    $('#email_fe_error_update').text("");
    $('#email_fpt_error_update').text("");
    $('#so_dien_thoai_error_update').text("");
    $('#ten_tai_khoan_error_update').text("");
    $('#ten_tai_khoan_error_update').text("");
}

function save() {
    event.preventDefault();
    clear();
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
    } else if (regexFE.test(emailFe) == false) {
        $("#email_fe_error").text("Email Fe Phải Đúng Định Dạng");
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
    } else if (regexFPT.test(emailFpt) == false) {
        $("#email_fpt_error").text("Email Fpt Phải Đúng Định Dạng");
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
    } else if (regexSoDienThoai.test(soDienThoai) == false) {
        $("#so_dien_thoai_error").text("Số Điện Thoại Phải Định Dạng");
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
                console.log("ERROR : ", e);
            }
        });
    }
};

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
    event.preventDefault();
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
    } else if (regexFE.test(emailFe) == false) {
        $("#email_fe_error_update").text("Email Fe Phải Đúng Định Dạng");
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
    } else if (regexFPT.test(emailFpt) == false) {
        $("#email_fpt_error_update").text("Email Fpt Phải Đúng Định Dạng");
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
    } else if (regexSoDienThoai.test(soDienThoai) == false) {
        $("#so_dien_thoai_error_update").text("Số Điện Thoại Phải Định Dạng");
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
                console.log("ERROR : ", e);
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
                        alert("Import file excel thành công");
                        window.open(canBoView, '_self');
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

function openModalRemoveCanBo(canBoId) {
    bootbox.confirm({
        title: "Xóa nhóm môn",
        message: "Bạn muốn xóa cán bộ này?",
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
                        if (responseData.responseJSON.message === 'Can Bo not exist') {
                            bootbox.alert({
                                message: 'Cán Bộ Không Tồn Tại',
                                backdrop: true,
                                callback: function () {
                                    window.open(nhomMonDatnView, '_self');
                                }
                            });
                        }
                    }
                });
            }
        }
    });
}



$("#modal_excel_mau").submit(function (event) {
    event.preventDefault();
    let typeExcel = $("#type_mau_excel").val();
    if (typeExcel == '') {
        $("#mau_excel_error").text("chọn định dạng file download");
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
});