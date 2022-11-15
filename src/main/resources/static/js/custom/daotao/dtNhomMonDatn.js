const nhomMonDatnApi = '/api/dao-tao/nhom-mon-datn';
const nhomMonDatnView = '/dao-tao/nhom-mon-datn';

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(nhomMonDatnView);
});

function openModalCreate() {
    $("#maNhomMon").val('');
    $("#tenNhomMon").val('');
    $("#ma_mon_datn_error").text('');
    $("#ten_mon_datn_error").text('');
    $("#chuyen_nganh_error").text('');
    $("#modal_create").modal('show');
}

function createNhomMonDatn() {
    let maMon = $("#maNhomMon").val();
    let tenMonDatn = $("#tenNhomMon").val();
    let chuyenNganhId = $("#chuyenNganh").val();
    let monDatnRequest = {};
    monDatnRequest["maNhomMon"] = maMon;
    monDatnRequest["tenNhomMon"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    let index = 0;
    if (maMon.trim().length === 0) {
        $("#ma_mon_datn_error").text("Mã nhóm môn không được để trống");
        index++;
    } else {
        $("#ma_mon_datn_error").text("");
    }
    if (tenMonDatn.trim().length === 0) {
        $("#ten_mon_datn_error").text("Tên nhóm môn không được để trống");
        index++;
    } else {
        $("#ten_mon_datn_error").text("");
    }
    if (chuyenNganhId === '') {
        $("#chuyen_nganh_error").text("Mời chọn chuyên ngành");
        index++;
    } else {
        $("#chuyen_nganh_error").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: nhomMonDatnApi,
            data: JSON.stringify(monDatnRequest),
            dataType: 'json',
            success: function () {
                window.open(nhomMonDatnView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                if (e.responseJSON.message === 'Ma nhom mon da ton tai') {
                    $("#ma_mon_datn_error").text('Mã nhóm đã tồn tại');
                }
            }
        });
    }
}

function openModalUpdateNhomMonDatn(monDatnId) {
    $("#errorMaMonUpdate").text('');
    $("#errorTenMonUpdate").text('');
    $("#modal_update_mon_datn").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomMonDatnApi + "/" + monDatnId,
        data: JSON.stringify(monDatnId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_nhom_mon_datn_update").val(responseData.data.id);
            $("#ma_nhom_mon_datn_update").val(responseData.data.maMon);
            $("#ten_nhom_mon_datn_update").val(responseData.data.tenMonDatn);
            $("#chuyenNganhUpdate").val(responseData.data.chuyenNganhId)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function updateNhomMonDatn() {
    let maNhomMon = $("#ma_nhom_mon_datn_update").val();
    let tenNhomMonDatn = $("#ten_nhom_mon_datn_update").val();
    let nhomMonDatnId = $("#id_nhom_mon_datn_update").val();
    let chuyenNganhId = $("#chuyenNganhUpdate").val();
    let monDatnRequest = {};
    monDatnRequest["maNhomMon"] = maNhomMon;
    monDatnRequest["tenNhomMon"] = tenNhomMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    let index = 0;
    if (maNhomMon.trim().length === 0) {
        $("#errorMaMonUpdate").text("Mã nhóm môn không được để trống");
        index++;
    } else {
        $("#errorMaMonUpdate").text("");
    }
    if (tenNhomMonDatn.trim().length === 0) {
        $("#errorTenMonUpdate").text("Tên nhóm môn không được để trống");
        index++;
    } else {
        $("#errorTenMonUpdate").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: nhomMonDatnApi + "/" + nhomMonDatnId,
            data: JSON.stringify(monDatnRequest),
            dataType: 'json',
            success: function () {
                window.open(nhomMonDatnView, '_self');
                $("#modal_update_mon_datn").modal("hide");
            },
            error: function (e) {
                if (e.responseJSON.message === 'Ma nhom mon da ton tai') {
                    $("#errorMaMonUpdate").text('Mã nhóm đã tồn tại');
                }
            }
        });
    }
}

function deleteNhomMonDatn(nhomMonDatnId) {
    bootbox.confirm({
        title: "Xóa nhóm môn",
        message: "Bạn muốn xóa nhóm môn này?",
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
                    url: nhomMonDatnApi + "/" + nhomMonDatnId,
                    dataType: 'json',
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                window.open(nhomMonDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        if (responseData.responseJSON.message === 'Nhom mon dang ton tai mon') {
                            bootbox.alert({
                                message: 'Đang có môn trong nhóm môn này. Không thể xóa',
                                backdrop: true,
                                callback: function () {
                                    window.open(nhomMonDatnView, '_self');
                                }
                            });
                        }
                    }
                });
            }
        }
    });
}

function openModalChiTietNhomMonDatn(nhomMonDatnId) {
    $("#modal_nhom_mon_chi_tiet").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomMonDatnApi + "/detail/" + nhomMonDatnId,
        dataType: 'json',
        success: function (responseData) {
            $("#data").html('');
            $("#table_detail").html('');
            if (responseData.data.length > 0) {
                $("#table_detail").html(responseData.data.map(function (item) {
                    return `
               <tr>
                   <td >${item.stt}</td>
                   <td >${item.maMon}</td>
                   <td >${item.tenMon}</td>
               </tr>
               `
                }))
            } else {
                $("#data").html('<h3>Chưa có môn nào trong nhóm này</h3>');
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}