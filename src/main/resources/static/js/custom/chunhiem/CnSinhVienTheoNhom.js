const sinhVienApi = '/api/chu-nhiem/sinh-vien';
const sinhVienTheoNhomView = '/chu-nhiem/sinh-vien';

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#keyword").val("");
}

$("#form_create_ma_nhom").submit(function (event) {
    event.preventDefault();
    let idNhom = $("#idNhom").val()
    let maSinhVien = $("#maSinhVien").val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?idNhom=" + idNhom + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien,idNhom),
        success: function (responseData) {
            console.log(responseData)
            if (responseData.data==true) {
                window.open(sinhVienTheoNhomView+ "?idNhom=" + idNhom, '_self');
            } else alert(responseData.data.message)
        },
        error: function (responseData) {
        }
    });
});

function openModalRemoveNhom(maSinhVien) {
    $("#modal_sinh_vien_theo_nhom_remove").modal('show');
    $("#remove_sinh_vien_theo_nhom").val(maSinhVien);
}

$("#form_hoc_ky_delete").submit(function (event) {
    event.preventDefault();
    let maSinhVien = $("#remove_sinh_vien_theo_nhom").val();
    let idNhom = $("#idNhom").val()

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: sinhVienApi + "/" + maSinhVien,
        success: function () {
            window.open(sinhVienTheoNhomView+ "?idNhom=" + idNhom, '_self');
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});





