const monDatnApi = '/api/dao-tao/mon-datn';
const monDatnView = '/dao-tao/mon-datn';

$(document).ready(function () {
    $("#mon_datn_error").text("");
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(monDatnView);
});

function openModalCreate() {
    $("#maMon").val("");
    $("#tenMonDatn").val("");
    $("#ma_mon_datn_error").text("");
    $("#ten_mon_datn_error").text("");
    $("#modal_create").modal('show');
}

function create() {
    let maMon = $("#maMon").val().trim();
    let tenMonDatn = $("#tenMonDatn").val().trim();
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
                    callback: function () {
                        window.open(monDatnView, '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message == 'Ma mon da ton tai') {
                    $("#ma_mon_datn_error").text("Mã môn đã tồn tại");
                }
            }
        });
    }
};

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
            $("#chuyen_nganh_update").val(responseData.data.chuyenNganhId);
            // getNhomMon(responseData.data.chuyenNganhId, 2);
            if (responseData.data.nhomMonDatnId == monDatnId) {
                getNhomMon(responseData.data.chuyenNganhId, 2, 1);
            } else {
                getNhomMon(responseData.data.chuyenNganhId, 2, responseData.data.nhomMonDatnId);
                $("#ten_nhom_mon_datn_update").val(responseData.data.nhomMonDatnId);
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let maMon = $("#ma_mon_datn_update").val().trim();
    let tenMonDatn = $("#ten_mon_datn_update").val().trim();
    let monDatnId = $("#id_mon_datn_update").val();
    let chuyenNganh = $("#chuyen_nganh_update").val();
    let nhomMon = $("#ten_nhom_mon_datn_update").val();
    let monDatnRequest = {};
    monDatnRequest["maMon"] = maMon;
    monDatnRequest["tenMonDatn"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganh;
    monDatnRequest["nhomMonDatnId"] = nhomMon;
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
                    callback: function () {
                        window.open(monDatnView, '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message != null) {
                    $("#errorMaMonUpdate").text("Mã môn đã tồn tại");
                }
            }
        });
    }
};

function openModalRemoveMonDatn(monDatnId) {
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
                    url: monDatnApi + "/" + monDatnId,
                    data: JSON.stringify(monDatnId),
                    dataType: 'json',
                    success: function (responseData) {
                            bootbox.alert({
                                message: 'Xóa thành công',
                                backdrop: true,
                                callback: function (e) {
                                    window.open(monDatnView, '_self');
                                }
                            });
                    },
                    error: function (e) {
                        if (e.responseJSON.message != null) {
                            bootbox.alert({
                                message: '<div style="color: red">Môn đang có phân công, sinh viên. Không thể xóa</div>',
                                backdrop: true,
                            });
                        }
                    }
                });
            }
        }
    });
}

function openImport() {
    $("#labelFile").text('Choose file');
    $("#inputGroupFile04").val('');
    $("#errImport").text('');
    $("#import_excel").modal("show")
}

function importExcel () {
    const newFile = $("#inputGroupFile04").val();

    if(newFile.trim().length === 0) {
        $("#errImport").text("Bạn hãy chọn file");
        return;
    }

    if($("#inputGroupFile04")[0].files[0].size > 104857600) {
        $("#errImport").text("File quá hơn 104857600 byte");
        return;
    }

    if(newFile.substring(newFile.lastIndexOf(".")+1, newFile.length) !== "xlsx") {
        $("#errImport").text("Không đúng định dạng xlsx");
        return;
    }
    var importDocoment = document.getElementById("form_import_excel")
    $.ajax({
            type: 'POST',
            url: "/api/dao-tao/mon-datn/import",
            enctype: $("#form_import_excel").attr('action'),
            data: new FormData(importDocoment),
            processData: false,
            contentType: false,
            success: function (response) {
                $('#import_excel').modal("hide");
                if (response.data.status == true) {
                    bootbox.alert({
                        title: "Thành công",
                        message: "<strong>Import thành công</strong>",
                        backdrop: true,
                        callback: function () {
                            window.open(monDatnView, '_self');
                        }
                    });
                } else {
                    bootbox.alert({
                        title: "Thất bại",
                        message: '<strong>'+ response.data.message +'</strong>',
                        backdrop: true,
                        callback: function () {
                            window.open(monDatnView, '_self');
                        }
                    });
                }
            },
            error: function (e) {
            }
        }
    );
};

function getNhomMon(chuyenNganhId, type, nhomMonHienTai) {
    $.ajax({
        type: "GET",
        url: monDatnApi + "/get-nhom-mon?chuyenNganhId=" + chuyenNganhId,
        success: function (data) {
            var text = `<option value="" selected>Môn độc lập</option>`;
            data.data.map(function (item) {
                if (nhomMonHienTai == item.id && nhomMonHienTai != null) {
                    text += `<option value="${item.id}" selected>${item.tenMonDatn}</option>`
                } else {
                    text += `<option value="${item.id}" >${item.tenMonDatn}</option>`
                }
            })
            if (type == 1) {
                $('#tenNhomMonDatn').html(text);
            }
            if (type == 2) {
                $('#ten_nhom_mon_datn_update').html(text);
            }
        }
    })
}

$('#chuyenNganh').change(function () {
    var chuyenNganhId = $(this).val();
    getNhomMon(chuyenNganhId, 1, null);
})

$('#chuyen_nganh_update').change(function () {
    var chuyenNganhId = $(this).val();
    getNhomMon(chuyenNganhId, 2, null);
})