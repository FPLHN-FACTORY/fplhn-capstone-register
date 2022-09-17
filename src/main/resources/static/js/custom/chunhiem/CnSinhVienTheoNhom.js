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
        url: sinhVienApi + "/addSinhVien?id=" + idNhom + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien,idNhom),
        success: function (responseData) {
            console.log(responseData)
            if (responseData.data==true) {
                window.open(sinhVienTheoNhomView+ "?id=" + idNhom, '_self');
            } else $("#idStudentError").html(responseData.data.message)
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
            window.open(sinhVienTheoNhomView+ "?id=" + idNhom, '_self');
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});

function openModalChangeGvhd(){
    $('#modal_change_gvhd').modal('show');
}

$('#form_change_gvhd').submit(function (event){
    event.preventDefault();
    let idGroup = $("#idNhom").val();
    let giangVienId = $("#giangVienId").val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addGvhd?" + "giangVienId="+giangVienId+"&idNhom="+idGroup,
        success: function (responseData) {
            if (responseData.data==true) {
                window.open(sinhVienTheoNhomView+ "?id=" + idGroup, '_self');
            } else{
                $("#change_gvhd").html(responseData.data.message)
            }
        },
        error: function (e) {
        }
    });

})




