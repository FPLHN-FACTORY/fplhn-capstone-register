const sinhVienApi = '/api/chu-nhiem/sinh-vien';
const sinhVienTheoNhomView = '/chu-nhiem/sinh-vien';
const CnNhomDatnView = '/chu-nhiem/nhom-datn';
let idOldCaptain;

$(document).ready(function () {
    $(".chosen-select-width").chosen({
        width: "75%"
    });
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function clearData() {
    $("#keyword").val("");
}

function searchGvhd() {
    let text = $("#tags").val()
    let idNhom = $("#idNhom").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/listGvhd" + "?tenTaiKhoan=" + text + "&tenGvhd=" + text + "&idNhom=" + idNhom,
        dataType: 'json',
        success: function (responseData) {
            let array = [];
            for (var item of responseData.data) {
                array.push(item.thongTinGvhd)
            }
            $("#tags").autocomplete({
                source: array
            });
        },
        error: function (e) {
        }
    });
}

loadData()

function openModalThemSinhVien() {
    $("#modal_create").modal('show')
}

function themSinhVien() {
    let idNhom = $("#idNhom").val()
    let listMaSinhVien = document.querySelectorAll("#ma_sinh_vien")
    var maSinhViens = '';
    for (let item of listMaSinhVien) {
        maSinhViens += item.innerHTML + "-";
    }
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?id=" + idNhom + "&maSinhVien=" + maSinhViens,
        success: function (responseData) {
            window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
        },
        error: function (e) {
            $("#idStudentError").html(e.responseJSON.message)
        }
    });
}

function openModalRemoveNhom(maSinhVien) {
    bootbox.confirm({
        title: "Kích sinh viên ra khỏi nhóm",
        message: "Bạn muốn kích sinh viên này không?",
        backdrop: true,
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (result) {
            if (result) {
                let idNhom = $("#idNhom").val();
                let soThanhVien = $("#soThanhVien").val();
                if (idOldCaptain == maSinhVien) {
                    $.ajax({
                        type: "PUT",
                        contentType: "application/json",
                        url: sinhVienApi + "/changeCaptainToOther?idCaptain=" + idOldCaptain + "&idGroup=" + idNhom,
                        success: function () {
                            window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                        },
                        error: function () {
                        }
                    })
                }
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: sinhVienApi + "?maSinhVien=" + maSinhVien + "&idNhom=" + idNhom,
                    success: function () {
                        if (soThanhVien > 1) {
                            window.open(sinhVienTheoNhomView + "?id=" + idNhom, '_self');
                        } else {
                            window.open(CnNhomDatnView, '_self');
                        }
                    },
                    error: function (e) {
                    }
                });
            }
        }
    });
}

function openModalChangeGvhd() {
    $('#modal_change_gvhd').modal('show');
}

function thayDoiGiangVien() {
    let idGroup = $("#idNhom").val();
    let giangVienId = $("#tags").val().split('_');
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addGvhd" + "?giangVienId=" + giangVienId[0] + "&idNhom=" + idGroup,
        success: function () {
            window.open(sinhVienTheoNhomView + "?id=" + idGroup, '_self');
        },
        error: function (e) {
            if (e.responseJSON.message == 'Giang vien khong ton tai') {
                $("#change_gvhd_error").text('Giảng viên không tồn tại')
            }
        }
    });
}

function openModalChangeCaptain() {
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
              class="btn btn-success text-white"
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
    bootbox.confirm({
        title: "Đổi trưởng nhóm",
        message: "Bạn có chắc muốn đổi trưởng nhóm không?",
        backdrop: true,
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (result) {
            if (result) {
                let idGroup = $("#idNhom").val();
                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: sinhVienApi + "/changeCaptain?idCaptain=" + idCaptain + "&idGroup=" + idGroup,
                    success: function () {
                        bootbox.alert({
                            message: "Thay đổi trưởng nhóm thành công",
                            backdrop: true,
                            callback: function () {
                                window.open(sinhVienTheoNhomView + "?id=" + idGroup, '_self');
                            }
                        });
                    },
                    error: function () {
                    }
                })
            }
        }
    });
}

function loadData() {
    let idGroup = $("#idNhom").val();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/oldCaptain?idGroup=" + idGroup,
        success: function (captain) {
            idOldCaptain = captain.data.maSinhVien;
        },
        error: function () {
        }
    })
}






