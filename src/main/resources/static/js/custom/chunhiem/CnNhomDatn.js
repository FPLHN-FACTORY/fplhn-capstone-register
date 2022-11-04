const CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
const CNNhomDatnView = '/chu-nhiem/nhom-datn';
let flag = 0;
let checkListCbb = 0;
let currentPaging = -1;
const checkHanChuNhiem = $("#check_han_chu_nhiem").val();

function submitModal() {
    $("#modal_confirm_success").modal('hide')
}

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function clearData() {
    $("#maNhom").val("");
    $("#tenDeTai").val("");
    $("#tenTaiKhoan").val("");
    $("#trangThai").val("");
    $("#dotDangKyId").val("");
    $("#monDatnId").val("");
}

$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})

function loadDataNhomDatn(count) {

    let maNhom = $("#maNhom").val()
    let tenTaiKhoan = $("#tenTaiKhoan").val()
    let tenDeTai = $("#tenDeTai").val()
    let monDatnId = $("#monDatnId").val()
    let trangThai = $("#trangThai").val()
    let dotDangKyId = $("#dotDangKyId").val()
    currentPaging = count;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search" + "?maNhom=" + maNhom + "&tenDeTai=" + tenDeTai + "&monDatnId=" + monDatnId
            + "&tenTaiKhoan=" + tenTaiKhoan + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data.data
            if (array.length == 0) {
                $("#listNhomDatn").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
                if (responseData.data.totalPages == 1) {
                    loadDataNhomDatn(0);
                    return;
                }
                if (count == responseData.data.totalPages && count != 0) {
                    loadDataNhomDatn(count - 1);
                    return;
                }
            } else {
                $("#empty").html(``)
                var templateString;
                $("#listNhomDatn").html(array.map(function (item) {
                    templateString = item.trangThai == 4 | item.trangThai == 0 ? `<td><div class="form-check"><input type="checkbox" class="form-check-input" data-toggle="tooltip" title="Chọn một" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"><input hidden id="check-trang-thai" value="${item.trangThai}"></div></td>` : `<td><div class="form-check"><input disabled type="checkbox" data-toggle="tooltip" title="Chọn một" class="form-check-input" id="checkbox_son" value="${item.id}" onchange="checkBoxSonListener()"><input hidden id="check-trang-thai" value="${item.trangThai}"></div></td>`
                    return `
                <tr>`

                        + templateString +
                        `
                <td>${item.stt}</td>
                <td>${item.maNhom == null ? 'Chưa có' : item.maNhom}</td>
                <td>${item.tenDeTai == null ? 'Chưa có' : item.tenDeTai}</td>
                <td>${item.maMonDatn}</td>
                <td>${item.total}</td>
                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenGvhd + ' - ' + item.tenTaiKhoan}</td>
                <td>${item.tenDotDangKy}</td>
                <td>${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Chủ nhiệm bộ môn đã xác nhận' : (item.trangThai == 2 ? 'Giảng viên đã liên hệ' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>
                <td><a href="/chu-nhiem/sinh-vien?id=${item.id}"><div id="eye-custom" data-toggle="tooltip" title="Xem chi tiết nhóm">
                                                                    <img src="/img/eye-fill.svg"></div></a>
                <button class="btn_check_circle" style="${item.trangThai == 0 && checkHanChuNhiem == 'true' ? 'display : block' : 'display : none'}" data-toggle="tooltip" title="Xác nhận nhóm" onclick="CNXacNhanNhom('${item.id}','${count}')">
                                                                    <img src="/img/check-circle.svg"></button>
                <button class="btn_check" style="${item.trangThai == 4 && checkHanChuNhiem == 'true' ? 'display : block' : 'display : none'}" data-toggle="tooltip" title="Chốt đề tài" onclick="CNChotDuLieu('${item.id}','${count}')">
                                                                    <img src="/img/clipboard-check.svg"></button>
                <button class="btn_x_circle" style="${item.trangThai == 4 && checkHanChuNhiem == 'true' ? 'display : block' : 'display : none'}" data-toggle="tooltip" title="Từ chối chốt đề tài" onclick="openModalNhanXet('${item.id}','${count}')">
                                                                    <img src="/img/x-circle.svg"></button>
                <button class="btn_trash" style="${item.trangThai == 5 || checkHanChuNhiem == 'false' ? 'display : none' : 'display : block'}" data-toggle="tooltip" title="Xóa" onclick="openModalRemoveNhomDatn('${item.id}','${count}')">
                                                                    <img src="/img/trash.svg"></button></td>
                </tr>
                `
                }))
                $('[data-toggle = "tooltip"]').tooltip()
                $("#page").html(``)
                let templatePageMid = ``;
                let templatePageBefore = `
                <li class="page-item">
                     <a class="page-link" onclick="loadDataNhomDatn(0)">First</a>
                </li>
                <li class="page-item">
                     <a class="page-link" onclick="loadDataNhomDatn(count - 1)">Back</a>
                </li>
                `;
                for (let i = 0; i < responseData.data.totalPages; i++) {
                    templatePageMid += `<li class="page-item" >
                                        <a class="page-link" onclick="loadDataNhomDatn(${i})">${i + 1}</a>
                                    </li>`
                }
                let templatePageAfter = `
                <li class="page-item">
                     <a class="page-link" onclick="loadDataNhomDatn(count + 1)">First</a>
                </li>
                <li class="page-item">
                     <a class="page-link" onclick="loadDataNhomDatn(responseData.data.totalPages - 1)">Back</a>
                </li>
                `;
                let templateHtml = ``;
                templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
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
            // ++flag;
            // if (flag == checkListCbb) {
            //     toastr.success('Chốt thành công');
            // }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            alert(e.responseJSON.message);
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
            // ++flag;
            // if (flag == checkListCbb) {
            //     toastr.success('Xác nhận thành công');
            // }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            alert(e.responseJSON.message);
            return;
        }
    });
}

