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
    let nhomMonId = $("#tenNhomMonDatn").val();
    let monDatnRequest = {};
    monDatnRequest["maMon"] = maMon;
    monDatnRequest["tenMonDatn"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    monDatnRequest["nhomMonDatnId"] = nhomMonId;
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
            $("#remove_mon_datn").text(e.responseJSON.message);
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

function clearModal() {
    $("#remove_mon_datn").text("");
    $("#ma_mon_datn_error").text("");
    $("#ten_mon_datn_error").text("");
    $("#errorMaMonUpdate").text("");
    $("#errorTenMonUpdate").text("");
}

$("#form_import_excel").submit(function (event) {
    event.preventDefault();
        $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                enctype: 'multipart/form-data',
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    $('#import_excel').modal("hide");
                    if (response.data.status == true) {
                        bootbox.alert({
                            title: "Import Excel",
                            message: "<div class='alert alert-success alert-dismissible fade show' "+
                                    " role='alert'><strong>SUCCESS !!! </strong><p>Import thành công</p></div>",
                            backdrop: true,
                            callback: function () {
                                reload();
                            }
                        });
                    } else {
                        let mess = "";
                        response.data.message.forEach(el => {
                            mess = mess + "</br>" + el;
                        });
                        bootbox.alert({
                            title: "Import Excel",
                            message: '<div class="alert alert-danger alert-dismissible fade show" role="alert"><strong>ERROR !!! </strong><div><p>'+ mess +'</p></div></div>',
                            backdrop: true,
                            callback: function () {
                                reload();
                            }
                        });
                    }
                },
                error: function (e) {
                }
            }
        );
});

function reload() {
    window.open('/dao-tao/mon-datn', '_self');
}

$("#inputGroupFile04").on("input", function () {
    if(!$("#inputGroupFile04").val().trim().length == 0) {
        $("#import").removeAttr('disabled');
        $("#import").css( 'cursor', 'pointer' );
    } else {
        $("#import").attr('disabled','disabled');
        $("#import").css( 'cursor', 'not-allowed');
    }
})
