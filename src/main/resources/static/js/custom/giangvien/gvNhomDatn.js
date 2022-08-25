const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#tenHocKy").val("");
    $("#tenChuyenNganh").val("");
    $("#tenDotDangKy").val("");
}

$(document).ready(function () {

    fillter()

});

function fillter(){

    $('#tenChuyenNganh').change(function (){
        seachNhomdatn(0);
    })

    $("#tenHocKy").on('change', function (){
        seachNhomdatn(0);
    });

    $("#tenDotDangKy").on('change', function (){
        seachNhomdatn(0);
    });

}


function showDetail(maNhom) {
    $("#form-showDetail").modal('show');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: nhomDatnAPI + "?maNhom=" + maNhom,
            dataType: 'json',
            success: function (responseData) {
                $("#table_body_dssv").html(responseData.data.map(function (item) {
                    return `
                    <tr>
                    <td>${item.stt}</td>
                    <td>${item.maSinhVien}</td>
                    <td>${item.tenSinhVien}</td>
                    <td>${item.soDienThoai}</td>
                    <td>${item.email}</td>
                    <td>${item.khoa}</td>
                    `
                }))
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
}

function chotDuLieu(maNhom) {
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: nhomDatnAPI + "?maNhom=" + maNhom,
        success: function () {
            window.location.reload(false)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
$("#form_list_giang_vien").submit(function (event){
    event.preventDefault();
})

function seachNhomdatn(index) {
    if(index == undefined){
        index =0
    }
    let tenChuyenNganh = $("#tenChuyenNganh").val()
    let tenHocKy = $("#tenHocKy").val()
    let tenDotDangKy = $("#tenDotDangKy").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/search" + "?chuyenNganhId=" + tenChuyenNganh
            + "&hocKyId=" + tenHocKy + "&dotDangKyId=" + tenDotDangKy + "&page="+index,
        success: function (responseData) {
            $("#list_nhom_datn").html(responseData.data.data.map(function (item) {
                return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.totalMaSinhVien}</td>
                 <td>
                           <button
                                    type="button"
                                    class="btn btn-primary"
                                    onclick= "showDetail('${item.id}')">
                                    chi tiết nhóm
                            </button>
                       </td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd}</td>
                <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
                       <td>
                           <button
                                    type="button"
                                    class="btn btn-primary"
                                    id="chotDuLieu" style="${item.trangThai == 1 ? 'display : none' : 'display : block'}"
                                    onclick="chotDuLieu('${item.id}')">Chốt
                           </button>
                      </td>
                </tr>
                `
            }))
            $("#page").html(``)
            let templateHtml = ``;
            for(let i =0; i < responseData.data.totalPages; i++){
                templateHtml += `<li class="page-item" >
                                <a class="page-link" onclick="seachNhomdatn(${i})">${i +1}</a>
                            </li>`
            }
            if(responseData.data.totalPages == 1){
                templateHtml = ``;
            }
            $("#page").html(templateHtml)

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
