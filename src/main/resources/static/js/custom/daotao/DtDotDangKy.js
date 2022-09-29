const dotDangKyApi = '/api/dao-tao/dot-dang-ky';
const dotDangKyView = '/dao-tao/dot-dang-ky';

$(document).ready(function () {
    $("#dot_dang_ky_error").text("");
});

function openModalUpdateDotDangKy(dotDangKyId) {
    $("#modal_update_dot_dang_ky").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi + "/" + dotDangKyId,
        data: JSON.stringify(dotDangKyId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_dot_dang_ky_update").val(responseData.data.id);
            $("#id_hoc_ky_update").val(responseData.data.hocKyId);
            $("#id_co_so_update").val(responseData.data.coSoId);
            $("#ten_dot_dang_ky_update").val(responseData.data.tenDotDangKy);
            var ngayBatDau = new Date(responseData.data.ngayBatDau);
            var gioBatDau = ngayBatDau.getHours() < 10 ? '0' + ngayBatDau.getHours() : ngayBatDau.getHours();
            var phutBatDau = ngayBatDau.getMinutes() < 10 ? '0' + ngayBatDau.getMinutes() : ngayBatDau.getMinutes();
            var ngayBatDauStr = ngayBatDau.toLocaleDateString('en-CA') + " " +
                gioBatDau + ":" + phutBatDau;
            $("#ngay_bat_dau_update").val(ngayBatDauStr);
            var ngayKetThuc = new Date(responseData.data.ngayKetThuc);
            var gioKetThuc = ngayKetThuc.getHours() < 10 ? '0' + ngayKetThuc.getHours() : ngayKetThuc.getHours();
            var phutKetThuc = ngayKetThuc.getMinutes() < 10 ? '0' + ngayKetThuc.getMinutes() : ngayKetThuc.getMinutes();
            var ngayBatDauStr = ngayKetThuc.toLocaleDateString('en-CA') + " " +
                gioKetThuc + ":" + phutKetThuc;
            $("#ngay_ket_thuc_update").val(ngayBatDauStr);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_dot_dang_ky_update").submit(function (event) {
    event.preventDefault();
    let dotDangKyId = $("#id_dot_dang_ky_update").val();
    let tenDotDangKy = $("#ten_dot_dang_ky_update").val().trim();
    let ngayBatDauStr = $("#ngay_bat_dau_update").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngay_ket_thuc_update").val();
    let ngayKetThuc = new Date(ngayKetThucStr);
    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.getTime();
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.getTime();

    let check = 0;
    if (ngayBatDau >= ngayKetThuc) {
        $("#errorMessageNgayKetThucUpdate").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
        check += 1;
    } else {
        $("#errorMessageNgayKetThucUpdate").text("");
    }

    if (ngayBatDauStr === "") {
        $("#errorMessageNgayBatDauUpdate").text("Ngày giờ bắt đầu không được để trống");
        check += 1;
    } else {
        $("#errorMessageNgayBatDauUpdate").text("");
    }

    if (ngayKetThucStr === "") {
        $("#errorMessageNgayKetThucUpdate").text("Ngày giờ kết thúc không được để trống");
        check += 1;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#errorMessageNgayKetThucUpdate").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
        check += 1;
    } else {
        $("#errorMessageNgayKetThucUpdate").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listDotDangKy = responseData.data;
            let size = listDotDangKy.length;
            for (let i = 0; i < size; i++) {
                if (dotDangKyId === listDotDangKy[i].id) {
                    continue;
                }
                if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listDotDangKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listDotDangKy[i].ngayBatDau) {
                        $("#errorMessageNgayBatDauUpdate").text("Thời gian bắt đầu trùng với: " + listDotDangKy[i].tenDotDangKy);
                    } else {
                        $("#errorMessageNgayKetThucUpdate").text("Thời gian kết thúc trùng với: " + listDotDangKy[i].tenDotDangKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: dotDangKyApi + "/" + dotDangKyId,
            data: JSON.stringify(dotDangKyRequest),
            dataType: 'json',
            success: function () {
                window.open(dotDangKyView, '_self');
                // location.reload();
                $("#modal_update_dot_dang_ky").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function clearData() {
    $("#hocKyAdd").val("");
    $("#errorMessageAddHocKy").text("");
    $("#errorMessageAddNgayKetThuc").text("");
    $("#tenDotDangKyAdd").val('');
    $("#ngayBatDauAdd").val('');
    $("#ngayKetThucAdd").val('');
    $("#errorMessageAddTenDot").text("");
    $("#errorMessageAddNgayBatDau").text("");
}

$("#form_create_dot_dang_ky").submit(function (event) {
    event.preventDefault();
    let tenDotDangKy = $("#tenDotDangKyAdd").val().trim();
    let ngayBatDauStr = $("#ngayBatDauAdd").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucAdd").val();
    let ngayKetThuc = new Date(ngayKetThucStr);
    let hocKyId = $("#hocKyAdd").val();

    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.getTime();
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.getTime();
    dotDangKyRequest["hocKyId"] = hocKyId;

    let check = 0;
    if (hocKyId.length === 0) {
        $("#errorMessageAddHocKy").text("Mời chọn học kỳ");
        check += 1;
    } else {
        $("#errorMessageAddHocKy").text("");
    }

    if (tenDotDangKy.length === 0) {
        $("#errorMessageAddTenDot").text("Tên đợt đăng ký không được để trống");
        check += 1;
    } else if (tenDotDangKy.length < 6) {
        $("#errorMessageAddTenDot").text("Tên đợt đăng ký tối thiếu 6 ký tự");
        check += 1;
    } else {
        $("#errorMessageAddTenDot").text("");
    }

    if (ngayBatDauStr === "") {
        $("#errorMessageAddNgayBatDau").text("Ngày giờ bắt đầu không được để trống");
        check += 1;
    } else {
        $("#errorMessageAddNgayBatDau").text("");
    }

    if (ngayKetThucStr === "") {
        $("#errorMessageAddNgayKetThuc").text("Ngày giờ kết thúc không được để trống");
        check += 1;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#errorMessageAddNgayKetThuc").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
        check += 1;
    } else {
        $("#errorMessageAddNgayKetThuc").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listDotDangKy = responseData.data;
            let size = listDotDangKy.length;
            for (let i = 0; i < size; i++) {
                if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listDotDangKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listDotDangKy[i].ngayBatDau) {
                        $("#errorMessageAddNgayBatDau").text("Thời gian bắt đầu trùng với: " + listDotDangKy[i].tenDotDangKy);
                    } else {
                        $("#errorMessageAddNgayKetThuc").text("Thời gian kết thúc trùng với: " + listDotDangKy[i].tenDotDangKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: dotDangKyApi,
            data: JSON.stringify(dotDangKyRequest),
            dataType: 'json',
            success: function (responseData) {
                window.open(dotDangKyView, '_self');
                // location.reload();
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});