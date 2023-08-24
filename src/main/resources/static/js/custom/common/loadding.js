let loadHtml = `
    <div class="spinner-overlay">
<div class="spinner-container">
  <div class="spinner">
   <div class="spinner">
    <div class="spinner">
      <div class="spinner">
        <div class="spinner">
            <div class="spinner"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
    </div>`

$(document).ajaxStart(function() {
    console.log('Ajax start');
    $('body').append(loadHtml);
}).ajaxStop(function() {
    console.log('Ajax stop');
    $('.spinner-overlay').remove();
});
