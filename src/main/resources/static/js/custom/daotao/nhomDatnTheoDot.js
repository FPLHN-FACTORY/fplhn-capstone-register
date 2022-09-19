let DTNhomDatnApi = '/api/dao-tao/nhom-datn';

function clearform() {
    $("#tenNhom").val("");
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
            console.log(responseData.data.data)
            var text = "";
            responseData.data.data.map(function (item) {
                text += `
                <tr>
                <td>${item.stt}</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenSinhVien}</td>
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

function loadDataNhomDatn(count) {
    let tenNhom = $("#tenNhom").val()
    let chuyenNganhId = $("#chuyenNganh").val()
    let dotDangKyId = $("#dotDangKy").val()
    let trangThai = $("#trangThai").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/search" + "?tenNhom=" + tenNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            let array = responseData.data.data.data
            console.log(array)
            if (array.length == 0) {
                console.log(array.length)
                $("#listNhomDatn").html(``)
                $("#list_page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
            } else {
                $("#empty").html(``)
                $("#listNhomDatn").html(array.map(function (item) {
                    return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.countSinhVien}/7</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd}</td>
                
                <td>${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Đã liên hệ giảng viên' : (item.trangThai == 2 ? 'Sẵn sàng phê duyệt' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>                <td><button type="button" data-toggle="modal" data-target="#modal_create" class="btn btn-primary" onclick="openModalDanhSachSinhVien('${item.id}')">Chi tiết thành viên</button>
                <div class="modal fade"></div>
                </td>
                </tr>
                `
                }))
                $("#list_page").html(``)
                $("#list_page").html(responseData.data.list.map(function (item, index) {
                    if (responseData.data.list.length == 1) {
                        return ``
                    }
                    return `<li class="page-item" >
                                <a class="page-link" onclick="loadDataNhomDatn(${index})">${item}</a>
                            </li>`
                }))
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}