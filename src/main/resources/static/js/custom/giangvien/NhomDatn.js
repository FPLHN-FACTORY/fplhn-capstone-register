const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#tenHocKy").val("");
    $("#tenChuyenNganh").val("");
    $("#tenDotDangKy").val("");
}

$(document).ready(function () {
    $("#hoc_ky_error").text("");
    $("#id_nhom_datn_update").text("");
    $("#ten_de_tai_update").text("");

    fillter()
});

function fillter(){

    $('#tenChuyenNganh').change(function (){
        seachNhomdatn();
    })

    $("#tenHocKy").on('change', function (){
        seachNhomdatn();
    });

    $("#tenDotDangKy").on('change', function (){
        seachNhomdatn();
    });

}


function showDetail(maNhom) {
    $("#form-showDetail").modal('show');
    var sinhvienRequest = {};
    sinhvienRequest["maNhom"] = maNhom;
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: nhomDatnAPI ,
            data: JSON.stringify(sinhvienRequest),
            dataType: 'json',
            success: function () {
                // console.log(responseData)
                // $("#table_body_dssv").html(responseData.data.map(function (item) {
                //     return `
                //     <tr>
                //     <td>${item.stt}</td>
                //     <td>${item.maSinhVien}</td>
                //     <td>${item.tenSinhVien}</td>
                //     <td>${item.soDienThoai}</td>
                //     <td>${item.email}</td>
                //     <td>${item.khoa}</td>
                //     `
                // }))
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
}

function chotDuLieu(maNhom) {
    let nhomDatnRequest = {};
    nhomDatnRequest["maNhom"] = maNhom;
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: nhomDatnAPI ,
        data: JSON.stringify(nhomDatnRequest),
        dataType: 'json',
        success: function () {
            window.location.reload(true);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function seachNhomdatn() {
    let tenChuyenNganh = $("#tenChuyenNganh").val()
    let tenHocKy = $("#tenHocKy").val()
    let tenDotDangKy = $("#tenDotDangKy").val()
    let nhomDatnRequest = {};
    nhomDatnRequest["tenChuyenNganh"] = tenChuyenNganh;
    nhomDatnRequest["tenHocKy"] = tenHocKy;
    nhomDatnRequest["tenDotDangKy"] = tenDotDangKy;
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/search" ,
        data: JSON.stringify(nhomDatnRequest),
        dataType: 'json',
        success: function (responseData) {
            console.log(nhomDatnRequest)
            console.log(JSON.stringify(nhomDatnRequest))
            // $("#list_nhom_datn").html(responseData.data.map(function (item) {
            //     return `
            //     <tr>
            //     <td>${item.stt}</td>
            //     <td>${item.tenNhom}</td>
            //     <td>${item.tenDeTai}</td>
            //     <td>${item.tenChuyenNganh}</td>
            //     <td>${item.totalMaSinhVien}</td>
            //      <td>
            //                <button
            //                         type="button"
            //                         class="btn btn-primary"
            //                         onclick= "showDetail('${item.id}')">
            //                         chi tiết nhóm
            //                 </button>
            //            </td>
            //     <td>${item.maSinhVien}</td>
            //     <td>${item.tenGvhd}</td>
            //     <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
            //            <td>
            //                <button
            //                         type="button"
            //                         class="btn btn-primary"
            //                         id="chotDuLieu" style="${item.trangThai == 1 ? 'display : none' : 'display : block'}"
            //                         onclick="chotDuLieu('${item.id}')">Chốt
            //                </button>
            //           </td>
            //     </tr>
            //     `
            // }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}