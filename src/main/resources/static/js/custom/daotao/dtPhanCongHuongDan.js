const phanCongHuongDanApi = "/api/dao-tao/phan-cong-hong-dan";
const phanCongHuongDanView = document.URL;

let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

function openModalHistory(page, check) {
    let dotDangKyId = $("#search_dot_dang_ky").val();
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeHistory").val());
    } else {
        $("#sizeHistory").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "/history?page=" + page + "&size=" + size + "&dotDangKy=" + dotDangKyId,
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
        }
    });
}

function openModalHistoryDownload() {
    let dotDangKyId = $("#search_dot_dang_ky").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "/download-log?dotDangKy=" + dotDangKyId ,
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

function openPhanCong() {
    $("#modal_create").modal("show")
    viewTablePhanCongHuongDan(0, 0);
}

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function phanCong() {
    event.preventDefault();
    let monDatnId = $("#getMonDatnId").attr('value');
    let listPhanCong = [];
    let index = false;
    let table = $("#table-add");
    let row = table.get(0).lastElementChild.children
    for (const a of row) {
        if (a.children[4].children[0].children[0].checked === true) {
            index = true;
            let phanCongRequest = {};
            phanCongRequest["monDatnId"] = monDatnId;
            phanCongRequest["giangVienId"] = a.children[1].getAttribute('value');
            listPhanCong.push(phanCongRequest)
        }
    }

    if (index == false) {
        $("#errLoi").text("Bạn phải chọn một dòng");
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: phanCongHuongDanApi,
        data: JSON.stringify(listPhanCong),
        dataType: 'json',
        success: function () {
            $("#modal_create").modal("hide");
            bootbox.alert({
                message: 'Thêm thành công',
                backdrop: true,
                callback: function () {
                    window.open(phanCongHuongDanView, '_self');
                }
            });
        },
        error: function (e) {
        }
    });
};

let checkOut = 0;

function checkAll() {
    let search = $("#searchPhanCong");
    search.val("");
    viewTablePhanCongHuongDan();
    checkOut++;
    let checkBoxs = $(".checkThemTatCa")
    if (checkOut % 2 == 0) {
        for (const checkBox of checkBoxs) {
            checkBox.checked = false
        }
    } else {
        for (const checkBox of checkBoxs) {
            checkBox.checked = true
        }
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
        viewTable(value - 1);
    }
}

// search giảng viên
function viewTable(page) {
    if (page == -1) {
        page = totalPages - 1;
    }
    if (page == totalPages) {
        page = 0;
    }
    currentPage = page;
    let dotDangKyId = $("#search_dot_dang_ky").val();
    $("#valueInput").val(page + 1);
    let keyword = $("#keyword").val().trim();
    let monDatnId = $("#getMonDatnId").attr('value');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "/search-home/" + monDatnId + "?tenGV=" + keyword + "&page=" + page + "&dotDangKy=" + dotDangKyId,
        dataType: 'json',
        success: function (responseData) {
            totalPages = responseData.data.totalPages;
            if (responseData.data.data.length > 0) {
                $("#is_empty").html(``)
                $("#list").html(responseData.data.data.map(function (item) {
                    return ` 
                        <tr class="table-light">
                            <td>${item.stt}</td>
                            <td>${item.tenTaiKhoan}</td>
                            <td>${item.tenGV}</td>
                            <td>${item.dotDangKy}</td>
                             <td style="display: flex; justify-content: center">
                                <button
                                        class="btn_trash" data-toggle="tooltip"
                                        title="Xóa"
                                        onclick="openModalRemovePhanCongHuongDan('${item.id}', '${monDatnId}')">
                                    <img src="/img/trash.svg" alt="">
                                </button>
                            </td>
                        </tr>`
                }));
                $('[data-toggle = "tooltip"]').tooltip();
                if (responseData.data.totalPages > 1) {
                    genPaging();
                } else {
                    $("#list_page").html(function () {
                        return ``
                    })
                }
            } else ($("#list").html(''), $("#list_page").html(function () {
                return ``
            }), $("#is_empty").html(function () {
                return `<span>Không có dữ liệu</span>`
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

}

// search giảng viên trong modal
function viewTablePhanCongHuongDan(page, check) {
    let search = $("#searchPhanCong");
    let dotDangKyId = $("#search_dot_dang_ky").val();
    let searchValue = search.val().trim();

    let monDatnId = $("#getMonDatnId").attr('value');
    let size = check;
    if (check === 0) {
        size = parseInt($("#sizeAdd").val());
    } else {
        $("#sizeAdd").val("20");
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "/search-add/"+ monDatnId +"?page=" + page + "&size=" + size + "&tenGV="+ searchValue + "&dotDangKy=" + dotDangKyId ,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.data.length == 0) {
                $("#add-giang-vien").html(``);
                $("#pageAdd").html('');
                $("#emptyAdd").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#emptyAdd").html(``);
                $("#add-giang-vien").html(responseData.data.data.map(function (element, index) {
                    return `
                <tr class="table-light">                 
                      <td class="text-center align-middle">${element.stt}</td>
                      <td class="text-center align-middle giangVienId"
                            value="${element.id}">${element.tenTaiKhoan}</td>
                      <td class="text-center align-middle">${element.tenGV}</td>
                      <td class="text-center align-middle">${element.dotDangKy}</td>
                        <td class="text-center align-middle">
                            <div class="d-flex justify-content-center align-items-center">
                                <input type="checkbox" name="checkThemTatCa" id=""
                                       class="checkThemTatCa"></div>
                        </td>
                </tr>
               `
                }));

                var count = responseData.data.totalPages;
                if (responseData.data.totalPages <= 1) {
                    $("#pageAdd").html('');
                } else {
                    let templatePageMid = ``;
                    let templatePageBefore = page === 0 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTablePhanCongHuongDan(${page - 1}, ${size})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTablePhanCongHuongDan(${page + 1}, ${size})"><img src="/img/next.svg"></a>
                </li>
                `;
                    let templateHtml = ``;
                    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
                    $("#pageAdd").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
                }
            }
            ;
        }
    });
}

genPaging();

//
function genPaging() {
    if (totalPages <= 1) {
        $("#list_page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTable(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTable(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#list_page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    }
}


function openModalRemovePhanCongHuongDan(giangVienHuongDanId, monDatnId) {
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
                    url: phanCongHuongDanApi + "?idGiangVien=" + giangVienHuongDanId + "&idMon=" + monDatnId,
                    data: JSON.stringify(giangVienHuongDanId, monDatnId),
                    dataType: 'json',
                    success: function () {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                window.open(phanCongHuongDanView, '_self');
                            }
                        });
                    },
                    error: function (e) {
                    }
                });
            }
        }
    });

}