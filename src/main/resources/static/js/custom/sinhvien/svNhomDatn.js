const svNhomDatnApi = '/api/sinh-vien';
const svNhomDatnView = '/sinh-vien';

function openModalDoiDeTai(){
    $("#modal_doi_ten_de_Tai").modal('show');
}

function openModalChonGVHD(){
    $("#modal_chon_giang_vien").modal('show');
}

function doiTenDeTai() {
    $("#modal_doi_ten_de_Tai").modal('hide');
    let inputTenDeTai = $("#inputTenDeTai").val();
    let updateTenDeTaiRequest = {};
    updateTenDeTaiRequest["tenDeTai"] = inputTenDeTai;
    bootbox.confirm({
        title: "Đổi tên đề tài",
        message: "Bạn muốn đổi tên đề tài?",
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
                    type: "PUT",
                    contentType: "application/json",
                    url: svNhomDatnApi + "/update-ten-de-tai",
                    data: JSON.stringify(updateTenDeTaiRequest),
                    datatype: 'json',
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Đổi đề tài thành công',
                            backdrop: true,
                            callback: function () {
                                window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        console.log(responseData);
                        $("#errorTenDeTai").text(responseData.responseJSON.message);
                    }
                });
            }
        }
    });
}

function confirmRoiNhom(soThanhVien) {
    let check = false;
    if (soThanhVien == 1) {
        bootbox.confirm({
            title: "Rời nhóm",
            message: "Nhóm sẽ bị xóa nếu bạn rời nhóm. Bạn có muốn tiếp tục rời nhóm?",
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
                    roiNhom();
                }
            }
        });
    } else {
        bootbox.confirm({
            title: "Rời nhóm",
            message: "Bạn muốn rời nhóm?",
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
                    roiNhom();
                }
            }
        });
    }

}

function roiNhom() {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: svNhomDatnApi + "/roi-nhom",
        success: function (responseData) {
            bootbox.alert('Rời nhóm thành công', function () {
                    window.open(svNhomDatnView, '_self');
                }
            );
        },
        error: function (responseData) {
            console.log(responseData);
            alert(responseData.responseJSON.message);
        }
    });
}

function chuyenQuyenTruongNhom(idTruongNhomMoi, tenTruongNhomMoi) {
    bootbox.confirm({
        title: "Chuyển quyền trưởng nhóm",
        message: "Bạn muốn chuyển quyền trưởng nhóm cho " + tenTruongNhomMoi + "?",
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
                    type: "PUT",
                    contentType: "application/json",
                    url: svNhomDatnApi + "/chuyen-quyen-truong-nhom?idTruongNhomMoi=" + idTruongNhomMoi,
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Bạn đã chuyển quyền trưởng nhóm cho ' + tenTruongNhomMoi,
                            backdrop: true,
                            callback: function () {
                                window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        console.log(responseData);
                        alert(responseData.responseJSON.message);
                    }
                });
            }
        }
    });
}

function chonGiangVienHuongDan() {
    let idGiangVien = $('#chonGiangVien').val();
    $("#modal_chon_giang_vien").modal('hide');
    bootbox.confirm({
        title: "Đổi giảng viên",
        message: "Bạn chắc chắn muốn đổi sang giảng viên này?",
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
                    type: "PUT",
                    contentType: "application/json",
                    url: svNhomDatnApi + "/chon-giang-vien?idGiangVien=" + idGiangVien,
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Đã cập nhật giảng viên',
                            backdrop: true,
                            callback: function () {
                                window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        console.log(responseData);
                        alert(responseData.responseJSON.message);
                    }
                });
            }
        }
    });
}