const svNhomDatnApi = '/api/sinh-vien';
const svNhomDatnView = '/sinh-vien';

var detailTenNhom = $("#detailTenNhom").val() == "Chưa có" ? "" :  $("#detailTenNhom").val();
var detailTenDeTai = $("#detailTenDeTai").val() == "Chưa có" ? "" : $("#detailTenDeTai").val();

function openModalHistory(page, size, maNhom) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: svNhomDatnApi + "/history?page=" + page + "&size=" + size + "&maNhom=" + maNhom,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.content.length == 0) {
                $("#listHistory").html(``);
                $("#pageHistory").html('');
                $("#emptyHistory").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#emptyHistory").html(``);
                $("#listHistory").html(responseData.data.content.map(function (element, index) {
                    return `
                <tr class="table-light">                 
                      <td class="text-center align-middle">${element.stt}</td>
                      <td class="text-center align-middle">${element.mail}</td>
                      <td class="text-center align-middle">${element.createDate}</td>
                      <td class="text-center align-middle">${element.content}</td>
                      <td class="text-center align-middle">${element.author}</td>
                </tr>
               `
                }));

                var count = responseData.data.totalPages;
                if (responseData.data.totalPages <= 1) {
                    $("#pageHistory").html('');
                } else {
                    let templatePageMid = ``;
                    let templatePageBefore = page === 0 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistory(${page - 1}, ${size}, ${maNhom})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" :  `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistory(${page + 1}, ${size}, ${maNhom})"><img src="/img/next.svg"></a>
                </li>
                `;
                    let templateHtml = ``;
                    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
                    $("#pageHistory").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
                }
            };
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

// function openModalHistoryDownload() {
//     $.ajax({
//         type: "GET",
//         contentType: "application/json",
//         url: canBoApi + "/download-log",
//         success: function (responseData) {
//             var blob = new Blob([responseData], { type: "text/csv" });
//             var url = window.URL.createObjectURL(blob);
//             var a = document.createElement("a");
//             a.href = url;
//             a.download = "data.csv";
//             document.body.appendChild(a);
//             a.click();
//
//             window.URL.revokeObjectURL(url);
//         },
//         error: function (e) {
//             if (e.status === 404) {
//                 bootbox.alert({
//                     message: "Không tìm thấy file trong hệ thống",
//                     backdrop: true
//                 });
//             } else {
//                 console.error("Lỗi khi tải file CSV: " + e.statusText);
//             }
//         }
//     });
// }

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
                                // $("#detailTenDeTai").val(detailTenDeTai == "" ? 'Chưa có' : responseData.data);
                                window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (responseData) {
                        if(e.responseJSON.message === 'Het han quyen sinh vien') {
                            bootbox.alert({
                                message: 'Hết hạn quyền sinh viên',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Khong tim thay sinh vien nao') {
                            bootbox.alert({
                                message: 'Không tìm thấy sinh viên nào',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Sinh vien khong co nhom') {
                            bootbox.alert({
                                message: 'Sinh viên không có nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Khong phai truong nhom') {
                            bootbox.alert({
                                message: 'Không phải trưởng nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Trang thai nhom khong thoa man') {
                            bootbox.alert({
                                message: 'Trạng thái nhóm không thỏa mãn',
                                backdrop: true,
                            });
                        }
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
                                // $("#detailTenNhom").val(detailTenNhom == "" ? 'Chưa có' : responseData.data);
                                window.open(svNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (e) {
                        if(e.responseJSON.message === 'Sinh vien khong phai truong nhom'){
                            bootbox.alert({
                                message: 'Sinh viên không phải trưởng nhóm',
                                backdrop: true,
                            });
                        }
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
        error: function (e) {
            if(e.responseJSON.message === 'Het han quyen sinh vien'){
                bootbox.alert('Hết hạn quyền sinh viên', function () {
                    }
                );
            }
            if(e.responseJSON.message === 'Khong tim thay sinh vien nao'){
                bootbox.alert('Không tìm thấy sinh viên nào', function () {
                    }
                );
            }
            if(e.responseJSON.message === 'Sinh vien khong co nhom'){
                bootbox.alert('Sinh viên không có nhóm', function () {
                    }
                );
            }
            if(e.responseJSON.message === 'Nhom du an tot nghiep not exist'){
                bootbox.alert('Nhóm dự án tốt nghiệp không tồn tại', function () {
                    }
                );
            }
            if(e.responseJSON.message === 'Chua du 12 tieng trong nhom'){
                bootbox.alert('Chưa đủ 12 tiếng trong nhóm', function () {
                    }
                );
            }
            if(e.responseJSON.message === 'Roi nhom khi la truong nhom'){
                bootbox.alert('Rời nhóm khi là trưởng nhóm', function () {
                    }
                );
            }
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
                    error: function (e) {
                        if(e.responseJSON.message === 'Het han quyen sinh vien') {
                            bootbox.alert({
                                message: 'Hết hạn quyền sinh viên',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Khong tim thay sinh vien nao') {
                            bootbox.alert({
                                message: 'Không tìm thấy sinh viên nào',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Sinh vien khong co nhom') {
                            bootbox.alert({
                                message: 'Sinh viên không có nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Khong phai truong nhom') {
                            bootbox.alert({
                                message: 'Không phải trưởng nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Sinh vien dang la truong nhom') {
                            bootbox.alert({
                                message: 'Sinh viên đang là trưởng nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Truong nhom moi khong cung nhom') {
                            bootbox.alert({
                                message: 'Trưởng nhóm mới không cùng nhóm',
                                backdrop: true,
                            });
                        }
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
                    error: function (e) {
                        if(e.responseJSON.message === 'Het han quyen sinh vien'){
                            bootbox.alert({
                                message: 'Hết hạn quyền sinh viên',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Giang vien khong ton tai'){
                            bootbox.alert({
                                message: 'Giảng viên không tồn tại',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Giang Vien Da Huong Dan Du So Nhom'){
                            bootbox.alert({
                                message: 'Giảng viên đã hướng dẫn đủ số nhóm',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Nhom du an tot nghiep not exist'){
                            bootbox.alert({
                                message: 'Nhóm dự án tốt nghiệp không tồn tại',
                                backdrop: true,
                            });
                        }
                        if(e.responseJSON.message === 'Trang thai nhom khong thoa man'){
                            bootbox.alert({
                                message: 'Trạng thái không thỏa mãn',
                                backdrop: true,
                            });
                        }
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
