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
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien?page=" + page + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.data.length > 0) {
                $("#is_empty").html('')
                $("#list").html(responseData.data.data.map(function (item) {
                    if (item.trangThai == 0) {
                        return ` <tr>
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.khoa}</td>
                                <td>${item.monChuongTrinh == null ? 'Chưa có' : item.monChuongTrinh + ' - ' + item.maMonChuongTrinh}</td>
                                <td>${item.maNhom == null ? 'Chưa có' : item.maNhom}</td>
                                <td>${item.tenDeTai == null ? 'Chưa có' : item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenGvhd}</td>
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
                                         title="Loại sinh viên"
                                         onclick="loaiSinhVienDetail('${item.id}','${page}')">                      
                                           <img src="/img/person-off.svg">                                                               
                                     </button>
                                </td>
                        </tr>`
                    } else {
                        return ` <tr class="text-danger">
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.khoa}</td>
                                <td>${item.monChuongTrinh == null ? 'Chưa có' : item.monChuongTrinh + ' - ' + item.maMonChuongTrinh}</td>
                                <td>${item.maNhom == null ? 'Chưa có' : item.maNhom}</td>
                                <td>${item.tenDeTai == null ? 'Chưa có' : item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? 'Chưa có' : item.tenGvhd}</td>
                                <td>${item.tenChuyenNganh}</td>
                                <td>
                                    <button class="btn_eye"
                                            data-toggle="tooltip"
                                            title="Xem chi tiết"
                                            onclick="detail('${item.id}')">
                                            <img src="/img/eye-fill.svg">
                                    </button>
                                </td>
                        </tr>`
                    }

                }));
                if (responseData.data.totalPages > 1) {
                    genPagingAndFillColor(responseData.data.totalPages, responseData.data.currentPage)
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

function openModalUpdateSinhVien(idSinhVien) {
    $("#ma_sinh_vien_update_error").text("");
    $("#ten_sinh_vien_update_error").text("");
    $("#khoa_hoc_update_error").text("");
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
            $("#khoa_hoc_update").val(responseData.data.khoa);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let maSinhVien = $("#ma_sinh_vien_update").val().trim();
    let tenSinhVien = $("#ten_sinh_vien_update").val().trim();
    let khoaHoc = $("#khoa_hoc_update").val().trim();
    let idSinhVien = $("#id_sinh_vien_update").val();
    let sinhVienRequest = {};
    sinhVienRequest["maSinhVien"] = maSinhVien;
    sinhVienRequest["tenSinhVien"] = tenSinhVien;
    sinhVienRequest["khoa"] = khoaHoc;
    let index = 0;
    if (maSinhVien.trim().length === 0) {
        $("#ma_sinh_vien_update_error").text("Mã sinh viên không được để trống");
        index++;
    } else {
        $("#ma_sinh_vien_update_error").text("");
    }
    if (tenSinhVien.trim().length === 0) {
        $("#ten_sinh_vien_update_error").text("Tên sinh viên không được để trống");
        index++;
    } else {
        $("#ten_sinh_vien_update_error").text("");
    }
    if (khoaHoc.trim().length === 0) {
        $("#khoa_hoc_update_error").text("Khóa học không được để trống");
        index++;
    } else {
        $("#khoa_hoc_update_error").text("");
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
                    message: 'Update thành công',
                    backdrop: true,
                    callback: function () {
                        window.open("/dao-tao/sinh-vien", '_self');
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.message != null) {
                    $("#ma_sinh_vien_update_error").text("Sinh viên đã tồn tại");
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
                        bootbox.alert({
                            message: 'Xóa thành công',
                            backdrop: true,
                            callback: function () {
                                window.open("/dao-tao/sinh-vien", '_self');
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
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length);
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
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length);
    if (typeExcel.length <= 0) {
        $("#errorImportFile").text("Chọn file");
    } else if (typeFile == 'xlsx') {
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
            $("#sdt").val(responseData.data.soDienThoai);
            $("#email").val(responseData.data.email);
            $("#khoa").val(responseData.data.khoa);
            $("#monDATN").val(responseData.data.tenMonDatn == null ? "Chưa có" : responseData.data.tenMonDatn + ' - ' + responseData.data.maMonDatn);
            $("#tenCN").val(responseData.data.tenChuyenNganh == null ? "Chưa có" : responseData.data.tenChuyenNganh);
            $("#maNhom").val(responseData.data.maNhom == null ? "Chưa có" : responseData.data.maNhom);
            $("#tenDeTai").val(responseData.data.tenDeTai == null ? "Chưa có" : responseData.data.tenDeTai);
            $("#GVDH").val(responseData.data.tenGvhd == null ? "Chưa có" : responseData.data.tenGvhd);
            $("#DDK").val(responseData.data.tenDotDangKy);
            $("#trangT").val(responseData.data.trangThai == 0 ? "Đủ điều kiện" : "Không đủ điều kiện");
            $("#soTV").val(responseData.data.soThanhVien == null ? "Chưa tham gia nhóm" : responseData.data.soThanhVien + '/7');
            $("#monCT").val(responseData.data.monChuongTrinh == null ? "Chưa có" : responseData.data.monChuongTrinh + ' - ' + responseData.data.maMonChuongTrinh);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function genPagingAndFillColor(totalPages, currentPage) {
    let templatePageMid = ``;
    let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="viewTable(${currentPage - 1})">Back</a>
                </li>
                `;
    for (let i = 0; i < totalPages; i++) {
        templatePageMid += `<li class="page-item" style="color: #0a8cf0">
                                        <a class="page-link" id="pageColor" onclick="viewTable(${i})">${i + 1}</a>
                                    </li>`
    }
    let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == totalPages - 1 ? 'pointer-events: none' : ''}" class="page-link" onclick="viewTable(${currentPage + 1})">Next</a>
                </li>
                `;
    let templateHtml = ``;
    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
    $("#list_page").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
    let colorPage = document.querySelectorAll("#pageColor");
    colorPage[currentPage].style.backgroundColor = '#0a8cf0';
    colorPage[currentPage].style.color = 'white';
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