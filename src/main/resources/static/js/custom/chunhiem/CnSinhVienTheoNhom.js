const sinhVienApi = '/api/chu-nhiem/sinh-vien';
const CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
const sinhVienTheoNhomView = '/chu-nhiem/sinh-vien';
const CnNhomDatnView = '/chu-nhiem/nhom-datn';
const CNNhomDatnView = '/chu-nhiem/nhom-datn';
let idOldCaptain;

$(document).ready(function () {
    $(".chosen-select-width").chosen({
        width: "75%"
    });
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function clearData() {
    $("#keyword").val("");
}

function searchGvhd() {
    let text = $("#tags").val()
    let idNhom = $("#idNhom").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/listGvhd" + "?tenTaiKhoan=" + text + "&tenGvhd=" + text + "&idNhom=" + idNhom,
        dataType: 'json',
        success: function (responseData) {
            let array = [];
            for (var item of responseData.data) {
                array.push(item.thongTinGvhd)
            }
            $("#tags").autocomplete({
                source: array
            });
        },
        error: function (e) {
        }
    });
}

loadData()

function openModalThemSinhVien() {
    $("#idStudentError").text("")
    $("#modal_create").modal('show')
}

function openModalDoiTenDeTai() {
    let tenDeTaiCu = $("#tenDeTaiCu").val()
    $("#tenDeTai").val(tenDeTaiCu);
    $("#change_ten_de_tai_error").text("")
    $("#modal_change_ten_de_tai").modal('show')
}

function doiTenDeTai() {
    let idNhom = $("#idNhom").val()
    let tenDeTai = $("#tenDeTai").val().trim()
    if (tenDeTai === "") {
        $("#change_ten_de_tai_error").html("Tên đề tài không được để trống")
        return;
    }
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: sinhVienApi + "/doi-ten-de-tai?idNhom=" + idNhom + "&tenDeTai=" + tenDeTai,
        success: function (responseData) {
            bootbox.alert({
                message: 'Đổi tên đề tài thành công',
                backdrop: true,
                callback: function () {
                    window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                }
            });
        },
        error: function (e) {
            if (e.responseJSON.message === 'Nhom du an tot nghiep not exist') {
                $("#change_ten_de_tai_error").html("Nhóm dự án tốt nghiệp không tồn tại")
            }
            if (e.responseJSON.message === 'Do dai khong qua 255 ky tu') {
                $("#change_ten_de_tai_error").html("Độ dài không quá 255 ký tự")
            }
        }
    });
}

function themSinhVien() {
    let idNhom = $("#idNhom").val()
    let listMaSinhVien = document.querySelectorAll("#ma_sinh_vien")
    var maSinhViens = '';
    for (let item of listMaSinhVien) {
        maSinhViens += item.innerHTML + "-";
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?id=" + idNhom + "&maSinhVien=" + maSinhViens,
        success: function (responseData) {
            bootbox.alert({
                message: 'Thêm sinh viên vào nhóm thành công',
                backdrop: true,
                callback: function () {
                    window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                }
            });

        },
        error: function (e) {
            if (e.responseJSON.message === 'So luong thanh vien toi da trong nhom la 7 nguoi') {
                $("#idStudentError").html("Số lượng thành viên tối đa trong nhóm là 7 người")
            }
            if (e.responseJSON.message === 'mon dang hoc cua sinh vien khong thich hop.vui long chon lai') {
                $("#idStudentError").html("Môn đang học của sinh viên không thích hợp. Vui lòng chọn lại")
            }
        }
    });
}

function openModalRemoveNhom(maSinhVien) {
    bootbox.confirm({
        title: "Kích sinh viên ra khỏi nhóm",
        message: "Bạn muốn kích sinh viên này không?",
        backdrop: true,
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
                let idNhom = $("#idNhom").val();
                let soThanhVien = $("#soThanhVien").val();
                if (idOldCaptain == maSinhVien) {
                    $.ajax({
                        type: "PUT",
                        contentType: "application/json",
                        url: sinhVienApi + "/changeCaptainToOther?idCaptain=" + idOldCaptain + "&idGroup=" + idNhom,
                        success: function () {
                            window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                        },
                        error: function () {
                        }
                    })
                }
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: sinhVienApi + "?maSinhVien=" + maSinhVien + "&idNhom=" + idNhom,
                    success: function () {
                        if (soThanhVien > 1) {
                            window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                        } else {
                            window.open(CnNhomDatnView, '_self');
                        }
                    },
                    error: function (e) {
                        if (e.responseJSON.message === 'Nhom con 4 thanh vien neu kick thanh vien tiep ra khoi nhom nhom se thanh nhom khong hop le') {
                            bootbox.alert({
                                message: "Nhóm chỉ còn từ 4 thành viên trở xuống nếu kick tiếp thành viên ra thì nhóm sẽ trở thành nhóm không hợp lệ",
                                backdrop: true
                            });
                            return;
                        }
                    }
                });
            }
        }
    });
}

function openModalChangeGvhd() {
    $("#tags").val("")
    $("#change_gvhd_error").text("")
    $('#modal_change_gvhd').modal('show');
}

