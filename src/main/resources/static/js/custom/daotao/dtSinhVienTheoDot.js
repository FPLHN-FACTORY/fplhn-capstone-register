function clearform() {
    $("#keyword").val('');
    $("#chuyenNganh").val('');
    $("#trangThai").val('');
    $("#dotDangKy").val('');
}

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
                    return ` <tr>
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.soDienThoai}</td>
                                <td>${item.email}</td>
                                <td>${item.khoa}</td>
                                <td>${item.tenChuyenNganh}</td>
                                <td>${item.tenMonDatn == null ? 'chưa có' : item.tenMonDatn}</td>
                                <td>${item.maNhom == null ? 'chưa có' : item.maNhom}</td>
                                <td>${item.tenDeTai == null ? 'chưa có' : item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? 'chưa có' : item.tenGvhd}</td>
                        </tr>`
                }));
                var arr = new Array();
                if (responseData.data.totalPages > 1) {
                    for (let i = 1; i <= responseData.data.totalPages; i++) {
                        arr.push(i)
                    }
                    $("#list_page").html(arr.map(function (value) {
                        return `
                        <div>
                            <li class="page-item">
                                  <a class="page-link"
                                    onclick="viewTable(${value - 1})"
                                    >${value}
                                  </a>
                              </li>
                        </div>
                    `
                    }));
                } else {
                    $("#list_page").html('')
                }
            } else (
                $("#list").html(''),
                    $("#is_empty").html('không có dữ liệu')
            )
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
            url: "/api/dao-tao/sinh-vien/export?typeExcel=" + typeExcel,

            success: function () {
                window.open("/api/dao-tao/sinh-vien/export?typeExcel=" + typeExcel, '_self');
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
    let typeFile =typeExcel.substring(indexDot+1,typeExcel.length)
    if (typeExcel.length <= 0) {
        $("#import_excel_error").text("chọn file");
    } else if((typeFile== 'xls') || (typeFile== 'xlsx') ){
        $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                enctype: 'multipart/form-data',
                data: new FormData(this),
                processData: false,
                contentType: false,
                success: function (data) {
                    window.open('/dao-tao/sinh-vien', '_self');
                },
                error: function (e) {
                    alert(e.responseText);
                    window.open('/dao-tao/sinh-vien', '_self');
                    console.log("ERROR: "+e);
                }
            }
        );
    }
    else {
        $("#import_excel_error").text("chọn đúng file Excel ");
    }
});

