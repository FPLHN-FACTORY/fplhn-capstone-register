let DTNhomDatnApi = '/api/dao-tao/nhom-datn';
let DTNhomDatnView = '/dao-tao/nhom-datn';
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function openModalHistory(page, check) {
    let dotDangKy = $("#dotDangKy").val()
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeHistory").val());
    } else {
        $("#sizeHistory").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/history?page=" + page + "&size=" + size + "&dotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            $("#history").modal('show')
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
                    let templatePageAfter = page === Number(count) - 1 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistory(${page + 1}, ${size})"><img src="/img/next.svg"></a>
                </li>
                `;
                    let templateHtml = ``;
                    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
                    $("#pageHistory").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
                }
            }
            ;
        },
        error: function (e) {
            if (e.responseJSON.message === 'File log khong ton tai') {
                bootbox.alert({
                    message: 'File log không tồn tại',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
            if (e.responseJSON.message === 'Khong co dot hien tai vui long chon dot') {
                bootbox.alert({
                    message: 'Không có đợt hiện tại. Vui lòng chọn đợt',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

function openModalHistoryDownload() {
    let dotDangKy = $("#dotDangKy").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/download-log?dotDangKy=" + dotDangKy,
        success: function (responseData) {
            var blob = new Blob([responseData], {type: "text/csv"});
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "nhom_datn_theo_dot.csv";
            document.body.appendChild(a);
            a.click();

            window.URL.revokeObjectURL(url);
        },
        error: function (e) {
            console.log(e.responseJSON.message)
            console.log(e.status )
            if (e.status === 404) {
                bootbox.alert({
                    message: "Không tìm thấy file trong hệ thống",
                    backdrop: true
                });
            } else {
                console.error("Lỗi khi tải file CSV: " + e.statusText);
            }
            if (e.responseJSON.message === 'Khong co dot hien tai vui long chon dot') {
                bootbox.alert({
                    message: 'Không có đợt hiện tại. Vui lòng chọn đợt',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

function openModalHistoryDetail(page, check, maNhom, dotDangKyId) {
    if (dotDangKyId.trim().length === 0) {
        dotDangKyId = $("#dotDangKy").val();
    }
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeHistoryDetail").val());
    } else {
        $("#sizeHistoryDetail").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/history-detail?page=" + page + "&size=" + size + "&maNhom=" + maNhom + "&dotDangKyId=" + dotDangKyId,
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
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page - 1}, ${size}, ${maNhom}, ${dotDangKyId})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page + 1}, ${size}, ${maNhom}, ${dotDangKyId})"><img src="/img/next.svg"></a>
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
            if (e.responseJSON.message === 'File log khong ton tai') {
                bootbox.alert({
                    message: 'File log không tồn tại',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
            if (e.responseJSON.message === 'Khong co dot hien tai vui long chon dot') {
                bootbox.alert({
                    message: 'Không có đợt hiện tại. Vui lòng chọn đợt',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

function openModalHistoryDownloadDetail() {
    let dotDangKy = $("#dotDangKy").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/download-log-luong?dotDangKy=" + dotDangKy,
        success: function (responseData) {
            var blob = new Blob([responseData], {type: "text/csv"});
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "danh_sach_nhom_datn.csv";
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
            if (e.responseJSON.message === 'Khong co dot hien tai vui long chon dot') {
                bootbox.alert({
                    message: 'Không có đợt hiện tại. Vui lòng chọn đợt',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

function openModalExportExcel() {
    $("#type_excel_error").text('');
    $("#modal_excel").modal('show');
}

function clearform() {
    $("#maNhom").val("");
    $("#chuyenNganh").val("");
    $("#trangThai").val("");
    $("#dotDangKy").val("");
    $("#soThanhVien").val("");
}

function openModalDanhSachSinhVien(id) {
    $("#modal_show_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "?keyword=" + id,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData.data)
            var text = "";
            responseData.data.map(function (item) {
                if (item.chucVu == 1) {
                    text += `
                    <tr style="color: red" class="table-light">
                    <td class="text-center align-middle"><b>${item.stt}</b></td>
                    <td class="text-center align-middle"><b>${item.maSinhVien}</b></td>
                    <td class="text-center align-middle"><b>${item.tenSinhVien}</b></td>
                    </tr>
                `
                } else {
                    text += `
                        <tr class="table-light">
                        <td class="text-center align-middle">${item.stt}</td>
                        <td class="text-center align-middle">${item.maSinhVien}</td>
                        <td class="text-center align-middle">${item.tenSinhVien}</td>
                        </tr>
                `
                }
            })
            $("#show_Dssv").html(text)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function exportFileExcel() {
    let maNhom = $("#maNhom").val()
    let chuyenNganhId = $("#chuyenNganh").val()
    let dotDangKyId = $("#dotDangKy").val()
    let trangThai = $("#trangThai").val()
    let typeExcel = $("#type_excel").val();
    if (typeExcel == '') {
        $("#type_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/nhom-datn/export?typeExcel=" + typeExcel + "&maNhom=" + maNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId,
            success: function () {
                window.open("/api/dao-tao/nhom-datn/export?typeExcel=" + typeExcel + "&maNhom=" + maNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId, '_self');
                $("#modal_excel").modal('hide')
                $("#type_excel_error").val("");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function exportFileExcelSinhVien() {
    let maNhom = $("#maNhom").val()
    let chuyenNganhId = $("#chuyenNganh").val()
    let dotDangKyId = $("#dotDangKy").val()
    let trangThai = $("#trangThai").val()
    let soThanhVien = $("#soThanhVien").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/nhom-datn/export-excel?maNhom=" + maNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&soThanhVien=" + soThanhVien,
        success: function () {
            window.open("/api/dao-tao/nhom-datn/export-excel?maNhom=" + maNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&soThanhVien=" + soThanhVien, '_self');
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

document.querySelector("#checkbox_father").addEventListener("change", function (e) {
    e.preventDefault()
    var list_CheckBox = document.querySelectorAll("#checkbox_son")
    for (var i = 0; i < list_CheckBox.length; i++) {
        if (!list_CheckBox[i].disabled) {
            list_CheckBox[i].checked = this.checked
        }
    }
})

function checkBoxSonListener() {
    var checkboxFather = document.querySelector("#checkbox_father")
    checkboxFather.checked = check();
}

function check() {
    var count = 0;
    var list_CheckBox = document.querySelectorAll("#checkbox_son")
    for (var item of list_CheckBox) {
        if (item.checked) {
            count++;
        }
    }
    if (count == list_CheckBox.length) {
        return true;
    }
    return false;
}

function xoaNhom(count) {
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")

    flag = 0;
    checkListCbb = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            checkListCbb++
        }
    }

    if (checkListCbb == 0) {
        bootbox.alert({
            message: 'Chọn nhóm muốn xóa',
            backdrop: true,
            callback: function () {
                loadDataNhomDatn(0);
            }
        });
    } else {
        bootbox.confirm({
            title: "Xóa nhóm DATN",
            message: "Bạn có chắc muốn xóa các nhóm này không?",
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
                    let arrayId = [];
                    for (let xx of list_checkbox_son) {
                        if (xx.checked) {
                            arrayId.push(xx.value)
                        }
                    }
                    $.ajax({
                        type: "DELETE",
                        contentType: "application/json",
                        url: DTNhomDatnApi + "/xoa-nhom",
                        data: JSON.stringify(arrayId),
                        dataType: 'json',
                        success: function () {
                            if (currentPage == -1) {
                                loadDataNhomDatn(count)
                            } else {
                                loadDataNhomDatn(currentPage)
                            }
                            bootbox.alert('Xoá nhóm thành công')
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
                    var checkboxFather = document.querySelector("#checkbox_father")
                    checkboxFather.checked = false;
                }
            }
        });
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
        loadDataNhomDatn(value - 1);
    }
}

genPagzing();

function genPagzing() {
    if (totalPages <= 1) {
        $("#page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataNhomDatn(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataNhomDatn(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#page").html(templateHtml);
    }
}

function loadDataNhomDatn(count) {
    if (count == -1) {
        count = totalPages - 1;
    }
    if (count == totalPages) {
        count = 0;
    }
    let maNhom = $("#maNhom").val().trim()
    let chuyenNganhId = $("#chuyenNganh").val()
    let dotDangKyId = $("#dotDangKy").val()
    let trangThai = $("#trangThai").val()
    let soThanhVien = $("#soThanhVien").val().trim()
    let sizeNhomDatn = parseInt($("#sizeNhomDatn").val())
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/search" + "?maNhom=" + maNhom + "&dotDangKyId=" + dotDangKyId + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&soThanhVien=" + soThanhVien + "&page=" + count + "&size=" + sizeNhomDatn,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data.data;
            totalPages = responseData.data.totalPages;
            if (array.length == 0) {
                $("#listNhomDatn").html(``);
                $("#page").html(``);
                $("#empty").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#empty").html(``)
                $("#listNhomDatn").html(array.map(function (item) {
                    return `
                <tr class="table-light">
                <td class="text-center align-middle">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" data-toggle="tooltip" title="Chọn một" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()">
                        <input hidden id="check-trang-thai" value="${item.trangThai}">
                    </div>
                </td>
                <td class="text-center align-middle">${item.stt}</td>
                <td class="text-center align-middle">${item.maNhom}</td>
                <td class="text-center align-middle">${item.tenMonDatn}</td>
                <td class="text-center align-middle">${item.tenDeTai != null ? item.tenDeTai : 'Chưa có'}</td>
                <td class="text-center align-middle">${item.tenChuyenNganh}</td>
                <td class="text-center align-middle">
                    ${item.danhSachSinhVien.split(', ').map(function (value, index) {
                        return `<span style="${item.maSinhVien == value ? 'font-weight: bold; text-decoration: underline;' : ''}">${value}${index === item.danhSachSinhVien.length - 1 ? ' ' : ''}</span>`;
                    })}
                </td>
                <td class="text-center align-middle">${item.countSinhVien}/7</td>
                <td class="text-center align-middle">${item.tenGvhd != null ? item.tenGvhd + ' - ' + item.tenTaiKhoan : 'Chưa có'}</td>
                <td class="text-center align-middle">${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Chủ nhiệm bộ môn đã xác nhận' : (item.trangThai == 2 ? 'Giảng viên đã liên hệ' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>                
                <td class="text-center align-middle">
                    <div class="d-flex justify-content-center align-items-center">
                        <button type="button"
                        class="btn_eye"
                        title="Xem chi tiết nhóm"
                        onclick="openModalDanhSachSinhVien('${item.id}')"
                         >
                            <img src="/img/eye-fill.svg">
                        </button>
                        <button type="button"
                        class="btn_eye"
                        data-toggle="tooltip"
                        title="Xem log nhóm"
                        onclick="openModalHistoryDetail(0, 20, '${item.maNhom}', '${item.dotDangKyId}')"
                         >
                            <img src="/img/history.svg">
                        </button>
                    </div>
                </td>
                </tr>`
                }
                ))
                genPagzing();
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