function thayDoiGiangVien() {
    let idGroup = $("#idNhom").val();
    let giangVienId = $("#tags").val().split('_');
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addGvhd" + "?giangVienId=" + giangVienId[0] + "&idNhom=" + idGroup,
        success: function () {
            window.open(sinhVienTheoNhomView + "?id=" + idGroup, '_self');
        },
        error: function (e) {
            if (e.responseJSON.message == 'Giang vien khong ton tai') {
                $("#change_gvhd_error").text('Giảng viên không tồn tại')
            }
            if (e.responseJSON.message == 'Giang Vien Da Huong Dan Du So Nhom') {
                $("#change_gvhd_error").text('Giảng viên đã hướng dẫn đủ số nhóm')
            }
            if (e.responseJSON.message == 'giang vien nay khong day mon hoc nay. vui long chon giang vien khac') {
                $("#change_gvhd_error").text('Giảng viên này không dạy môn DATN của nhóm này. Vui lòng chọn giảng viên khác')
            }
        }
    });
}

function openModalChangeCaptain() {
    $("#modal_change_captain").modal('show');
    let idGroup = $("#idNhom").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/oldCaptain?idGroup=" + idGroup,
        success: function (captain) {
            $("#oldCaptain").html(`
              <table 
               class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
              >
              <thead>
              <th class="text-center align-middle">Mã sinh viên</th>
              <th class="text-center align-middle">Họ tên</th>
              <th class="text-center align-middle">Chức vụ</th>
              </thead>
              <tr class="table-light">
              <td class="text-center align-middle">${captain.data.maSinhVien}</td>
              <td class="text-center align-middle">${captain.data.tenSinhVien}</td>
              <td class="text-center align-middle">Trưởng nhóm</td>
              </tr>
              </table>
         `)
        },
        error: function () {

        }
    })

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/listStudentNotCaptain?idGroup=" + idGroup,
        success: function (responseData) {
            var text = `
              <table 
               class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
              >
              <thead>
              <th class="text-center align-middle">Mã sinh viên</th>
              <th class="text-center align-middle">Họ tên</th>
              <th class="text-center align-middle">Chức năng</th>
              </thead>
            `;
            responseData.data.map(function (sinhVien) {
                text += `
            
              <tr class="table-light">
              <td class="text-center align-middle">${sinhVien.maSinhVien}</td>
              <td class="text-center align-middle">${sinhVien.tenSinhVien}</td>
              <td class="text-center align-middle">
              <div class="d-flex justify-content-center align-items-center">
              <a onclick="changeCaptain('${sinhVien.id}')"
              class="btn btn-success text-white"
              >
              Thay đổi
              </a>
              </div>
              </td>
              </tr>
             `
            })
            text += `</table>`
            $("#manofteam").html(text)
        },
        error: function (responseData) {
        }
    })
}

function changeCaptain(idCaptain) {
    bootbox.confirm({
        title: "Đổi trưởng nhóm",
        message: "Bạn có chắc muốn đổi trưởng nhóm không?",
        backdrop: true,
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
                let idGroup = $("#idNhom").val();
                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: sinhVienApi + "/changeCaptain?idCaptain=" + idCaptain + "&idGroup=" + idGroup,
                    success: function () {
                        bootbox.alert({
                            message: "Thay đổi trưởng nhóm thành công",
                            backdrop: true,
                            callback: function () {
                                window.open(sinhVienTheoNhomView + "?id=" + idGroup, '_self');
                            }
                        });
                    },
                    error: function () {
                    }
                })
            }
        }
    });
}

function loadData() {
    let idGroup = $("#idNhom").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/oldCaptain?idGroup=" + idGroup,
        success: function (captain) {
            if (captain.data != null) {
                idOldCaptain = captain.data.maSinhVien;
            }
        },
        error: function () {
        }
    })
}

function openModalHistoryDetail(page, check, maNhom, dotDangKyId) {
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeHistory").val());
    } else {
        $("#sizeHistory").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/history-detail?page=" + page + "&size=" + size + "&maNhom=" + maNhom + "&dotDangKyId=" + dotDangKyId,
        dataType: 'json',
        success: function (responseData) {
            $("#historyDetail").modal('show')
            if (responseData.data.content.length == 0) {
                $("#listHistoryDetail").html(``);
                $("#pageHistoryDetail").html('');
                $("#emptyHistoryDetail").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#emptyHistoryDetail").html(``);
                $("#listHistoryDetail").html(responseData.data.content.map(function (element, index) {
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
                    $("#pageHistoryDetail").html('');
                } else {
                    let templatePageMid = ``;
                    let templatePageBefore = page === 0 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page - 1}, ${size}, ${maNhom})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page + 1}, ${size}, ${maNhom})"><img src="/img/next.svg"></a>
                </li>
                `;
                    let templateHtml = ``;
                    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
                    $("#pageHistoryDetail").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
                }
            }
            ;
        },
        error: function (e) {
            console.log(e)
            if (e.responseJSON.message === 'File log khong ton tai') {
                bootbox.alert({
                    message: 'File log không tồn tại',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

function openModalRemoveNhomDatn(nhomDatnId) {
    bootbox.confirm({
        title: "Xóa nhóm dự án tốt nghiệp",
        message: "Bạn muốn xóa vĩnh viễn nhóm này không?",
        backdrop: true,
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
                    url: CNNhomDatnApi + "/" + nhomDatnId,
                    data: JSON.stringify(nhomDatnId),
                    dataType: 'json',
                    success: function () {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                window.open(CNNhomDatnView, '_self');
                            }
                        });
                    },
                    error: function (e) {
                        if (e.responseJSON.message === 'Nhom du an tot nghiep not exist') {
                            bootbox.alert({
                                message: 'Nhóm dự án tốt nghiệp không tồn tại',
                                backdrop: true,
                                callback: function () {

                                }
                            });
                        }
                        return;
                    }
                });
            }
        }
    });
}



