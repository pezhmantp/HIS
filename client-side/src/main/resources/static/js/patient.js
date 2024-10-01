$(document).ready(function() {
var visited="";
var receptionId="";
var formPopulated=false;
    if(!(statusCode == null || statusCode == ""))
       {
        $('#alertDiv').removeClass("invisible");
        ("#alertDiv").text(message);
         if(statusCode == 201)
           {
           $(".reception-element").removeAttr("disabled");
           $("#alertDiv").addClass("alert-success");
           $("#alertDiv").removeClass("alert-warning");
           }
         else
           {
           $("#alertDiv").removeClass("alert-success");
           $("#alertDiv").addClass("alert-warning");
           }
         }

        $('#nationalIdInput').focusout(function (){
          var len = $('#nationalIdInput').val();
          if($('#nationalIdInput').val() != "" )
          {
           $.ajax({
           type: 'GET',
           url: '/patient/getPatient/'+$('#nationalIdInput').val(),
           dataType: 'json',
           success: function(result,textStatus) {
           formPopulated = true;
           if(result.receptionResponseEntity.reception != null)
           {
           receptionIsOpen(result.receptionResponseEntity.reception,
           result.patientResponseEntity.patient.firstName + " " + result.patientResponseEntity.patient.lastName);
           }
           else{
           patientExistsFunc(result.patientResponseEntity);
           }
           },
           error: function(jqXHR, textStatus, errorThrown) {
            if(formPopulated)
            {
            formPopulated=false;
            clearForm();
            }
           }
           });
          }
         });

$("#closeReceptionPopUpBtn").on("click",function(){
$(".patient-input").val("");
$("#openReceptionModal").addClass("invisible");
})

function receptionIsOpen(result, fullName)
{
visited=result.visitStatus;
receptionId=result.receptionId;
$("#openReceptionModal").removeClass("invisible");
$( "#receptionRow > :nth-child(1)").text(result.receptionId);
$( "#receptionRow > :nth-child(2)").text(result.patientId);
$( "#receptionRow > :nth-child(3)").text(fullName);
$( "#receptionRow > :nth-child(4)").text(result.visitStatus);
$( "#receptionRow > :nth-child(5)").text(result.receptionStatus);
}

$("#markRecpAsCmpltd").on("click",function (){
   if(visited != "visited"){
      alert("Visit process is not completed yet!");
   }
   else{
      markReceptionAsCompletedFunc();
   }
});

function markReceptionAsCompletedFunc()
{
  $.ajax({
    type: 'GET',
    url: '/reception/changeReceptionStatusToCompleted/'+receptionId,
    dataType: 'json',
    success: function(result,textStatus) {
      alert("Reception status changed to completed")
      $(".patient-input").val("");
      $("#openReceptionModal").addClass("invisible");
      },
      error: function(jqXHR, textStatus, errorThrown) {
      console.log("err: " + textStatus + "-" +errorThrown);
      }
      });
}

function clearForm(){
  $(".patient-input").val("");
  $("#patientIdInput").val("");
  $('#savePatientBtn').removeAttr("disabled");
  $('#continueBtn').addClass("invisible");
  $('#alertDiv').addClass("invisible");
  $('#alertDiv').text("");
}

function patientExistsFunc(result){
      $("#nationalIdInput").val(result.patient.nationalId);
      $("#firstNameInput").val(result.patient.firstName);
      $("#lastNameInput").val(result.patient.lastName);
      $("#dobInput").val(result.patient.dob);
      $("#genderInput").val(result.patient.gender);
      $("#emailInput").val(result.patient.email);
      $("#provinceInput").val(result.patient.address.province);
      $("#cityInput").val(result.patient.address.city);
      $("#postalCodeInput").val(result.patient.address.postalCode);
      $("#restOfAddressInput").val(result.patient.address.restOfAddress);
      $("#phoneNumberInput").val(result.patient.phones[0].phoneNumber);
      $('#patientIdInput').val(result.patient.patientId);
      $('.reception-element').removeAttr("disabled");
      $('#alertDiv').text(result.message);
      $('#savePatientBtn').attr("disabled","disabled");
      $("#alertDiv").removeClass("alert-success");
      $("#continueBtn").removeClass("invisible");
      $("#alertDiv").addClass("alert-warning");
      $('#alertDiv').removeClass("invisible");
    }
  });





