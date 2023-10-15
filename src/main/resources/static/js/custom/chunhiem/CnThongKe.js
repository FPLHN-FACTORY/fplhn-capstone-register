const CnDashBoardAPI = '/api/chu-nhiem/thong-ke-chi-so';
const CnDashBoardView = '/chu-nhiem/thong-ke-chi-so';

function clearDataAll() {
    $("#dotDangKyId").val("")
    $("#chuyenNganhId").val("")
}

function clearDataGvhd() {
    $("#gvhd").val("")
    $("#monDatnId").val("")
    $("#sortGvhd").val("sortDesc")
    $("#soNhomHuongDanToiDa").val("")
    $("#tenDeTaiSearch").val("")
}

function loadDashBoard() {
    let dotDangKyId = $("#dotDangKyId").val()
    let chuyenNganhId = $("#chuyenNganhId").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CnDashBoardAPI + "/search-all?dotDangKyId=" + dotDangKyId + "&chuyenNganhId=" + chuyenNganhId,
        dataType: 'json',
        success: function (responseData) {
            let data = responseData.data;
            $("#tenChuyenNganh").text(data.tenChuyenNganh);
            $("#tongSLSinhVien").text(data.tongSLSinhVien);
            $("#tongSoNhom").text(data.tongSoNhom);
            $("#tongSoGvhd").text(data.tongSoGvhd);
            $("#tongSoGvhdDangCoNhom").text(data.tongSoGvhdDangCoNhom);
            $("#tongSoNhomDaChotDeTai").text(data.tongSoNhomDaChotDeTai);
            $("#tongSinhVienDaCoNhomHopLe").text(data.tongSinhVienDaCoNhomHopLe);
            $("#tongSLSinhVienDuDieuKien").text(data.tongSLSinhVienDuDieuKien);
            $("#tongSoGvhdChuaCoNhomNao").text(data.tongSoGvhdChuaCoNhomNao);
            $("#tongSoNhomChuaChotDeTai").text(data.tongSoNhomChuaChotDeTai);
            $("#tongSinhVienChuaCoNhomHopLe").text(data.tongSinhVienChuaCoNhomHopLe);
            $("#tongSinhVienChuaCoNhom").text(data.tongSinhVienChuaCoNhom);
            $("#tongSLNhomChuaCoGvhd").text(data.tongSLNhomChuaCoGvhd);
            $("#tongSLSinhVienKhongDuDieuKien").text(data.tongSLSinhVienKhongDuDieuKien);
            $("#tongSlNhomChuaDatTenDeTai").text(data.tongSlNhomChuaDatTenDeTai);

            let template = data.listGvhd.map(function (item, index) {
                let isListEmpty = item.listNhomDatn.length === 0;

                let messageDisplay = isListEmpty ? '' : 'none';
                return `
                        <div
                         style="padding: 10px; border-radius: 7px; margin-bottom: 5px">
                        <div class="row">
                            <div class="col-12">
                                <h6 class="font-weight-bold" style="margin-bottom: 0">
                                    <span>${index + 1 + '. ' + item.gvhd}</span>
                                    <span style="color: red">${' (' + item.soNhomDangHuongDan + ')'}</span>
                                    <span>${' (Môn hướng dẫn: ' + item.monDatn + ')'}</span>
                                </h6>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-12">
                                <table class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
                                style="${isListEmpty ? 'none' : ''}">
                                    <thead class="thead-primary" style="${isListEmpty ? 'none' : ''}">
                                    <tr>
                                        <th width="50px">STT</th>
                                        <th>Mã nhóm</th>
                                        <th>SL thành viên</th>
                                        <th>Tên đề tài</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    ${item.listNhomDatn.map(function (nhomDatn, indexNhomDatn) {
                    return `
                                        <tr>
                                            <td>${indexNhomDatn + 1}</td>
                                            <td>${nhomDatn.maNhom + ' - ' + nhomDatn.monDatn}</td>
                                            <td>${nhomDatn.soLuongThanhVien + '/7'}</td>
                                            <td>${(nhomDatn.tenDeTai == null || nhomDatn.tenDeTai == "") ? '-' : nhomDatn.tenDeTai}</td>
                                        </tr>
                                    `;
                }).join('')}
                                    </tbody>
                                </table>
                                <div 
                                     style="text-align: center; display: ${messageDisplay}"><span style="color: red">Không có dữ liệu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            }).join('');
            $("#box_gvhd").html(template);
        },
        error: function (e) {
        }
    });
}

function loadDashBoardGvhd() {
    let dotDangKyId = $("#dotDangKyId").val()
    let chuyenNganhId = $("#chuyenNganhId").val()
    let maGvhd = $("#gvhd").val().trim()
    let monDatnId = $("#monDatnId").val()
    let soNhomHuongDanToiDa = $("#soNhomHuongDanToiDa").val()
    let sort = $("#sortGvhd").val()
    let tenDeTaiSearch = $("#tenDeTaiSearch").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CnDashBoardAPI + "/search-gvhd?dotDangKyId=" + dotDangKyId + "&chuyenNganhId=" + chuyenNganhId
            + "&maGvhd=" + maGvhd + "&monDatnId=" + monDatnId + "&soNhomHuongDanToiDa=" + soNhomHuongDanToiDa
            + "&tenDeTaiSearch=" + tenDeTaiSearch + "&sort=" + sort,
        dataType: 'json',
        success: function (responseData) {
            let data = responseData.data;

            let template = data.map(function (item, index) {
                let isListEmpty = item.listNhomDatn.length === 0;

                let messageDisplay = isListEmpty ? '' : 'none';
                return `
                        <div
                         style="padding: 10px; border-radius: 7px; margin-bottom: 5px">
                        <div class="row">
                            <div class="col-12">
                                <h6 class="font-weight-bold" style="margin-bottom: 0">
                                    <span>${index + 1 + '. ' + item.gvhd}</span>
                                    <span style="color: red">${' (' + item.soNhomDangHuongDan + ')'}</span>
                                    <span>${' (Môn hướng dẫn: ' + item.monDatn + ')'}</span>
                                </h6>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-12">
                                <table class="table table-bordered m-table d-sm-table m-table--head-bg-primary"
                                style="${isListEmpty ? 'none' : ''}">
                                    <thead class="thead-primary" style="${isListEmpty ? 'none' : ''}">
                                    <tr>
                                        <th class="text-center align-middle" width="50px">STT</th>
                                        <th class="text-center align-middle">Mã nhóm</th>
                                        <th class="text-center align-middle">SL thành viên</th>
                                        <th class="text-center align-middle">Tên đề tài</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    ${item.listNhomDatn.map(function (nhomDatn, indexNhomDatn) {
                    return `
                                        <tr>
                                            <td class="text-center align-middle">${indexNhomDatn + 1}</td>
                                            <td class="text-center align-middle">${nhomDatn.maNhom + ' - ' + nhomDatn.monDatn}</td>
                                            <td class="text-center align-middle">${nhomDatn.soLuongThanhVien + '/7'}</td>
                                            <td class="text-center align-middle">${(nhomDatn.tenDeTai == null || nhomDatn.tenDeTai == "") ? '-' : nhomDatn.tenDeTai}</td>
                                        </tr>
                                    `;
                }).join('')}
                                    </tbody>
                                </table>
                                <div 
                                     style="text-align: center; display: ${messageDisplay}"><span style="color: red">Không có dữ liệu</span>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
            }).join('');
            $("#box_gvhd").html(template);
            if (data == null || data.length == 0) {
                $("#empty").html(`<span style="color: red;font-size: 18px">Không có dữ liệu</span>`)
            } else {
                $("#empty").html(``)
            }
        },
        error: function (e) {
        }
    });
}