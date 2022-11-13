const CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
const CNNhomDatnView = '/chu-nhiem/nhom-datn';
let flag = 0;
let checkListCbb = 0;
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})

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
                // toastr.success('Chốt thành công');
                bootbox.alert('Chốt thành công')
            }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            bootbox.alert({
                message: e.responseJSON.message + '',
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
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
                // toastr.success('Xác nhận thành công');
                bootbox.alert('Xác nhận thành công')
            }
            loadDataNhomDatn(count)
        },
        error: function (e) {
            bootbox.alert({
                message: e.responseJSON.message + '',
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
            return;
        }
    });
}

$('#nhom_mon_datn_create').change(function (event) {
    event.preventDefault();
    let nhomMonId = $('#nhom_mon_datn_create').val();

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

function openModalRemoveNhomDatn(nhomDatnId, count) {
    bootbox.confirm({
        title: "Xóa nhóm dự án tốt nghiệp",
        message: "Bạn muốn xóa nhóm này không?",
        backdrop: true,
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: CNNhomDatnApi + "/" + nhomDatnId,
                    data: JSON.stringify(nhomDatnId),
                    dataType: 'json',
                    success: function () {
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                loadDataNhomDatn(0);
                            }
                        });
                    },
                    error: function (e) {
                        bootbox.alert({
                            message: e.responseJSON.message,
                            backdrop: true,
                            callback: function () {
                                loadDataNhomDatn(0);
                            }
                        });
                        return;
                    }
                });
            }
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
            bootbox.alert({
                message: e.responseJSON.message,
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
            return;
        }
    });
}

function tuChoi() {
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
            bootbox.alert({
                message: e.responseJSON.message,
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
            return;
        }
    });
}

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
        bootbox.alert({
            message: 'Chọn nhóm muốn chốt đề tài',
            backdrop: true,
            callback: function () {
                loadDataNhomDatn(0);
            }
        });
    } else {

        for (let i = 0; i < list_checkbox_son.length; i++) {
            if (list_checkbox_son[i].checked && listTrangThai[i].value == 4) {
                countTrangThai++;
            }
        }

        if (countTrangThai != checkListCbb) {
            bootbox.alert({
                message: 'Trạng thái của nhóm không thỏa mãn để thực hiện chức năng chốt',
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
            return;
        }
        bootbox.confirm({
            title: "Chốt đề tài nhiều nhómd",
            message: "Bạn có chắc muốn chốt đề tài những nhóm được chọn không?",
            backdrop: true,
            buttons: {
                cancel: {
                    className: 'btn-outline-secondary btn btn-light',
                    label: '<i class="fa fa-times"></i> Quay lại'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Xác nhận'
                }
            },
            callback: function (result) {
                if (result) {
                    for (var xx of list_checkbox_son) {
                        if (xx.checked) {
                            if (currentPage == -1) {
                                chotDeTai(xx.value, count);
                            } else {
                                chotDeTai(xx.value, currentPage);
                            }
                        }
                    }
                    var checkboxFather = document.querySelector("#checkbox_father")
                    checkboxFather.checked = false;
                }
            }
        });
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
        bootbox.alert({
            message: 'Chọn nhóm muốn xác nhận',
            backdrop: true,
            callback: function () {
                loadDataNhomDatn(0);
            }
        });
    } else {

        for (let i = 0; i < list_checkbox_son.length; i++) {
            if (list_checkbox_son[i].checked && listTrangThai[i].value == 0) {
                countTrangThai++;
            }
        }

        if (countTrangThai != checkListCbb) {
            bootbox.alert({
                message: 'Trạng thái của nhóm không thỏa mãn để thực hiện chức năng xác nhận',
                backdrop: true,
                callback: function () {
                    loadDataNhomDatn(0);
                }
            });
            return;
        }
        bootbox.confirm({
            title: "Xác nhận nhiều nhómd",
            message: "Bạn có chắc muốn xác nhận những nhóm được chọn không?",
            backdrop: true,
            buttons: {
                cancel: {
                    className: 'btn-outline-secondary btn btn-light',
                    label: '<i class="fa fa-times"></i> Quay lại'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Xác nhận'
                }
            },
            callback: function (result) {
                if (result) {
                    for (var xx of list_checkbox_son) {
                        if (xx.checked) {
                            if (currentPage == -1) {
                                xacNhanNhom(xx.value, count);
                            } else {
                                xacNhanNhom(xx.value, currentPage);
                            }
                        }
                    }
                    var checkboxFather = document.querySelector("#checkbox_father")
                    checkboxFather.checked = false;
                }
            }
        });
    }
}

function clearForm() {
    $("#matKhau").val("");
    $("#gvhd_create").val("");
    $("#tags").val("");
}

function openModalCreate() {
    $("#modal_create").modal('show')
}

function create() {

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
                clearForm()
                $("#modal_create").modal("hide");
                bootbox.alert({
                    message: 'Thêm thành công',
                    backdrop: true,
                    callback: function () {
                        loadDataNhomDatn(0);
                    }
                });
            },
            error: function (e) {
                $("#modal_create").modal("hide");
                bootbox.alert({
                    message: e.responseJSON.message,
                    backdrop: true,
                    callback: function () {
                        loadDataNhomDatn(0);
                    }
                });
            }
        });
    }
}

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
        }
    });
}

