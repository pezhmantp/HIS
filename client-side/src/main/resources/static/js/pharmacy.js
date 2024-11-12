$(document).ready(function(){

 $.ajax({
  url: '/pharmacy/getAllMedicineRequests',
  type: 'GET',
  dataType: 'json',
  success: function(data)
  {
   generateMedicineRequestsTable(data);
  },
  error: function(textStatus, errorThrown){
    console.log(textStatus + " " + errorThrown)
   }
  })


  $("#addMedicineBtn").on('click',function (){
    $("#addMedicineModal").removeClass("invisible");
  })
  $("#saveMedicineBtn").on('click',function(){
  if($("#medicineNameInput").val() !="")
  {
  $.ajax({
        data:{'medicineName' : $("#medicineNameInput").val()},
        url: '/pharmacy/addNewMedicine',
        type: 'POST',
        success: function(data)
            {
            if(data != "" || data != null)
            {
              alert("Medicine saved successfully");
              $("#addMedicineModal").addClass("invisible");
            }
            },
            error: function(textStatus, errorThrown){
              console.log(textStatus + " " + errorThrown)
            }
      })
  }
  else{
  alert("Please enter the medicine name");
  }

  })
  $("#closeModal").on('click',function(){
    $("#addMedicineModal").addClass("invisible");
  })

function generateMedicineRequestsTable(data){
for(i=0; i<data.length; i++){
    trow = "<tr><td>" + data[i].visitId + "</td>" +
    "<td>" + data[i].medicineRequestId + "</td>" +
    "<td><input type='button' name="+ data[i].medicineRequestId +" onclick=fetchPrescription('"+ data[i].medicineRequestId +"') value='Open' class='btn btn-primary'/></td>";
    trow= trow + "</tr>";
    $('#tbody1').append(trow);
  }
  }

});
function generatePrescriptionTable(data){
//    console.log(">>: "+JSON.stringify(data[i]));
$("#mRTbody").empty();
    for(i=0; i<data.prescriptions.length; i++){
        trow = "<tr><td>" + data.prescriptions[i].medicineName + "</td>" +
        "<td>" + data.prescriptions[i].dosage  + "</td>" +
        "<td>" + data.prescriptions[i].measurementUnit + "</td>" +
        "<td>" + data.prescriptions[i].frequency  + "</td>" +
        "<td>" + data.prescriptions[i].noOfDays  + "</td>" +
        "<td>" + data.prescriptions[i].instruction  + "</td>";
        trow= trow + "</tr>";
        $('#mRTbody').append(trow);
      }
}
function closePrescriptionModal(){
  $("#medicineRequestModal").addClass("invisible");
}
function fetchPrescription(medicineRequestId){
$("#medicineRequestModal").removeClass("invisible");
  $.ajax({
    type:"GET",
    url:"/pharmacy/getMedicineRequest/"+medicineRequestId,
    dataType:"json",
    success: function(data)
    {
      generatePrescriptionTable(data);
    },
    error: function(textStatus, errorThrown){

    }
  });
}