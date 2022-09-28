const svNhomDatnApi = '/api/sinh-vien';
const svNhomDatnView = '/sinh-vien';

function guiYeuCauPheDuyet(id){
    var check = confirm("Bạn muốn gửi yêu cầu phê duyệt?");
    if (check == true) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: svNhomDatnApi + "/" + id,
            dataType: 'json',
            success: function (responseData) {
                $("#messageSuccess").text("Gửi yêu cầu phê duyệt thành công");
                $("#modal_success").modal('show');
            },
            error: function (e) {
                console.log("ERROR : ", e.message);
            }
        });
    }
}

function openModalDoiTenDeTai(){
    $("#modal_doi_ten_de_Tai").modal("show");
}

function doiTenDeTai() {
    let inputTenDeTai = $("#inputTenDeTai").val();
    let updateTenDeTaiRequest = {};
    updateTenDeTaiRequest["tenDeTai"] = inputTenDeTai;
    let checkConfirm = confirm("Bạn muốn đổi tên đề tài?");
    if (checkConfirm == true) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: svNhomDatnApi + "/update-ten-de-tai",
            data: JSON.stringify(updateTenDeTaiRequest),
            datatype: 'json',
            success: function (responseData) {
                $("#modal_doi_ten_de_Tai").modal('hide');
                $("#messageSuccess").text("Đổi đề tài thành công");
                $("#modal_success").modal('show');
            },
            error: function (responseData) {
                console.log(responseData);
                $("#errorTenDeTai").text(responseData.responseJSON.message);
            }
        });
    }
}

$("#form_success").submit(function (event) {
    event.preventDefault();
    window.open(svNhomDatnView, '_self');
})

$("#form_confirm").submit(function (event) {
    event.preventDefault();
    window.open(svNhomDatnView, '_self');
})

function roiNhom(soThanhVien){
    let check = -1;
    let checkConfirm = confirm("Bạn muốn rời nhóm?");
    if (checkConfirm == true){
        check += 1;
    }
    if (soThanhVien == 1) {
        let checkConfirmXoaNhom = confirm("Nhóm sẽ bị xóa nếu bạn rời nhóm\n Bạn có muốn tiếp tục rời nhóm?");
        check != 1;
    }

    if (check > -1) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: svNhomDatnApi + "/roi-nhom",
            success: function (responseData) {
                $("#modal_doi_ten_de_Tai").modal('hide');
                $("#messageSuccess").text("Rời nhóm thành công");
                $("#modal_success").modal('show');
            },
            error: function (responseData) {
                console.log(responseData);
                alert(responseData.responseJSON.message);
            }
        });
    }
}

function chuyenQuyenTruongNhom(idTruongNhomMoi, tenTruongNhomMoi){
    let checkConfirm = confirm("Bạn muốn chuyển quyển trưởng nhóm cho: " + tenTruongNhomMoi + "?");
    if (checkConfirm == true) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: svNhomDatnApi + "/chuyen-quyen-truong-nhom?idTruongNhomMoi=" + idTruongNhomMoi,
            success: function (responseData) {
                $("#modal_doi_ten_de_Tai").modal('hide');
                $("#messageSuccess").text("Đã chuyển quyền trưởng nhóm cho " + tenTruongNhomMoi);
                $("#modal_success").modal('show');
            },
            error: function (responseData) {
                console.log(responseData);
                alert(responseData.responseJSON.message);
            }
        });
    }
}

