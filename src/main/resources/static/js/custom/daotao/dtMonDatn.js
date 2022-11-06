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
        $("#ma_mon_datn_error").text("Mã môn không được để trống");
        index++;
    } else {
        $("#ma_mon_datn_error").text("");
    }
    if (tenMonDatn.trim().length === 0) {
        $("#ten_mon_datn_error").text("Tên môn không được để trống");
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
                $("#modal_create").modal("hide");
                bootbox.alert({
                    message: 'Thêm thành công',
                    backdrop: true,
                    callback: function (e) {
                        window.open(monDatnView, '_self');
                    }
                });
            },
            error: function (e) {
                console.log(e);
                $("#ma_mon_datn_error").text("Mã môn đã tồn tại");
            }
        });
    }
});

function openModalUpdateMonDatn(monDatnId) {
    $("#errorMaMonUpdate").text("");
    $("#errorTenMonUpdate").text("");
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
        $("#errorMaMonUpdate").text("Mã môn không được để trống");
        index++;
    } else {
        $("#errorMaMonUpdate").text("");
    }
    if (tenMonDatn.trim().length === 0) {
        $("#errorTenMonUpdate").text("Tên môn không được để trống");
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
                $("#modal_update_mon_datn").modal("hide");
                bootbox.alert({
                    message: 'Update thành công',
                    backdrop: true,
                    callback: function (e) {
                        window.open(monDatnView, '_self');
                    }
                });
            },
            error: function (e) {
                $("#errorMaMonUpdate").text("Mã môn đã tồn tại ");
            }
        });
    }
});

function openModalRemoveMonDatn(monDatnId) {
    $("#remove_mon_datn").text("");
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
            $("#modal_mon_datn_remove").modal("hide");
            bootbox.alert({
                message: 'Xóa thành công',
                backdrop: true,
                callback: function (e) {
                    window.open(monDatnView, '_self');
                }
            });
        },
        error: function (e) {
            $("#remove_mon_datn").text("Không xóa được (môn đang tồn tại trong đợt đăng ký)");
        }
    });
});

function clearModal(){
    $("#ma_mon_datn_error").text("");
    $("#ten_mon_datn_error").text("");
}

$("#form_import_excel").submit(function (event) {
    event.preventDefault();
    const newFile = $("#inputGroupFile04").val();
    if(newFile.trim().length === 0) {
        $("#errImport").text("Bạn hãy chọn file");
        return;
    }

    if(newFile.substring(newFile.lastIndexOf(".")+1, newFile.length) !== "xlsx") {
        $("#errImport").text("Không đúng định dạng xlsx");
        return;
    }
    $.ajax({
            type: 'POST',
            url: $(this).attr('action'),
            enctype: 'multipart/form-data',
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (response) {
                $('#import_excel').modal("hide");
                console.log(response.data)
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
