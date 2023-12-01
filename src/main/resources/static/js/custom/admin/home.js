let viewHome = "/admin/bit-dev";
let apiHome = "/api/admin/bit-dev";

function openModalHistoryDownload() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiHome + "/download-log-500",
        success: function (responseData) {
            var blob = new Blob([responseData], {type: "text/csv"});
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "log_bug_500.csv";
            document.body.appendChild(a);
            a.click();

            window.URL.revokeObjectURL(url);
        },
        error: function (e) {
            if (e.status === 404) {
                bootbox.alert({
                    message: "Không tìm thấy file trong hệ thống",
                    backdrop: true
                });
            } else {
                console.error("Lỗi khi tải file CSV: " + e.statusText);
            }
        }
    });
}

function deleteFileLog500() {
    bootbox.confirm({
        message: "Bạn có chắc muốn xóa file log 500 không ?",
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            }, confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (item) {
            if (item) {
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: apiHome + "/delete-log-500",
                    success: function (responseData) {
                        bootbox.alert({
                            message: "Xóa thành công file log 500",
                            backdrop: true
                        });
                    },
                    error: function (e) {
                        if (e.status === 404) {
                            bootbox.alert({
                                message: "Không tìm thấy file trong hệ thống",
                                backdrop: true
                            });
                        } else {
                            console.error("Lỗi khi tải file CSV: " + e.statusText);
                        }
                    }
                });
            }
        }
    })
}

function openModalHistoryDownloadBadRequest() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiHome + "/download-log-bad-request",
        success: function (responseData) {
            var blob = new Blob([responseData], {type: "text/csv"});
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "log_bug_bad_request.csv";
            document.body.appendChild(a);
            a.click();

            window.URL.revokeObjectURL(url);
        },
        error: function (e) {
            if (e.status === 404) {
                bootbox.alert({
                    message: "Không tìm thấy file trong hệ thống",
                    backdrop: true
                });
            } else {
                console.error("Lỗi khi tải file CSV: " + e.statusText);
            }
        }
    });
}

function deleteFileLogBadRequest() {
    bootbox.confirm({
        message: "Bạn có chắc muốn chốt đề tài không?",
        buttons: {
            cancel: {
                className: 'btn-outline-secondary btn btn-light',
                label: '<i class="fa fa-times"></i> Quay lại'
            }, confirm: {
                label: '<i class="fa fa-check"></i> Xác nhận'
            }
        },
        callback: function (item) {
            if (item) {
                $.ajax({
                    type: "DELETE",
                    contentType: "application/json",
                    url: apiHome + "/delete-log-bad-request",
                    success: function (responseData) {
                        bootbox.alert({
                            message: "Xóa thành công file log bad request",
                            backdrop: true
                        });
                    },
                    error: function (e) {
                        if (e.status === 404) {
                            bootbox.alert({
                                message: "Không tìm thấy file trong hệ thống",
                                backdrop: true
                            });
                        } else {
                            console.error("Lỗi khi tải file CSV: " + e.statusText);
                        }
                    }
                });
            }
        }
    })
}
