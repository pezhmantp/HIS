var medicineList=[];
var medicineInfo;
$(document).ready(function (){


fetchPatientTests();
$(".alert").on("click",function(){
    $(".alert").addClass("invisible");
  });
$("#addNewMedicine").on('click',function(){
  $(".new-medicine *").removeAttr("disabled");
  $("#addNewMedicine").attr("disabled","disabled");
  $("#saveMedicine").removeAttr("disabled");

})
//  $("input").is("[disabled]").addClass("invisible");
  $("#testReqForm").submit(function(e) {
      e.preventDefault();
      var form = $(this);
      var actionUrl = form.attr('action');
      if($("#testType").val() == ""){
        $("#alertDiv").removeClass("invisible");
      }
      else{
      $(".alert").addClass("invisible");
        $.ajax({
        type: "POST",
        url: actionUrl,
        data: form.serialize(),
        success: function(data)
        {
        clearElements("tbody1");
        fetchPatientTests();
        },
        error: function(textStatus, errorThrown) {
        alert("err: " + textStatus + "-" +errorThrown);
        }
      });
      }
  });

  $("#sendPrescription").on('click',function (){

    $.ajax({
      data: JSON.stringify(medicineList),
      type: 'POST',
      url: '/visit/sendPrescription/'+visitId,
      contentType: "application/json",
      success: function(data)
      {
      if(data !=null || data !="")
      {
        alert("Prescription saved")
        $(".new-medicine *").attr("disabled","disabled");
        $("#medicineSelect").val("");
        $("#measurementUnit").val("");
        $("#dosage").val("");
        $("#frequency").val("");
        $("#noOfDays").val("");
        $("#instruction").val("");
        $("#saveMedicine").attr("disabled","disabled");
        $("#addNewMedicine").removeAttr("disabled");
        $("#sendPrescription").removeAttr("disabled");
        medicineList.length=0;
        showSelectedMedicines();
      }
      },
      error: function(textStatus, errorThrown) {
      alert("err: " + JSON.stringify(textStatus) + "-" +JSON.stringify(errorThrown));
      }
    });
  });

  $("#saveMedicine").on('click',function(){
  var medicineExists=false;
  for(let j=0;j<medicineList.length;j++){
    if(medicineList[j].medicineName == $("#medicineSelect").val())
    {
      medicineExists=true;
    }
  }
  if(!medicineExists && $("#medicineSelect").val() !="" && $("#dosage").val() !="" && $("#measurementUnit").val() !="")
  {
  medicineList.push({
        "medicineName":$("#medicineSelect").val(),
        "dosage":$("#dosage").val(),
        "measurementUnit":$("#measurementUnit").val(),
        "frequency":$("#frequency").val(),
        "noOfDays":$("#noOfDays").val(),
        "instruction":$("#instruction").val()
      });
      showSelectedMedicines();
  //    $("#selectedMedicinesDiv").append("<div class='col'>")
      $(".new-medicine *").attr("disabled","disabled");
      $("#medicineSelect").val("");
      $("#measurementUnit").val("");
      $("#dosage").val("");
      $("#frequency").val("");
      $("#noOfDays").val("");
      $("#instruction").val("");
      $("#saveMedicine").attr("disabled","disabled");
      $("#addNewMedicine").removeAttr("disabled");
      $("#sendPrescription").removeAttr("disabled");

  }
  else{
    alert("Something is wrong");
  }


//    alert(medicineList.length);
  })





function fetchPatientTests(){
var url = "/visit/getTestsByVisitId/"+visitId;
$.ajax({
      type: "GET",
      url: url,
      dataType: 'json',
      success: function(data)
      {
      if(data.length > 0)
      generateTestTable(data);
      },
      error: function(textStatus, errorThrown){
        console.log(textStatus + " " + errorThrown)
      }
      });
}

function clearElements(element){
  $("#"+element).empty();
}

function removeTest(testId,testType){
  $.ajax({
    url:"/visit/removeTest",
    type: "GET",
//    dataType: "json",
    data: { 'testId' : testId, 'testType' : testType },
    success: function(data)
    {
//      alert("removed: ");
      clearElements("tbody1");
      fetchPatientTests();
    },
    error: function(textStatus, errorThrown){
        console.log(textStatus + " " + errorThrown)
    }
  });
}

$('input[type=text]:disabled').css({
   "background-color": "white"
});
});

