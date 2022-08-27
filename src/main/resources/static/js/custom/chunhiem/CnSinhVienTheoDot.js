const sinhVienApi = '/api/chu-nhiem/dot-dang-ky/sinh-vien';
const sinhVienTheoDotDangKyView = '/chu-nhiem/dot-dang-ky/sinh-vien';

function openModalAddSinhVien(maSinhVien, count) {
    $("#modal_create_nhom").modal('show');
    $("#addSinhVienTheoNhom").val(maSinhVien);
    $("#currentPaging").val(count)
}

$("#form_create_group").submit(function (event) {
    event.preventDefault();
    let maNhom = $("#maNhom").val()
    let maSinhVien = $("#addSinhVienTheoNhom").val();
    let count = $("#currentPaging").val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?id=" + maNhom + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien, maNhom),
        success: function (responseData) {
            if (responseData.data == true) {
                loadDataToTable(count)
                $("#modal_create_nhom").modal('hide');
                alert("Thêm thành công")
            } else alert(responseData.data.message)
        },
        error: function (responseData) {
        }
    });
});

function loadDataToTable(count) {
    let tenSinhVien = $("#ten-hoc-ky").val();
    $("#listSV").html("");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/search" + "?tenSinhVien=" + tenSinhVien + "&page=" + count,
        success: function (responseData) {

            let array = responseData.data.data
            var text = "";
            var text1 = "";
            var text2 = "";
            var text3 = "";
            if (array.length == 0) {
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
                $("#listSV").html(``)
                $("#page").html(``)
            } else {
                $("#empty").html(``)
                array.map(function (sinhVien) {
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
                  <a
                      data-toggle="modal"
                      onclick="openModalAddSinhVien('${maSinhVien}','${count}')"
                      class=" btn btn-success">
                      Thêm nhóm</a>
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
                $("#listSV").html(text);
                $("#page").html(``)
                let templateHtml = ``;
                for (let i = 0; i < responseData.data.totalPages; i++) {
                    templateHtml += `<li class="page-item" >
                                        <a class="page-link" onclick="loadDataToTable(${i})">${i+1}</a>
                                    </li>`
                }
                if(responseData.data.totalPages == 1){
                    templateHtml = ``;
                }
                $("#page").html(templateHtml);
            }
        },
        error: function (responseData) {
        }
    });
}

$("#search").submit(function (event) {
    event.preventDefault();
    loadDataToTable(0);
})