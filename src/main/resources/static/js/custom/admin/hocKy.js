const hocKyApi = '/api/dao-tao/hoc-ky';
const hocKyView = '/dao-tao/hoc-ky';

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#ten-hoc-ky").val("");
}

$("#form_create_hoc_ky").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#tenHocKyAdd").val().trim();
    let ngayBatDauStr = $("#ngayBatDauAdd").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucAdd").val();
    let ngayKetThuc = new Date(ngayKetThucStr);

    let check = 0;

    if (tenHocKy.trim().length === 0) {
        $("#hoc_ky_error").text("Tên học kỳ không được để trống");
        ++check;
    } else {
        $("#hoc_ky_error").text("");
    }

    if (tenHocKy.trim().length < 6) {
        $("#hoc_ky_error").text("Tên học kỳ tối thiểu 6 ký tự");
        ++check;
    } else {
        $("#hoc_ky_error").text("");
    }

    if (ngayBatDauStr.trim().length === 0) {
        $("#ngay_bat_dau_add_error").text("Ngày bắt đầu không được để trống");
        ++check;
    } else {
        $("#ngay_bat_dau_add_error").text("");
    }

    if (ngayKetThucStr.trim().length === 0) {
        $("#ngay_ket_thuc_add_error").text("Ngày kết thúc không được để trống");
        ++check;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#ngay_ket_thuc_add_error").text("Ngày kết thúc phải sau ngày bắt đầu");
        ++check;
    } else {
        $("#ngay_ket_thuc_add_error").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listHocKy = responseData.data;
            let size = listHocKy.length;
            for (let i = 0; i < size; i++) {
                if (ngayBatDau.getTime() < listHocKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listHocKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listHocKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listHocKy[i].ngayBatDau) {
                        $("#ngay_bat_dau_add_error").text("Thời gian bắt đầu trùng với: " + listHocKy[i].tenHocKy);
                        $("#ngay_ket_thuc_add_error").text('');
                    } else {
                        $("#ngay_bat_dau_add_error").text('');
                        $("#ngay_ket_thuc_add_error").text("Thời gian kết thúc trùng với: " + listHocKy[i].tenHocKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check === 0) {

        let hocKyRequest = {};
        hocKyRequest["tenHocKy"] = tenHocKy;
        hocKyRequest["ngayBatDau"] = ngayBatDau.toLocaleDateString('en-CA');
        hocKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleDateString('en-CA');

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: hocKyApi,
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                window.open(hocKyView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
                bootbox.alert({
                    message: 'Thêm thất bại',
                    backdrop: true,
                });
            }
        });
    }
});

