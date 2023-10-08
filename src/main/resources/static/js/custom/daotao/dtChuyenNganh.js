const chuyenNganhApi = '/api/dao-tao/chuyen-nganh';
const chuyenNganhView = '/dao-tao/chuyen-nganh';

$(document).ready(function () {
    $("#chuyen_nganh_error").text("");
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(chuyenNganhView);
});

function clearData() {
    $("#ten-chuyen-nganh-search").val("");
}

function clearForm() {
    $("#tenChuyenNganhAdd").val("");
    $("#chuyen_nganh_create_error").text("");
    $("#chuyen_nganh_error").text("");
}

function openModalHistory(page, size) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/history?page=" + page + "&size=" + size,
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
        url: chuyenNganhApi + "/download-log",
        success: function (responseData) {
            var blob = new Blob([responseData], { type: "text/csv" });
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "chuyen_nganh.csv";
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

function openModalCreateChuyenNganh() {
    $("#modal_create_chuyen_nganh").modal('show');
    $("#chuyen_nganh_add_error").text("");
    $("#CNBM_add_error").text("");
    $("#tenChuyenNganhAdd").val('');
}

function create() {
    let tenChuyenNganh = $("#tenChuyenNganhAdd").val();
    let CNBMId = $("#tenCNBMhAdd").val() == 'none' ? null : $("#tenCNBMhAdd").val();
    let chuyenNganhRequest = {};
    chuyenNganhRequest["chuNhiemBoMon"] = CNBMId;
    chuyenNganhRequest["tenChuyenNganh"] = tenChuyenNganh;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
        $("#chuyen_nganh_add_error").text("Tên chuyên ngành không được để trống");
        index++;
    } else if (tenChuyenNganh.trim().length > 100) {
        $("#chuyen_nganh_add_error").text("Tên chuyên ngành không được quá 100 ký tự");
        index++;
    } else {
        $("#chuyen_nganh_add_error").text("");
    }

    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: chuyenNganhApi,
            data: JSON.stringify(chuyenNganhRequest),
            dataType: 'json',
            success: function () {
                $("#modal_create_chuyen_nganh").modal("hide");
                bootbox.alert({
                    message: 'Thêm thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message == 'Chuyen nganh da ton tai') {
                    $("#chuyen_nganh_add_error").text("Chuyên ngành đã tồn tại trong đợt");
                    $("#CNBM_add_error").text("");
                }
            }
        });
    }
};

function openModalUpdateChuyenNganh(chuyenNganhId, tenCNBM) {
    $("#modal_update_chuyen_nganh").modal('show');
    $("#chuyennganh_Update_error").text("");
    $("#CNBM_update_error").text("");
    let cnbm = $("#chuNhiemBoMon").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: chuyenNganhApi + "/" + chuyenNganhId,
        data: JSON.stringify(chuyenNganhId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_chuyen_nganh_update").val(responseData.data.id);
            $("#tenChuyenNganhUpdate").val(responseData.data.tenChuyenNganh);
            $("#tenCNBMhUpdate").val(responseData.data.chuNhiemBoMon == null ? 'none' : responseData.data.chuNhiemBoMon);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let tenChuyenNganh = $("#tenChuyenNganhUpdate").val().trim();
    let chuyenNganhId = $("#id_chuyen_nganh_update").val();
    let tenCNBM = $("#tenCNBMhUpdate").val() == 'none' ? null : $("#tenCNBMhUpdate").val();
    let chuyenNganhrequest = {};
    chuyenNganhrequest["tenChuyenNganh"] = tenChuyenNganh;
    chuyenNganhrequest["chuNhiemBoMon"] = tenCNBM;
    let index = 0;
    if (tenChuyenNganh.trim().length === 0) {
         $("#chuyennganh_Update_error").text("Tên chuyên ngành không được để trống");
        index++;
    } else if (tenChuyenNganh.trim().length > 100) {
        $("#chuyennganh_Update_error").text("Tên chuyên ngành không được quá 100 ký tự");
        index++;
    } else {
        $("#chuyennganh_Update_error").text("");
    }

    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: chuyenNganhApi + "/" + chuyenNganhId,
            data: JSON.stringify(chuyenNganhrequest),
            dataType: 'json',
            success: function () {
                $("#modal_update_chuyen_nganh").modal("hide");
                bootbox.alert({
                    message: 'Cập nhật thành công',
                    backdrop: true,
                    callback: function () {
                        window.open(chuyenNganhView, '_self');
                    }
                });
            },
            error: function (e) {
                console.log(e)
                if (e.responseJSON.message == 'Chuyen nganh da ton tai') {
                    $("#chuyennganh_Update_error").text("Chuyên ngành đã tồn tại trong đợt");
                }
            }
        });
    }
};

function remove(chuyenNganhId) {
    $("#remove_chuyen_nganh").text("")
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
                    url: chuyenNganhApi + "/" + chuyenNganhId,
                    data: JSON.stringify(chuyenNganhId),
                    dataType: 'json',
                    success: function (responseData) {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function (e) {
                                window.open(chuyenNganhView, '_self');
                            }
                        });
                    },
                    error: function (e) {
                        if (e.responseJSON.message == 'Chuyen nganh khong duoc xoa') {
                            bootbox.alert({
                                message: 'Chuyên ngành đang có sinh viên, môn DATN. Không thể xóa!',
                                backdrop: true
                            });
                        }
                    }
                });
            }
        }
    });
}
