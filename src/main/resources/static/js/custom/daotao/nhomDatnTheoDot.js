let DTNhomDatnApi = '/api/dao-tao/nhom-datn';

function clearform() {
    $("#keyword").val("");
    $("#chuyenNganh").val("");
    $("#trangThai").val("");
    $("#dotDangKy").val("");
}

function openModalDanhSachSinhVien(id) {
    $("#modal_show_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "?keyword=" + id,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData.data.data)
                var text = "";
            responseData.data.data.map(function (item) {
                text += `
                <tr>
                <td>${item.maSinhVien}</td>
                <td>${item.tenSinhVien}</td>
                </tr>
                `
            })
            $("#show_Dssv").html(text)
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}
// mới
$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})
function loadDataNhomDatn(count) {
    let tenNhom = $("#keyword").val()
    let chuyenNganhId = $("#chuyenNganhId").val()
    let tenGvhd = $("#tenGvhd").val()
    let trangThai = $("#trangThai").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: DTNhomDatnApi + "/search" + "?keyword=" + tenNhom + "&chuyenNganhId=" + chuyenNganhId
            + "&tenGvhd=" + tenGvhd + "&trangThai=" + trangThai + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            let array = responseData.data.data.data
            console.log(array)
            if (array.length == 0) {
                console.log(array.length)
                $("#listNhomDatn").html(``)
                $("#page").html(``)
                $("#empty").html(`<h3>Không có dữ liệu</h3>`)
            } else {
                $("#empty").html(``)
                $("#listNhomDatn").html(array.map(function (item) {
                    return `
                <tr>
                <td>${item.stt}</td>
                <td>${item.tenNhom}</td>
                <td>${item.tenDeTai}</td>
                <td>${item.tenChuyenNganh}</td>
                <td>${item.countSinhVien}</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd}</td>
                <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
                <td><button type="button" data-toggle="modal" data-target="#modal_create" class="btn btn-primary" onclick="openModalDanhSachSinhVien('${item.id}')">Chi tiết thành viên</button>
                <div class="modal fade"></div>
                </td>
                </tr>
                `
                }))
                $("#page").html(``)
                $("#page").html(
                    responseData.data.list.map(function (item, index) {
                        if (responseData.data.list.length == 1) {
                            return ``
                        }
                        return `<li class="page-item" >
                                <a class="page-link" onclick="loadDataNhomDatn(${index})">${item}</a>
                            </li>`
                    })
                )
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}