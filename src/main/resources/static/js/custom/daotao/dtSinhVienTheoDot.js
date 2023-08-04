const apiDtSinhVien = '/api/dao-tao/sinh-vien';

let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

function clearform() {
    $("#keyword").val('');
    $("#chuyenNganh").val('');
    $("#trangThai").val('');
    $("#dotDangKy").val('');
}

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function viewTable(page) {
    if (page == -1) {
        page = totalPages - 1;
    }
    if (page == totalPages) {
        page = 0;
    }
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    currentPage = page;
    $("#valueInput").val(page + 1);
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien?page=" + page + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.data.length > 0) {
                totalPages = responseData.data.totalPages;
                $("#is_empty").html('')
                $("#list").html(responseData.data.data.map(function (item) {
                    return ` <tr style="${item.trangThai == 1 ? 'color : red' : ''}">
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.monChuongTrinh == null ? '-' : item.maMonChuongTrinh}</td>
                                <td>${item.maNhom == null ? '-' : item.maNhom}</td>
                                <td>${item.tenDeTai == null ? '-' : item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? '-' : item.tenGvhd}</td>
                                <td>${item.tenChuyenNganh}</td>
                                <td>
                                    <button class="btn_eye"
                                            data-toggle="tooltip"
                                            title="Xem chi tiết"
                                            onclick="detail('${item.id}')">
                                            <img src="/img/eye-fill.svg">
                                    </button>
                                    <button class="btn_pencil_square"
                                            data-toggle="tooltip"
                                            title="Cập nhật thông tin sinh viên"
                                            onclick="openModalUpdateSinhVien('${item.id}')">
                                            <img src="/img/pencil-square.svg">
                                    </button>
                                    <button class="btn_trash"
                                            data-toggle="tooltip"
                                            title="Xóa sinh viên"
                                            onclick="openModalRemoveSinhVien('${item.id}')">
                                            <img src="/img/trash.svg">
                                    </button>
                                     <button class="btn_pencil_square" style="${item.trangThai == 0 ? 'display : block' : 'display : none'}"
                                         data-toggle="tooltip"
                                         title="Loại sinh viên"
                                         onclick="loaiSinhVienDetail('${item.id}','${page}')">                      
                                           <img src="/img/person-off.svg">                                                               
                                     </button>
                                </td>
                        </tr>`
                }));
                $('[data-toggle = "tooltip"]').tooltip();
                if (responseData.data.totalPages > 1) {
                    genPaging();
                } else {
                    $("#list_page").html(function () {
                        return ``
                    })
                }
            } else ($("#list").html(''), $("#list_page").html(function () {
                return ``
            }), $("#is_empty").html(function () {
                return `<span>Không có dữ liệu</span>`
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function openModalThemSinhVien() {
    $("#modal_create").modal('show')
    clearData();
}

function create() {
    let maSinhVien = $("#ma_sinh_vien_create").val().trim();
    let tenSinhVien = $("#ten_sinh_vien_create").val().trim();
    let sdt = $("#sdt_create").val().trim();
    let email = $("#email_create").val().trim();
    let khoa = $("#khoa_hoc_create").val().trim();
    let monChuongTrinh = $("#mon_chuong_trinh_create").val();
    let tenChuyenNganh = $("#ten_chuyen_nganh_create").val();
    let dotDangKyId = $("#dot_dang_ky_create").val();
    let trangThai = $("#trang_thai_create").val();
    let sinhVienRequest = {};
    sinhVienRequest["maSinhVien"] = maSinhVien;
    sinhVienRequest["tenSinhVien"] = tenSinhVien;
    sinhVienRequest["sdt"] = sdt;
    sinhVienRequest["email"] = email;
    sinhVienRequest["khoa"] = khoa;
    sinhVienRequest["monChuongTrinh"] = monChuongTrinh;
    sinhVienRequest["chuyenNganhId"] = tenChuyenNganh;
    sinhVienRequest["dotDangKyId"] = dotDangKyId;
    sinhVienRequest["trangThai"] = trangThai;
    let index = 0;
    if (maSinhVien.trim().length === 0) {
        $("#ma_sinh_vien_create_error").text("Mã sinh viên không được để trống");
        index++;
    } else {
        $("#ma_sinh_vien_create_error").text('');
    }
    if (tenSinhVien.trim().length === 0) {
        $("#ten_sinh_vien_create_error").text("Tên sinh viên không được để trống");
        index++;
    } else {
        $("#ten_sinh_vien_create_error").text('');
    }
    if (email.trim().length === 0) {
        $("#email_create_error").text("Email không được để trống");
        index++;
    } else {
        $("#email_create_error").text('');
    }
    if (monChuongTrinh == '') {
        $("#mon_chuong_trinh_create_error").text("Mời chọn môn chương trình");
        index++;
    } else {
        $("#mon_chuong_trinh_create_error").text('');
    }
    if (tenChuyenNganh == '') {
        $("#ten_chuyen_nganh_create_error").text("Mời chọn chuyên ngành");
        index++;
    } else {
        $("#ten_chuyen_nganh_create_error").text('');
    }
    if (dotDangKyId == '') {
        $("#dot_dang_ky_create_error").text("Mời chọn đợt đăng ký");
        index++;
    } else {
        $("#dot_dang_ky_create_error").text('');
    }
    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/dao-tao/sinh-vien",
            data: JSON.stringify(sinhVienRequest),
            dataType: 'json',
            success: function () {
                $("#modal_create").modal("hide");

                bootbox.alert({
                    message: 'Thêm sinh viên thành công',
                    backdrop: true,
                    callback: function () {
                        viewTable(currentPage);
                    }
                });
            },
            error: function (e) {
                let message = e.responseJSON.message.trim();
                if (message == 'Sinh vien da ton tai') {
                    bootbox.alert({
                        message: 'Mã sinh viên không được trùng',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'email fpt da ton tai') {
                    bootbox.alert({
                        message: 'Email fpt đã tồn tại',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'Nhom du an tot nghiep not exist') {
                    bootbox.alert({
                        message: 'Nhóm không tồn tại',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'Nhom da du thanh vien') {
                    bootbox.alert({
                        message: 'Nhóm đã đủ thành viên',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                }
            }
        });
    }
}

function clearData() {

    $("#ma_sinh_vien_create").val('');
    $("#ten_sinh_vien_create").val('');
    $("#sdt_create").val('');
    $("#email_create").val('');
    $("#khoa_hoc_create").val('');
    $("#mon_chuong_trinh_create").val('');
    $("#ten_chuyen_nganh_create").val('');
    $("#dot_dang_ky_create").val('');

    $("#ma_sinh_vien_create_error").text('');
    $("#ten_sinh_vien_create_error").text('');
    $("#email_create_error").text('');
    $("#mon_chuong_trinh_create_error").text('');
    $("#ten_chuyen_nganh_create_error").text('');
    $("#dot_dang_ky_create_error").text('');

    $("#ma_sinh_vien_update_error").text('');
    $("#ten_sinh_vien_update_error").text('');
    $("#sdt_update_error").text('');
    $("#email_update_error").text('');
    $("#khoa_hoc_update_error").text('');
    $("#mon_chuong_trinh_update_error").text('');
    $("#ten_chuyen_nganh_update_error").text('');
    $("#dot_dang_ky_update_error").text('');
}

function openModalUpdateSinhVien(idSinhVien) {
    clearData();
    $("#modal_update_sinh_vien").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien/search/" + idSinhVien,
        data: JSON.stringify(idSinhVien),
        dataType: 'json',
        success: function (responseData) {
            $("#id_sinh_vien_update").val(responseData.data.id);
            $("#ma_sinh_vien_update").val(responseData.data.maSinhVien);
            $("#ten_sinh_vien_update").val(responseData.data.tenSinhVien);
            $("#sdt_update").val(responseData.data.soDienThoai);
            $("#email_update").val(responseData.data.email);
            $("#khoa_hoc_update").val(responseData.data.khoa);
            $("#mon_chuong_trinh_update").val(responseData.data.idMonChuongTrinh);
            $("#ten_chuyen_nganh_update").val(responseData.data.chuyenNganhId);
            changeDataCbbMonDatn(responseData.data.chuyenNganhId, responseData.data.idMonChuongTrinh, 2);
            $("#dot_dang_ky_update").val(responseData.data.idDotDangKy);
            $("#trang_thai_update").val(responseData.data.trangThai);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let idSinhVien = $("#id_sinh_vien_update").val();
    let maSinhVien = $("#ma_sinh_vien_update").val().trim();
    let tenSinhVien = $("#ten_sinh_vien_update").val().trim();
    let sdt = $("#sdt_update").val().trim();
    let email = $("#email_update").val().trim();
    let khoa = $("#khoa_hoc_update").val().trim();
    let monChuongTrinh = $("#mon_chuong_trinh_update").val();
    let tenChuyenNganh = $("#ten_chuyen_nganh_update").val();
    let dotDangKyId = $("#dot_dang_ky_update").val();
    let trangThai = $("#trang_thai_update").val();
    let sinhVienRequest = {};
    sinhVienRequest["maSinhVien"] = maSinhVien;
    sinhVienRequest["tenSinhVien"] = tenSinhVien;
    sinhVienRequest["sdt"] = sdt;
    sinhVienRequest["email"] = email;
    sinhVienRequest["khoa"] = khoa;
    sinhVienRequest["monChuongTrinh"] = monChuongTrinh;
    sinhVienRequest["chuyenNganhId"] = tenChuyenNganh;
    sinhVienRequest["dotDangKyId"] = dotDangKyId;
    sinhVienRequest["trangThai"] = trangThai;
    let index = 0;
    if (maSinhVien.trim().length === 0) {
        $("#ma_sinh_vien_update_error").text("Mã sinh viên không được để trống");
        index++;
    } else {
        $("#ma_sinh_vien_update_error").text('');
    }
    if (tenSinhVien.trim().length === 0) {
        $("#ten_sinh_vien_update_error").text("Tên sinh viên không được để trống");
        index++;
    } else {
        $("#ten_sinh_vien_update_error").text('');
    }
    if (email.trim().length === 0) {
        $("#email_update_error").text("Email không được để trống");
        index++;
    } else {
        $("#email_update_error").text('');
    }
    if (email.trim().length === 0) {
        $("#email_update_error").text("Email không được để trống");
        index++;
    } else {
        $("#email_update_error").text('');
    }
    if (monChuongTrinh == '') {
        $("#mon_chuong_trinh_update_error").text("Mời chọn môn chương trình");
        index++;
    } else {
        $("#mon_chuong_trinh_update_error").text('');
    }
    if (tenChuyenNganh == '') {
        $("#ten_chuyen_nganh_update_error").text("Mời chọn chuyên ngành");
        index++;
    } else {
        $("#ten_chuyen_nganh_update_error").text('');
    }
    if (dotDangKyId == '') {
        $("#dot_dang_ky_update_error").text("Mời chọn đợt đăng ký");
        index++;
    } else {
        $("#dot_dang_ky_update_error").text('');
    }
    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/api/dao-tao/sinh-vien/" + idSinhVien,
            data: JSON.stringify(sinhVienRequest),
            dataType: 'json',
            success: function () {
                $("#modal_update_sinh_vien").modal("hide");
                bootbox.alert({
                    message: 'Cập nhật thành công',
                    backdrop: true,
                    callback: function () {
                        viewTable(currentPage);
                    }
                });
            },
            error: function (e) {
                let message = e.responseJSON.message.trim();
                if (message == 'Sinh vien da ton tai') {
                    bootbox.alert({
                        message: 'Mã sinh viên không được trùng',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'email fpt da ton tai') {
                    bootbox.alert({
                        message: 'Email fpt đã tồn tại',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'Nhom du an tot nghiep not exist') {
                    bootbox.alert({
                        message: 'Nhóm không tồn tại',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                } else if (message == 'Nhom da du thanh vien') {
                    bootbox.alert({
                        message: 'Nhóm đã đủ thành viên',
                        backdrop: true,
                        callback: function () {
                        }
                    });
                }
            }
        });
    }
};

function openModalRemoveSinhVien(idSinhVien) {
    bootbox.confirm({
        title: "Xóa",
        message: "Bạn chắc chắn xóa không?",
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
                    url: "/api/dao-tao/sinh-vien/" + idSinhVien,
                    data: JSON.stringify(idSinhVien),
                    dataType: 'json',
                    success: function () {
                        viewTable(currentPage)
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                            }
                        });
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                    }
                });
            }
        }
    });
}

$("#ten_chuyen_nganh_create").change(function (event) {
    event.preventDefault();
    let chuyenNganhId = $("#ten_chuyen_nganh_create").val();
    changeDataCbbMonDatn(chuyenNganhId, null, 1);
})

$("#ten_chuyen_nganh_update").change(function (event) {
    event.preventDefault();
    let chuyenNganhId = $("#ten_chuyen_nganh_update").val();
    changeDataCbbMonDatn(chuyenNganhId, null, 2);
})

function changeDataCbbMonDatn(chuyenNganhId, idMonDatn, type) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiDtSinhVien + '/search-mondatn/' + chuyenNganhId,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data
            var text = ``;
            if (idMonDatn == null) {
                text = "<option value='' hidden selected>Chọn môn chương trình</option>"
                array.map(function (item) {
                    text += `<option value="${item.id}" >${item.maMonDatn}</option>`
                })
            } else {
                var text_selected = ``;
                array.map(function (item) {
                    if (item.id == idMonDatn) {
                        text_selected = `"<option value="${item.id}" selected>${item.maMonDatn}</option>"`;
                    } else {
                        text += `<option value="${item.id}" >${item.maMonDatn}</option>`
                    }
                })
                text += text_selected;
            }
            if (type == 1) {
                $('#mon_chuong_trinh_create').html(text)
            } else {
                $('#mon_chuong_trinh_update').html(text)
            }
        },
        error: function (e) {
        }
    });
}

function openModalExportExcel() {
    $("#type_excel_error").text('');
    $("#modal_excel").modal('show');
}

function exportExcel() {
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    let typeExcel = $("#type_excel").val();
    if (typeExcel == '') {
        $("#type_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/sinh-vien/export?typeExcel=" + typeExcel + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy,

            success: function () {
                window.open("/api/dao-tao/sinh-vien/export?typeExcel=" + typeExcel + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function showExportMau() {
    $("#mau_excel_error").text('');
    $("#modal_excel_mau").modal('show');
}

function mauImport() {
    let typeExcel = $("#type_mau_excel").val();
    if (typeExcel == '') {
        $("#mau_excel_error").text("Chọn định dạng file download");
    } else {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/api/dao-tao/sinh-vien/export-mau?typeExcel=" + typeExcel,

            success: function () {
                window.open("/api/dao-tao/sinh-vien/export-mau?typeExcel=" + typeExcel, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
};

function openModalImportExcel() {
    $("#labelFile").text('Choose file');
    $("#inputGroupFile01").val('');
    $("#import_excel_error").text('');
    $("#import_excel").modal('show');
}

function importExcel() {
    let typeExcel = $("#inputGroupFile01").val();
    let fileName = typeExcel.split(".");
    let typeFile = fileName[fileName.length - 1];
    if (typeExcel.length <= 0) {
        $("#import_excel_error").text("Chọn file");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        var form = $('#form_import_excel')[0];
        $.ajax({
                type: 'POST',
                url: '/api/dao-tao/sinh-vien/import',
                enctype: 'multipart/form-data',
                data: new FormData(form),
                processData: false,
                contentType: false,
                success: function (response) {
                    if (response.data.status == true) {
                        $("#import_excel").modal('hide');
                        bootbox.alert({
                            message: 'Import file thành công',
                            backdrop: true,
                            callback: function () {
                                location.reload();
                            }
                        });
                    } else {
                        bootbox.alert({
                            message: response.data.message + '',
                            backdrop: true,
                            callback: function () {
                                location.reload();
                            }
                        });
                    }
                },
                error: function (e) {
                    bootbox.alert({
                        message: e.responseJSON.message + '',
                        backdrop: true,
                        callback: function () {
                            location.reload();
                        }
                    });
                }
            }
        );
    } else {
        $("#import_excel_error").text("Chọn đúng file Excel ");
    }
}

function openModalLoaiSinhVien() {
    $("#labelFileLoaiSV").text('Choose file');
    $("#fileLoaiSv").val('');
    $("#errorImportFile").text('');
    $("#modal_loai_sinh_vien").modal('show');
}

function loaiSinhVien() {
    let typeExcel = $("#fileLoaiSv").val();
    let fileName = typeExcel.split(".");
    if (typeExcel.length <= 0) {
        $("#errorImportFile").text("Chọn file");
    } else if (fileName[fileName.length - 1] == 'xlsx') {
        var form = $('#form_loai_sinh_vien')[0];
        $.ajax({
                type: 'POST',
                url: '/api/dao-tao/sinh-vien/loai-sinh-vien',
                enctype: 'multipart/form-data',
                processData: false,
                data: new FormData(form),
                contentType: false,
                cache: false,
                success: function (response) {
                    $("#modal_loai_sinh_vien").modal('hide');
                    bootbox.alert({
                        message: response.data.message + '',
                        backdrop: true,
                        callback: function () {
                            location.reload();
                        }
                    });
                },
                error: function (e) {
                    console.log("ERROR : ", e);
                }
            }
        );
    } else {
        $("#errorImportFile").text("Chọn đúng file Excel ");
    }
}

function reload() {
    window.open('/dao-tao/sinh-vien', '_self');
}

function detail(sinhVienId) {
    $("#modal_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien/search/" + sinhVienId,
        dataType: 'json',
        success: function (responseData) {
            $("#maSV").val(responseData.data.maSinhVien);
            $("#tenSV").val(responseData.data.tenSinhVien);
            $("#sdt").val(responseData.data.soDienThoai == '' ? "Chưa có" : responseData.data.soDienThoai);
            $("#email").val(responseData.data.email);
            $("#khoa").val(responseData.data.khoa == '' ? "Chưa có" : responseData.data.khoa);
            $("#monDATN").val(responseData.data.tenMonDatn == null ? "Chưa có" : responseData.data.tenMonDatn + ' - ' + responseData.data.maMonDatn);
            $("#tenCN").val(responseData.data.tenChuyenNganh == null ? "Chưa có" : responseData.data.tenChuyenNganh);
            $("#maNhom").val(responseData.data.maNhom == null ? "Chưa có" : responseData.data.maNhom);
            $("#tenDeTai").val(responseData.data.tenDeTai == null ? "Chưa có" : responseData.data.tenDeTai);
            $("#GVDH").val(responseData.data.tenGvhd == null ? "Chưa có" : responseData.data.tenGvhd);
            $("#DDK").val(responseData.data.tenDotDangKy);
            $("#trangT").val(responseData.data.trangThai == 0 ? "Đủ điều kiện" : "Không đủ điều kiện");
            $("#soTV").val(responseData.data.soThanhVien == null ? "Chưa tham gia nhóm" : responseData.data.soThanhVien + '/7');
            $("#monCT").val(responseData.data.idMonChuongTrinh == null ? "Chưa có" : responseData.data.monChuongTrinh + ' - ' + responseData.data.maMonChuongTrinh);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
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
        viewTable(value - 1);
    }
}

genPaging();

function genPaging() {
    if (totalPages <= 1) {
        $("#list_page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTable(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="viewTable(${currentPage + 1})"><img src="/img/next.svg"></a>
                </li>
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        $("#list_page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    }
}

function loaiSinhVienDetail(id, page) {
    bootbox.confirm({
        title: "Loại sinh viên",
        message: "Bạn chắc chắn loại sinh viên không?",
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
                    type: "PUT",
                    contentType: "application/json",
                    url: "/api/dao-tao/sinh-vien/loai-sv/" + id,
                    data: JSON.stringify(id),
                    dataType: 'json',
                    success: function (responseData) {
                        if (responseData.data == null) {
                            bootbox.alert({
                                message: 'Loại sing viên thất bại',
                                backdrop: true,
                            });
                        } else {
                            bootbox.alert({
                                message: 'Loại sinh viên thành công',
                                backdrop: true,
                                callback: function () {
                                    viewTable(page);
                                }
                            });
                        }
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                    }
                });
            }
        }
    });
}