function showSelectedMedicines(){
$("#selectedMedicinesDiv").empty();
medicineList.map((i,j)=>{
  $("#selectedMedicinesDiv").append("<div class='col'>" +
        "<button id='"+medicineList[j].medicineName+"' onclick=deleteSelectedMedicine('"+ medicineList[j].medicineName +"') type='button' class='btn btn-outline-success'>" +
        "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-x-circle' viewBox='0 0 16 16'><path d='M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16'/><path d='M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708'/></svg>"
         + medicineList[j].medicineName
         + "</button>"
         + "</div>");
})


//for(let i=0;i<medicineList.length;i++)
//    {
//      $("#selectedMedicinesDiv").append("<div class='col'>" +
//      "<button id='"+medicineList[i].medicineName+"' onclick=deleteSelectedMedicine('"+ medicineList[i].medicineName +"') type='button' class='btn btn-outline-success'>" +
//      "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-x-circle' viewBox='0 0 16 16'><path d='M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16'/><path d='M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708'/></svg>"
//       + medicineList[i].medicineName
//       + "</button>"
//       + "</div>");
//    }


}

function deleteSelectedMedicine(medicineName){


medicineList.map((i,j)=>{
  if(i.medicineName == medicineName)
  delete medicineList[j];
})
 showSelectedMedicines();
}

function generateTestTable(data){
//  var table = $('<table>').addClass('foo');
$('#testTableDiv').removeClass("invisible");


  for(i=0; i<data.length; i++){
////////////////////////////////////////////////
    trow = "<tr><td>" + data[i].visitId + "</td>" +
    "<td>" + data[i].testId + "</td>" +
    "<td>" + data[i].testType + "</td>" +
    "<td>" + data[i].status + "</td>";
//
  if(data[i].status == "ready")
  {
   trow = trow + "<td><input type='button' name="+ data[i].testId +" onclick=fetchTest('"+ data[i].testId +"','"+ data[i].testType +"') value='Show' class='btn btn-link'/></td>";
  }
  else{
   trow = trow + "<td><input type='button' name="+ data[i].testId +" onclick=fetchTest('"+ data[i].testId +"','"+ data[i].testType +"') disabled value='Show' class='btn btn-link'/></td>";
  }
  trow = trow + "<td><input type='button' name="+ data[i].testId +" onclick=removeTest('"+data[i].testId+"','"+data[i].testType+"') value='Remove' class='btn btn-link text-danger'/></td>";
//  trow = trow + "<td><input type='button' value='Remove' class='btn btn-link text-danger'/></td>";

    trow= trow + "</tr>";

    $('#tbody1').append(trow);
    //////////////////////////////////
//    $('#removeTestForm').append("</form>");
//    $('#tbody1').append("</form>");
  }
}


function fetchTest(testId,testType){
  $.ajax({
    url:"/visit/getTestResult",
    type: "GET",
    dataType: "json",
    data: { 'testId' : testId, 'testType' : testType },
    success: function(data)
    {
    generateTestResultModal(data);
//      alert("removed: ");
      console.log("data: " + JSON.stringify(data));
    },
    error: function(textStatus, errorThrown){
        console.log(textStatus + " " + errorThrown)
    }
  });
}

function generateTestResultModal(data){
var visitId="visitId";
var type="type";
var status="status";
var className="className";
delete data[visitId];
delete data[status];
delete data[type];
delete data[className];
const keys = Object.keys(data);
$("#testResultModal").removeClass("invisible");
    for(var j=0;j<keys.length;j=j+3){
    $("#testResultModalContent").append("<div id='"+j+"' class='row row-cols-2 row-cols-sm-3 mt-4'>");
     for(var k=j;k<j+3;k++){
         if(k==keys.length)
         {
         break;
         }
     const key = keys[k];
         $("#"+j).append("<div class='col'><label for='"+k+"' class='form-label fw-bold'>"+key+"</label>" +
         "<input type='text' id='"+k+"' class='form-control' disabled value='"+data[key]+"'/></div>");
      }
    }
    $("#testResultModalContent").append("<input type='button' onclick='closeTestResultModal()' class='btn btn-primary mt-4' value='Close'/>");



}

function closeTestResultModal(){
  clearElements("testResultModalContent");
  $("#testResultModal").addClass("invisible");
}