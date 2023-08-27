const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#idMon").val("");
}

$(document).ready(function () {

    fillter()

});

function fillter() {
    $("#idMon").on('change', function () {
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
                    <tr class="table-light">
                    <td class="text-center align-middle">${item.stt}</td>
                    <td class="text-center align-middle">${item.chucVu != 0 ? "Trưởng nhóm" : "Thành viên"}</td>
                    <td class="text-center align-middle">${item.maSinhVien}</td>
                    <td class="text-center align-middle">${item.tenSinhVien}</td>
                    <td class="text-center align-middle">${item.soDienThoai}</td>
                    <td class="text-center align-middle">${item.email}</td>
                    <td class="text-center align-middle">${item.maMon}</td>
                    <td class="text-center align-middle">${item.khoa}</td>
                    `
            }))
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function xacDaNhanLienHe(idNhom, maNhom) {
    bootbox.confirm({
        title: "Xác nhận nhóm đã liên hệ",
        message: "Bạn có muốn xác nhận đã liên hệ với nhóm " + maNhom + " ?",
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            }, confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: nhomDatnAPI + "/phe-duyet-nhom?maNhom=" + idNhom,
                    datatype: 'json',
                    success: function () {
                        bootbox.alert('Xác nhận thành công', function () {
                            window.location.reload(false)
                        });
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                    }
                });
            }
        }
    });


}

function seachNhomdatn(index) {
    if (index == undefined) {
        index = 0
    }
    var monId = $("#idMon").val()
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/search" + "?monId=" + monId + "&page=" + index,
        success: function (responseData) {
            $("#list_nhom_datn").html(responseData.data.data.map(function (item) {
                return `
                <tr class="table-light">
                <td class="text-center align-middle">${item.stt}</td>
                <td class="text-center align-middle">${item.maNhom}</td>
                <td class="text-center align-middle">${item.tenDeTai == null || item.tenDeTai == '' ? 'Chưa có' : item.tenDeTai}</td>
                <td class="text-center align-middle">${item.totalMaSinhVien}</td>
                <td class="text-center align-middle">
                    ${item.danhSachThanhVien.split(', ').map(function (value, index) {
                    return `<span style="${item.truongNhom == value ? 'font-weight: bold; text-decoration: underline;' : ''}">${value}${index === item.danhSachThanhVien.length - 1 ? ' ' : ''}</span>`;
                })}
                </td>
                <td class="text-center align-middle">${item.maMon}</td>
                <td class="text-center align-middle">${item.trangThai == 0 ? 'Mới thành lập' : (item.trangThai == 1 ? 'Chủ nhiệm bộ môn đã xác nhận' : (item.trangThai == 2 ? 'Giảng viên đã liên hệ' : (item.trangThai == 3 ? 'Cần sửa' : (item.trangThai == 4 ? 'Giảng viên đã chốt' : 'Chủ nhiệm bộ môn đã chốt'))))}</td>
                 <td class="text-center align-middle">
                 <div class="d-flex justify-content-center align-items-center">
                        <button type="button" onclick=showDetail('${item.id}') class="btn_eye" data-toggle="tooltip" title="Xem chi tiết nhóm">
                                    <img src="/img/eye-fill.svg">
                        </button>
                        <button type="button" onclick= "xacDaNhanLienHe('${item.id}', '${item.maNhom}')"
                                style="${item.trangThai == 1 ? 'display: block' : 'display: none'}" 
                                class="btn_check_circle " data-toggle="tooltip" title="Xác nhận nhóm">
                                    <img src="/img/check-circle.svg">
                        </button>
                        <button type="button" onclick= "chotDeTaiNhomDatn('${item.id}', '${item.maNhom}')"
                                style="${item.trangThai == 2 ? 'display: block' : (item.trangThai == 3 ? 'display: block' : 'display: none')}"
                                class="btn_check " data-toggle="tooltip" title="Xác nhận đề tài">
                                    <img src="/img/clipboard-check.svg">
                         </button>
                         <button type="button" onclick= "updateTenDeTai('${item.id}')"
                                style="${item.trangThai == 2 ? 'display: block' : (item.trangThai == 3 ? 'display: block' : (item.trangThai == 4 ? 'display: block' : 'display: none'))}"
                                class="btn_pencil_square " data-toggle="tooltip" title="Sửa tên đề tài">
                                    <img src="/img/pencil-square.svg">
                         </button>
                    </div>
                 </td>
                </tr>
                `
            }))

        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function updateTenDeTai(maNhom) {
    $("#modal_update_ten_de_tai").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/" + maNhom,
        dataType: 'json',
        success: function (responseData) {
            $("#id_nhom_datn_update").val(responseData.data.id);
            $("#ten_de_tai_update").val(responseData.data.tenDeTai);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function updateTenDeTaiSubmit() {
    let tenDeTai = $("#ten_de_tai_update").val();
    let maNhom = $("#id_nhom_datn_update").val();

    let NhomDatnRequest = {};
    NhomDatnRequest["tenDeTai"] = tenDeTai.trim();
    NhomDatnRequest["maNhom"] = maNhom;
    if (tenDeTai.trim().length == 0) {
        $("#errorMessageUpdate").text("Tên đề tài không được để trống");
    } else if (tenDeTai.trim().length < 6) {
        $("#errorMessageUpdate").text("Tên đề tài tối thiếu 6 ký tự");
    } else if (tenDeTai.trim().length > 100) {
        $("#errorMessageUpdate").text("Tên đề tài quá 100 ký tự");
    } else {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: nhomDatnAPI + "/update-ten-de-tai/" + maNhom,
            data: JSON.stringify(NhomDatnRequest),
            dataType: 'json',
            success: function () {
                window.location.reload(false)
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
};

function chotDeTaiNhomDatn(idNhom, maNhom) {

    bootbox.confirm({
        title: "Xác nhận chốt đề tài", message: "Bạn có muốn xác nhận đề tài nhóm " + maNhom + " ?", buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            }, confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        }, callback: function (result) {
            if (result) {
                $.ajax({
                    type: "PUT",
                    contentType: "application/json",
                    url: nhomDatnAPI + "/phe-duyet-de-tai-nhom?maNhom=" + idNhom,
                    success: function () {
                        bootbox.alert('Xác nhận thành công', function () {
                            window.location.reload(false)
                        });
                    },
                    error: function (e) {
                        console.log("ERROR : ", e);
                    }
                });
            }
        }
    });
}
