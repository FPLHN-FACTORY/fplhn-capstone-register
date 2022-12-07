let DTNhomDatnApi = '/api/dao-tao/nhom-datn';
let DTNhomDatnView = '/dao-tao/nhom-datn';
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function openModalExportExcel() {
    $("#type_excel_error").text('');
    $("#modal_excel").modal('show');
}

function clearform() {
    $("#maNhom").val("");
    $("#chuyenNganh").val("");
    $("#trangThai").val("");
    $("#dotDangKy").val("");
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
                    <tr style="color: red">
                    <td><b>${item.stt}</b></td>
                    <td><b>${item.maSinhVien}</b></td>
                    <td><b>${item.tenSinhVien}</b></td>
                    </tr>
                `
                } else {
                    text += `
                        <tr>
                        <td>${item.stt}</td>
                        <td>${item.maSinhVien}</td>
                        <td>${item.tenSinhVien}</td>
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
    $("#page").html(``);
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
    if (totalPages == 1) {
        templateHtml = ``;
    }
    $("#page").html(templateHtml);
}

function loadDataNhomDatn(count) {
    if (count == -1) {
        count = totalPages - 1;
    }
    if (count == totalPages) {
        count = 0;
    }
    let maNhom = $("#maNhom").val()
    let chuyenNganhId = $("#chuyenNganh").val()
    let dotDangKyId = $("#dotDangKy").val()
    let trangThai = $("#trangThai").val()
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/search" + "?maNhom=" + maNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
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
                <tr>
                <td>${item.stt}</td>
                <td>${item.maNhom}</td>
                <td>${item.tenMonDatn}</td>
                <td>${item.tenDeTai != null ? item.tenDeTai : 'Chưa có'}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.countSinhVien}/7</td>
                <td>${item.tenGvhd != null ? item.tenGvhd + ' - ' + item.tenTaiKhoan : 'Chưa có'}</td>
                <td>${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Đã liên hệ giảng viên' : (item.trangThai == 2 ? 'Sẵn sàng phê duyệt' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>                
                <td>
                <button type="button"
                        class="btn_eye"
                        title="Xem chi tiết nhóm"
                        onclick="openModalDanhSachSinhVien('${item.id}')"
                >
                    <img src="/img/eye-fill.svg">
                </button>
                </td>
                </tr>
                `
                }))
                genPagzing();
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


