const sinhVienApi = '/api/chu-nhiem/sinh-vien-dot-dang-ky'
const sinhVienTheoDotDangKyView = '/chu-nhiem/sinh-vien-dot-dang-ky';

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
            // console.log(responseData)
            let array = responseData.data.data.data
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
                  <button 
                      data-toggle="modal"
                      onclick="openModalAddSinhVien('${maSinhVien}','${count}')"
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
                $("#listSV").html(text);
                $("#page").html(``)
                $("#page").html(
                    responseData.data.list.map(function (item, index) {
                        if (responseData.data.list.length == 1) {
                            return ``
                        }
                        return `<li class="page-item" >
                                <a class="page-link" onclick="loadDataToTable(${index})">${item}</a>
                            </li>`
                    })
                )
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