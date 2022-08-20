const  phanCongHuongDanApi = "/api/dao-tao/phan-cong-hong-dan";
const phanCongHuongDanView = document.URL;


function addNew(gianVienID) {
  let monDatnId = $("#getMonDatnId").attr('value');
  let phanCongRequest = {};
  phanCongRequest["giangVienId"] = gianVienID;
  phanCongRequest["monDatnId"] = monDatnId;
  $.ajax({
      type: "POST",
      contentType: "application/json",
      url: phanCongHuongDanApi,
      data: JSON.stringify(phanCongRequest),
      dataType: 'json',
      success: function () {
          window.open(phanCongHuongDanView, '_self');
          $("#modal_create").modal("hide");
      },
      error: function (e) {
          console.log("ERROR : ", e);
      }
  });
}

$("#form_add_phan_cong").submit(function (event) {
  event.preventDefault();
      let gianVienID = $(".giangVienId")
      
      let monDatnId = $("#getMonDatnId").attr('value');
      let phanCongRequest = {};
      
      for (const e of gianVienID) {
          phanCongRequest["monDatnId"] = monDatnId;
          phanCongRequest["giangVienId"] = e.getAttribute('value');
           $.ajax({
          type: "POST",
          contentType: "application/json",
          url: phanCongHuongDanApi,
          data: JSON.stringify(phanCongRequest),
          dataType: 'json',
          success: function () {
              window.open(phanCongHuongDanView, '_self');
              $("#modal_create").modal("hide");
          },
          error: function (e) {
              console.log("ERROR : ", e);
          }
          });
      }
});

function openModalRemoveHocKy(giangVienHuongDanId) {
   $("#modal_phan_cong_huong_dan_remove").modal('show');
   $.ajax({
       type: "GET",
       contentType: "application/json",
       url: phanCongHuongDanApi + "/" + giangVienHuongDanId,
       data: JSON.stringify(giangVienHuongDanId),
       dataType: 'json',
       success: function () {
           $("#remove_phan_cong_huong_dan").val(giangVienHuongDanId);
       },
       error: function (e) {
           console.log("ERROR : ", e);
       }
   });
}


$("#form_phan_cong_huong_dan_delete").submit(function (event) {
  event.preventDefault();
  let giangVienHuongDanId = $("#remove_phan_cong_huong_dan").val();

  $.ajax({
      type: "DELETE",
      contentType: "application/json",
      url: phanCongHuongDanApi + "/" + giangVienHuongDanId,
      data: JSON.stringify(giangVienHuongDanId),
      dataType: 'json',
      success: function () {
          window.open(phanCongHuongDanView, '_self');
          $("#modal_phan_cong_huong_dan_remove").modal("hide");
      },
      error: function (e) {
          console.log("ERROR : ", e);
      }
  });

});