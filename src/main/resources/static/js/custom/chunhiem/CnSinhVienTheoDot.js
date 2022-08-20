const sinhVienApi = '/api/chu-nhiem/sinh-vien-dot-dang-ky'
const sinhVienTheoDotDangKyView = '/chu-nhiem/sinh-vien-dot-dang-ky';

function openModalRemoveNhom(maSinhVien) {
    $("#modal_create_nhom").modal('show');
    $("#addSinhVienTheoNhom").val(maSinhVien);
}

$("#search").submit(function (event) {
    event.preventDefault();
    searchByTenSinhVien();
})


$("#form_create_group").submit(function (event) {
    event.preventDefault();
    let maNhom = $("#maNhom").val()
    let maSinhVien = $("#addSinhVienTheoNhom").val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?idNhom=" + maNhom + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien, maNhom),
        success: function (responseData) {
            if (responseData.data == true) {
                window.open(sinhVienTheoDotDangKyView , '_self');
                alert("Thêm thành công")
            } else alert(responseData.data.message)
        },
        error: function (responseData) {
        }
    });
});

function searchByTenSinhVien() {
    let tenSinhVien = $("#ten-hoc-ky").val();
    $("#print").html("");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "?tenSinhVien=" + tenSinhVien,
        data: JSON.stringify(tenSinhVien),
        success: function (responseData) {
            var text = "";
            var text1 = "";
            var text2 = "";
            var text3 = "";
            responseData.data.forEach(function (sinhVien) {
                var tenDeTai = sinhVien.tenDeTai == null ? 'Chưa có' : sinhVien.tenDeTai;
                var tenGvhd = sinhVien.tenGvhd == null ? 'Chưa có' : sinhVien.tenGvhd;
                var maSinhVien = sinhVien.maSinhVien;
                text1 = `
                      <tr >
                            <td >${sinhVien.stt}</td>
                            <td> ${sinhVien.maSinhVien}</td>
                            <td> ${sinhVien.tenSinhVien}</td>
                            <td> ${sinhVien.tenChuyenNganh}</td>
                            <td> ${sinhVien.maMon}</td>
                                                        `
                text2 = sinhVien.maNhom == null ? `
                <td>
                  <button 
                      data-toggle="modal"
                      onclick="openModalRemoveNhom('${maSinhVien}')"
                      class=" btn btn-success">
                      Thêm nhóm</button>
                      </td>
                ` : `
                <td>
                    <p>${sinhVien.maNhom}</p>
                </td>
                `;
                text3 = `    
                    <td>${tenDeTai}</td>
                    <td>${tenGvhd}</td>
                    </tr >
                `;
                text += text1 + text2 + text3;
            });
            $("#print").html(text);

        },
        error: function (responseData) {
        }
    });
}