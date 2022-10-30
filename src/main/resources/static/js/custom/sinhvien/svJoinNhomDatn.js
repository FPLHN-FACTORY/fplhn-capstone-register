const SVNhomDatnApi = '/api/sinh-vien';
const SVNhomDatnView = '/sinh-vien';
const checkHanSV = $("#check_han_sv").text();

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
    let tenDeTai = $("#tenDeTai").val().trim();
    let soLuong = $("#soLuong").val().trim();
    let giangVien = $("#giangVien").val().trim();
    let truongNhom = $("#truongNhom").val().trim();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: SVNhomDatnApi + "/search" + "?tenDeTai=" + tenDeTai + "&truongNhom=" + truongNhom
            + "&soLuong=" + soLuong + "&giangVien=" + giangVien + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            let array = responseData.data.data
            console.log(array)
            if (array.length == 0) {
                console.log(array.length)
                $("#listNhomDatn").html(``)
                $("#list_page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
            }
            else {
                $("#empty").html(``)
                $("#listNhomDatn").html(array.map(function (item) {
                    return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.countSinhVien}/7</td>
                <td>${item.maSinhVien}-${item.tenSinhVien}</td>
                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenTaiKhoan + ' - ' + item.tenGvhd}</td>
                <td>${item.matKhauTonTai == 0 ? 'Công khai' : 'Nhóm kín'}</td>
                <td><button type="button" class="btn btn-success" onclick="openModalDanhSachSinhVien('${item.id}')">Xem chi tiết</button>
                <td style="${checkHanSV == 'true' ? 'display:block' : 'display:none'}">
                    <div class="row">
                        <div class="col-6 d-inline">
                            <button class=" btn btn-success"                                    
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
                let templateHtml = ``;
                for (let i = 0; i < responseData.data.totalPages; i++) {
                    templateHtml += `<li class="page-item" >
                                        <a class="page-link" onclick="loadDataNhomDatn(${i})">${i + 1}</a>
                                    </li>`
                }
                if (responseData.data.totalPages == 1) {
                    templateHtml = ``;
                }
                $("#list_page").html(templateHtml);
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
        bootbox.confirm({
            title: "Tham gia nhóm",
            message: "Bạn muốn tham gia nhóm này?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Quay lại'
                },
                confirm: {
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
                            alert(responseData.responseJSON.message);
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
            alert(responseData.responseJSON.message)
            if (responseData.responseJSON.message === 'Mat Khau Khong Chinh Xac') {
                $("#errorPassword").text("Mật khẩu không chính xác");
            }
        }
    });
}

function getGiangVien(idMonDatn) {
    $.ajax({
        type: "GET",
        url: SVNhomDatnApi + "/search-giang-vien?idMonDatn=" + idMonDatn,
        success: function (data) {
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

    if (tenNhom.length == 0) {
        $("#ten_nhom_error").text("*Không để trống tên nhóm");
        check = false;
    }

    if (tenDeTai.length == 0) {
        $("#ten_de_tai_error").text("*Không để trống tên đề tài");
        check = false;
    }

    if (check) {
        let matKhau = "";
        if ($("#checkTrangThaiCreateNhom").is(':checked')) {
            matKhau = Math.floor(Math.random() * 100000000)
        } else {
            matKhau = null;
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