const chuyenNganhApi = '/api/dao-tao/chuyen-nganh';
const chuyenNganhView = '/dao-tao/chuyen-nganh';

$(document).ready(function () {
    $("#chuyen_nganh_error").text("");
});

function clearData() {
    $("#ten-chuyen-nganh-search").val("");
}

$("#form_create_chuyen_nganh").submit(function (event) {
    event.preventDefault();
    let tenchuyenNganh = $("#tenChuyenNganhAdd").val();
    let CNBMId = $("#tenCNBMhAdd").val();
    let chuyenNganhRequest = {};
    chuyenNganhRequest["chuNhiemBoMon"] = CNBMId;
    chuyenNganhRequest["tenChuyenNganh"] = tenchuyenNganh;
    if (tenchuyenNganh.trim().length === 0) {
        $("#chuyen_nganh_error").text("Tên chuyên ngành không được để trống");
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: chuyenNganhApi,
            data: JSON.stringify(chuyenNganhRequest),
            dataType: 'json',
            success: function () {
                window.open(chuyenNganhView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalUpdateChuyenNganh(chuyenNganhId) {
    $("#modal_update_chuyen_nganh").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_chuyen_nganh_update").val(responseData.data.id);
            $("#tenChuyenNganhUpdate").val(responseData.data.tenChuyenNganh);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_chuyen_nganh_update").submit(function (event) {
    event.preventDefault();
    let tenchuyennganh = $("#tenChuyenNganhUpdate").val();
    let chuyenNganhId = $("#id_chuyen_nganh_update").val();
    let tenCNBM = $("#tenCNBMhUpdate").val();
    console.log(chuyenNganhId);
    let chuyenNganhrequest = {};
    chuyenNganhrequest["tenChuyenNganh"] = tenchuyennganh;
    chuyenNganhrequest["chuNhiemBoMon"] = tenCNBM;
    if (tenchuyennganh.trim().length === 0) {
        $("#chuyennganh_error").text("Tên Chuyen Nganh không được để trống");
    } else {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: chuyenNganhApi + "/" + chuyenNganhId,
            data: JSON.stringify(chuyenNganhrequest),
            dataType: 'json',
            success: function () {
                window.open(chuyenNganhView, '_self');
                $("##modal_update_chuyen_nganh").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalRemoveChuyenNganh(chuyenNganhId) {
    $("#modal_chuyen_nganh_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function () {
            $("#remove_chuyen_nganh").val(chuyenNganhId);
            console.log("abcc");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_chuyen_nganh_delete").submit(function (event) {
    event.preventDefault();
    let chuyenNganhId = $("#remove_chuyen_nganh").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function () {
            window.open(chuyenNganhView, '_self');
            $("#modal_chuyen_nganh_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});