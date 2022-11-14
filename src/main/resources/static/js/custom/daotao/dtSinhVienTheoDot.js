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
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    currentPage = page
    $("#valueInput").val(page+1)
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien?page=" + page + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            if (responseData.data.data.length > 0) {
                totalPages = responseData.data.totalPages
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
                    genPagingAndFillColor()
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

function showExportMau(){
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

function changeInput(){
    let value = document.querySelector("#valueInput").value
    if(Number(value) <= 0){
        value = 1;
    }
    if(Number(value) > totalPages){
        value = totalPages;
    }
    viewTable(value - 1);
}

genPagingAndFillColor()

function genPagingAndFillColor() {
    let templatePageMid = ``;
    let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="viewTable(${currentPage - 1})">Back</a>
                </li>
                `;
        templatePageMid += `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 55px" value="/   ${totalPages}" readonly>`
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