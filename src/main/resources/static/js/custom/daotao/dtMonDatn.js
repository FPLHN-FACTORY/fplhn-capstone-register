const monDatnApi = '/api/dao-tao/mon-datn';
const monDatnView = '/dao-tao/mon-datn';

$(document).ready(function () {
    $("#mon_datn_error").text("");
});

$("#form_create_mon_datn").submit(function (event) {
    event.preventDefault();

    let maMon = $("#maMon").val();
    let tenMonDatn = $("#tenMonDatn").val();
    let chuyenNganhId = $("#chuyenNganh").val();
    let monDatnRequest = {};
    monDatnRequest["maMon"] = maMon;
    monDatnRequest["tenMonDatn"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    if (maMon.trim().length === 0) {
        $("#mon_datn_error").text("Mã môn DATN không được để trống");
    }
    else if (tenMonDatn.trim().length === 0) {
        $("#mon_datn_error").text("Tên môn DATN không được để trống");
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: monDatnApi,
            data: JSON.stringify(monDatnRequest),
            dataType: 'json',
            success: function (e) {
                if (e.data == true) {
                    window.open(monDatnView, '_self');
                    $("#modal_create").modal("hide");
                } else {
                    alert(e.data.localizedMessage)
                }
            },
            error: function (e) {
                console.log("ERROR : " + e);
            }
        });
    }
});

function openModalUpdateMonDatn(monDatnId) {
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

$("#form_mon_datn_update").submit(function (event) {
    event.preventDefault();
    let maMon = $("#ma_mon_datn_update").val();
    let tenMonDatn = $("#ten_mon_datn_update").val();
    let monDatnId = $("#id_mon_datn_update").val();
    let monDatnRequest = {};
    monDatnRequest["maMon"] = maMon;
    monDatnRequest["tenMonDatn"] = tenMonDatn;
    if (maMon.length === 0) {
        $("#errorMessageUpdate").text("Mã môn DATN không được để trống");
    } else if (tenMonDatn.length === 0) {
        $("#errorMessageUpdate").text("Tên môn DATN không được để trống");
    } else {
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
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalRemoveMonDatn(monDatnId) {
    $("#modal_mon_datn_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: monDatnApi + "/" + monDatnId,
        data: JSON.stringify(monDatnId),
        dataType: 'json',
        success: function () {
            $("#remove_mon_datn").val(monDatnId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_mon_datn_delete").submit(function (event) {
    event.preventDefault();
    let monDatnId = $("#remove_mon_datn").val();
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: monDatnApi + "/" + monDatnId,
        data: JSON.stringify(monDatnId),
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