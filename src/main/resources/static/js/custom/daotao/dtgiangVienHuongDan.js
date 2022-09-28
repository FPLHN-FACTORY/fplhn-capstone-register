const giangVienHuongDanApi = '/api/dao-tao/giang-vien-huong-dan';
const giangVienHuongDanView = '/dao-tao/giang-vien-huong-dan';
const regexFE = new RegExp('[a-z0-9]+@fe.edu.vn');
const regexFPT = new RegExp('[a-z0-9]+@fpt.edu.vn');
const regexSDT = new RegExp('(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}');
const regexSoNhom = new RegExp('[0-9]+');

$(document).ready(function () {
    $("#giang_vien_error").text("");
});

function clearData() {
    $("#tenGiangVien").val("");
}

$("#form_create_giang_vien").submit(function (event) {
    event.preventDefault();
    let tenGvhd = $("#tenGvhd").val().trim();
    let soNhomHuongDanToiDa = $("#soNhomHuongDanToiDa").val().trim();
    let tenTaiKhoan = $("#tenTaiKhoan").val().trim();
    let soDienThoai = $("#soDienThoai").val().trim();
    let emailFpt = $("#emailFpt").val().trim();
    let emailFe = $("#emailFe").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    let giangVienRequest = {};
    giangVienRequest["tenGvhd"] = tenGvhd;
    giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
    giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
    giangVienRequest["soDienThoai"] = soDienThoai;
    giangVienRequest["emailFpt"] = emailFpt;
    giangVienRequest["emailFe"] = emailFe;
    giangVienRequest["dotDangKyId"] = dotDangKyId;

    let index = 0;

    if (tenGvhd.length === 0) {
        $("#errorTenGiangVien").text(" Tên giang viên không được để trống");
        index++;
    } else {
        $("#errorTenGiangVien").text("");
    }
    if (soNhomHuongDanToiDa.length === 0) {
        $("#errorSoNhomHD").text(" Số nhóm không được để trống");
        index++;
    } else if (regexSoNhom.test(soNhomHuongDanToiDa) == false) {
        $("#errorSoNhomHD").text(" Số nhóm phải là số");
        index++;
    } else if (Number.parseInt(soNhomHuongDanToiDa) <= 0) {
        $("#errorSoNhomHD").text("Số nhóm phải lớn hơn 0");
        index++;
    }else if (Number.isInteger(soNhomHuongDanToiDa) == false) {
        $("#errorSoNhomHD").text("Số nhóm phải là số ");
        index++;
    }  else if (Number.parseInt(soNhomHuongDanToiDa) < 1 || Number.parseInt(soNhomHuongDanToiDa) > 7) {
        $("#errorSoNhomHD").text("Số nhóm chỉ từ 1 đến 7");
        index++;
    } else {
        $("#errorSoNhomHD").text("");
    }

    if (tenTaiKhoan.length === 0) {
        $("#errorTenTaiKhoan").text(" Tên tài khoản không được để trống");
        index++;
    } else {
        $("#errorTenTaiKhoan").text("");
    }

    if (soDienThoai.length === 0) {
        $("#errorSDT").text(" Số điện thoại không được để trống");
        index++;
    } else if (regexSDT.test(soDienThoai) == false) {
        $("#errorSDT").text(" Số điện thoại không đúng định dạng");
        index++;
    } else if (soDienThoai.length !=10 ) {
        $("#errorSDT").text(" Số điện thoại không đúng định dạng");
        index++;
    }else {
        $("#errorSDT").text("");
    }

    if (emailFpt.length === 0) {
        $("#errorEmailFpt").text(" EmailFpt không được để trống");
        index++;
    } else if (emailFpt.trim().includes(" ")) {
        $("#errorEmailFpt").text("Email Fpt Phải Đúng Định Dạng");
        index++;
    }else if (regexFPT.test(emailFpt) == false) {
        $("#errorEmailFpt").text("Email Fpt Phải Đúng Định Dạng");
         index++;
    }  else {
        $("#errorEmailFpt").text("");
    }

    if (emailFe.length === 0) {
        $("#errorEmailFe").text(" EmailFe không được để trống");
        index++;
    }else if (regexFE.test(emailFe) == false) {
         $("#errorEmailFe").text("Email Fe Phải Đúng Định Dạng");
         index++;
    }  else if (emailFpt.trim().includes(" ")) {
        $("#errorEmailFe").text("Email Fe Phải Đúng Định Dạng");
        index++;
    } else {
        $("#errorEmailFe").text("");
    }

    if (dotDangKyId == "Chọn đợt đăng kí") {
        $("#errorDotDangKy").text(" Đợt đăng ký không được để trống");
        index++;
    } else {
        $("#errorDotDangKy").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: giangVienHuongDanApi,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function ( responseData) {
            console.log(responseData)
                if(responseData.data.message == null){
                    window.open(giangVienHuongDanView, '_self');
                    $("#modal_create").modal("hide");
                }else{
                    alert(responseData.data.message)
                }
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalUpdateGiangVien(giangVienId, item) {
    $("#modal_update_giang_vien").modal('show');
    $("#update_giang_vien_item").val(item);
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
    let tenGvhd = $("#ten_giang_vien_update").val().trim();
    let soNhomHuongDanToiDa = $("#so_nhom_update").val().trim();
    let tenTaiKhoan = $("#ten_tai_khoan_update").val().trim();
    let soDienThoai = $("#so_dien_thoai_update").val().trim();
    let emailFpt = $("#emailFpt_update").val().trim();
    let emailFe = $("#emailFe_update").val().trim();
    let dotDangKyId = $("#dot_dang_ky_update").val().trim();
    let giangVienId = $("#giangVienId").val().trim();
    let item = $("#update_giang_vien_item").val().trim();
    let giangVienRequest = {};
    giangVienRequest["tenGvhd"] = tenGvhd;
    giangVienRequest["soNhomHuongDanToiDa"] = soNhomHuongDanToiDa;
    giangVienRequest["tenTaiKhoan"] = tenTaiKhoan;
    giangVienRequest["soDienThoai"] = soDienThoai;
    giangVienRequest["emailFpt"] = emailFpt;
    giangVienRequest["emailFe"] = emailFe;
    giangVienRequest["dotDangKyId"] = dotDangKyId;
    let index = 0;
    if (tenGvhd.length === 0) {
        $("#errorTenGiangVienUpdate").text(" Tên giang viên không được để trống");
        index++;
    } else {
        $("#errorTenGiangVienUpdate").text("");
    }

    if (soNhomHuongDanToiDa.length === 0) {
        $("#errorSoNhomHDUpdate").text(" Số nhóm không được để trống");
        index++;
    } else if (regexSoNhom.test(soNhomHuongDanToiDa) == false) {
        $("#errorSoNhomHDUpdate").text(" Số nhóm phải là số");
        index++;
    }else if (Number.isInteger(soNhomHuongDanToiDa) == false) {
         $("#errorSoNhomHDUpdate").text(" Số nhóm phải là số");
        index++;
    } else if (Number.parseInt(soNhomHuongDanToiDa) < 1 || Number.parseInt(soNhomHuongDanToiDa) > 7) {
        $("#errorSoNhomHDUpdate").text("Số nhóm chỉ từ 1 đến 7");
         index++;
    } else {
        $("#errorSoNhomHD").text("");
    }

    if (tenTaiKhoan.length === 0) {
        $("#errorTenTaiKhoanUpdate").text(" Tên tài khoản không được để trống");
        index++;
    } else {
        $("#errorTenTaiKhoanUpdate").text("");
    }

    if (soDienThoai.length === 0) {
        $("#errorSDT").text(" Số điện thoại không được để trống");
        index++;
    } else if (regexSDT.test(soDienThoai) == false) {
        $("#errorSDTUpdate").text(" Số điện thoại không đúng định dạng");
        index++;
    } else if ( soDienThoai.length != 10 ) {
        $("#errorSDTUpdate").text(" Số điện thoại không đúng định dạng");
        index++;
     }  else {
        $("#errorSDTUpdate").text("");
    }

    if (emailFpt.length === 0) {
        $("#errorEmailFptUpdate").text(" EmailFpt không được để trống");
        index++;
    } else if (regexFPT.test(emailFpt) == false) {
        $("#errorEmailFptUpdate").text("Email Fpt Phải Đúng Định Dạng");
        index ++;
    } else if (regexFPT.test(emailFpt) == false) {
        $("#errorEmailFptUpdate").text("Email Fpt Phải Đúng Định Dạng");
        index ++;
    } else {
        $("#errorEmailFpt").text("");
    }

    if (emailFe.length === 0) {
        $("#errorEmailFeUpdate").text(" EmailFe không được để trống");
        index++;
    } else if (regexFE.test(emailFe) == false) {
        $("#errorEmailFeUpdate").text("Email Fe Phải Đúng Định Dạng");
        index++;
    }else if (emailFpt.trim().includes(" ")) {
         $("#errorEmailFeUpdate").text("Email Fe Phải Đúng Định Dạng");
         index++;
    } else {
        $("#errorEmailFeUpdate").text("");
    }

    if (dotDangKyId == "Chọn đợt đăng kí") {
        $("#errorDotDangKyUpdate").text(" Đợt đăng ký không được để trống");
        index++;
    } else {
        $("#errorDotDangKyUpdate").text("");
    }
    if (index == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: giangVienHuongDanApi + "/" + giangVienId,
            data: JSON.stringify(giangVienRequest),
            dataType: 'json',
            success: function (responseData) {
                if(responseData.data.message == null){
                    window.open(giangVienHuongDanView, '_self');
                    $("#modal_create").modal("hide");
                }else{
                    alert(responseData.data.message)
                }

            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }

});

function openModalRemoveGiangVien(giangVienId, item) {
    $("#modal_giang_vien_remove").modal('show');
    $("#remove_giang_vien_item").val(item);
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
    let item = $("#remove_giang_vien_item").val();
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/" + giangVienId,
        data: JSON.stringify(giangVienId),
        dataType: 'json',
        success: function () {
            loadTable(item);
            $("#modal_giang_vien_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

});

$("#searchGvhd").submit(function (event) {
    event.preventDefault();
    loadTable(0);
})

function loadTable(count) {
    let tenGiangVien = $("#tenGiangVien").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: giangVienHuongDanApi + "/search" + "?tenGiangVien=" + tenGiangVien + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            $("#searchListGvhd").html(responseData.data.data.map(function (item) {
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
                   <td>
                    <button
                        type="button"
                        class="btn btn-primary"
                        onclick="openModalUpdateGiangVien('${item.id}','${count}')">
                          Sửa
                     </button>
                     </button>
                     <button
                         type="button"
                         class="btn btn-danger"
                         data-toggle="modal"
                         onclick="openModalRemoveGiangVien('${item.id}','${count}')">
                         Xoá
                     </button>
                    </td>

               </tr>
               `
            }))
            $("#page").html(``)
            let templateHtml = ``;
            for (let i = 0; i < responseData.data.totalPages; i++) {
                    templateHtml += `<li class="page-item" >
                                         <a class="page-link" onclick="loadTable(${i})">${i+1}</a>
                                     </li>`
            }
            if(responseData.data.totalPages == 1){
                    templateHtml = ``;
            }
            $("#page").html(templateHtml);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
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
                    window.open('/dao-tao/giang-vien-huong-dan', '_self');
                },
                error: function (e) {
                    alert(e.responseText);
                    window.open('/dao-tao/giang-vien-huong-dan', '_self');
                    console.log("ERROR: "+e);
                }
            }
        );
    }
    else {
        $("#import_excel_error").text("chọn đúng file Excel ");
    }
});
