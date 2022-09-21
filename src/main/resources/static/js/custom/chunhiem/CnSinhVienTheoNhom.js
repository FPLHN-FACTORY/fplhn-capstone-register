const sinhVienApi = '/api/chu-nhiem/sinh-vien';
const sinhVienTheoNhomView = '/chu-nhiem/sinh-vien';
let idOldCaptain;
$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#keyword").val("");
}
loadData()
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
    if(idOldCaptain == maSinhVien) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: sinhVienApi + "/changeCaptainToOther?idCaptain=" + idOldCaptain + "&idGroup=" + idNhom,
            success: function () {
            },
            error: function () {
            }
        })
    }
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

function openModalChangeCaptain(){
        $("#modal_change_captain").modal('show');
        let idGroup = $("#idNhom").val();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: sinhVienApi + "/oldCaptain?idGroup=" + idGroup,
            success: function (captain) {
                $("#oldCaptain").html(`
              <table 
               class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
              >
              <thead>
              <th>Mã sinh viên</th>
              <th>Họ tên</th>
              <th>Chức vụ</th>
              </thead>
              <tr>
              <td>${captain.data.maSinhVien}</td>
              <td>${captain.data.tenSinhVien}</td>
              <td>Trưởng nhóm</td>
              </tr>
              </table>
         `)
            },
            error: function () {

            }
        })
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: sinhVienApi + "/listStudentNotCaptain?idGroup=" + idGroup,
            success: function (responseData) {
                var text = `
              <table 
               class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
              >
              <thead>
              <th>Mã sinh viên</th>
              <th>Họ tên</th>
              <th>Chức năng</th>
              </thead>
            `;
                responseData.data.map(function (sinhVien) {
                    text += `
            
              <tr>
              <td>${sinhVien.maSinhVien}</td>
              <td>${sinhVien.tenSinhVien}</td>
              <td>
              <a onclick="changeCaptain('${sinhVien.id}')"
              class="btn btn-success"
              >
              Thay đổi
              </a>
              </td>
              </tr>
             `
                })
                text += `</table>`
                $("#manofteam").html(text)
            },
            error: function (responseData) {
            }
        })

}
function changeCaptain(idCaptain) {
    $("#modal_change_captain_confirm").modal('show')
    $("#change_captain_confirm").val(idCaptain)
}
$("#form_change_captain_confirm").submit(function (event) {
    event.preventDefault();
    let idCaptain = $("#change_captain_confirm").val();
    let idGroup = $("#idNhom").val();
    $.ajax({
        type:"PUT",
        contentType:"application/json",
        url:sinhVienApi+"/changeCaptain?idCaptain="+idCaptain +"&idGroup="+idGroup,
        success:function (){
            alert("Thay đổi trưởng nhóm thành công");
            window.open(sinhVienTheoNhomView+ "?id=" + idGroup, '_self');
        },
        error: function (){

        }
    })

})
function loadData() {
    let idGroup = $("#idNhom").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/oldCaptain?idGroup=" + idGroup,
        success: function (captain) {
          idOldCaptain= captain.data.maSinhVien;
        },
        error: function () {
        }
    })


}






