const SVNhomDatnApi = '/api/sinh-vien';
const SVNhomDatnView = '/sinh-vien';

function clearform() {
    $("#tenDeTai").val("");
    $("#truongNhom").val("");
    $("#giangVien").val("");
    $("#soLuong").val("");
    $("#errorPassword").text("");
    $("#inputPassword").val("");
}

function openModalDanhSachSinhVien(id) {
    $("#modal_show_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: SVNhomDatnApi + "?id=" + id,
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

$("#form_search").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})

function loadDataNhomDatn(count) {
    let tenDeTai = $("#tenDeTai").val()
    let soLuong = $("#soLuong").val()
    let giangVien = $("#giangVien").val()
    let truongNhom = $("#truongNhom").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: SVNhomDatnApi + "/search" + "?tenDeTai=" + tenDeTai + "&truongNhom=" + truongNhom + "&soLuong=" + soLuong + "&giangVien=" + giangVien + "&page=" + count,
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
                <td>${item.matKhauTonTai == 0 ? 'Công khai' : 'Nhóm kín'}</td>
                <td><button type="button" data-toggle="modal" data-target="#modal_create" class="btn btn-success" onclick="openModalDanhSachSinhVien('${item.id}')">Xem chi tiết</button>
                <td><div class="row"><div class="col-6 d-inline"><button class=" btn btn-success"
                                    data-target="#modal_create1"
                                    data-toggle="modal"
                                    onclick="openModalAddSinhVienJoinNhom(${item.matKhauTonTai}, '${item.id}')"
                                    >
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

function openModalAddSinhVienJoinNhom(matKhauTonTai, idNhom) {
    clearform();
    if (Number(matKhauTonTai) == 0) {
        let addSinhVienRequest = {};
        addSinhVienRequest["idNhom"] = idNhom;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: SVNhomDatnApi + "/add",
            data: JSON.stringify(addSinhVienRequest),
            datatype: 'json',
            success: function () {
                $("#modal_confirm_success").modal('show');
            },
            error: function (responseData) {
                console.log(responseData);
            }
        });
    } else {
        $("#modal_add_sinh_vien").modal('show');
        $("#idNhom").val(idNhom);
    }
}

function addSinhVienHasPassword() {
    let inputPassword = $("#inputPassword").val();
    let idNhom = $("#idNhom").val();
    let addSinhVienRequest = {};
    addSinhVienRequest["idNhom"] = idNhom;
    addSinhVienRequest["inputPassword"] = inputPassword;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: SVNhomDatnApi + "/add",
        data: JSON.stringify(addSinhVienRequest),
        datatype: 'json',
        success: function (responseData) {
            $("#modal_add_sinh_vien").modal('hide');
            $("#modal_confirm_success").modal('show');
        },
        error: function (responseData) {
            console.log(responseData);
            if (responseData.responseJSON.message === 'Mat Khau Khong Chinh Xac') {
                $("#errorPassword").text("Mật khẩu không chính xác");
            }
        }
    });
}

$("#form_confirm_success").submit(function (event) {
    event.preventDefault();
    window.open(SVNhomDatnView, '_self');
})