$("#nhom_mon_datn_create").change(function (event) {
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

$("#mon_datn_create").change(function (event) {
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
    var listTrangThai = document.querySelectorAll("#check-trang-thai")
    var countTrangThai = 0;

    flag = 0;
    checkListCbb = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            checkListCbb++
        }
    }

    if (checkListCbb == 0) {
        alert("Chọn nhóm muốn chốt đề tài")
    } else {

        for (let i = 0; i < list_checkbox_son.length; i++) {
            if (list_checkbox_son[i].checked && listTrangThai[i].value == 4) {
                countTrangThai++;
            }
        }

        if (countTrangThai != checkListCbb) {
            alert("Trạng thái của nhóm không thỏa mãn để thực hiện chức năng chốt")
            return;
        }

        bootbox.confirm("Bạn có chắc muốn chốt đề tài những nhóm được chọn không ?", function (item) {
            if (item) {
                for (var xx of list_checkbox_son) {
                    if (xx.checked) {
                        if (currentPaging == -1) {
                            chotDeTai(xx.value, count);
                        } else {
                            chotDeTai(xx.value, currentPaging);
                        }
                    }
                }
                var checkboxFather = document.querySelector("#checkbox_father")
                checkboxFather.checked = false;
            }
        })
    }
}

function xacNhanNhieuNhom(count) {
    var list_checkbox_son = document.querySelectorAll("#checkbox_son")
    var listTrangThai = document.querySelectorAll("#check-trang-thai")
    var countTrangThai = 0;

    flag = 0;
    checkListCbb = 0;
    for (var item of list_checkbox_son) {
        if (item.checked) {
            checkListCbb++
        }
    }

    if (checkListCbb == 0) {
        alert("Chọn nhóm muốn xác nhận")
    } else {

        for (let i = 0; i < list_checkbox_son.length; i++) {
            if (list_checkbox_son[i].checked && listTrangThai[i].value == 0) {
                countTrangThai++;
            }
        }

        if (countTrangThai != checkListCbb) {
            alert("Trạng thái của nhóm không thỏa mãn để thực hiện chức năng xác nhận")
            return;
        }

        bootbox.confirm("Bạn có chắc muốn xác nhận những nhóm được chọn không ?", function (item) {
            if (item) {
                for (var xx of list_checkbox_son) {
                    if (xx.checked) {
                        if (currentPaging == -1) {
                            xacNhanNhom(xx.value, count);
                        } else {
                            xacNhanNhom(xx.value, currentPaging);
                        }
                    }
                }
                var checkboxFather = document.querySelector("#checkbox_father")
                checkboxFather.checked = false;
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

    if (matKhau === '') {
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

$("#form_type_excel").submit(function (event) {
    event.preventDefault();
    let typeExcel = $("#type_excel").val();
    if (typeExcel == '') {
        $("#type_excel_error").text("chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/chu-nhiem/nhom-datn/export?typeExcel=" + typeExcel,

            success: function () {
                window.open("/api/chu-nhiem/nhom-datn/export?typeExcel=" + typeExcel, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});