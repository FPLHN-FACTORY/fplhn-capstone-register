const hocKyApi = '/api/dao-tao/hoc-ky';
const hocKyView = '/dao-tao/hoc-ky';

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#ten-hoc-ky").val("");
}

$("#form_create_hoc_ky").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#tenHocKyAdd").val();
    let ngayBatDauStr = $("#ngayBatDauAdd").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucAdd").val();
    let ngayKetThuc = new Date(ngayKetThucStr);

    let check = 0;

    if (tenHocKy.trim().length === 0) {
        $("#hoc_ky_error").text("Tên học kỳ không được để trống");
        ++check;
    } else {
        $("#hoc_ky_error").text("");
    }

    if (tenHocKy.trim().length < 6) {
        $("#hoc_ky_error").text("Tên học kỳ tối thiểu 6 ký tự");
        ++check;
    } else {
        $("#hoc_ky_error").text("");
    }

    if (ngayBatDauStr.trim().length === 0) {
        $("#ngay_bat_dau_add_error").text("Ngày bắt đầu không được để trống");
        ++check;
    } else {
        $("#ngay_bat_dau_add_error").text("");
    }

    if (ngayKetThucStr.trim().length === 0) {
        $("#ngay_ket_thuc_add_error").text("Ngày kết thúc không được để trống");
        ++check;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#ngay_ket_thuc_add_error").text("Ngày kết thúc phải sau ngày bắt đầu");
        ++check;
    } else {
        $("#ngay_ket_thuc_add_error").text("");
    }

    if (check === 0) {

        let hocKyRequest = {};
        hocKyRequest["tenHocKy"] = tenHocKy;
        hocKyRequest["ngayBatDau"] = ngayBatDau.toLocaleString('en-CA', {hour12: false});
        hocKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleString('en-CA', {hour12: false});

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: hocKyApi,
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                window.open(hocKyView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalUpdateHocKy(hocKyId) {
    $("#modal_update_hoc_ky").modal('show');
    $("#errorMessageUpdate").text("");
    $("#ngay_ket_thuc_update_error").text("");
    $("#ngay_ket_thuc_update_error").text("");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi + "/" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_hoc_ky_update").val(responseData.data.id);
            $("#ten_hoc_ky_update").val(responseData.data.tenHocKy);
            var dateNgayBatDau = new Date(responseData.data.ngayBatDau);
            var yearNgayBatDau = dateNgayBatDau.getFullYear();
            var monthNgayBatDau = (dateNgayBatDau.getMonth() + 1).toString().padStart(2, '0');
            var dayNgayBatDau = dateNgayBatDau.getDate().toString().padStart(2, '0');
            var formattedDateNgayBatDau = yearNgayBatDau + '-' + monthNgayBatDau + '-' + dayNgayBatDau;
            $("#ngayBatDauUpdate").val(formattedDateNgayBatDau);
            var dateNgayKetThuc = new Date(responseData.data.ngayKetThuc);
            var yearNgayKetThuc = dateNgayKetThuc.getFullYear();
            var monthNgayKetThuc = (dateNgayKetThuc.getMonth() + 1).toString().padStart(2, '0');
            var dayNgayKetThuc = dateNgayKetThuc.getDate().toString().padStart(2, '0');
            var formattedDateNgayKetThuc = yearNgayKetThuc + '-' + monthNgayKetThuc + '-' + dayNgayKetThuc;
            $("#ngayKetThucUpdate").val(formattedDateNgayKetThuc);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function openModalCreateHocKy() {
    $("#modal_create").modal('show');
    $("#tenHocKyAdd").val("")
    $("#ngayBatDauAdd").val("")
    $("#ngayKetThucAdd").val("")
    $("#hoc_ky_error").val("")
    $("#ngay_bat_dau_add_error").val("")
    $("#ngay_ket_thuc_add_error").val("")
}

$("#form_hoc_ky_update").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#ten_hoc_ky_update").val();
    let hocKyId = $("#id_hoc_ky_update").val();
    let ngayBatDauStr = $("#ngayBatDauUpdate").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucUpdate").val();
    let ngayKetThuc = new Date(ngayKetThucStr);

    let check = 0;

    if (tenHocKy.trim().length === 0) {
        $("#errorMessageUpdate").text("Tên học kỳ không được để trống");
        ++check;
    } else {
        $("#errorMessageUpdate").text("");
    }

    if (tenHocKy.trim().length < 6) {
        $("#errorMessageUpdate").text("Tên học kỳ tối thiểu 6 ký tự");
        ++check;
    } else {
        $("#errorMessageUpdate").text("");
    }

    if (ngayBatDauStr.trim().length === 0) {
        $("#ngay_bat_dau_update_error").text("Ngày bắt đầu không được để trống");
        ++check;
    } else {
        $("#ngay_bat_dau_update_error").text("");
    }

    if (ngayKetThucStr.trim().length === 0) {
        $("#ngay_ket_thuc_update_error").text("Ngày kết thúc không được để trống");
        ++check;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#ngay_ket_thuc_update_error").text("Ngày kết thúc phải sau ngày bắt đầu");
        ++check;
    } else {
        $("#ngay_ket_thuc_update_error").text("");
    }

    if (check === 0) {
        let hocKyRequest = {};
        hocKyRequest["tenHocKy"] = tenHocKy;
        hocKyRequest["ngayBatDau"] = ngayBatDau.toLocaleString('en-CA', {hour12: false});
        hocKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleString('en-CA', {hour12: false});

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: hocKyApi + "/" + hocKyId,
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                window.open(hocKyView, '_self');
                $("#modal_update_hoc_ky").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
})
;

// function openModalRemoveHocKy(hocKyId) {
//     $("#modal_hoc_ky_remove").modal('show');
//     $.ajax({
//         type: "GET",
//         contentType: "application/json",
//         url: hocKyApi + "/" + hocKyId,
//         data: JSON.stringify(hocKyId),
//         dataType: 'json',
//         success: function () {
//             $("#remove_hoc_ky").val(hocKyId);
//         },
//         error: function (e) {
//             console.log("ERROR : ", e);
//         }
//     });
// }

function remove(id) {
    bootbox.confirm({
        title: "Xóa học kỳ?",
        message: "Bạn chắc chắn muốn xóa học kỳ này?",
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
                    url: hocKyApi + "/" + id,
                    data: JSON.stringify(id),
                    dataType: 'json',
                    success: function () {
                        window.open(hocKyView, '_self');
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                        if (e.responseJSON.message == 'Ton tai dot dang ky') {
                            bootbox.alert({
                                message: `<strong>Thất bại.</strong> Học kỳ đang tồn tại đợt đăng ký`,
                                backdrop: true,
                            });
                        } else {
                            bootbox.alert({
                                message: 'Xóa thất bại',
                                backdrop: true,
                            });
                        }
                    }
                });
            }
        }
    });
};