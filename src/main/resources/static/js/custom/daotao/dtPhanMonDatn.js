const phanCongHuongDanApi = "/api/dao-tao/phan-cong-hong-dan";
const phanCongHuongDanView = document.URL;

$("#form_add_phan_cong").submit(function (event) {
    event.preventDefault();
    let giangVienHuongDanId = $("#getGiangVienHuongDanId").attr('value');
    let listPhanCong = [];
    let index = false;
    let table = $("#table-add");
    let row = table.get(0).lastElementChild.children
    for (const a of row) {
        if (a.children[2].children[0].checked === true) {
            index = true;
            let phanCongRequest = {};
            phanCongRequest["giangVienId"] = giangVienHuongDanId;
            phanCongRequest["monDatnId"] = a.children[1].getAttribute('value');
            listPhanCong.push(phanCongRequest)
        }
    }

    if (index == false) {
        alert("Không có dòng nào được chọn")
        return;
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: phanCongHuongDanApi,
        data: JSON.stringify(listPhanCong),
        dataType: 'json',
        success: function () {
            window.open(phanCongHuongDanView, '_self');
            $("#modal_create").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
});

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
    $("#modal_phan_cong_huong_dan_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: phanCongHuongDanApi + "?idGiangVien=" + giangVienHuongDanId + "&idMon=" + monDatnId,
        data: JSON.stringify(giangVienHuongDanId),
        dataType: 'json',
        success: function () {
            $("#remove_phan_cong_huong_dan").val(monDatnId);
            $("#getGiangVienHuongDanId").val(giangVienHuongDanId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


$("#form_phan_cong_huong_dan_delete").submit(function (event) {
    event.preventDefault();
    let monDatnId = $("#remove_phan_cong_huong_dan").val();
    let giangVienHuongDanId = $("#getGiangVienHuongDanId").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: phanCongHuongDanApi + "?idGiangVien=" + giangVienHuongDanId + "&idMon=" + monDatnId,
        data: JSON.stringify(giangVienHuongDanId, monDatnId),
        dataType: 'json',
        success: function () {
            window.open(phanCongHuongDanView, '_self');
            $("#modal_phan_cong_huong_dan_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

});