function openModalUpdateHocKy(hocKyId) {
    $("#modal_update_hoc_ky").modal('show');
    $("#errorMessageUpdate").text("");
    $("#ngay_ket_thuc_update_error").text("");
    $("#ngay_ket_thuc_update_error").text("");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi + "/" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_hoc_ky_update").val(responseData.data.id);
            $("#ten_hoc_ky_update").val(responseData.data.tenHocKy);
            var dateNgayBatDau = new Date(responseData.data.ngayBatDau);
            var yearNgayBatDau = dateNgayBatDau.getFullYear();
            var monthNgayBatDau = (dateNgayBatDau.getMonth() + 1).toString().padStart(2, '0');
            var dayNgayBatDau = dateNgayBatDau.getDate().toString().padStart(2, '0');
            var formattedDateNgayBatDau = yearNgayBatDau + '-' + monthNgayBatDau + '-' + dayNgayBatDau;
            $("#ngayBatDauUpdate").val(formattedDateNgayBatDau);
            var dateNgayKetThuc = new Date(responseData.data.ngayKetThuc);
            var yearNgayKetThuc = dateNgayKetThuc.getFullYear();
            var monthNgayKetThuc = (dateNgayKetThuc.getMonth() + 1).toString().padStart(2, '0');
            var dayNgayKetThuc = dateNgayKetThuc.getDate().toString().padStart(2, '0');
            var formattedDateNgayKetThuc = yearNgayKetThuc + '-' + monthNgayKetThuc + '-' + dayNgayKetThuc;
            $("#ngayKetThucUpdate").val(formattedDateNgayKetThuc);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function openModalCreateHocKy() {
    $("#modal_create").modal('show');
    $("#tenHocKyAdd").val("")
    $("#ngayBatDauAdd").val("")
    $("#ngayKetThucAdd").val("")
    $("#hoc_ky_error").text("")
    $("#ngay_bat_dau_add_error").text("")
    $("#ngay_ket_thuc_add_error").text("")
}

function openModalHistory(page, check) {
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeHistory").val());
    } else {
        $("#sizeHistory").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi + "/history?page=" + page + "&size=" + size,
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
                     <a style="border: none" class="page-link" onclick="openModalHistory(${page - 1}, ${size})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" :  `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistory(${page + 1}, ${size})"><img src="/img/next.svg"></a>
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

function openModalHistoryDownload() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi + "/download-log",
        success: function (responseData) {
            var blob = new Blob([responseData], { type: "text/csv" });
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "hoc_ky.csv";
            document.body.appendChild(a);
            a.click();

            window.URL.revokeObjectURL(url);
        },
        error: function (e) {
            if (e.status === 404) {
                bootbox.alert({
                    message: "Không tìm thấy file trong hệ thống",
                    backdrop: true
                });
            } else {
                console.error("Lỗi khi tải file CSV: " + e.statusText);
            }
        }
    });
}

$("#form_hoc_ky_update").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#ten_hoc_ky_update").val().trim();
    let hocKyId = $("#id_hoc_ky_update").val();
    let ngayBatDauStr = $("#ngayBatDauUpdate").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucUpdate").val();
    let ngayKetThuc = new Date(ngayKetThucStr);

    let check = 0;

    if (tenHocKy.trim().length === 0) {
        $("#errorMessageUpdate").text("Tên học kỳ không được để trống");
        ++check;
    } else {
        $("#errorMessageUpdate").text("");
    }

    if (tenHocKy.trim().length < 6) {
        $("#errorMessageUpdate").text("Tên học kỳ tối thiểu 6 ký tự");
        ++check;
    } else {
        $("#errorMessageUpdate").text("");
    }

    if (ngayBatDauStr.trim().length === 0) {
        $("#ngay_bat_dau_update_error").text("Ngày bắt đầu không được để trống");
        ++check;
    } else {
        $("#ngay_bat_dau_update_error").text("");
    }

    if (ngayKetThucStr.trim().length === 0) {
        $("#ngay_ket_thuc_update_error").text("Ngày kết thúc không được để trống");
        ++check;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#ngay_ket_thuc_update_error").text("Ngày kết thúc phải sau ngày bắt đầu");
        ++check;
    } else {
        $("#ngay_ket_thuc_update_error").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: hocKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listHocKy = responseData.data;
            let size = listHocKy.length;
            for (let i = 0; i < size; i++) {
                if (hocKyId === listHocKy[i].id) {
                    continue;
                }
                if (ngayBatDau.getTime() < listHocKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listHocKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listHocKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listHocKy[i].ngayBatDau) {
                        $("#ngay_bat_dau_update_error").text("Thời gian bắt đầu trùng với: " + listHocKy[i].tenHocKy);
                        $("#ngay_ket_thuc_update_error").text('');
                    } else {
                        $("#ngay_bat_dau_update_error").text('');
                        $("#ngay_ket_thuc_update_error").text("Thời gian kết thúc trùng với: " + listHocKy[i].tenHocKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check === 0) {
        let hocKyRequest = {};
        hocKyRequest["tenHocKy"] = tenHocKy;
        hocKyRequest["ngayBatDau"] = ngayBatDau.toLocaleDateString('en-CA');
        hocKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleDateString('en-CA');

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: hocKyApi + "/" + hocKyId,
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                window.open(hocKyView, '_self');
                $("#modal_update_hoc_ky").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
                bootbox.alert({
                    message: 'Sửa thất bại',
                    backdrop: true,
                });
            }
        });
    }
})
;

// function openModalRemoveHocKy(hocKyId) {
//     $("#modal_hoc_ky_remove").modal('show');
//     $.ajax({
//         type: "GET",
//         contentType: "application/json",
//         url: hocKyApi + "/" + hocKyId,
//         data: JSON.stringify(hocKyId),
//         dataType: 'json',
//         success: function () {
//             $("#remove_hoc_ky").val(hocKyId);
//         },
//         error: function (e) {
//             console.log("ERROR : ", e);
//         }
//     });
// }

function remove(id) {
    bootbox.confirm({
        title: "Xóa học kỳ?",
        message: "Bạn chắc chắn muốn xóa học kỳ này?",
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
                    url: hocKyApi + "/" + id,
                    data: JSON.stringify(id),
                    dataType: 'json',
                    success: function () {
                        window.open(hocKyView, '_self');
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                        if (e.responseJSON.message == 'Ton tai dot dang ky') {
                            bootbox.alert({
                                message: `<strong>Thất bại.</strong> Học kỳ đang tồn tại đợt đăng ký`,
                                backdrop: true,
                            });
                        } else {
                            bootbox.alert({
                                message: 'Xóa thất bại',
                                backdrop: true,
                            });
                        }
                    }
                });
            }
        }
    });
};