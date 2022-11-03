const monDatnApi = '/api/dao-tao/nhom-mon-datn';
const monDatnView = '/dao-tao/nhom-mon-datn';

$(document).ready(function () {
    $("#mon_datn_error").text("");
});

$("#form_create_nhom_mon_datn").submit(function (event) {
    event.preventDefault();
    let maMon = $("#maMon").val();
    let tenMonDatn = $("#tenMonDatn").val();
    let chuyenNganhId = $("#tenChuyenNganh").val();
    let monDatnRequest = {};
    monDatnRequest["maMon"] = maMon;
    monDatnRequest["tenMonDatn"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    let index = 0;
    if (maMon.trim().length === 0) {
        $("#ma_mon_datn_error").text("Ma mon DATN khong duoc de trong");
        index++;
    } else {
        $("#ma_mon_datn_error").text("");
    }
    if (tenMonDatn.trim().length === 0) {
        $("#ten_mon_datn_error").text("Ten mon DATN khong duoc de trong");
        index++;
    } else {
        $("#ten_mon_datn_error").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: monDatnApi,
            data: JSON.stringify(monDatnRequest),
            dataType: 'json',
            success: function () {
                window.open(monDatnView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                $("#ma_mon_datn_error").text(e.responseJSON.message);
            }
        });
    }
});

function openModalUpdateNhomMonDatn(monDatnId) {
    $("#modal_update_mon_datn").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: monDatnApi + "/" + monDatnId,
        data: JSON.stringify(monDatnId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_mon_datn_update").val(responseData.data.id);
            $("#ma_mon_datn_update").val(responseData.data.maMon);
            $("#ten_mon_datn_update").val(responseData.data.tenMonDatn);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_nhom_mon_datn_update").submit(function (event) {
    event.preventDefault();
    let maNhomMon = $("#ma_mon_datn_update").val();
    let tenNhomMonDatn = $("#ten_mon_datn_update").val();
    let monDatnId = $("#id_mon_datn_update").val();
    let chuyenNganhId = $("#tenChuyenNganhUpdate").val();
    let monDatnRequest = {};
    monDatnRequest["maNhomMon"] = maNhomMon;
    monDatnRequest["tenNhomMon"] = tenNhomMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    monDatnRequest["monDatnId"] = monDatnId;
    let index = 0;
    if (maNhomMon.trim().length === 0) {
        $("#errorMaMonUpdate").text("Ma mon DATN khong duoc de trong");
        index++;
    } else {
        $("#errorMaMonUpdate").text("");
    }
    if (tenNhomMonDatn.trim().length === 0) {
        $("#errorTenMonUpdate").text("Ten mon DATN khong duoc de trong");
        index++;
    } else {
        $("#errorTenMonUpdate").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: monDatnApi + "/" + monDatnId,
            data: JSON.stringify(monDatnRequest),
            dataType: 'json',
            success: function () {
                window.open(monDatnView, '_self');
                $("#modal_update_mon_datn").modal("hide");
            },
            error: function (e) {
                $("#errorMaMonUpdate").text(e.responseJSON.message);
            }
        });
    }
});

function openModalRemoveNhomMonDatn(monDatnId) {
    $("#modal_mon_datn_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: monDatnApi + "/" + monDatnId,
        dataType: 'json',
        success: function () {
            $("#remove_mon_datn").val(monDatnId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_nhom_mon_datn_delete").submit(function (event) {
    event.preventDefault();
    let monDatnId = $("#remove_mon_datn").val();
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: monDatnApi + "/" + monDatnId,
        dataType: 'json',
        success: function () {
            window.open(monDatnView, '_self');
            $("#modal_mon_datn_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});
