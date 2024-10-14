$(document).ready(function (){
fetchPatientTests();
$(".alert").on("click",function(){
    $(".alert").addClass("invisible");
  });

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

});


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