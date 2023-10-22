let viewHome = "/admin/bit-dev";
let apiHome = "/api/admin/bit-dev";

function openModalHistoryDownload() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: apiHome + "/download-log",
        success: function (responseData) {
            var blob = new Blob([responseData], {type: "text/csv"});
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement("a");
            a.href = url;
            a.download = "nhom_datn.csv";
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