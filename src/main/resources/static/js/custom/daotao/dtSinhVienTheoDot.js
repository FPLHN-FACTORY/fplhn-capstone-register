function clearform() {
    $("#keyword").val('');
    $("#chuyenNganh").val('');
    $("#trangThai").val('');
    $("#dotDangKy").val('');
}

let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()
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
                     <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="viewTable(currentPage-1)">Back</a>
                </li>
                `;
    for (let i = 0; i < totalPages; i++) {
        templatePageMid += `<li class="page-item" style="color: #0a8cf0">
                                        <a class="page-link" id="pageColor" onclick="viewTable(${i})">${i + 1}</a>
                                    </li>`
    }
    let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == totalPages - 1 ? 'pointer-events: none' : ''}" class="page-link" onclick="viewTable(Number(currentPage) + 1)">Next</a>
                </li>
                `;
    let templateHtml = ``;
    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
    if (totalPages == 1) {
        templateHtml = ``;
    }
    $("#page").html(templateHtml);
}


function viewTable(page) {
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    currentPage = page;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien?page=" + page + "&tenSinhVien=" + keyword + "&idChuyenNganh=" + chuyenNganh + "&idTrangThaiNhom=" + trangThai + "&idDotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data.data
            totalPages = responseData.data.totalPages
            if (array.length == 0) {
                $("#list").html(``)
                $("#page").html(``)
                $("#is_empty").html(function () {
                    return `<span>Không có dữ liệu</span>`
                })
                if (responseData.data.totalPages == 1) {
                    viewTable(0);
                    return;
                }
                if (page == responseData.data.totalPages && page != 0) {
                    viewTable(page - 1);
                    return;
                }
            } else {
                $("#is_empty").html('')
                $("#list").html(responseData.data.data.map(function (item) {
                    if (item.trangThai == 0) {
                        return ` <tr>
                                    <td>${item.stt}</td>
                                    <td>${item.maSinhVien}</td>
                                    <td>${item.tenSinhVien}</td>
                                    <td>${item.khoa}</td>
                                    <td>${item.monChuongTrinh == null ? 'chưa có' : item.monChuongTrinh + ' - ' + item.maMonChuongTrinh}</td>
                                    <td>${item.maNhom == null ? 'chưa có' : item.maNhom}</td>
                                    <td>${item.tenDeTai == null ? 'chưa có' : item.tenDeTai}</td>
                                    <td>${item.tenGvhd == null ? 'chưa có' : item.tenGvhd}</td>
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
                    } else {
                        return ` <tr class="text-danger">
                                    <td>${item.stt}</td>
                                    <td>${item.maSinhVien}</td>
                                    <td>${item.tenSinhVien}</td>
                                    <td>${item.khoa}</td>
                                    <td>${item.getMonChuongTrinh == null ? 'chưa có' : item.getMonChuongTrinh + ' - ' + item.getMaMonChuongTrinh}</td>
                                    <td>${item.maNhom == null ? 'chưa có' : item.maNhom}</td>
                                    <td>${item.tenDeTai == null ? 'chưa có' : item.tenDeTai}</td>
                                    <td>${item.tenGvhd == null ? 'chưa có' : item.tenGvhd}</td>
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
                genPagingAndFillColor()
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_type_excel").submit(function (event) {
    event.preventDefault();
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    let typeExcel = $("#type_excel").val();
    console.log("aaaaaaaaaa"+ dotDangKy)
    if (typeExcel == '') {
        $("#type_excel_error").text("chọn định dạng file download");
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
});
$("#modal_excel_mau").submit(function (event) {
    event.preventDefault();
    let typeExcel = $("#type_mau_excel").val();
    if (typeExcel == '') {
        $("#mau_excel_error").text("chọn định dạng file download");
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
});
$("#form_import_excel").submit(function (event) {
    event.preventDefault();
    let typeExcel = $("#inputGroupFile01").val();
    let indexDot = typeExcel.indexOf('.');
    let typeFile = typeExcel.substring(indexDot + 1, typeExcel.length)
    if (typeExcel.length <= 0) {
        $("#import_excel_error").text("chọn file");
    } else if ((typeFile == 'xls') || (typeFile == 'xlsx')) {
        $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                enctype: 'multipart/form-data',
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (response) {
                    if (response.data.status == true) {
                        $("#messNotification").text("import file thành công");
                    } else {
                        $("#messNotification").text(response.data.message);
                    }
                    $('#import_excel').modal("hide");
                    $("#modal_notification").modal("show");
                },
                error: function (e) {
                    console.log("ERROR: " + e);
                }
            }
        );
    } else {
        $("#import_excel_error").text("chọn đúng file Excel ");
    }
});

function loaiSinhVien() {
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
                if (response.data.status == true) {
                    $("#messNotification").text("Loại sinh viên không đạt thành công");
                } else {
                    $("#messNotification").text(response.data.message);
                }
                $('#modal_loai_sinh_vien').modal("hide");
                $("#modal_notification").modal("show");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        }
    );
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
            $("#monDATN").val(responseData.data.tenMonDatn == null ? "chưa có" : responseData.data.tenMonDatn + ' - ' + responseData.data.maMonDatn);
            $("#tenCN").val(responseData.data.tenChuyenNganh == null ? "chưa có" : responseData.data.tenChuyenNganh);
            $("#maNhom").val(responseData.data.maNhom == null ? "chưa có" : responseData.data.maNhom);
            $("#tenDeTai").val(responseData.data.tenDeTai == null ? "chưa có" : responseData.data.tenDeTai);
            $("#GVDH").val(responseData.data.tenGvhd == null ? "chưa có" : responseData.data.tenGvhd);
            $("#DDK").val(responseData.data.tenDotDangKy);
            $("#trangT").val(responseData.data.trangThai == 0 ? "đủ điều kiện" : "không đủ điều kiện");
            $("#soTV").val(responseData.data.soThanhVien == null ? "chưa tham gia nhóm" : responseData.data.soThanhVien + '/7');
            $("#monCT").val(responseData.data.monChuongTrinh == null ? "chưa có" : responseData.data.monChuongTrinh + ' - ' + responseData.data.maMonChuongTrinh);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}