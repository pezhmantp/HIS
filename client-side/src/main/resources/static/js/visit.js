$(document).ready(function (){

alert(visitId);
  $("#testReqForm").submit(function(e) {

      e.preventDefault();

      var form = $(this);
      var actionUrl = form.attr('action');

      $.ajax({
          type: "POST",
          url: actionUrl,
          data: form.serialize(),
          success: function(data)
          {
            alert(data);
          }
      });

  });

});

//function addTestFunc(){
//  $.ajax({
//      type: 'POST',
//      url: '/reception/changeReceptionStatusToCompleted/'+receptionId,
//      dataType: 'json',
//      success: function(result,textStatus) {
//        alert("Reception status changed to completed")
//        $(".patient-input").val("");
//        $("#openReceptionModal").addClass("invisible");
//        },
//        error: function(jqXHR, textStatus, errorThrown) {
//        console.log("err: " + textStatus + "-" +errorThrown);
//        }
//        });
//}