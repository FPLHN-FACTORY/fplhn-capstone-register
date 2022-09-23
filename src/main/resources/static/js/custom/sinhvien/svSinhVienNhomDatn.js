let SVNhomDatnApi = '/api/sinh-vien/nhom-datn';
let SVNhomDatnView = '/sinh-vien/nhom-datn';

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
        url: SVNhomDatnApi + "?keyword=" + id,
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
        url: SVNhomDatnApi + "/search" + "?tenNhom=" + tenNhom + "&chuyenNganhId=" + chuyenNganhId + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
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
                <td>${item.countSinhVien}/7</td>
                <td>${item.maSinhVien}-${item.tenSinhVien}</td>
                <td>${item.tenGvhd}-${item.tenTaiKhoan}</td>
                <td><button type="button" data-toggle="modal" data-target="#modal_create" class="btn btn-success" onclick="openModalDanhSachSinhVien('${item.id}')">Xem chi tiết</button>
                <td><div class="row"><div class="col-6 d-inline"><button class=" btn btn-success"
                                    data-target="#modal_create1"
                                    data-toggle="modal"
                                    onclick="clearData();">
                                Tham gia nhóm
                            </button>
                        </div>
                    </div>
                </td>
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

function openModalCreateSinhVienJoinNhom(matKhauNhom, idNhom) {
    if (matKhauNhom == '') {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: SVNhomDatnApi + "/addSinhVien?idNhom=" + idNhom,
            success: function () {
                alert("Tham gia thanh cong")
                loadDataNhomDatn(0);
                $("#modal_create_sinh_vien_join_nhom").modal('hide');
            },
            error: function (responseData) {
            }
        });
    } else {
        $("#modal_create_sinh_vien_join_nhom").modal('show');
        $("#idNhom").val(idNhom)
        $("#matKhauNhom").val(matKhauNhom)
    }
}

$("#form_create_sinh_vien_join_nhom").submit(function(event) {
    event.preventDefault();
    let matKhauNguoiDung = $("#matKhau").val()
    let idNhom = $("#idNhom").val()
    let matKhauNhom = $("#matKhauNhom").val()
    if (matKhauNguoiDung == matKhauNhom) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: SVNhomDatnApi + "/addSinhVien?idNhom=" + idNhom,
            success: function (responseData) {
                alert("Tham gia thanh cong")
                $("#modal_create_sinh_vien_join_nhom").modal('hide');
                loadDataNhomDatn(0);
            },
            error: function (responseData) {
            }
        });
    } else {
        alert("Mat khau sai")
    }
})


