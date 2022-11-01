const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#tenHocKy").val("");
    $("#tenChuyenNganh").val("");
    $("#tenDotDangKy").val("");
}

// $(document).ready(function () {
//
//     $('.enityNhomDatn[value="2"]').closest("tr").hide()
//     var checkMocThoiGians = $("#mocthoiGian").val()
//     var chucNangs = document.querySelectorAll(".chucNangs")
//     if(checkMocThoiGians == 'false'){
//         for(var i =0; i< chucNangs.length; i++){
//             chucNangs[i].style.display = "none"
//         }
//     }
//     fillter()
//
// });

function fillter() {

    $("#ten_de_tai_update").on('change', function () {
        $("#errorMessageUpdate").text("");
    });

    $('#tenChuyenNganh').change(function () {
        seachNhomdatn(0);
    })

    $("#tenHocKy").on('change', function () {
        seachNhomdatn(0);
    });

    $("#tenDotDangKy").on('change', function () {
        seachNhomdatn(0);
    });

}

function showDetail(maNhom) {
    $("#form-showDetail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "?maNhom=" + maNhom,
        dataType: 'json',
        success: function (responseData) {
            $("#table_body_dssv").html(responseData.data.map(function (item) {
                return `
                    <tr>
                    <td>${item.stt}</td>
                    <td>${item.maSinhVien}</td>
                    <td>${item.tenSinhVien}</td>
                    <td>${item.soDienThoai}</td>
                    <td>${item.email}</td>
                    <td>${item.khoa}</td>
                    `
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function pheDuyetNhomDatn(maNhom) {
        $("#modal_nhom_datn_update").modal('show');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: nhomDatnAPI + "/" + maNhom,
            success: function (responseData) {
                $('#update_nhom_datn').val(responseData.data.id)
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
}

$("#XacNhanLienHeNhomDatn").submit(function (event) {
    event.preventDefault();
    var maNhom = $('#update_nhom_datn').val()
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: nhomDatnAPI + "/phe-duyet-nhom?maNhom=" + maNhom,
        success: function () {
            window.location.reload(false)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
})


function seachNhomdatn(index) {
    if (index == undefined) {
        index = 0
    }
    let tenChuyenNganh = $("#tenChuyenNganh").val()
    let tenHocKy = $("#tenHocKy").val()
    let tenDotDangKy = $("#tenDotDangKy").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/search" + "?chuyenNganhId=" + tenChuyenNganh
            + "&hocKyId=" + tenHocKy + "&dotDangKyId=" + tenDotDangKy + "&page=" + index,
        success: function (responseData) {
            $("#list_nhom_datn").html(responseData.data.data.map(function (item) {
                return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.totalMaSinhVien}</td>
                <td>${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Đã liên hệ giảng viên' : (item.trangThai == 2 ? 'sẵn sàng phê duyệt' : (item.trangThai == 3 ? 'cần sửa' : (item.trangThai == 4 ? 'Giáo viên đã chốt' : 'CNBM đã chốt'))))}</td>
                <td>
                     <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "showDetail('${item.id}')">
                                    chi tiết nhóm
                     </button>
                 </td>
                 <td>
                     <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "pheDuyetNhomDatn('${item.id}')"
                                    style="${item.trangThai == 1 ? 'display: block' : 'display: none'}">
                                    xác nhận
                     </button>
                     <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "updateTenDeTai('${item.id}')"
                                    style="${item.trangThai ==2 ? 'display: block' : (item.trangThai ==3 ? 'display: block' : (item.trangThai ==4 ? 'display: block' : 'display: none'))}">
                                    sửa đề tài
                     </button>
                     <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "chotDeTaiNhomDatn('${item.id}')"
                                    style="${item.trangThai ==2 ? 'display: block' : (item.trangThai ==3 ? 'display: block' :  'display: none')}">
                                    Chốt đề tài
                     </button>
                 </td>
                </tr>
                `
            }))
            $("#page").html(``)
            let templateHtml = ``;
            for (let i = 0; i < responseData.data.totalPages; i++) {
                templateHtml += `<li class="page-item" >
                                <a class="page-link" onclick="seachNhomdatn(${i})">${i + 1}</a>
                            </li>`
            }
            if (responseData.data.totalPages == 1) {
                templateHtml = ``;
            }
            $("#page").html(templateHtml)

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function updateTenDeTai(maNhom) {
        $("#modal_update_ten_de_tai").modal('show');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: nhomDatnAPI + "/" + maNhom,
            dataType: 'json',
            success: function (responseData) {
                $("#id_nhom_datn_update").val(responseData.data.id);
                $("#ten_de_tai_update").val(responseData.data.tenDeTai);
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
}

$("#updateTenDeTaiNhomDatn").submit(function (event) {
    event.preventDefault()
    let tenDeTai = $("#ten_de_tai_update").val();
    let maNhom = $("#id_nhom_datn_update").val();

    let NhomDatnRequest = {};
    NhomDatnRequest["tenDeTai"] = tenDeTai;
    NhomDatnRequest["maNhom"] = maNhom;
    console.log(NhomDatnRequest)
    if (tenDeTai.trim().length == 0) {
        $("#errorMessageUpdate").text("Tên đề tài không được để trống");
    } else if (tenDeTai.trim().length < 6) {
        $("#errorMessageUpdate").text("Tên đề tài tối thiếu 6 ký tự");
    } else {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: nhomDatnAPI + "/update-ten-de-tai/" + maNhom,
            data: JSON.stringify(NhomDatnRequest),
            dataType: 'json',
            success: function () {
                window.location.reload(false)
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function chotDeTaiNhomDatn(maNhom) {
        $("#modal_nhom_datn_chot_de_tai").modal('show');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: nhomDatnAPI + "/" + maNhom,
            success: function (responseData) {
                $('#ma_nhom_datn').val(responseData.data.id)
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
}

$("#submitChotDeTaiNhomDatn").submit(function (event) {
    event.preventDefault()
    var maNhom = $('#ma_nhom_datn').val()
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: nhomDatnAPI + "/phe-duyet-de-tai-nhom?maNhom=" + maNhom,
        success: function () {
            window.location.reload(false)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
})