function openModalExport() {
    $("#modal_excel").modal('show')
}

function openModalImport() {
    $("#import_excel_error").text("");
    $("#labelFile").text('Choose file');
    $("#inputGroupFile01").val('');
    $("#modal_import_excel").modal('show')
}

function exportFile() {
    let typeExcel = $("#type_excel").val()
    if (typeExcel == '') {
        $("#type_excel_error").text("chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: CNNhomDatnApi + "/export?typeExcel=" + typeExcel,
            success: function () {
                window.open(CNNhomDatnApi + "/export?typeExcel=" + typeExcel, '_self');
                $("#modal_excel").modal('hide')
            },
            error: function (e) {
            }
        });
    }
}

function importExcel() {
    var form = $('#form_import_excel')[0];
    let typeExcel = $("#inputGroupFile01").val();
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length)
    if (typeExcel.length <= 0) {
        $("#import_excel_error").text("Hãy chọn file excel cần import");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        $.ajax({
                type: 'POST',
                url: CNNhomDatnApi + '/import',
                enctype: 'multipart/form-data',
                processData: false,
                data: new FormData(form),
                contentType: false,
                cache: false,
                success: function (response) {
                    if (response.data.status == true) {
                        bootbox.alert({
                            message: 'Import file excel thành công',
                            backdrop: true,
                            callback: function () {
                                loadDataNhomDatn(0);
                            }
                        });
                    } else {
                        bootbox.alert({
                            message: response.data.message,
                            backdrop: true,
                            callback: function () {
                                loadDataNhomDatn(0);
                            }
                        });
                    }
                    $('#modal_import_excel').modal("hide");
                },
                error: function (e) {
                }
            }
        );
    } else {
        $("#import_excel_error").text("Hãy chọn đúng file excel");
    }
}

function reload() {
    window.open('/chu-nhiem/nhom-datn', '_self');
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
    let tenTaiKhoan = $("#tenTaiKhoan").val()
    let tenDeTai = $("#tenDeTai").val()
    let monDatnId = $("#monDatnId").val()
    let trangThai = $("#trangThai").val()
    let dotDangKyId = $("#dotDangKyId").val()
    currentPage = count;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search" + "?maNhom=" + maNhom + "&tenDeTai=" + tenDeTai + "&monDatnId=" + monDatnId
            + "&tenTaiKhoan=" + tenTaiKhoan + "&trangThai=" + trangThai + "&dotDangKyId=" + dotDangKyId + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data.data
            totalPages = responseData.data.totalPages
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
                <td><a href="/chu-nhiem/sinh-vien?id=${item.id}">
                <div id="eye-custom" data-toggle="tooltip" title="Xem chi tiết nhóm">
                <img src="/img/eye-fill.svg"></div></a>
                <button class="btn_check_circle" 
                style="${item.trangThai == 0 ? 'display : block' : 'display : none'}" 
                data-toggle="tooltip" title="Xác nhận nhóm" onclick="CNXacNhanNhom('${item.id}','${count}')">
                <img src="/img/check-circle.svg"></button>
                <button class="btn_check" 
                style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" 
                data-toggle="tooltip" title="Chốt đề tài" onclick="CNChotDuLieu('${item.id}','${count}')">
                <img src="/img/clipboard-check.svg"></button>
                <button class="btn_x_circle" 
                style="${item.trangThai == 4 ? 'display : block' : 'display : none'}" 
                data-toggle="tooltip" title="Từ chối chốt đề tài" onclick="openModalNhanXet('${item.id}','${count}')">
                <img src="/img/x-circle.svg"></button>
                <button class="btn_trash" 
                style="${item.trangThai == 5 ? 'display : none' : 'display : block'}" 
                data-toggle="tooltip" title="Xóa" onclick="openModalRemoveNhomDatn('${item.id}','${count}')">
                <img src="/img/trash.svg"></button></td>
                </tr>
                `
                }))
                $('[data-toggle = "tooltip"]').tooltip()
                genPagingAndFillColor()
            }
        },
        error: function (e) {
        }
    });
}


