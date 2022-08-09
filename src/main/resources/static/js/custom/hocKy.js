$(document).ready(function () {
    $("#hoc_ky_error").text("");
});

function clearData() {
    $("#keyword").val("");
}

$("#form_create_hoc_ky").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#tenHocKy").val();
    let hocKyRequest = {};
    hocKyRequest["tenHocKy"] = tenHocKy;

    if (tenHocKy.length === 0) {
        $("#hoc_ky_error").text("Tên học kỳ không được để trống");
    } else if (tenHocKy.length < 6) {
        $("#hoc_ky_error").text("Tên học kỳ tối thiếu 6 ký tự");
    } else {
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/hoc-ky/add",
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                parent.location.reload();
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalUpdateHocKy(hocKyId) {
    $("#modal_update_hoc_ky").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/hoc-ky/detail?id=" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function (data) {
            console.log(data)
            $("#id_hoc_ky_update").val(hocKyId);
            $("#ten_hoc_ky_update").val(data.tenHocKy);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_hoc_ky_update").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#ten_hoc_ky_update").val();
    let hocKyId = $("#id_hoc_ky_update").val();
    let hocKyRequest = {};
    hocKyRequest["tenHocKy"] = tenHocKy;
    console.log(hocKyRequest);
    if (tenHocKy.length === 0) {
        $("#errorMessageUpdate").text("Tên học kỳ không được để trống");
    } else if (tenHocKy.length < 6) {
        $("#errorMessageUpdate").text("Tên học kỳ tối thiếu 6 ký tự");
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/hoc-ky/update/" + hocKyId,
            data: JSON.stringify(hocKyRequest),
            dataType: 'json',
            success: function () {
                parent.location.reload();
                $("#modal_update_hoc_ky").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});

function openModalRemoveHocKy(hocKyId) {
    $("#modal_hoc_ky_remove").modal('show');
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/hoc-ky/detail?id=" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function () {
            $("#remove_hoc_ky").val(hocKyId);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

$("#form_hoc_ky_delete").submit(function (event) {
    event.preventDefault();
    let hocKyId = $("#remove_hoc_ky").val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/hoc-ky/remove/" + hocKyId,
        data: JSON.stringify(hocKyId),
        dataType: 'json',
        success: function () {
            parent.location.reload();
            $("#modal_update_hoc_ky").modal("hide");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

});