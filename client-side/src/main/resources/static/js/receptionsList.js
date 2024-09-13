$(document).ready(function(){
  $(".alert").on("click",function(){
    $(".alert").addClass("invisible");
  });
});

function deleteReceptionFunc(receptionId){
  var result= confirm("Are you sure?");
  if(result)
  {
     document.location.href = '/reception/delete/' + receptionId;
  }
}

function getVisitStatus(receptionId){
 $.ajax({
   type: 'GET',
   url: '/reception/getVisitStatus/'+receptionId,
   success: function(result,textStatus){
     if(result == false)
     {
       $("#visitErr").text("This reception has an open visit");
       $("#visitErr").removeClass("invisible");
     }
     else if(result == true){
     markReceptionAsCompletedFunc(receptionId);
     }
   },
   error: function(jqXHR, textStatus, errorThrown){
     alert("errorThrown");
   },
 });
}

function markReceptionAsCompletedFunc(receptionId)
{
  $.ajax({
    type: 'GET',
    url: '/reception/changeReceptionStatusToCompleted/'+receptionId,
    success: function(result) {
    document.location.href = '/reception/receptionsList';
    },
      error: function(jqXHR, textStatus, errorThrown) {
      console.log("err: " + textStatus + "-" +errorThrown);
      }
    });
}





