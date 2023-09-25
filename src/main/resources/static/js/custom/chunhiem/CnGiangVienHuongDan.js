const CnGvhdAPI = '/api/chu-nhiem/giang-vien-huong-dan';
const CnGvhdView = '/chu-nhiem/giang-vien-huong-dan';
let totalPages = $("#totalPages").val();
let currentPage = $("#currentPage").val();

function clearData() {
    $("#maGvhd").val("")
    $("#soNhomHuongDanToiDa").val("")
    $("#monDatnId").val("")
    $("#dotDangKyId").val("")
    $("#chuyenNganhId").val("")
    $("#sortGvhd").val("sortDesc")
}

function changeInput() {
    if ($("#valueInput").val().length != 0) {
        let value = parseInt($("#valueInput").val());
        if (Number(value) <= 0) {
            value = 1;
        }
        if (Number(value) > totalPages) {
            value = totalPages;
        }
        loadDataGvhd(value - 1);
    }
}

function genPagzing() {
    if (totalPages <= 1) {
        $("#page").html('');
    } else {
        let templatePageMid = ``;
        let templatePageBefore = `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="loadDataGvhd(${currentPage - 1})"><img src="/img/back.svg"></a>
                </li>
                `;
        templatePageMid = `
                            <input id="valueInput" class="input-page" type="number" min="1" max="${totalPages}" value="${Number(currentPage) + 1}" onchange="changeInput()">
                             <input style="border: none; width: 50px" value="|    ${totalPages}" readonly>`
        let templatePageAfter = `
                              <li style="color: #0a8cf0" class="page-item">
                                   <a style="border: none" class="page-link" onclick="loadDataGvhd(${currentPage + 1})"><img src="/img/next.svg"></a>
                              </li><li style="color: #0a8cf0" class="page-item">
                `;
        let templateHtml = ``;
        templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
        if (totalPages == 1) {
            templateHtml = ``;
        }
        $("#page").html(templateHtml);
    }
}

genPagzing()

function loadDataGvhd(count) {
    let maGvhd = $("#maGvhd").val().trim()
    let soNhomHuongDanToiDa = $("#soNhomHuongDanToiDa").val().trim()
    let monDatnId = $("#monDatnId").val()
    let dotDangKyId = $("#dotDangKyId").val()
    let chuyenNganhId = $("#chuyenNganhId").val()
    let sortGvhd = $("#sortGvhd").val()

    currentPage = count;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CnGvhdAPI + "?dotDangKyId=" + dotDangKyId + "&chuyenNganhId=" + chuyenNganhId
            + "&maGvhd=" + maGvhd + "&monDatnId=" + monDatnId + "&soNhomHuongDanToiDa=" + soNhomHuongDanToiDa
            + "&sort=" + sortGvhd + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            let array = responseData.data;
            totalPages = array.totalPages;
            if (array.data.length == 0) {
                $("#listGvhd").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
                if (array.totalPages == 1) {
                    loadDataGvhd(0);
                    return;
                }
                if (count == array.totalPages && count != 0) {
                    loadDataGvhd(count - 1);
                    return;
                }
            } else {
                $("#empty").html(``)
                $("#listGvhd").html(array.data.map(function (gvhd) {
                    return `
                        <tr class="table-light">
                            <td class="text-center align-middle"">${gvhd.stt}</td>
                            <td class="text-center align-middle">${gvhd.name}</td>
                            <td class="text-center align-middle">${gvhd.email}</td>
                            <td class="text-center align-middle">${gvhd.soDienThoai}</td>
                            <td class="text-center align-middle">${gvhd.monDatn}</td>
                            <td class="text-center align-middle">${gvhd.soNhomDangHuongDan + '/' + gvhd.soNhomHuongDanToiDa}</td>
                        </tr>
                    `
                }));
                genPagzing()
            }
        },
        error: function (e) {
        }
    });
}
