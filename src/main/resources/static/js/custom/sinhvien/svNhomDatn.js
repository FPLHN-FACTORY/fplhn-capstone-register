const svNhomDatnApi = '/api/sinh-vien';
const svNhomDatnView = '/sinh-vien';

var detailTenNhom = $("#detailTenNhom").val() == "Chưa có" ? "" :  $("#detailTenNhom").val();
var detailTenDeTai = $("#detailTenDeTai").val() == "Chưa có" ? "" : $("#detailTenDeTai").val();

function openModalDoiDeTai() {
    $("#inputTenDeTai").val(detailTenDeTai);
    $("#modal_doi_ten_de_Tai").modal('show');
}

function openModalChonGVHD() {
    $("#modal_chon_giang_vien").modal('show');
}

function doiTenDeTai() {
    $("#modal_doi_ten_de_Tai").modal('hide');
    let inputTenDeTai = $("#inputTenDeTai").val().trim();
    let updateTenDeTaiRequest = {};
    updateTenDeTaiRequest["tenDeTai"] = inputTenDeTai;
    var message = "";
    if (inputTenDeTai.length == 0) {
        message = "Bạn muốn để trống tên đề tài?";
    } else {
        message = "Bạn muốn đổi tên đề tài?";
    }

    bootbox.confirm({
        title: "Đổi tên đề tài",
        message: message,
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
                        console.log(responseData)
                        bootbox.alert({
                            message: 'Đổi đề tài thành công',
                            backdrop: true,
                            callback: function () {
                                detailTenDeTai = responseData.data;
                                $("#detailTenDeTai").val(detailTenDeTai == "" ? 'Chưa có' : responseData.data);
                            }
                        });
                    },
                    error: function (responseData) {
                        console.log(responseData);
                        bootbox.alert({
                            message: 'Đổi tên đề tài thất bại',
                            backdrop: true,
                        });
                    }
                });
            }
        }
    });
}

function openModalDoiTenNhom() {
    $("#inputTenNhom").val(detailTenNhom);
    $("#modal_doi_ten_nhom").modal('show');
}

function doiTenNhom() {
    $("#modal_doi_ten_nhom").modal('hide');
    let inputTenNhom = $("#inputTenNhom").val().trim();
    let updateTenNhomRequest = {};
    updateTenNhomRequest["tenNhom"] = inputTenNhom;
    var message = "";
    if (inputTenNhom.length == 0) {
        message = "Bạn muốn để trống tên nhóm?";
    } else {
        message = "Bạn muốn đổi tên nhóm?";
    }
    bootbox.confirm({
        title: "Đổi tên nhóm",
        message: message,
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
                    url: svNhomDatnApi + "/update-ten-nhom?tenNhom=" + inputTenNhom,
                    datatype: 'json',
                    data: JSON.stringify(updateTenNhomRequest),
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Đổi tên nhóm thành công',
                            backdrop: true,
                            callback: function () {
                                detailTenNhom = responseData.data;
                                $("#detailTenNhom").val(detailTenNhom == "" ? 'Chưa có' : responseData.data);
                                // window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        console.log(responseData);
                        bootbox.alert({
                            message: 'Đổi tên nhóm thất bại',
                            backdrop: true,
                        });
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

function exportWord() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: svNhomDatnApi + "/export-word",
        success: function (responseData) {
            window.open(svNhomDatnApi + "/export-word",'_self');
        },
        error: function (responseData) {
            console.log(responseData);
            bootbox.alert(responseData.responseJSON.message);
        }
    });
}
