const chuyenNganhApi = '/api/dao-tao/chuyen-nganh';
const chuyenNganhView = '/dao-tao/chuyen-nganh';

$(document).ready(function () {
    $("#chuyen_nganh_error").text("");
});

function clearData() {
    $("#ten-chuyen-nganh-search").val("");
}

function clearForm(){
    $("#tenChuyenNganhAdd").val("");
    $("#chuyen_nganh_create_error").text("");
    $("#chuyen_nganh_error").text("");
}
function checkCnbm(cnbm){
    if(cnbm != 0) {
        $("#tenCNBMhAdd").show();
        $("#create_chuyenNganh").show();
        $("#tenCNBMhUpdate").show();
        $("#CNBM_add_error").text("");
    }else{
        $("#create_chuyenNganh").hide();
        $("#tenCNBMhAdd").hide();
        $("#tenCNBMhUpdate").hide();
        $("#CNBM_add_error").text("Không còn chủ nhiệm bộ môn");
    }
}
function openModalCreateChuyenNganh(){
    $("#modal_create_chuyen_nganh").modal('show');
    $("#chuyen_nganh_add_error").text("");
    $("#CNBM_add_error").text("");
    let cnbm = $("#CNBM").val();
    checkCnbm(cnbm);
}

function create () {
    event.preventDefault();
    let tenChuyenNganh = $("#tenChuyenNganhAdd").val();
    let CNBMId = $("#tenCNBMhAdd").val();
    let chuyenNganhRequest = {};
    chuyenNganhRequest["chuNhiemBoMon"] = CNBMId;
    chuyenNganhRequest["tenChuyenNganh"] = tenChuyenNganh;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
            $("#chuyen_nganh_add_error").text("Tên chuyên ngành không được để trống");
            index ++;
    }

    if (CNBMId == null) {
        $("#CNBM_add_error").text("Không có chủ nhiệm bộ môn");
        index ++;
    }

    if(index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: chuyenNganhApi,
            data: JSON.stringify(chuyenNganhRequest),
            dataType: 'json',
            success: function () {
                bootbox.alert({
                    message: 'Thêm thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                if(e.responseJSON.message == 'Chuyen nganh da ton tai'){
                    $("#chuyen_nganh_add_error").text("Chuyên ngành đã tồn tại trong đợt");
                    $("#CNBM_add_error").text("");
                }else{
                    $("#CNBM_add_error").text("Chủ nhiệm bộ môn đang tồn tại trong đợt");
                    $("#chuyen_nganh_add_error").text("");
                }
            }
        });
    }
};

function openModalUpdateChuyenNganh(chuyenNganhId) {
    $("#modal_update_chuyen_nganh").modal('show');
    $("#chuyennganh_error").text("");
    $("#chuyennganhUpdate_error").text("");
    console.log('a');
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

function update() {
    event.preventDefault();
    let tenChuyenNganh = $("#tenChuyenNganhUpdate").val();
    let chuyenNganhId = $("#id_chuyen_nganh_update").val();
    let tenCNBM = $("#tenCNBMhUpdate").val();
    let chuyenNganhrequest = {};
    chuyenNganhrequest["tenChuyenNganh"] = tenChuyenNganh;
    chuyenNganhrequest["chuNhiemBoMon"] = tenCNBM;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
            $("#chuyennganh_Update_error").text("Tên chuyên ngành không được để trống");
            index ++;
    }

    if (tenCNBM == null) {
        $("#CNBM_update_error").text("Không có chủ nhiệm bộ môn");
        index ++;
    }

    if(index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: chuyenNganhApi + "/" + chuyenNganhId,
            data: JSON.stringify(chuyenNganhrequest),
            dataType: 'json',
            success: function () {
                $("#modal_update_chuyen_nganh").modal("hide");
                window.open(chuyenNganhView, '_self');
                bootbox.alert({
                    message: 'Cập nhật thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                if(e.responseJSON.message == 'Chuyen nganh da ton tai'){
                    $("#chuyennganh_Update_error").text("Chuyên ngành đã tồn tại trong đợt");
                    $("#chuyennganh_Update_error").text("");
                }
            }
        });
    }
};

function openModalRemoveChuyenNganh(chuyenNganhId) {
    $("#remove_chuyen_nganh").text("")
    $("#modal_chuyen_nganh_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function () {
            $("#remove_chuyen_nganh").val(chuyenNganhId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function deleteChuyenNganh() {
    event.preventDefault();
    let chuyenNganhId = $("#remove_chuyen_nganh").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function () {
            $("#modal_chuyen_nganh_remove").modal("hide");
            window.open(chuyenNganhView, '_self');
            bootbox.alert({
                message: 'Xóa thành công',
                  backdrop: true,
                  callback: function () {
                    window.open(chuyenNganhView, '_self');
                  }
            });
        },
        error: function (e) {
            $("#modal_chuyen_nganh_remove").modal("hide");
            bootbox.alert({
                message: '<div style="color:red;">Chuyên ngành có trong đợt đăng ký </div>',
                  backdrop: true,
                  callback: function () {
                    window.open(chuyenNganhView, '_self');
                  }
            });
        }
    });
};
