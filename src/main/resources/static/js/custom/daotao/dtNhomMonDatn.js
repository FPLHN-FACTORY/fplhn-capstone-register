const nhomMonDatnApi = '/api/dao-tao/nhom-mon-datn';
const nhomMonDatnView = '/dao-tao/nhom-mon-datn';

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(nhomMonDatnView);
});

function openModalHistory(page, size) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomMonDatnApi + "/history?page=" + page + "&size=" + size,
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
        url: nhomMonDatnApi + "/download-log",
        success: function (responseData) {
            var blob = new Blob([responseData], { type: "text/csv" });
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "nhom_mon_datn.csv";
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

let totalPages = $("#totalPages").val();
let currentPage = $("#currentPage").val();

function clearData() {
    $("#search_ma_nhom_mon").val("");
    $("#chuyenNganh").val("");
}

function genPaging() {
    if (totalPages <= 1) {
        $("#page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadTable(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadTable(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    }
}

function changeInput() {
    if ($("#valueInput").val().length != 0) {
        let value = parseInt($("#valueInput").val());
        if (Number(value) <= 0) {
            value = 1;
        }
        if (Number(value) > totalPages) {
            value = totalPages;
        }
        loadTable(value - 1);
    }
}

genPaging();

function loadTable(count) {
    if (count == -1) {
        count = totalPages - 1;
    }
    if (count == totalPages) {
        count = 0;
    }
    let maNhomMon = $("#search_ma_nhom_mon").val();
    let chuyenNganhId = $("#chuyenNganh").val();
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomMonDatnApi + "/search" + "?maNhomMon=" + maNhomMon + "&chuyenNganhId=" + chuyenNganhId + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            totalPages = responseData.data.totalPages;
            if (responseData.data.data.length == 0) {
                $("#searchListMon").html(``);
                $("#page").html(``);
                $("#empty").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#empty").html(``)
                $("#searchListMon").html(responseData.data.data.map(function (nhomMonDatn) {
                    return `
                <tr class="table-light">
                      <td class="text-center align-middle">${nhomMonDatn.stt}</td>
                      <td class="text-center align-middle">${nhomMonDatn.maNhomMon}</td>
                      <td class="text-center align-middle">${nhomMonDatn.tenNhomMon}</td>
                      <td class="text-center align-middle">${nhomMonDatn.tenChuyenNganh}</td>
                      <td class="text-center align-middle">${nhomMonDatn.soMon}</td>
                      <td class="text-center align-middle">
                        <div class="d-flex justify-content-center align-items-center">
                          <button
                            class="btn_pencil_square" 
                            data-toggle="tooltip"
                            title="Sửa nhóm môn"
                            onclick="openModalUpdateNhomMonDatn('${nhomMonDatn.id}','${count}')">
                            <img src="/img/pencil-square.svg">
                          </button>
                          <button
                            class="btn_trash"
                            data-toggle="tooltip"
                            title="Xóa nhóm môn"
                            onclick="deleteNhomMonDatn('${nhomMonDatn.id}','${count}')">
                            <img src="/img/trash.svg">
                         </button>
                         <button
                            class="btn_eye"
                            data-toggle="tooltip"
                            title="Xem chi tiết nhóm môn"
                            onclick="openModalChiTietNhomMonDatn('${nhomMonDatn.id}','${count}')">
                            <img src="/img/eye-fill.svg">
                        </button>
                        </div>
                    </td>
                </tr>
               `
                }));
                genPaging();
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function openModalCreate() {
    $("#maNhomMon").val('');
    $("#tenNhomMon").val('');
    $("#chuyenNganhId").val('');
    $("#ma_mon_datn_error").text('');
    $("#ten_mon_datn_error").text('');
    $("#chuyen_nganh_error").text('');
    $("#modal_create").modal('show');
}

function createNhomMonDatn() {
    let maMon = $("#maNhomMon").val().trim();
    let tenMonDatn = $("#tenNhomMon").val().trim();
    let chuyenNganhId = $("#chuyenNganhId").val();
    let monDatnRequest = {};
    monDatnRequest["maNhomMon"] = maMon;
    monDatnRequest["tenNhomMon"] = tenMonDatn;
    monDatnRequest["chuyenNganhId"] = chuyenNganhId;
    let index = 0;
    if (maMon.trim().length === 0) {
        $("#ma_mon_datn_error").text("Mã nhóm môn không được để trống");
        index++;
    } else if (maMon.trim().length > 20) {
        $("#ma_mon_datn_error").text("Mã nhóm môn không được quá 20 ký tự");
        index++;
    } else {
        $("#ma_mon_datn_error").text("");
    }
    if (tenMonDatn.trim().length === 0) {
        $("#ten_mon_datn_error").text("Tên nhóm môn không được để trống");
        index++;
    } else if (tenMonDatn.trim().length > 100) {
        $("#ten_mon_datn_error").text("Tên nhóm môn không được quá 100 ký tự");
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
                    $("#ma_mon_datn_error").text('Mã nhóm đã tồn tại hoặc mã nhóm môn đang trùng với mã môn');
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
    let maNhomMon = $("#ma_nhom_mon_datn_update").val().trim();
    let tenNhomMonDatn = $("#ten_nhom_mon_datn_update").val().trim();
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
    } else if (maNhomMon.trim().length > 20) {
        $("#errorMaMonUpdate").text("Mã nhóm môn không được quá 20 ký tự");
        index++;
    } else {
        $("#errorMaMonUpdate").text("");
    }
    if (tenNhomMonDatn.trim().length === 0) {
        $("#errorTenMonUpdate").text("Tên nhóm môn không được để trống");
        index++;
    } else if (tenNhomMonDatn.trim().length > 100) {
        $("#errorTenMonUpdate").text("Tên nhóm môn không được quá 100 ký tự");
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
               <tr class="table-light">
                   <td class="text-center align-middle">${item.stt}</td>
                   <td class="text-center align-middle">${item.maMon}</td>
                   <td class="text-center align-middle">${item.tenMon}</td>
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