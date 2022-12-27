const phanCongHuongDanApi = "/api/dao-tao/phan-cong-hong-dan";
const phanCongHuongDanView = document.URL;

function openPhanCong() {
    $("#modal_create").modal("show")
}

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function phanCong() {
    event.preventDefault();
    let monDatnId = $("#getMonDatnId").attr('value');
    let listPhanCong = [];
    let index = false;
    let table = $("#table-add");
    let row = table.get(0).lastElementChild.children
    for (const a of row) {
        if (a.children[3].children[0].checked === true) {
            index = true;
            let phanCongRequest = {};
            phanCongRequest["monDatnId"] = monDatnId;
            phanCongRequest["giangVienId"] = a.children[1].getAttribute('value');
            listPhanCong.push(phanCongRequest)
        }
    }

    if (index == false) {
        $("#errLoi").text("Bạn phải chọn một dòng");
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: phanCongHuongDanApi,
        data: JSON.stringify(listPhanCong),
        dataType: 'json',
        success: function () {
            $("#modal_create").modal("hide");
          bootbox.alert({
                message: 'Thêm thành công',
                backdrop: true,
                callback: function () {
                    window.open(phanCongHuongDanView, '_self');
                }
            });
        },
        error: function (e) {
        }
    });
};

let checkOut = 0;

function checkAll() {
    checkOut++;
    let checkBoxs = $(".checkThemTatCa")
    if (checkOut % 2 == 0) {
        for (const checkBox of checkBoxs) {
            checkBox.checked = false
        }
    } else {
        for (const checkBox of checkBoxs) {
            checkBox.checked = true
        }
    }
}

function openModalRemovePhanCongHuongDan(giangVienHuongDanId, monDatnId) {
    bootbox.confirm({
        title: "Xóa",
        message: "Bạn chắc chắn xóa không?",
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
                $.ajax({
                        type: "DELETE",
                        contentType: "application/json",
                        url: phanCongHuongDanApi + "?idGiangVien=" + giangVienHuongDanId + "&idMon=" + monDatnId,
                        data: JSON.stringify(giangVienHuongDanId, monDatnId),
                        dataType: 'json',
                        success: function () {
                            bootbox.alert({
                                  message: 'Xóa thành công',
                                  backdrop: true,
                                  callback: function () {
                                      window.open(phanCongHuongDanView, '_self');
                                  }
                              });
                        },
                        error: function (e) {
                        }
                });
            }
        }
    });

}