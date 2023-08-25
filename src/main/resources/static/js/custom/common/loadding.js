let loadHtml = `
<div class="spinner-overlay">
    <div class="spinner-border text-light" style="width: 3.5rem; height: 3.5rem;" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</div>`

$(document).ajaxStart(function() {
    $('body').append(loadHtml);
}).ajaxStop(function() {
    $('.spinner-overlay').remove();
});
