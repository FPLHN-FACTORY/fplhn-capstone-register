$(document).ready(function () {
    $("#hoc_ky_error").text("");
});
$("#form_create_hoc_ky").submit(function (event) {
    event.preventDefault();
    let tenHocKy = $("#tenHocKy").val();
    let createHocKyRequest = {};
    createHocKyRequest["tenHocKy"] = $("#tenHocKy").val();

    if (tenHocKy.length === 0) {
        $("#hoc_ky_error").text("Tên học kỳ không được để trống");
    } else if (tenHocKy.length < 6) {
        $("#hoc_ky_error").text("Tên học kỳ tối thiếu 6 ký tự");
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/hoc-ky/add",
            data: JSON.stringify(createHocKyRequest),
            dataType: 'json',
            success: function (data) {
                $("#modal_create").modal("hide");
            },
            error: function (e) {
                console.log("ERROR : ", e);
            }
        });
    }
});


