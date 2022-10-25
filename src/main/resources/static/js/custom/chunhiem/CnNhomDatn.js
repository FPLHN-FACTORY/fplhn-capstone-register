let CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
let CNNhomDatnView = '/chu-nhiem/nhom-datn';
let flag = 0;
let checkListCbb = 0;

var pages = {
        'nhomDatn' : `<p>aaaaaaaaaaaaaaaaa</p>`,
    'sinhVien' : '<p>Bloooooooooooo</p>'
}

function submitModal() {
    $("#modal_confirm_success").modal('hide')

}

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
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
                    templateString = item.trangThai == 4 | item.trangThai == 0 ? `<td><div class="form-check"><input type="checkbox" class="form-check-input" data-toggle="tooltip" title="Chọn một" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"></div></td>` : `<td><div class="form-check"><input disabled type="checkbox" data-toggle="tooltip" title="Chọn một" class="form-check-input" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"></div></td>`
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
                <td><a href="/chu-nhiem/sinh-vien?id=${item.id}"><div id="eye-custom" data-toggle="tooltip" title="Xem chi tiết nhóm">
                                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                                         width="16" height="16" fill="currentColor"
                                                                         class="bi bi-eye-fill" viewBox="0 0 16 16">
                                                                        <path d="M10.5 8a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0z"/>
                                                                        <path d="M0 8s3-5.5 8-5.5S16 8 16 8s-3 5.5-8 5.5S0 8 0 8zm8 3.5a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7z"/>
                                                                    </svg></div></a>
                <a style="${item.trangThai == 0 ? 'display : block' : 'display : none'}" type="button" onclick="CNXacNhanNhom('${item.id}','${count}')"><div id="custom-xac-nhan" data-toggle="tooltip" title="Xác nhận">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                         height="16" fill="currentColor"
                                                                         class="bi bi-check-circle" viewBox="0 0 16 16">
                                                                        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                                        <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z"/>
                                                                    </svg>
                                                                </div></a>
                <a style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" type="button" onclick="CNChotDuLieu('${item.id}','${count}')"><div id="custom-chot-de-tai" data-toggle="tooltip"
                                                                     title="Chốt đề tài">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                         height="16" fill="currentColor"
                                                                         class="bi bi-clipboard-check-fill"
                                                                         viewBox="0 0 16 16">
                                                                        <path d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3Zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5h3Z"/>
                                                                        <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5v-1Zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708Z"/>
                                                                    </svg>
                                                                </div></a>
                <a type="button" style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" onclick="openModalNhanXet('${item.id}','${count}')"><div id="custom-tu-choi" data-toggle="tooltip"
                                                                     title="Từ chối">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                                         height="16" fill="currentColor"
                                                                         class="bi bi-x-lg"
                                                                         viewBox="0 0 16 16">
                                                                        <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z"/>
                                                                    </svg>
                                                                </div></a>
                <a style="${item.trangThai == 5 ? 'display : none' : 'display : block'}" type="button" onclick="openModalRemoveNhomDatn('${item.id}','${count}')"><div id="custom-delete" data-toggle="tooltip"
                                                                     title="Xóa">
                                                                    <svg xmlns="http://www.w3.org/2000/svg"
                                                                         width="16"
                                                                         height="16" fill="currentColor"
                                                                         class="bi bi-trash3" viewBox="0 0 16 16">
                                                                        <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"/>
                                                                    </svg>
                                                                </div></a></td>
                </tr>
                `
                }))
                $('[data-toggle = "tooltip"]').tooltip()
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
    flag = 0;
    checkListCbb = 1;
    bootbox.confirm("Bạn có chắc muốn chốt đề tài không ?", function (item) {
        if (item) {
            chotDeTai(id, count)
        }
    })
}

function chotDeTai(id, count) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: CNNhomDatnApi + "/chot-de-tai/" + id + "?page=" + count,
        dataType: 'json',
        success: function () {
            ++flag;
            if (flag == checkListCbb) {
                toastr.success('Chốt thành công');
            }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            toastr.error(e.responseJSON.message);
            return;
        }
    });
}

function CNXacNhanNhom(id, count) {
    flag = 0;
    checkListCbb = 1;
    bootbox.confirm("Bạn có chắc muốn xác nhận không ?", function (item) {
        if (item) {
            xacNhanNhom(id, count)
        }
    })
}

function xacNhanNhom(id, count) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: CNNhomDatnApi + "/xac-nhan-nhom/" + id + "?page=" + count,
        dataType: 'json',
        success: function () {
            ++flag;
            if (flag == checkListCbb) {
                toastr.success('Xác nhận thành công');
            }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            toastr.error(e.responseJSON.message);
            return;
        }
    });
}

$("#nhom_mon_datn_create").change(function (event){
    event.preventDefault();
    let nhomMonId = $("#nhom_mon_datn_create").val();

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/list-mon-datn" + "?nhomMonId=" + nhomMonId,
        data: JSON.stringify(nhomMonId),
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data
            var text = "<option value='' hidden selected>Chọn môn dự án tốt nghiệp</option>"
            array.map(function (item) {
                text += `<option value="${item.id}" >${item.tenMonDatn}</option>`
            })
            $('#mon_datn_create').html(text)
        },
        error: function (e) {
        }
    });
})

$("#mon_datn_create").change(function (event){
    event.preventDefault();
    let idMon = $("#mon_datn_create").val();

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/list-gvhd" + "?idMon=" + idMon,
        data: JSON.stringify(idMon),
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data
            var text = "<option value='' hidden selected>Chọn giảng viên hướng dẫn</option>"
            array.map(function (item) {
                text += `<option value="${item.id}" >${item.tenTaiKhoan + '_' + item.tenGvhd}</option>`
            })
            $('#gvhd_create').html(text)
        },
        error: function (e) {
        }
    });
})

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
    flag = 0;
    checkListCbb = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            checkListCbb++
        }
    }
    if (checkListCbb == 0) {
        toastr.warning('Hãy chọn nhóm muốn chốt');
    } else {
        bootbox.confirm("Bạn có chắc muốn chốt đề tài những nhóm được chọn không ?", function (item) {
            if (item) {
                for (var item of list_checkbox_son) {
                    if (item.checked) {
                        chotDeTai(item.value, count);
                    }
                }
            }
        })
    }
}

function xacNhanNhieuNhom(count) {
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    flag = 0;
    checkListCbb = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            checkListCbb++
        }
    }
    if (checkListCbb == 0) {
        toastr.warning('Hãy chọn nhóm muốn xác nhận');
    } else {
        bootbox.confirm("Bạn có chắc muốn xác nhận những nhóm được chọn không ?", function (item) {
            if (item) {
                for (var item of list_checkbox_son) {
                    if (item.checked) {
                        xacNhanNhom(item.value, count);
                    }
                }
            }
        })
    }
}

function clearForm() {
    $("#matKhau").val("");
    $("#maGiangVien").val("");
    $("#maTruongNhom").val("");
}

function openModalCreate() {
    $("#modal_create").modal('show')
}

$("#form_create_nhom_datn").submit(function (event) {
    event.preventDefault();

    let matKhau = $("#matKhau").val().trim();
    let monDatn = $("#mon_datn_create").val();
    let maGiangVien = $("#gvhd_create").val().trim().split('_');
    let maTruongNhom = $("#tags").val().trim().split('_');

    if(matKhau === ''){
        matKhau = null;
    }

    let nhomDatnRequest = {};
    nhomDatnRequest["matKhau"] = matKhau;
    nhomDatnRequest["idGiangVien"] = maGiangVien[0];
    nhomDatnRequest["maSinhVien"] = maTruongNhom[0];
    nhomDatnRequest["monDatnId"] = monDatn;

    if (matKhau != null && matKhau.length < 6 && matKhau.length > 0) {
        $("#mat_khau_erorr").text("Mật khẩu tối thiểu 6 ký tự");
    } else if (maTruongNhom[0].length == 0) {
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















