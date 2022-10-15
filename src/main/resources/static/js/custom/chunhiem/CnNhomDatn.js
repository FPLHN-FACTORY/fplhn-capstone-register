let CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
let CNNhomDatnView = '/chu-nhiem/nhom-datn';

function submitModal() {
    $("#modal_confirm_success").modal('hide')
}

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#tenNhom").val("");
    $("#chuyenNganhId").val("");
    $("#tenGvhd").val("");
    $("#trangThai").val("");
    $("#dotDangKyId").val("");
}

$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})

function loadDataNhomDatn(count) {

    let tenNhom = $("#tenNhom").val()
    let chuyenNganhId = $("#chuyenNganhId").val()
    let tenGvhd = $("#tenGvhd").val()
    let trangThai = $("#trangThai").val()
    let dotDangKyId = $("#dotDangKyId").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search" + "?tenNhom=" + tenNhom + "&chuyenNganhId=" + chuyenNganhId
            + "&tenGvhd=" + tenGvhd + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData);
            let array = responseData.data.data
            if (array.length == 0) {
                $("#listNhomDatn").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
            } else {
                $("#empty").html(``)
                var templateString;
                $("#listNhomDatn").html(array.map(function (item) {
                    templateString = item.trangThai == 4 | item.trangThai == 0 ? `<td><input type="checkbox" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"></td>` : `<td><input disabled type="checkbox" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"></td>`
                    return `
                <tr>`

                        + templateString +
                        `
                <td>${item.stt}</td>
                <td>${item.tenNhom == null ? 'Chưa có' : item.tenNhom}</td>
                <td>${item.tenDeTai == null ? 'Chưa có' : item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.total}</td>
                <td>${item.maSinhVien + ' - ' + item.tenSinhVien}</td>
                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenGvhd + ' - ' + item.tenTaiKhoan}</td>
                <td>${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Chủ nhiệm bộ môn đã xác nhận' : (item.trangThai == 2 ? 'Giảng viên đã xác nhận' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>
                <td><a class="btn btn-primary"  href="/chu-nhiem/sinh-vien?id=${item.id}">Chi tiết nhóm</a></td>
                <td><button style="${item.trangThai == 0 ? 'display : block' : 'display : none'}" type="button" class="btn btn-primary" onclick="CNXacNhanNhom('${item.id}','${count}')">Xác nhận</button></td>
                <td><button style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" type="button" class="btn btn-primary" onclick="CNChotDuLieu('${item.id}','${count}')">Chốt đề tài</button> | 
                <button type="button" class="btn btn-danger text-white" style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" onclick="openModalNhanXet('${item.id}','${count}')">Từ chối</button></td>
                <td><button style="${item.trangThai == 5 ? 'display : none' : 'display : block'}" type="button" class="btn btn-danger text-white" onclick="openModalRemoveNhomDatn('${item.id}','${count}')">Xóa</button>
                </tr>
                `
                }))
                $("#page").html(``)
                let templateHtml = ``;
                for (let i = 0; i < responseData.data.totalPages; i++) {
                    templateHtml += `<li class="page-item" >
                                        <a class="page-link" onclick="loadDataNhomDatn(${i})">${i + 1}</a>
                                    </li>`
                }
                if (responseData.data.totalPages == 1) {
                    templateHtml = ``;
                }
                $("#page").html(templateHtml);
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function CNChotDuLieu(id, count) {
    $("#modal_chot_de_tai").modal('show')
    $("#id_nhom_datn_chot").val(id)
    $("#currentPagingChot").val(count)
}

$("#form_chot_de_tai").submit(function (event) {
    event.preventDefault();
    let id = $("#id_nhom_datn_chot").val()
    let count = $("#currentPagingChot").val()
    chotDeTai(id, count)
    $("#modal_chot_de_tai").modal('hide')
    $("#messageSuccess").text("Chốt đề tài thành công")
    $("#modal_confirm_success").modal('show')
});

function chotDeTai(id, count) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: CNNhomDatnApi + "/chot-de-tai/" + id + "?page=" + count,
        dataType: 'json',
        success: function () {
            loadDataNhomDatn(count)
        },
        error: function (e) {
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
}

function CNXacNhanNhom(id, count) {
    $("#modal_xac_nhan_nhom").modal('show')
    $("#id_nhom_datn_xac_nhan").val(id)
    $("#currentPagingXacNhan").val(count)
}

$("#form_xac_nhan_nhom").submit(function (event) {
    event.preventDefault();
    let id = $("#id_nhom_datn_xac_nhan").val()
    let count = $("#currentPagingXacNhan").val()
    xacNhanNhom(id, count)
    $("#modal_xac_nhan_nhom").modal('hide')
    $("#messageSuccess").text("Xác nhận nhóm thành công")
    $("#modal_confirm_success").modal('show')
});

function xacNhanNhom(id, count) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: CNNhomDatnApi + "/xac-nhan-nhom/" + id + "?page=" + count,
        dataType: 'json',
        success: function () {
            loadDataNhomDatn(count)
        },
        error: function (e) {
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
}

function openModalRemoveNhomDatn(id, count) {
    $("#modal_nhom_datn_remove").modal('show');
    $("#currentPaging").val(count)

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
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
}

function openModalNhanXet(id, count) {
    $("#modal_nhom_datn_nhan_xet").modal('show');
    $("#currentPaging").val(count)

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/" + id,
        data: JSON.stringify(id),
        dataType: 'json',
        success: function () {
            $("#id_nhan_xet_nhom_datn").val(id);
        },
        error: function (e) {
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
}

$("#form_nhom_datn_nhan_xet").submit(function (event) {
    event.preventDefault();
    let nhomDatnId = $("#id_nhan_xet_nhom_datn").val();
    let nhanXet = $("#nhan_xet").val()
    let currentPaging = $("#currentPaging").val();

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: CNNhomDatnApi + "?id=" + nhomDatnId + "&nhanXet=" + nhanXet,
        data: JSON.stringify(nhomDatnId),
        dataType: 'json',
        success: function () {
            loadDataNhomDatn(currentPaging)
            $("#modal_nhom_datn_nhan_xet").modal("hide");
        },
        error: function (e) {
            $("#modal_nhom_datn_nhan_xet").modal("hide");
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
});

$("#form_nhom_datn_delete").submit(function (event) {
    event.preventDefault();
    let nhomDatnId = $("#remove_nhom_datn").val();
    let currentPaging = $("#currentPaging").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: CNNhomDatnApi + "/" + nhomDatnId,
        data: JSON.stringify(nhomDatnId),
        dataType: 'json',
        success: function () {
            loadDataNhomDatn(currentPaging)
            $("#modal_nhom_datn_remove").modal("hide");
        },
        error: function (e) {
            $("#messageSuccess").text(e.responseJSON.message)
            $("#modal_confirm_success").modal('show')
            return;
        }
    });
});

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

function chotNhieuDeTai(count) {
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    var check = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            check++
        }
    }
    if (check == 0) {
        $("#messageSuccess").text("Hãy chọn nhóm muốn chốt đề tài")
        $("#modal_confirm_success").modal('show')
    } else {
        $("#modal_chot_nhieu_de_tai").modal('show')
        $("#currentPagingChotNhieu").val(count)
    }
}

