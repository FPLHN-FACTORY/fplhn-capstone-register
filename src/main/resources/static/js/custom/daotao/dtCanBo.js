const canBoApi = '/api/dao-tao/can-bo';
const canBoView = '/dao-tao/can-bo';
const regexFE = new RegExp('[a-z0-9]+@fe.edu.vn');
const regexFPT = new RegExp('[a-z0-9]+@fpt.edu.vn');
const regexSoDienThoai = /(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\b/;

$(document).ready(function () {
    $("#hoc_ky_error").text("");
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

$("#form_create_can_bo").submit(function (event) {
    event.preventDefault();
    clear();
    let emailFe = $("#emailfe").val();
    let emailFpt = $("#emailfpt").val();
    let soDienThoai = $("#sodienthoai").val();
    let tenCanBo = $("#tenCanBo").val();
    let tenTaiKhoan = $("#tentaikhoan").val();
    let vaitro = $("#vaiTro").val();

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
    } else {
        if (regexFE.test(emailFe) == false) {
            $("#email_fe_error").text("Email Fe Phải Đúng Định Dạng");
            check = false;
        }
    }
    if (emailFpt.length == 0) {
        $("#email_fpt_error").text("Email Fpt Không Được Để Trống");
        check = false;
    } else {
        if (regexFPT.test(emailFpt) == false) {
            $("#email_fpt_error").text("Email Fpt Phải Đúng Định Dạng");
            check = false;
        }
    }
    if (soDienThoai.length == 0) {
        $("#so_dien_thoai_error").text("Số Điện Thoại Không Được Để Trống");
        check = false;
    } else {
        if (regexSoDienThoai.test(soDienThoai) == false) {
            $("#so_dien_thoai_error").text("Số Điện Thoại Phải Định Dạng");
            check = false;
        }
    }

    if (tenCanBo.length == 0) {
        $("#ten_can_bo_error").text("Tên Cán Bộ Không Được Để Trống");
        check = false;
    }
    if (tenTaiKhoan.length == 0) {
        $("#ten_tai_khoan_error").text("Tên Tài Khoản Không Được Để Trống");
        check = false;
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
});

function openModalUpdateCanBo(canBoId) {
    $('#email_fe_error_update').text("");
    $('#email_fpt_error_update').text("");
    $('#so_dien_thoai_error_update').text("");
    $('#ten_can_bo_error_update').text("");
    $('#ten_tai_khoan_error_update').text("");

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

$("#form_hoc_ky_update").submit(function (event) {
    event.preventDefault();
    let emailFpt = $("#email_fpt_update").val();
    let emailFe = $("#email_fe_update").val();
    let soDienThoai = $("#so_dien_thoai_Update").val();
    let tenCanBo = $("#ten_Can_Bo_Update").val();
    let tenTaiKhoan = $("#ten_tai_khoan_Update").val();
    let vaiTro = $("#vai_Tro_Update").val();
    let canBoId = $("#id_can_bo_update").val();
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
    } else {
        if (regexFE.test(emailFe) == false) {
            $("#email_fe_error_update").text("Email Fe Phải Đúng Định Dạng");
            check = false;
        }
    }
    if (emailFpt.length == 0) {
        $("#email_fpt_error_update").text("Email Fpt Không Được Để Trống");
        check = false;
    } else {
        if (regexFPT.test(emailFpt) == false) {
            $("#email_fpt_error_update").text("Email Fpt Phải Đúng Định Dạng");
            check = false;
        }
    }
    if (soDienThoai.length == 0) {
        $("#so_dien_thoai_error_update").text("Số Điện Thoại Không Được Để Trống");
        check = false;
    } else {
        if (regexSoDienThoai.test(soDienThoai) == false) {
            $("#so_dien_thoai_error_update").text("Số Điện Thoại Phải Định Dạng");
            check = false;
        }
    }

    if (tenCanBo.length == 0) {
        $("#ten_can_bo_error_update").text("Tên Cán Bộ Không Được Để Trống");
        check = false;
    }
    if (tenTaiKhoan.length == 0) {
        $("#ten_tai_khoan_error_update").text("Tên Tài Khoản Không Được Để Trống");
        check = false;
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
});

function openModalRemoveCanBo(canBoId) {
    $("#modal_can_bo_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: canBoApi + "/" + canBoId,
        dataType: 'json',
        success: function () {
            $("#remove_can_bo").val(canBoId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_can_bo_delete").submit(function (event) {
    event.preventDefault();
    let canBoId = $("#remove_can_bo").val();
    console.log(canBoId)
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: canBoApi + "/" + canBoId,
        data: JSON.stringify(canBoId),
        dataType: 'json',
        success: function () {
            window.open(canBoView, '_self');
            $("#modal_update_hoc_ky").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});