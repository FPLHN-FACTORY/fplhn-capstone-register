const SVNhomDatnApi = '/api/sinh-vien';
const SVNhomDatnView = '/sinh-vien';
const checkHanSV = $("#check_han_sv").text();
const checkThoiGianRoiNhom = $("#check_thoi_gian_roi_nhom").text();
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

function clearform() {
    $("#tenDeTai").val("");
    $("#truongNhom").val("");
    $("#giangVien").val("");
    $("#soLuong").val("");
    $("#errorPassword").text("");
    $("#inputPassword").val("");
}

function openModalCreate() {
    $("#modal_create").modal('show');
}

function openModalDanhSachSinhVien(id) {
    $("#modal_show_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: SVNhomDatnApi + "?id=" + id,
        dataType: 'json',
        success: function (responseData) {
            var labelNhomDetail = responseData.data[0].maNhom;
            if (responseData.data[0].tenNhom != null) {
                labelNhomDetail += " - " + responseData.data[0].tenNhom;
            }
            $("#labelNhomDetail").text(labelNhomDetail);
            if (responseData.data[0].moTa != null) {
                var text = `<hr><h5 class="form-group row">
                                    Mô tả
                                </h5>
                                <div class="form-group row">
                                    <textarea rows="6"
                                        readonly="readonly" class="form-control">${responseData.data[0].moTa}</textarea>
                                </div>`;
                $("#moTa").attr('display', 'block');
                $("#moTa").html(text);
            } else {
                $("#moTa").attr('display', 'none');
            }
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

function openModalAddSinhVienJoinNhom(matKhauTonTai, idNhom) {
    clearform();
    if (Number(matKhauTonTai) == 0) {
        bootbox.confirm({
            title: "Tham gia nhóm", message: "Bạn muốn tham gia nhóm này?",
            buttons: {
                cancel: {
                    className: 'btn-outline-secondary btn btn-light',
                    label: '<i class="fa fa-times"></i> Quay lại'
                }, confirm: {
                    label: '<i class="fa fa-check"></i> Xác nhận'
                }
            },
            callback: function (result) {
                if (result) {
                    let addSinhVienRequest = {};
                    addSinhVienRequest["idNhom"] = idNhom;
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: SVNhomDatnApi + "/add",
                        data: JSON.stringify(addSinhVienRequest),
                        datatype: 'json',
                        success: function () {
                            bootbox.alert('Tham gia thành công', function () {
                                window.open(SVNhomDatnView, '_self');
                            });
                        },
                        error: function (responseData) {
                            if (responseData.responseJSON.message === 'Het han quyen sinh vien') {
                                bootbox.alert({
                                    message: 'Hết hạn quyền sinh viên', backdrop: true, callback: function () {
                                        window.open(SVNhomDatnView, '_self');
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    } else {
        $("#modal_add_sinh_vien").modal('show');
        $("#idNhom").val(idNhom);
    }
}

function addSinhVienHasPassword() {
    let inputPassword = $("#inputPassword").val().trim();
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
            bootbox.alert('Tham gia thành công', function () {
                window.open(SVNhomDatnView, '_self');
            });
        },
        error: function (responseData) {
            console.log(responseData);
            if (responseData.responseJSON.message === 'Mat Khau Khong Chinh Xac') {
                $("#errorPassword").text("Mật khẩu không chính xác");
            }
        }
    });
}

function getGiangVien(idMonDatn) {
    $.ajax({
        type: "GET", url: SVNhomDatnApi + "/search-giang-vien?idMonDatn=" + idMonDatn, success: function (data) {
            var text = "<option value='' selected>Không chọn</option>"
            data.data.map(function (item) {
                text += `<option value="${item.id}" >${item.tenGvhd}</option>`
            })
            $('#giangVienCreateNhom').html(text)
        }
    })
}

$('#monDatnCreateNhom').change(function () {
    var idMonDatn = $(this).val();
    getGiangVien(idMonDatn);
})

function createNhomDatn() {
    var check = true;
    $("#ten_de_tai_error").text("");
    $("#mon_datn_error").text("");
    $("#ten_nhom_error").text("");
    let giangVienId = $("#giangVienCreateNhom").val();
    let tenDeTai = $("#tenDeTaiCreateNhom").val().trim();
    let tenNhom = $("#tenNhomCreateNhom").val().trim();
    let moTa = $("#moTaCreateNhom").val().trim();
    let idMonDatn = $("#monDatnCreateNhom").val();

    if ($('#monDatnCreateNhom').val() === "") {
        $("#mon_datn_error").text("*Chưa chọn môn dự án tốt nghiệp");
        check = false;
    }

    if (check) {
        let matKhau = "";
        if ($("#checkTrangThaiCreateNhom").is(':checked')) {
            matKhau = true;
        } else {
            matKhau = false;
        }
        if (moTa.length == 0) {
            moTa = null;
        }
        if (giangVienId.length == 0) {
            giangVienId = null;
        }
        let nhomDatnRequest = {};
        nhomDatnRequest["giangVienId"] = giangVienId;
        nhomDatnRequest["tenNhom"] = tenNhom;
        nhomDatnRequest["tenDeTai"] = tenDeTai;
        nhomDatnRequest["matKhau"] = matKhau;
        nhomDatnRequest["moTa"] = moTa;
        nhomDatnRequest["idMonDatn"] = idMonDatn;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: SVNhomDatnApi,
            data: JSON.stringify(nhomDatnRequest),
            dataType: 'json',
            success: function (responseData) {
                $("#modal_create").modal("hide");
                bootbox.alert('Tạo nhóm thành công', function () {
                    window.open(SVNhomDatnView, '_self');
                });
            },
            error: function (e) {
                console.log("ERROR : ", e);
                alert(e.responseJSON.message);
            }
        });
    }
}

function changeInput() {
    if ($("#valueInput").val().length != 0) {
        let value = parseInt($("#valueInput").val());
        if (Number(value) <= 0) {
            value = 1;
        }
        if (Number(value) > totalPages) {
            value = totalPages;
        }
        loadDataNhomDatn(value - 1);
    }
}

genPagzing();

function genPagzing() {
    if (totalPages <= 1) {
        $("#page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataNhomDatn(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataNhomDatn(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    }
}

function loadDataNhomDatn(count) {
    if (count == -1) {
        count = totalPages - 1;
    }
    if (count == totalPages) {
        count = 0;
    }
    let tenDeTai = $("#tenDeTai").val().trim();
    let soLuong = $("#soLuong").val().trim();
    let giangVien = $("#giangVien").val().trim();
    let truongNhom = $("#truongNhom").val().trim();
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: SVNhomDatnApi + "/search" + "?tenDeTai=" + tenDeTai + "&truongNhom=" + truongNhom + "&soLuong=" + soLuong + "&giangVien=" + giangVien + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data.data;
            totalPages = responseData.data.totalPages;
            if (array.length == 0) {
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
                <td>${item.tenNhom == null ? 'Chưa có' : item.tenNhom}</td>
                <td>${item.tenDeTai == null ? 'Chưa có' : item.tenDeTai}</td>
                <td>${item.tenMonDatn}</td>
                <td>${item.countSinhVien}/7</td>
                <td>${item.maSinhVien + ' - ' + item.tenSinhVien}</td>
                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenGvhd + ' - ' + item.tenTaiKhoan}</td>
                <td>${item.matKhauTonTai == 0 ? 'Công khai' : 'Nhóm kín'}</td>
                <td>
                    <button type="button"
                        class="btn_eye"
                        title="Xem chi tiết nhóm"
                        onclick="openModalDanhSachSinhVien('${item.id}')"
                    >  
                        <img src="/img/eye-fill.svg">
                    </button>
                    <button class="btn_box_arrow" style="${checkHanSV == 'false' ? 'display:none' : checkThoiGianRoiNhom == 'true' ? 'display: block' : 'display:none'}"                                 
                            data-target="#modal_create1"
                            data-toggle="modal"
                            onclick="openModalAddSinhVienJoinNhom(${item.matKhauTonTai}, '${item.id}')"
                            >
                        <img src="/img/box-arrow.svg">
                    </button>
                </td>
                <div class="modal fade"></div>
                </td>
                </tr>
                `
                }))
                genPagzing();
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
