const dotDangKyApi = '/api/dao-tao/dot-dang-ky';
const dotDangKyView = '/dao-tao/dot-dang-ky';

$(document).ready(function () {
    $("#dot_dang_ky_error").text("");
});

$(document).ready(function () {
    $('[data-toggle = "tooltip"]').tooltip();
    $("#targetLink").text(dotDangKyView);
});

function openModalCreate() {
    $("#hocKyAdd").val("");
    $("#errorMessageAddHocKy").text("");
    $("#errorMessageAddNgayKetThuc").text("");
    $("#tenDotDangKyAdd").val('');
    $("#ngayBatDauAdd").val('');
    $("#ngayKetThucAdd").val('');
    $("#hanSinhVienAdd").val('');
    $("#hanGiangVienAdd").val('');
    $("#hanChuNhiemBoMonAdd").val('');
    $("#errorMessageAddTenDot").text("");
    $("#errorMessageAddNgayBatDau").text("");
    $("#errorMessageAddHanSinhVien").text("");
    $("#errorMessageAddHanGiangVien").text("");
    $("#errorMessageAddHanChuNhiemBoMon").text("");
    $("#modal_create").modal('show');
}

function openModalUpdateDotDangKy(dotDangKyId) {
    $("#errorMessageTenDotDangKyUpdate").text('');
    $("#errorMessageNgayBatDauUpdate").text("");
    $("#errorMessageNgayBatDauUpdate").text("");
    $("#errorMessageNgayKetThucUpdate").text("");
    $("#errorMessageHanSinhVienUpdate").text("");
    $("#errorMessageHanGiangVienUpdate").text("");
    $("#errorMessageHanChuNhiemBoMonUpdate").text("");
    $("#modal_update_dot_dang_ky").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi + "/" + dotDangKyId,
        data: JSON.stringify(dotDangKyId),
        dataType: 'json',
        success: function (responseData) {
            $("#id_dot_dang_ky_update").val(responseData.data.id);
            $("#id_hoc_ky_update").val(responseData.data.hocKyId);
            $("#id_co_so_update").val(responseData.data.coSoId);
            $("#ten_dot_dang_ky_update").val(responseData.data.tenDotDangKy);
            var ngayBatDau = new Date(responseData.data.ngayBatDau);
            var ngayBatDauStr = ngayBatDau.toLocaleDateString('en-CA') + " " + ngayBatDau.toLocaleTimeString();
            $("#ngay_bat_dau_update").val(ngayBatDauStr);
            var ngayKetThuc = new Date(responseData.data.ngayKetThuc);
            var ngayBatDauStr = ngayKetThuc.toLocaleDateString('en-CA') + " " + ngayBatDau.toLocaleTimeString();
            $("#ngay_ket_thuc_update").val(ngayBatDauStr);
            var hanSinhVien = new Date(responseData.data.hanSinhVien);
            var hanSinhVienStr = hanSinhVien.toLocaleDateString('en-CA') + " " + hanSinhVien.toLocaleTimeString();
            $("#han_sinh_vien_update").val(hanSinhVienStr);
            var hanGiangVien = new Date(responseData.data.hanGiangVien);
            var hanGiangVienStr = hanGiangVien.toLocaleDateString('en-CA') + " " + hanGiangVien.toLocaleTimeString();
            $("#han_giang_vien_update").val(hanGiangVienStr);
            var hanChuNhiemBoMon = new Date(responseData.data.hanChuNhiemBoMon);
            var hanChuNhiemBoMonStr = hanChuNhiemBoMon.toLocaleDateString('en-CA') + " " + hanChuNhiemBoMon.toLocaleTimeString();
            $("#han_chu_nhiem_bo_mon_update").val(hanChuNhiemBoMonStr);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

function update() {
    let dotDangKyId = $("#id_dot_dang_ky_update").val();
    let ngayBatDauStr = $("#ngay_bat_dau_update").val();
    let tenDotDangKy = $("#ten_dot_dang_ky_update").val().trim();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngay_ket_thuc_update").val();
    let ngayKetThuc = new Date(ngayKetThucStr);
    let hanSinhVienStr = $("#han_sinh_vien_update").val();
    let hanSinhVien = new Date(hanSinhVienStr);
    let hanGiangVienStr = $("#han_giang_vien_update").val();
    let hanGiangVien = new Date(hanGiangVienStr);
    let hanChuNhiemBoMonStr = $("#han_chu_nhiem_bo_mon_update").val();
    let hanChuNhiemBoMon = new Date(hanChuNhiemBoMonStr);

    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanSinhVien"] = hanSinhVien.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanGiangVien"] = hanGiangVien.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanChuNhiemBoMon"] = hanChuNhiemBoMon.toLocaleString('en-CA', {hour12: false});

    let check = 0;
    if  (tenDotDangKy === "") {
        $("#errorMessageTenDotDangKyUpdate").text("Tên đợt đăng ký không được để trống");
        check += 1;
    } else if (tenDotDangKy.length > 100) {
        $("#errorMessageTenDotDangKyUpdate").text("Quá số ký cho phép");
        check += 1;
    } else {
        $("#errorMessageTenDotDangKyUpdate").text('');
    }

    if (ngayBatDauStr === "") {
        $("#errorMessageNgayBatDauUpdate").text("Ngày giờ bắt đầu không được để trống");
        check += 1;
    } else {
        $("#errorMessageNgayBatDauUpdate").text("");
    }

    if (ngayKetThucStr === "") {
        $("#errorMessageNgayKetThucUpdate").text("Ngày giờ kết thúc không được để trống");
        check += 1;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#errorMessageNgayKetThucUpdate").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
        check += 1;
    } else {
        $("#errorMessageNgayKetThucUpdate").text("");
    }

    if (hanSinhVienStr === "") {
        check += 1;
        $("#errorMessageHanSinhVienUpdate").text("Hạn sinh viên không được để trống");
    } else if (hanSinhVien <= ngayBatDau || hanSinhVien >= ngayKetThuc) {
        $("#errorMessageHanSinhVienUpdate").text("Hạn sinh viên phải nằm trong thời gian của đợt đăng ký");
        check += 1;
    } else {
        $("#errorMessageHanSinhVienUpdate").text("");
    }

    if (hanGiangVienStr === "") {
        check += 1;
        $("#errorMessageHanGiangVienUpdate").text("Hạn giảng viên không được để trống");
    } else if (hanGiangVien <= ngayBatDau || hanGiangVien >= ngayKetThuc) {
        check += 1;
        $("#errorMessageHanGiangVienUpdate").text("Hạn giảng viên phải nằm trong thời gian của đợt đăng ký");
    } else if (hanGiangVien < hanSinhVien) {
        check += 1;
        $("#errorMessageHanGiangVienUpdate").text("Hạn giảng viên phải sau hạn sinh viên");
    } else {
        $("#errorMessageHanGiangVienUpdate").text("");
    }

    if (hanChuNhiemBoMonStr === "") {
        check += 1;
        $("#errorMessageHanChuNhiemBoMonUpdate").text("Hạn chủ nhiệm bộ môn không được để trống");
    } else if (hanChuNhiemBoMon <= ngayBatDau || hanChuNhiemBoMon >= ngayKetThuc) {
        check += 1;
        $("#errorMessageHanChuNhiemBoMonUpdate").text("Hạn chủ nhiệm bộ môn phải nằm trong thời gian của đợt đăng ký");
    } else if (hanChuNhiemBoMon < hanGiangVien) {
        check += 1;
        $("#errorMessageHanChuNhiemBoMonUpdate").text("Hạn chủ nhiệm bộ môn phải sau hạn giảng viên");
    } else {
        $("#errorMessageHanChuNhiemBoMonUpdate").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listDotDangKy = responseData.data;
            let size = listDotDangKy.length;
            for (let i = 0; i < size; i++) {
                if (dotDangKyId === listDotDangKy[i].id) {
                    continue;
                }
                if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listDotDangKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listDotDangKy[i].ngayBatDau) {
                        $("#errorMessageNgayBatDauUpdate").text("Thời gian bắt đầu trùng với: " + listDotDangKy[i].tenDotDangKy);
                    } else {
                        $("#errorMessageNgayKetThucUpdate").text("Thời gian kết thúc trùng với: " + listDotDangKy[i].tenDotDangKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check == 0) {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: dotDangKyApi + "/" + dotDangKyId,
            data: JSON.stringify(dotDangKyRequest),
            dataType: 'json',
            success: function () {
                $("#modal_update_dot_dang_ky").modal("hide");
                window.open(dotDangKyView, '_self');
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
};

function create() {
    let tenDotDangKy = $("#tenDotDangKyAdd").val().trim();
    let ngayBatDauStr = $("#ngayBatDauAdd").val();
    let ngayBatDau = new Date(ngayBatDauStr);
    let ngayKetThucStr = $("#ngayKetThucAdd").val();
    let ngayKetThuc = new Date(ngayKetThucStr);
    let hanSinhVienStr = $("#hanSinhVienAdd").val();
    let hanSinhVien = new Date(hanSinhVienStr);
    let hanGiangVienStr = $("#hanGiangVienAdd").val();
    let hanGiangVien = new Date(hanGiangVienStr);
    let hanChuNhiemBoMonStr = $("#hanChuNhiemBoMonAdd").val();
    let hanChuNhiemBoMon = new Date(hanChuNhiemBoMonStr);
    let hocKyId = $("#hocKyAdd").val();

    let dotDangKyRequest = {};
    dotDangKyRequest["tenDotDangKy"] = tenDotDangKy;
    dotDangKyRequest["ngayBatDau"] = ngayBatDau.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["ngayKetThuc"] = ngayKetThuc.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanSinhVien"] = hanSinhVien.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanGiangVien"] = hanGiangVien.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hanChuNhiemBoMon"] = hanChuNhiemBoMon.toLocaleString('en-CA', {hour12: false});
    dotDangKyRequest["hocKyId"] = hocKyId;

    let check = 0;
    if (hocKyId.length === 0) {
        $("#errorMessageAddHocKy").text("Mời chọn học kỳ");
        check += 1;
    } else {
        $("#errorMessageAddHocKy").text("");
    }

    if (tenDotDangKy.length === 0) {
        $("#errorMessageAddTenDot").text("Tên đợt đăng ký không được để trống");
        check += 1;
    } else if (tenDotDangKy.length < 6) {
        $("#errorMessageAddTenDot").text("Tên đợt đăng ký tối thiếu 6 ký tự");
        check += 1;
    } else if (tenDotDangKy.length > 100) {
        $("#errorMessageAddTenDot").text("Vượt quá số ký tự");
        check != 1;
    } else {
        $("#errorMessageAddTenDot").text("");
    }

    if (ngayBatDauStr === "") {
        $("#errorMessageAddNgayBatDau").text("Ngày giờ bắt đầu không được để trống");
        check += 1;
    } else {
        $("#errorMessageAddNgayBatDau").text("");
    }

    if (ngayKetThucStr === "") {
        $("#errorMessageAddNgayKetThuc").text("Ngày giờ kết thúc không được để trống");
        check += 1;
    } else if (ngayBatDau >= ngayKetThuc) {
        $("#errorMessageAddNgayKetThuc").text("Ngày giờ kết thúc phải sau ngày bắt đầu");
        check += 1;
    } else {
        $("#errorMessageAddNgayKetThuc").text("");
    }

    if (hanSinhVienStr === "") {
        check += 1;
        $("#errorMessageAddHanSinhVien").text("Hạn sinh viên không được để trống");
    } else if (hanSinhVien <= ngayBatDau || hanSinhVien >= ngayKetThuc) {
        $("#errorMessageAddHanSinhVien").text("Hạn sinh viên phải nằm trong thời gian của đợt đăng ký");
        check += 1;
    } else {
        $("#errorMessageAddHanSinhVien").text("");
    }

    if (hanGiangVienStr === "") {
        check += 1;
        $("#errorMessageAddHanGiangVien").text("Hạn giảng viên không được để trống");
    } else if (hanGiangVien <= ngayBatDau || hanGiangVien >= ngayKetThuc) {
        check += 1;
        $("#errorMessageAddHanGiangVien").text("Hạn giảng viên phải nằm trong thời gian của đợt đăng ký");
    } else if (hanGiangVien < hanSinhVien) {
        check += 1;
        $("#errorMessageAddHanGiangVien").text("Hạn giảng viên phải sau hạn sinh viên");
    } else {
        $("#errorMessageAddHanGiangVien").text("");
    }

    if (hanChuNhiemBoMonStr === "") {
        check += 1;
        $("#errorMessageAddHanChuNhiemBoMon").text("Hạn chủ nhiệm bộ môn không được để trống");
    } else if (hanChuNhiemBoMon <= ngayBatDau || hanChuNhiemBoMon >= ngayKetThuc) {
        check += 1;
        $("#errorMessageAddHanChuNhiemBoMon").text("Hạn chủ nhiệm bộ môn phải nằm trong thời gian của đợt đăng ký");
    } else if (hanChuNhiemBoMon < hanGiangVien) {
        check += 1;
        $("#errorMessageAddHanChuNhiemBoMon").text("Hạn chủ nhiệm bộ môn phải sau hạn giảng viên");
    } else {
        $("#errorMessageAddHanChuNhiemBoMon").text("");
    }

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi,
        dataType: 'json',
        async: false,
        success: function (responseData) {
            let listDotDangKy = responseData.data;
            let size = listDotDangKy.length;
            for (let i = 0; i < size; i++) {
                if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                    && ngayKetThuc.getTime() > listDotDangKy[i].ngayBatDau) {
                    if (ngayBatDau.getTime() < listDotDangKy[i].ngayKetThuc
                        && ngayBatDau.getTime() > listDotDangKy[i].ngayBatDau) {
                        $("#errorMessageAddNgayBatDau").text("Thời gian bắt đầu trùng với: " + listDotDangKy[i].tenDotDangKy);
                    } else {
                        $("#errorMessageAddNgayKetThuc").text("Thời gian kết thúc trùng với: " + listDotDangKy[i].tenDotDangKy);
                    }
                    check += 1;
                }
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

    if (check == 0) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: dotDangKyApi,
            data: JSON.stringify(dotDangKyRequest),
            dataType: 'json',
            success: function (responseData) {
                window.open(dotDangKyView, '_self');
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
}

function detail(dotDangKyid) {
    $("#modal_detail").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: dotDangKyApi + "/search/" + dotDangKyid,
        dataType: 'json',
        success: function (responseData) {
            var ngayBatDau = new Date(responseData.data.ngayBatDau);
            var ngayKetThuc = new Date(responseData.data.ngayKetThuc);
            var hanSinhVien = new Date(responseData.data.hanSinhVien);
            var hanGiangVien = new Date(responseData.data.hanGiangVien);
            var hanChuNhiemBoMon = new Date(responseData.data.hanChuNhiemBoMon);
            $("#detailTenHocKy").val(responseData.data.tenHocKy);
            $("#detailTenDotDangKy").val(responseData.data.tenDotDangKy);
            $("#detailNgayBatDau").val(ngayBatDau.toLocaleDateString() + " " + ngayBatDau.toLocaleTimeString());
            $("#detailNgayKetThuc").val(ngayKetThuc.toLocaleDateString() + " " + ngayKetThuc.toLocaleTimeString());
            $("#detailHanSinhVien").val(hanSinhVien.toLocaleDateString() + " " + hanSinhVien.toLocaleTimeString());
            $("#detailHanGiangVien").val(hanGiangVien.toLocaleDateString() + " " + hanGiangVien.toLocaleTimeString());
            $("#detailHanChuNhiemBoMon").val(hanChuNhiemBoMon.toLocaleDateString() + " " + hanChuNhiemBoMon.toLocaleTimeString());
            var tongSoSinhVien = responseData.data.tongSinhVien;
            $("#detailTongSoSinhVien").val(tongSoSinhVien == 0 ? 'Chưa có sinh viên trong đợt' : tongSoSinhVien + ' sinh viên');
            var tongSinhVienChuaCoNhom = responseData.data.tongSinhVienChuaCoNhom;
            $("#detailTongSoSinhVienChuaCoNhom").val(tongSoSinhVien == 0 ? 'Chưa có sinh viên trong đợt' :
                tongSinhVienChuaCoNhom == 0 ? 'Tất cả sinh viên đã có nhóm' : tongSinhVienChuaCoNhom + ' sinh viên');
            var tongSinhVienCoNhom = responseData.data.tongSinhVienCoNhom;
            $("#detailTongSoSinhVienCoNhom").val(tongSoSinhVien == 0 ? 'Chưa có sinh viên trong đợt' :
                tongSinhVienCoNhom == 0 ? 'Chưa có sinh viên nào tham gia nhóm' : tongSinhVienCoNhom + ' sinh viên');
            $("#detailTongSoNhom").val(responseData.data.tongSoNhom == 0 ? 'Chưa có nhóm nào được thành lập' : responseData.data.tongSoNhom);
            if (responseData.data.tongSoNhom == 0 || responseData.data.tiLeXacNhan == null) {
                $("#labelDetailTiLe").attr('style', "display: none");
            } else {
                $("#labelDetailTiLe").attr('style', "display: block");
                var tiLeXacNhan = responseData.data.tiLeXacNhan;
                $("#detailTiLeXacNhan").val(tiLeXacNhan == 0 ? 'Chưa có nhóm nào được xác nhận' : tiLeXacNhan + "%");
            }

            if (responseData.data.trangThai == 1) {
                $("#detailTrangThai").attr('style', 'color: #5867dd; font-weight: bold')
                $("#detailTrangThai").val("Mở");
            } else {
                $("#detailTrangThai").attr('style', 'color: red; font-weight: bold')
                $("#detailTrangThai").val("Đóng");
            }
            if (responseData.data.tongSinhVienChuaCoNhom == 0) {
                $("#btnShowSinhVienChuaCoNhom").attr('style', "display: none");
            } else {
                $("#btnShowSinhVienChuaCoNhom").attr('style', "display: block; line-height: 27px");
                $("#btnShowSinhVienChuaCoNhom").attr('href', "/dao-tao/sinh-vien?idTrangThaiNhom=b&idDotDangKy=" + dotDangKyid);
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

