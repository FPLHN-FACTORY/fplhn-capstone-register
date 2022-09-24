const svNhomDatnApi = '/api/sinh-vien/nhom-datn';
const svNhomDatnView = '/sinh-vien/nhom-datn';

function guiYeuCauPheDuyet(id){
    var check = confirm("Bạn muốn gửi yêu cầu phê duyệt?");
    if (check == true) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: svNhomDatnApi + "/" + id,
            dataType: 'json',
            success: function (responseData) {
                location.reload();
            },
            error: function (e) {
                console.log("ERROR : ", e.message);
            }
        });
    }
}