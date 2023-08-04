const totalPage = Number($("#totalPage").text());
const currentPage = Number($("#currentPage").text());
const targetLink = $("#targetLink").text();

function changePage() {
    if ( $("#inputPage").val().length != 0) {
        let input = parseInt($("#inputPage").val());
        if (input <= 0) {
            input = 1;
        }
        if (input > totalPage) {
            input = totalPage;
        }
        window.open(targetLink + '?page=' + (input - 1), '_self');
    }
}

function buttonChangePage(type) {
    if (type == 1) {
        if (currentPage == 0) {
            window.open(targetLink + '?page=' + (totalPage - 1), '_self');
        } else {
            window.open(targetLink + '?page=' + (currentPage - 1), '_self');
        }
    }
    if (type == 0) {
        if (currentPage == (totalPage - 1)) {
            window.open(targetLink + '?page=0', '_self');
        } else {
            window.open(targetLink + '?page=' + (currentPage + 1), '_self');
        }
    }
}