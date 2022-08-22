let CNNhomDatnApi = '/api/chu-nhiem/nhom-datn';
let CNNhomDatnView = '/chu-nhiem/nhom-datn';

$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#tenNhom").val("");
    $("#chuyenNganhId").val("");
    $("#tenGvhd").val("");
    $("#trangThai").val("");
}

$("#form_list_nhom_datn").submit(function (event) {
    event.preventDefault();
    loadDataNhomDatn(0);
})

function loadDataNhomDatn(count) {

    let tenNhom = $("#tenNhom").val()
    let chuyenNganhId = $("#chuyenNganhId").val()
    let tenGvhd = $("#tenGvhd").val()
    let trangThai = $("#trangThai").val()

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/search" + "?tenNhom=" + tenNhom + "&chuyenNganhId=" + chuyenNganhId
            + "&tenGvhd=" + tenGvhd + "&trangThai=" + trangThai + "&page=" + count,
        dataType: 'json',
        success: function (responseData) {
            console.log(responseData)
            let array = responseData.data.data.data
            if (array.length == 0) {
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
                <td>${item.total}</td>
                <td>${item.maSinhVien}</td>
                <td>${item.tenGvhd + ' - ' + item.tenTaiKhoan}</td>
                <td>${item.trangThai == 0 ? 'Chưa chốt đề tài' : (item.trangThai == 1 ? 'Giáo viên đã chốt' : 'CNBM đã chốt')}</td>
                <td><a class="btn btn-primary"  href="/chu-nhiem/sinh-vien?id=${item.id}">Chi tiết nhóm</a></td>
                <td><button style="${item.trangThai == 1 ? 'display : block' : 'display : none'}" type="button" class="btn btn-primary" onclick="CNChotDuLieu('${item.id}','${count}')">Chốt đề tài</button></td>
                <td><button style="${item.trangThai == 2 ? 'display : none' : 'display : block'}" type="button" class="btn btn-danger text-white" onclick="openModalRemoveNhomDatn('${item.id}','${count}')">Xóa</button>
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

function CNChotDuLieu(id, count) {
    let checkConfirm = confirm("Bạn có chắc muốn chốt dữ liệu không?");
    if (checkConfirm) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: CNNhomDatnApi + "/" + id + "?page=" + count,
            dataType: 'json',

            success: function () {
                loadDataNhomDatn(count)
                alert("Chốt thành công")
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function openModalRemoveNhomDatn(id, count) {
    $("#modal_nhom_datn_remove").modal('show');
    $("#currentPaging").val(count)

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: CNNhomDatnApi + "/" + id,
        data: JSON.stringify(id),
        dataType: 'json',
        success: function () {
            $("#remove_nhom_datn").val(id);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_nhom_datn_delete").submit(function (event) {
    event.preventDefault();
    let nhomDatnId = $("#remove_nhom_datn").val();
    let currentPaging = $("#currentPaging").val();

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: CNNhomDatnApi + "/" + nhomDatnId,
        data: JSON.stringify(nhomDatnId),
        dataType: 'json',
        success: function () {
            loadDataNhomDatn(currentPaging)
            $("#modal_nhom_datn_remove").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);

        }
    });
});

