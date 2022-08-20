let CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
let CNNhomDatnView = '/chu-nhiem/nhom-datn';

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#tenNhom").val("");
    $("#tenChuyenNganh").val("");
    $("#tenGvhd").val("");
    $("#trangThai").val("");
}

function openModalDanhSachSinhVien(id) {
    $("#modal_show_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "?keyword=" + id,
        data: JSON.stringify(id),
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            $("#table_body_dssv").html(responseData.data.map(function (item) {
                return `
                <tr>
                <td>${item.maSinhVien}</td>
                <td>${item.tenSinhVien}</td>
                <td>${item.soDienThoai}</td>
                <td>${item.email}</td>
                <td>${item.khoa}</td>
                <td><button type="button" class="btn btn-danger text-white">Xóa thành viên</button></td>
                </tr>
                `
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


function openModalRemoveNhomDatn(id, trangThai) {
    if (trangThai == 2) {
        alert("Không thể xóa nhóm đã chốt")
    } else {
        $("#modal_nhom_datn_remove").modal('show');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: CNNhomDatnApi + "/" + id,
            data: JSON.stringify(id),
            dataType: 'json',
            success: function () {
                $("#remove_nhom_datn").val(id);
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

$("#form_nhom_datn_delete").submit(function (event) {
    event.preventDefault();
    let nhomDatnId = $("#remove_nhom_datn").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: CNNhomDatnApi + "/" + nhomDatnId,
        data: JSON.stringify(nhomDatnId),
        dataType: 'json',
        success: function () {
            window.open(CNNhomDatnView, '_self');
            $("#modal_nhom_datn_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);

        }
    });

});

$("#form_list_giang_vien").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn();
})

function loadDataNhomDatn() {

    let tenNhom = $("#tenNhom").val()
    let tenChuyenNganh = $("#tenChuyenNganh").val()
    let tenGvhd = $("#tenGvhd").val()
    let trangThai = $("#trangThai").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search" + "?tenNhom=" + tenNhom + "&tenChuyenNganh=" + tenChuyenNganh
            + "&tenGvhd=" + tenGvhd + "&trangThai=" + trangThai,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            $("#list").html(responseData.data.data.map(function (item) {
                return  `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.total}</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd}</td>
                <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
                <td><a class="btn btn-primary"  href="/chu-nhiem/sinh-vien?idNhom=${item.id}">Chi tiết nhóm</a></td>
                <td><button style="${item.trangThai == 1 ? 'display : block' : 'display : none'}" type="button" class="btn btn-primary" onclick="CNChotDuLieu('${item.trangThai}','${item.id}')">Chốt đề tài</button></td>
                <td><button style="${item.trangThai == 2 ? 'display : none' : 'display : block'}" type="button" class="btn btn-danger text-white" onclick="openModalRemoveNhomDatn('${item.id}','${item.trangThai}')">Xóa</button>
                </tr>
                `
            }))
            $("#page").html(``)
            $("#page").html(
                responseData.data.dataPage.map(function (item){
                    if(responseData.data.dataPage.length == 1){
                        return ``
                    }
                    return `<li class="page-item" >
                                <a class="page-link" onclick="loadDataNhomDatn(${item}-1)">${item}</a>
                            </li>`
                })
            )
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function CNChotDuLieu(trangThai, id) {
    let checkConfirm = confirm("Bạn có chắc muốn chốt dữ liệu không?");
    if (checkConfirm) {
        alert("Chốt thành công")
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: CNNhomDatnApi + "/" + id,
            data: JSON.stringify(id),
            dataType: 'json',
            success: function () {
                // loadDataNhomDatn(0)
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#tenHocKy").val("");
    $("#tenChuyenNganh").val("");
    $("#tenDotDangKy").val("");
}

$(document).ready(function () {
    $("#hoc_ky_error").text("");
    $("#id_nhom_datn_update").text("");
    $("#ten_de_tai_update").text("");

});

function showDetail(maNhom) {
    $("#form-showDetail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "?maNhom=" + maNhom,
        data: JSON.stringify(),
        dataType: 'json',
        success: function (responseData) {
            $("#table_body_dssv").html(responseData.data.map(function (item) {
                return `
                <tr>
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

function chotDuLieu(maNhom) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: nhomDatnAPI + "/" + maNhom,
        success: function () {
            window.location.reload(true);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function seachNhomdatn() {
    let tenChuyenNganh = $("#tenChuyenNganh").val()
    let tenHocKy = $("#tenHocKy").val()
    let tenDotDangKy = $("#tenDotDangKy").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/search" + "?tenChuyenNganh=" + tenChuyenNganh
            + "&tenHocKy=" + tenHocKy + "&tenDotDangKy=" + tenDotDangKy,
        dataType: 'json',
        success: function (responseData) {
            $("#list_nhom_datn").html(responseData.data.map(function (item) {
                return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.totalMaSinhVien}</td>
                 <td>
                           <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "showDetail('${item.id}')">
                                    chi tiết nhóm
                            </button>
                       </td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd}</td>
                <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
                       <td>
                           <button
                                    type="button"
                                    class="btn btn-primary"
                                    id="chotDuLieu" style="${item.trangThai == 1 ? 'display : none' : 'display : block'}" 
                                    onclick="chotDuLieu('${item.id}')">Chốt
                           </button>
                      </td>
                </tr>
                `
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}