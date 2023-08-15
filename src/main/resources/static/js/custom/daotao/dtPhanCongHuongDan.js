const phanCongHuongDanApi = "/api/dao-tao/phan-cong-hong-dan";
const phanCongHuongDanView = document.URL;

let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

function openPhanCong() {
    $("#modal_create").modal("show")
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
        if (a.children[4].children[0].checked === true) {
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
    $("#valueInput").val(page + 1);
    let keyword = $("#keyword").val().trim();
    let monDatnId = $("#getMonDatnId").attr('value');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "/search-home/" + monDatnId + "?tenGV=" + keyword + "&page=" + page,
        dataType: 'json',
        success: function (responseData) {
            totalPages = responseData.data.totalPages;
            if (responseData.data.data.length > 0) {
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
function viewTablePhanCongHuongDan() {
    let table = $("#table-add");
    let row = table.get(0).lastElementChild.children;
    let search = $("#searchPhanCong");
    let searchValue = search.val().trim().toUpperCase();
    let dem = 0;

    for (const a of row) {
        let tenTaiKhoan = a.children[1].textContent.trim().toUpperCase();
        let hoVaTen = a.children[2].textContent.trim().toUpperCase();
        let stt = a.children[0];
        if (!tenTaiKhoan.includes(searchValue) && !hoVaTen.includes(searchValue)) {
            a.style.display = "none";
        } else {
            dem++;
            stt.textContent = dem;
            a.style.display = "";
        }
    }
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