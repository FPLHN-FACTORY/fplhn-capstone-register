const giangVienHuongDanApi = '/api/dao-tao/giang-vien-huong-dan';
const giangVienHuongDanView = '/dao-tao/giang-vien-huong-dan';

$(document).ready(function () {
    $("#giang_vien_error").text("");
});

function clearData() {
    $("#tenGiangVien").val("");
}

$("#form_create_giang_vien").submit(function (event) {
    event.preventDefault();
    let tenGvhd = $("#tenGvhd").val();
    let soNhomHuongDanToiDa = $("#soNhomHuongDanToiDa").val();
    let tenTaiKhoan = $("#tenTaiKhoan").val();
    let soDienThoai = $("#soDienThoai").val();
    let emailFpt = $("#emailFpt").val();
    let emailFe = $("#emailFe").val();
    let dotDangKyId = $("#dotDangKyId").val();
    let giangVienRequest = {};
    giangVienRequest["tenGvhd"] = tenGvhd;
    giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
    giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
    giangVienRequest["soDienThoai"] = soDienThoai;
    giangVienRequest["emailFpt"] = emailFpt;
    giangVienRequest["emailFe"] = emailFe;
    giangVienRequest["dotDangKyId"] = dotDangKyId;
    giangVienRequest["coSoId"] = coSoId;

    let index = 0;
    if (tenGvhd.length === 0) {
         $("#errorMessageUpdate").text( " Tên giang viên không được để trống");
         index++;
    }
    if (soNhomHuongDanToiDa.length === 0) {
          $("#errorMessageUpdate").text( " Số nhóm không được để trống");
          index++;
    }
    if (tenTaiKhoan.length === 0) {
          $("#errorMessageUpdate").text( " Tên tài khoản không được để trống");
          index++;
    }
    if (soDienThoai.length === 0) {
          $("#errorMessageUpdate").text( " Số điện thoại không được để trống");
          index++;
    }
    if (emailFpt.length === 0) {
          $("#errorMessageUpdate").text( " EmailFpt không được để trống");
          index++;
    }
    if (emailFe.length === 0) {
          $("#errorMessageUpdate").text( " EmailFe không được để trống");
          index++;
    }
    if (dotDangKyId.length === 0) {
          $("#errorMessageUpdate").text(" Đợt đăng ký không được để trống");
          index++;
    }
    if(index ==0){
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: giangVienHuongDanApi,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function () {
                window.open(giangVienHuongDanView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalUpdateGiangVien(giangVienId) {
    $("#modal_update_giang_vien").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/" + giangVienId,
        data: JSON.stringify(giangVienId),
        dataType: 'json',
        success: function (responseData) {
            $("#giangVienId").val(responseData.data.id);
            $("#ten_giang_vien_update").val(responseData.data.tenGvhd);
            $("#so_nhom_update").val(responseData.data.soNhomHuongDanToiDa);
            $("#ten_tai_khoan_update").val(responseData.data.tenTaiKhoan);
            $("#so_dien_thoai_update").val(responseData.data.soDienThoai);
            $("#emailFpt_update").val(responseData.data.emailFpt);
            $("#emailFe_update").val(responseData.data.emailFe);
            $("#dot_dang_ky_update").val(responseData.data.dotDangKyId);
            $("#co_so_update").val(responseData.data.coSoId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_giang_vien_update").submit(function (event) {
    event.preventDefault();
        let tenGvhd = $("#ten_giang_vien_update").val();
        let soNhomHuongDanToiDa = $("#so_nhom_update").val();
        let tenTaiKhoan = $("#ten_tai_khoan_update").val();
        let soDienThoai = $("#so_dien_thoai_update").val();
        let emailFpt = $("#emailFpt_update").val();
        let emailFe = $("#emailFe_update").val();
        let dotDangKyId = $("#dot_dang_ky_update").val();
        let giangVienId = $("#giangVienId").val();
        let giangVienRequest = {};
        giangVienRequest["tenGvhd"] = tenGvhd;
        giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
        giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
        giangVienRequest["soDienThoai"] = soDienThoai;
        giangVienRequest["emailFpt"] = emailFpt;
        giangVienRequest["emailFe"] = emailFe;
        giangVienRequest["dotDangKyId"] = dotDangKyId;
        giangVienRequest["coSoId"] = coSoId;
        let index = 0;
        if (tenGvhd.length === 0) {
             $("#errorMessageUpdate").text( " Tên giang viên không được để trống");
              index++;
        }
        if (soNhomHuongDanToiDa.length === 0) {
              $("#errorMessageUpdate").text( " Số nhóm không được để trống");
               index++;
        }
        if (tenTaiKhoan.length === 0) {
              $("#errorMessageUpdate").text( " Tên tài khoản không được để trống");
              index++;
        }
        if (soDienThoai.length === 0) {
               $("#errorMessageUpdate").text( " Số điện thoại không được để trống");
               index++;
        }
        if (emailFpt.length === 0) {
               $("#errorMessageUpdate").text( " EmailFpt không được để trống");
               index++;
        }
        if (emailFe.length === 0) {
               $("#errorMessageUpdate").text( " EmailFe không được để trống");
               index++;
        }
        if (dotDangKyId.length === 0) {
               $("#errorMessageUpdate").text(" Đợt đăng ký không được để trống");
               index++;
        }
        if(index ==0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: giangVienHuongDanApi + "/" + giangVienId,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function () {
                window.open(giangVienHuongDanView, '_self');
                $("#modal_update_giang_vien").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }

});

function openModalRemoveGiangVien(giangVienId) {
    $("#modal_giang_vien_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/" + giangVienId,
        data: JSON.stringify(giangVienId),
        dataType: 'json',
        success: function () {
            $("#remove_giang_vien").val(giangVienId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_giang_vien_delete").submit(function (event) {
    event.preventDefault();
    let giangVienId = $("#remove_giang_vien").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/" + giangVienId,
        data: JSON.stringify(giangVienId),
        dataType: 'json',
        success: function () {
            window.open(giangVienHuongDanView, '_self');
            $("#modal_giang_vien_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

});

$("#searchGvhd").submit(function (event){
    event.preventDefault();
    let tenGiangVien = $("#tenGiangVien").val();

    $.ajax({
            type: "GET",
            contentType: "application/json",
            url: giangVienHuongDanApi + "/search"+"?tenGiangVien=" + tenGiangVien ,
            dataType: 'json',
            success: function (responseData) {
               console.log(responseData)
               $("#searchListGvhd").html(responseData.data.map(function (item){
               return `
               <tr>
                   <td >${item.stt}</td>
                   <td >${item.tenCoSo}</td>
                   <td >${item.tenDotDangKy}</td>
                   <td >${item.tenGvhd}</td>
                   <td >${item.tenTaiKhoan}</td>
                   <td >${item.soNhomHuongDanToiDa}</td>
                   <td >${item.soDienThoai}</td>
                   <td >${item.emailFpt}</td>
                   <td >${item.emailFe}</td>
               </tr>
               `
               }))
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
})