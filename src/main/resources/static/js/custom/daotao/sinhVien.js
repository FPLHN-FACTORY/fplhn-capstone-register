function clearform() {
    $("#tenNhom").val("");
    $("#tenHocKy").val("0");
    $("#tenDotDangKy").val("0");
    $("#tenChuyenNganh").val("0");
}
function viewTable() {
    let keyword = $("#keyword").val();
    let chuyenNganh = $("#chuyenNganh").val();
    let trangThai = $("#trangThai").val();
    let dotDangKy = $("#dotDangKy").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/dao-tao/sinh-vien?keyword=" + keyword + "&chuyenNganh=" + chuyenNganh + "&trangThai=" + trangThai + "&dotDangKy=" + dotDangKy,
        dataType: 'json',
        success: function (responseData) {
            $("#list").html(responseData.data.map(function (item) {
                return ` <tr>
                                <td>${item.stt}</td>
                                <td>${item.maSinhVien}</td>
                                <td>${item.tenSinhVien}</td>
                                <td>${item.tenChuyenNganh}</td>
                                <td>${item.tenMonDatn == null ? 'chưa có':item.tenMonDatn}</td>
                                <td>${item.maNhom == null ? 'chưa có': item.maNhom}</td>
                                <td>${item.tenDeTai == null ? 'chưa có':item.tenDeTai}</td>
                                <td>${item.tenGvhd == null ? 'chưa có': item.tenGvhd}</td>
                                <td>${item.tenDotDangKy}</td>
                        </tr>`
            }));
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}