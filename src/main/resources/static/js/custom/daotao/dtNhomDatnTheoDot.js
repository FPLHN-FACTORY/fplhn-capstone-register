let DTNhomDatnApi = '/api/dao-tao/nhom-datn';
let DTNhomDatnView = '/dao-tao/nhom-datn';
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

function openModalExportExcel(){
    $("#modal_excel").modal('show')
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
                text += `
                <tr>
                <td>${item.stt}</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenSinhVien}</td>
                <td>${item.chucVu == 1 ? 'Trưởng nhóm' : 'Thành viên'}</td>
                </tr>
                `
            })
            $("#show_Dssv").html(text)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

// mới
$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})


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
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

genPagingAndFillColor()

function genPagingAndFillColor() {
    genPagzing();
    let colorPage = document.querySelectorAll("#pageColor");
    colorPage[currentPage].style.backgroundColor = '#0a8cf0';
    colorPage[currentPage].style.color = 'white';
}

function genPagzing() {
    $("#page").html(``)
    let templatePageMid = ``;
    let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadDataNhomDatn(currentPage - 1)">Back</a>
                </li>
                `;
    for (let i = 0; i < totalPages; i++) {
        templatePageMid += `<li class="page-item" style="color: #0a8cf0">
                                        <a class="page-link" id="pageColor" onclick="loadDataNhomDatn(${i})">${i + 1}</a>
                                    </li>`
    }
    let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == totalPages - 1 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadDataNhomDatn(Number(currentPage) + 1)">Next</a>
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
            console.log(responseData)
            let array = responseData.data.data
            totalPages = responseData.data.totalPages
            console.log(array)
            if (array.length == 0) {
                console.log(array.length)
                $("#listNhomDatn").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
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
                        data-target="#modal_create"
                        data-toggle="modal"
                        title="Xem chi tiết nhóm"
                        onclick="openModalDanhSachSinhVien('${item.id}')"
                >
                    <img src="/img/eye-fill.svg">
                </button>
                <div class="modal fade"></div>
                </td>
                </tr>
                `
                }))
                genPagingAndFillColor()
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


