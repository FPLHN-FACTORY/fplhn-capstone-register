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
    let hocKyRequest = {};
    hocKyRequest["tenHocKy"] = tenHocKy;

    if (tenHocKy.length === 0) {
        $("#hoc_ky_error").text("Tên học kỳ không được để trống");
    } else if (tenHocKy.length < 6) {
        $("#hoc_ky_error").text("Tên học kỳ tối thiếu 6 ký tự");
    } else {
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
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi + "/" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_hoc_ky_update").val(responseData.data.id);
            $("#ten_hoc_ky_update").val(responseData.data.tenHocKy);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_hoc_ky_update").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#ten_hoc_ky_update").val();
    let hocKyId = $("#id_hoc_ky_update").val();
    let hocKyRequest = {};
    hocKyRequest["tenHocKy"] = tenHocKy;
    if (tenHocKy.length === 0) {
        $("#errorMessageUpdate").text("Tên học kỳ không được để trống");
    } else if (tenHocKy.length < 6) {
        $("#errorMessageUpdate").text("Tên học kỳ tối thiếu 6 ký tự");
    } else {
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
});

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