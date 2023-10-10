const nhomDatnAPI = '/api/giang-vien/nhom-datn';
const nhomDatnView = '/giang-vien/nhom-datn';

function clearDataGVHD() {
    $("#idMon").val("");
}

$(document).ready(function () {

    fillter()

});


// function openModalHistory(page, check) {
//     let size = check;
//     if (check === 0) {
//         size = parseInt($("#sizeHistory").val());
//     } else {
//         $("#sizeHistory").val("20");
//     }
//     $.ajax({
//         type: "GET",
//         contentType: "application/json",
//         url: nhomDatnAPI + "/history?page=" + page + "&size=" + size,
//         dataType: 'json',
//         success: function (responseData) {
//             if (responseData.data.content.length == 0) {
//                 $("#listHistory").html(``);
//                 $("#pageHistory").html('');
//                 $("#emptyHistory").html(`<h3>Không có dữ liệu</h3>`);
//             } else {
//                 $("#emptyHistory").html(``);
//                 $("#listHistory").html(responseData.data.content.map(function (element, index) {
//                     return `
//                 <tr class="table-light">
//                       <td class="text-center align-middle">${element.stt}</td>
//                       <td class="text-center align-middle">${element.mail}</td>
//                       <td class="text-center align-middle">${element.createDate}</td>
//                       <td class="text-center align-middle">${element.content}</td>
//                       <td class="text-center align-middle">${element.author}</td>
//                 </tr>
//                `
//                 }));
//
//                 var count = responseData.data.totalPages;
//                 if (responseData.data.totalPages <= 1) {
//                     $("#pageHistory").html('');
//                 } else {
//                     let templatePageMid = ``;
//                     let templatePageBefore = page === 0 ? "" : `
//                 <li style="color: #0a8cf0" class="page-item">
//                      <a style="border: none" class="page-link" onclick="openModalHistory(${page - 1}, ${size})"><img src="/img/back.svg"></a>
//                 </li>
//                 `;
//                     templatePageMid = `
//                              <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
//                     let templatePageAfter = page === Number(count) - 1 ? "" : `
//                 <li style="color: #0a8cf0" class="page-item">
//                      <a style="border: none" class="page-link" onclick="openModalHistory(${page + 1}, ${size})"><img src="/img/next.svg"></a>
//                 </li>
//                 `;
//                     let templateHtml = ``;
//                     templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
//                     $("#pageHistory").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
//                 }
//             }
//             ;
//         },
//         error: function (e) {
//             console.log("ERROR : ", e);
//         }
//     });
// }

