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
            $("#list").html(responseData.data.data.map(function (item) {
                return ` <tr>
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.tenChuyenNganh}</td>
                                <td>${item.tenMonDatn == null ? 'chưa có' : item.tenMonDatn}</td>
                                <td>${item.maNhom == null ? 'chưa có' : item.maNhom}</td>
                                <td>${item.tenDeTai == null ? 'chưa có' : item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? 'chưa có' : item.tenGvhd}</td>
                                <td>${item.tenDotDangKy}</td>
                        </tr>`
            }));
            console.log(responseData.data.totalPages)
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
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}