$("#form_chot_nhieu_de_tai").submit(function (event) {
    event.preventDefault();
    let count = $('#currentPagingChotNhieu').val()
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    for (var item of list_checkbox_son) {
        if (item.checked) {
            chotDeTai(item.value, count);
        }
    }
    $("#modal_chot_nhieu_de_tai").modal('hide')
    $("#messageSuccess").text("Chốt thành công")
    $("#modal_confirm_success").modal('show')
})

function xacNhanNhieuNhom(count) {
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    var check = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            check++
        }
    }
    if (check == 0) {
        $("#messageSuccess").text("Hãy chọn nhóm muốn xác nhận")
        $("#modal_confirm_success").modal('show')
    } else {
        $("#modal_xac_nhan_nhieu_nhom").modal('show')
        $("#currentPagingXacNhanNhieu").val(count)
    }
}

$("#form_xac_nhan_nhieu_nhom").submit(function (event) {
    event.preventDefault();
    let count = $('#currentPagingXacNhanNhieu').val()
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    for (var item of list_checkbox_son) {
        if (item.checked) {
            xacNhanNhom(item.value, count);
        }
    }
    $("#modal_xac_nhan_nhieu_nhom").modal('hide')
    $("#messageSuccess").text("Xác nhận thành công")
    $("#modal_confirm_success").modal('show')
})

function clearForm() {
    $("#matKhau").val("");
    $("#maGiangVien").val("");
    $("#maTruongNhom").val("");
}

function openModalCreate(){
    $("#modal_create").modal('show')
}

$("#form_create_nhom_datn").submit(function (event) {
    event.preventDefault();

    let matKhau = $("#matKhau").val().trim();
    let maGiangVien = $("#maGiangVien").val().trim();
    let maTruongNhom = $("#tags").val().trim().split('_');

    let nhomDatnRequest = {};
    nhomDatnRequest["matKhau"] = matKhau;
    nhomDatnRequest["maGiangVien"] = maGiangVien;
    nhomDatnRequest["maSinhVien"] = maTruongNhom[0];

    if (matKhau.length < 6) {
        $("#mat_khau_erorr").text("Mật khẩu tối thiểu 6 ký tự");
    } else if (maTruongNhom.length == 0) {
        $("#ma_truong_nhom_erorr").text("Mã trưởng nhóm không được để trống");
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: CNNhomDatnApi,
            data: JSON.stringify(nhomDatnRequest),
            dataType: 'json',
            success: function () {
                window.open(CNNhomDatnView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                $("#modal_create").modal("hide");
                $("#messageSuccess").text(e.responseJSON.message)
                $("#modal_confirm_success").modal('show')
            }
        });
    }
});

function searchSinhVienNoGroup() {
    let text = $("#tags").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/list-sinh-vien-no-group" + "?maSinhVien=" + text + "&tenSinhVien=" + text,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data
            $("#tags").autocomplete({
                source: array
            });
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}















