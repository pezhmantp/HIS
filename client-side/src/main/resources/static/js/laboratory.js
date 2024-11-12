
function openTestResultForm(testId,testType,visitId){
  switch(testType){
    case "bloodTest" : {$("#bloodTestModal").removeClass("invisible");fillBloodTestInputs(testId,visitId);}
    break;
    case "urinalysisTest" : {$("#urinalysisTestModal").removeClass("invisible");fillUrinalysisTestInputs(testId,visitId);}
  }

}

function closeBloodTestModal(){
  $("#bloodTestModal").addClass("invisible")
}
function closeUrinalysisTestModal(){
  $("#urinalysisTestModal").addClass("invisible")
}
function fillBloodTestInputs(testId,visitId){
  $("#bloodTestIdInput").val(testId);
  $("#visitIdInput1").val(visitId);
}
function fillUrinalysisTestInputs(testId,visitId){
  $("#urinalysisTestIdInput").val(testId);
  $("#visitIdInput2").val(visitId);
}