const sinhVienApi = '/api/chu-nhiem/dot-dang-ky/sinh-vien';
const sinhVienTheoDotDangKyView = '/chu-nhiem/dot-dang-ky/sinh-vien';
let totalPages = $("#totalPages").val()
let currentPage = $("#currentPage").val()

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
});

function openModalAddSinhVien(maSinhVien, count) {
    $("#modal_create_nhom").modal('show');
    $("#addSinhVienTheoNhom").val(maSinhVien);
    $("#currentPaging").val(count)
}

function clearBoLoc(){
    $("#tenSinhVien").val("")
    $("#dotDangKyId").val("")
}

function openModalThemSinhVienVaoNhom(){
    $("#modal_create_nhom").modal('show')
}

function createSinhVienVaoNhom() {
    let maNhom = $("#maNhom").val().trim();
    let maSinhVien = $("#addSinhVienTheoNhom").val().trim();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: sinhVienApi + "/addSinhVien?id=" + maNhom + "&maSinhVien=" + maSinhVien,
        data: JSON.stringify(maSinhVien, maNhom),
        success: function (responseData) {
            $("#modal_create_nhom").modal('hide');
            bootbox.alert({
                message: 'Thêm thành công',
                backdrop: true,
                callback: function () {
                    loadDataToTable(currentPage)
                }
            });
        },
        error: function (e) {
            if(e.responseJSON.message.trim() == 'nhom khong ton tai'){
                $("#idNhom_error").html('Nhóm không tồn tại')
            }
        }
    });
}
genPagingAndFillColor()

function genPagingAndFillColor() {
    genPagzing();
    let colorPage = document.querySelectorAll("#pageColor");
    colorPage[currentPage].style.backgroundColor = '#0a8cf0';
    colorPage[currentPage].style.color = 'white';
}

function genPagzing() {
    $("#page").html(``)
    let templatePageMid = ``;
    let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == 0 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadDataToTable(currentPage - 1)">Back</a>
                </li>
                `;
    for (let i = 0; i < totalPages; i++) {
        templatePageMid += `<li class="page-item" style="color: #0a8cf0">
                                        <a class="page-link" id="pageColor" onclick="loadDataToTable(${i})">${i + 1}</a>
                                    </li>`
    }
    let templatePageAfter = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="${currentPage == totalPages - 1 ? 'pointer-events: none' : ''}" class="page-link" onclick="loadDataToTable(Number(currentPage) + 1)">Next</a>
                </li>
                `;
    let templateHtml = ``;
    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
    if (totalPages == 1) {
        templateHtml = ``;
    }
    $("#page").html(templateHtml);
}

function loadDataToTable(count) {
    let tenSinhVien = $("#tenSinhVien").val().trim();
    let dotDangKyId = $("#dotDangKyId").val().trim();
    $("#listSV").html("");
    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: sinhVienApi + "/search" + "?tenSinhVien=" + tenSinhVien + "&idDotDangKy=" + dotDangKyId + "&page=" + count,
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
                totalPages = responseData.data.totalPages
                array.map(function (sinhVien) {
                    var tenDeTai = sinhVien.tenDeTai == null ? 'Chưa có' : sinhVien.tenDeTai;
                    var tenGvhd = sinhVien.tenGvhd == null ? 'Chưa có' : sinhVien.tenGvhd;
                    var maSinhVien = sinhVien.maSinhVien;
                    text1 = `
                      <tr >
                            <td >${sinhVien.stt}</td>
                            <td> ${sinhVien.maSinhVien}</td>
                            <td> ${sinhVien.tenSinhVien}</td>
                            <td> ${sinhVien.maMonChuongTrinh == null ? 'Chưa có' : sinhVien.maMonChuongTrinh}</td>
                            <td> ${sinhVien.maMonDuAn == null ? 'Chưa có' : sinhVien.maMonDuAn}</td>
                                                        `
                    text2 = sinhVien.maNhom == null ? `
                <td>
                  <button onclick="openModalAddSinhVien('${maSinhVien}','${count}')"
                      class="btn_pencil_square">
                       <img src="/img/pencil-square.svg">  
                        </button>
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
                genPagingAndFillColor()
            }
        },
        error: function (responseData) {
        }
    });
}
