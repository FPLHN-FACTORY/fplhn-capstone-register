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
                gioBatDau  + ":" + phutBatDau;
            $("#ngay_bat_dau_update").val(ngayBatDauStr);
            var ngayKetThuc = new Date(responseData.data.ngayKetThuc);
            var gioKetThuc = ngayKetThuc.getHours() < 10 ? '0' + ngayKetThuc.getHours() : ngayKetThuc.getHours();
            var phutKetThuc = ngayKetThuc.getMinutes() < 10 ? '0' + ngayKetThuc.getMinutes() : ngayKetThuc.getMinutes();
            var ngayBatDauStr = ngayKetThuc.toLocaleDateString('en-CA') + " " +
                gioKetThuc  + ":" + phutKetThuc;
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
    let tenDotDangKy = $("#ten_dot_dang_ky_update").val();
    let ngayBatDau = new Date($("#ngay_bat_dau_update").val());
    let ngayKetThuc = new Date($("#ngay_ket_thuc_update").val());

    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.getTime();
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.getTime();
    if (tenDotDangKy.length === 0) {
        $("#errorMessageUpdate").text("Tên đợt đăng ký không được để trống");
    } else if (tenDotDangKy.length < 6) {
        $("#errorMessageUpdate").text("Tên đợt đăng ký tối thiếu 6 ký tự");
    } else if (ngayBatDau >= ngayKetThuc){
        $("#errorMessageUpdate").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
    } else {
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

$("#form_create_dot_dang_ky").submit(function (event) {
    event.preventDefault();

    let tenDotDangKy = $("#tenDotDangKyAdd").val();
    let ngayBatDau = new Date($("#ngayBatDauAdd").val());
    let ngayKetThuc = new Date($("#ngayKetThucAdd").val());
    let hocKyId = new String('1fd27bfb-1afd-4c56-92cf-00a727aa0778');

    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.getTime();
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.getTime();
    dotDangKyRequest["hocKyId"] = hocKyId;

    if (tenDotDangKy.length === 0) {
        $("#errorMessageAdd").text("Tên đợt đăng ký không được để trống");
    } else if (tenDotDangKy.length < 6) {
        $("#errorMessageAdd").text("Tên đợt đăng ký tối thiếu 6 ký tự");
    } else if (ngayBatDau >= ngayKetThuc){
        $("#errorMessageAdd").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
    } else {
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