function openModalHistoryDetail(page, size, maNhom, dotDangKyId) {
    if (dotDangKyId.trim().length === 0) {
        dotDangKyId = $("#dotDangKy").val();
    }
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: nhomDatnAPI + "/history-detail?page=" + page + "&size=" + size + "&maNhom=" + maNhom + "&dotDangKyId=" + dotDangKyId,
        dataType: 'json',
        success: function (responseData) {
            $("#historyDetail").modal('show')
            if (responseData.data.content.length == 0) {
                $("#listHistoryDetail").html(``);
                $("#pageHistoryDetail").html('');
                $("#emptyHistoryDetail").html(`<h3>Không có dữ liệu</h3>`);
            } else {
                $("#emptyHistoryDetail").html(``);
                $("#listHistoryDetail").html(responseData.data.content.map(function (element, index) {
                    return `
                <tr class="table-light">                 
                      <td class="text-center align-middle">${element.stt}</td>
                      <td class="text-center align-middle">${element.mail}</td>
                      <td class="text-center align-middle">${element.createDate}</td>
                      <td class="text-center align-middle">${element.content}</td>
                      <td class="text-center align-middle">${element.author}</td>
                </tr>
               `
                }));

                var count = responseData.data.totalPages;
                if (responseData.data.totalPages <= 1) {
                    $("#pageHistoryDetail").html('');
                } else {
                    let templatePageMid = ``;
                    let templatePageBefore = page === 0 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page - 1}, ${size}, ${maNhom}, ${dotDangKyId})"><img src="/img/back.svg"></a>
                </li>
                `;
                    templatePageMid = `
                             <input style="border: none; text-align: center; width: 100px" value="${Number(page) + 1} / ${Number(count)}" readonly>`
                    let templatePageAfter = page === Number(count) - 1 ? "" : `
                <li style="color: #0a8cf0" class="page-item">
                     <a style="border: none" class="page-link" onclick="openModalHistoryDetail(${page + 1}, ${size}, ${maNhom}, ${dotDangKyId})"><img src="/img/next.svg"></a>
                </li>
                `;
                    let templateHtml = ``;
                    templateHtml = templatePageBefore + templatePageMid + templatePageAfter;
                    $("#pageHistoryDetail").html('<ul class="pagination justify-content-end">' + templateHtml + '</ul>');
                }
            }
            ;
        },
        error: function (e) {
            if (e.responseJSON.message === 'File log khong ton tai') {
                bootbox.alert({
                    message: 'File log không tồn tại',
                    backdrop: true,
                    callback: function () {

                    }
                });
            }
        }
    });
}

// function openModalHistoryDownload() {
//     $.ajax({
//         type: "GET",
//         contentType: "application/json",
//         url: nhomDatnAPI + "/download-log",
//         success: function (responseData) {
//             var blob = new Blob([responseData], {type: "text/csv"});
//             var url = window.URL.createObjectURL(blob);
//             var a = document.createElement("a");
//             a.href = url;
//             a.download = "nhom_datn.csv";
//             document.body.appendChild(a);
//             a.click();
//
//             window.URL.revokeObjectURL(url);
//         },
//         error: function (e) {
//             if (e.status === 404) {
//                 bootbox.alert({
//                     message: "Không tìm thấy file trong hệ thống",
//                     backdrop: true
//                 });
//             } else {
//                 console.error("Lỗi khi tải file CSV: " + e.statusText);
//             }
//         }
//     });
// }

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
            let text = "";
            responseData.data.map(function (item) {
                if (item.chucVu != 0) {
                    text += `
                    <tr class="table-light" style="color: red;">
                        <td class="text-center align-middle"><b>${item.stt}</b></td>
                        <td class="text-center align-middle"><b>${item.chucVu != 0 ? "Trưởng nhóm" : "Thành viên"}</b></td>
                        <td class="text-center align-middle"><b>${item.maSinhVien}</b></td>
                        <td class="text-center align-middle"><b>${item.tenSinhVien}</b></td>
                        <td class="text-center align-middle"><b>${item.soDienThoai}</b></td>
                        <td class="text-center align-middle"><b>${item.email}</b></td>
                        <td class="text-center align-middle"><b>${item.maMon}</b></td>
                        <td class="text-center align-middle"><b>${item.khoa}</b></td></tr>
                `
                } else {
                    text += `
                        <tr class="table-light" style="${item.chucVu != 0 ? 'color: red; font-weight: 500' : ''}">
                        <td class="text-center align-middle">${item.stt}</td>
                        <td class="text-center align-middle">${item.chucVu != 0 ? "Trưởng nhóm" : "Thành viên"}</td>
                        <td class="text-center align-middle">${item.maSinhVien}</td>
                        <td class="text-center align-middle">${item.tenSinhVien}</td>
                        <td class="text-center align-middle">${item.soDienThoai}</td>
                        <td class="text-center align-middle">${item.email}</td>
                        <td class="text-center align-middle">${item.maMon}</td>
                        <td class="text-center align-middle">${item.khoa}</td></tr>
                `
                }
            })
            $("#table_body_dssv").html(text)
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
                        if (e.responseJSON.message === 'Nhom du an tot nghiep not exist') {
                            bootbox.alert({
                                message: "Nhóm dự án tốt nghiệp không tồn tại",
                                backdrop: true,
                                callback: function () {
                                }
                            });
                        }
                        if (e.responseJSON.message === 'khong the chot nhom datn') {
                            bootbox.alert({
                                message: "Không thể chốt nhóm dự án tốt nghiệp",
                                backdrop: true,
                                callback: function () {
                                }
                            });
                        }
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
                 <td class="text-center align-middle">${item.nhanXet == null ? '-' : item.nhanXet}</td>
                 <td class="text-center align-middle">
                 <div class="d-flex justify-content-center align-items-center">
                        <button type="button" onclick=showDetail('${item.id}') class="btn_eye" data-toggle="tooltip" title="Xem chi tiết nhóm">
                                    <img src="/img/eye-fill.svg">
                        </button>
                        <button type="button"
                        class="btn_eye"
                        data-toggle="tooltip"
                        title="Xem log nhóm"
                        onclick="openModalHistoryDetail(0, 20, '${item.maNhom}', '${item.dotDangKyId}')"
                         >
                            <img src="/img/history.svg">
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
    let tenDeTai = $("#ten_de_tai_update").val().trim();
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
                if (e.responseJSON.message === 'Nhom du an tot nghiep not exist') {
                    bootbox.alert({
                        message: "Nhóm dự án tốt nghiệp không tồn tại",
                        backdrop: true,
                        callback: function () {
                        }
                    });
                }
                if (e.responseJSON.message === 'khong the chinh sua ten de tai') {
                    bootbox.alert({
                        message: "Không thể chỉnh sửa tên đề tài",
                        backdrop: true,
                        callback: function () {
                        }
                    });
                }
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
                        if (e.responseJSON.message === 'Nhom du an tot nghiep not exist') {
                            bootbox.alert({
                                message: "Nhóm dự án tốt nghiệp không tồn tại",
                                backdrop: true,
                                callback: function () {
                                }
                            });
                        }
                        if (e.responseJSON.message === 'khong the chot de tai nhom datn') {
                            bootbox.alert({
                                message: "Không thể chốt đề tài nhóm đồ án tốt nghiệp",
                                backdrop: true,
                                callback: function () {
                                }
                            });
                        }
                    }
                });
            }
        }
    });
}
