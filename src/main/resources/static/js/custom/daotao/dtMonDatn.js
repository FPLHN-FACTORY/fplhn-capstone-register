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
    let index = 0;
    if (maMon.trim().length === 0) {
        $("#errorMaMonUpdate").text("Ma mon DATN khong duoc de trong");
        index++;
    } else {
        $("#errorMaMonUpdate").text("");
    }
    if (tenMonDatn.trim().length === 0) {
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

function openModalImportMonDatn() {
    $("#modal_mon_datn_import").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: monDatnApi,
        data: JSON.stringify(),
        dataType: 'json',
        success: function () {
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

//$("#modal_mon_datn_import").submit(function (event) {
//    event.preventDefault();
//    let file = $("#fileupload").val();
//    console.log(file)
//    let f = file.replace(/.*[\/\\]/, '');
    // let file = 'D:\fpoly-datn\test.xlsx'
//    $.ajax({
//        type: "POST",
//        contentType: "application/json",
//        url: monDatnApi + "/import",
//        data: JSON.stringify(file),
//        dataType: 'json',
//        success: function () {
//            window.open(monDatnView, '_self');
//            $("#modal_mon_datn_remove").modal("hide");
//        },
//        error: function (e) {
//            console.log("ERROR : ", e);
//        }
//    });
//});