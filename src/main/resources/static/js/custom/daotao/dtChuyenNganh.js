const chuyenNganhApi = '/api/dao-tao/chuyen-nganh';
const chuyenNganhView = '/dao-tao/chuyen-nganh';

$(document).ready(function () {
    $("#chuyen_nganh_error").text("");
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function clearData() {
    $("#ten-chuyen-nganh-search").val("");
}

function clearForm() {
    $("#tenChuyenNganhAdd").val("");
    $("#chuyen_nganh_create_error").text("");
    $("#chuyen_nganh_error").text("");
}

function openModalCreateChuyenNganh() {
    $("#modal_create_chuyen_nganh").modal('show');
    $("#chuyen_nganh_add_error").text("");
    $("#CNBM_add_error").text("");
    $("#tenChuyenNganhAdd").val('');
    let cnbm = $("#CNBM").val();
    if (cnbm != 0) {
        $("#tenCNBMhAdd").show();
        $("#create_chuyenNganh").show();
    } else {
        $("#create_chuyenNganh").hide();
        $("#tenCNBMhAdd").hide();
        $("#CNBM_add_error").text("Không còn chủ nhiệm bộ môn");
    }
}

function create() {
    let tenChuyenNganh = $("#tenChuyenNganhAdd").val();
    let CNBMId = $("#tenCNBMhAdd").val();
    let chuyenNganhRequest = {};
    chuyenNganhRequest["chuNhiemBoMon"] = CNBMId;
    chuyenNganhRequest["tenChuyenNganh"] = tenChuyenNganh;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
        $("#chuyen_nganh_add_error").text("Tên chuyên ngành không được để trống");
        index++;
    }

    if (CNBMId == null) {
        $("#CNBM_add_error").text("Không có chủ nhiệm bộ môn");
        index++;
    }

    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: chuyenNganhApi,
            data: JSON.stringify(chuyenNganhRequest),
            dataType: 'json',
            success: function () {
                $("#modal_create_chuyen_nganh").modal("hide");
                bootbox.alert({
                    message: 'Thêm thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message == 'Chuyen nganh da ton tai') {
                    $("#chuyen_nganh_add_error").text("Chuyên ngành đã tồn tại trong đợt");
                    $("#CNBM_add_error").text("");
                }
            }
        });
    }
};

function openModalUpdateChuyenNganh(chuyenNganhId, tenCNBM) {
    $("#modal_update_chuyen_nganh").modal('show');
    $("#chuyennganh_Update_error").text("");
    $("#CNBM_update_error").text("");
    let cnbm = $("#chuNhiemBoMon").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_chuyen_nganh_update").val(responseData.data.id);
            $("#tenChuyenNganhUpdate").val(responseData.data.tenChuyenNganh);
            $("#CNBMHienTai").val(responseData.data.chuNhiemBoMon);
            $("#CNBMHienTai").text(tenCNBM);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let tenChuyenNganh = $("#tenChuyenNganhUpdate").val();
    console.log(tenChuyenNganh);
    let chuyenNganhId = $("#id_chuyen_nganh_update").val();
    let tenCNBM = $("#tenCNBMhUpdate").val();
    let chuyenNganhrequest = {};
    chuyenNganhrequest["tenChuyenNganh"] = tenChuyenNganh;
    chuyenNganhrequest["chuNhiemBoMon"] = tenCNBM;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
        $("#chuyennganh_Update_error").text("Tên chuyên ngành không được để trống");
        index++;
    }

    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: chuyenNganhApi + "/" + chuyenNganhId,
            data: JSON.stringify(chuyenNganhrequest),
            dataType: 'json',
            success: function () {
                $("#modal_update_chuyen_nganh").modal("hide");
                bootbox.alert({
                    message: 'Cập nhật thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message == 'Chuyen nganh da ton tai') {
                    $("#chuyennganh_Update_error").text("Chuyên ngành đã tồn tại trong đợt");
                }
            }
        });
    }
};

function remove(chuyenNganhId) {
    $("#remove_chuyen_nganh").text("")
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
                    url: chuyenNganhApi + "/" + chuyenNganhId,
                    data: JSON.stringify(chuyenNganhId),
                    dataType: 'json',
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function (e) {
                                window.open(chuyenNganhView, '_self');
                            }
                        });
                    },
                    error: function (e) {
                        if (e.responseJSON.message != null) {
                            bootbox.alert({
                                message: '<div style="color: red">Chuyên ngành đang tồn tại trong đợt đăng ký không thể xóa</div>',
                                backdrop: true,
                            });
                        }
                    }
                });
            }
        }
    });
}
