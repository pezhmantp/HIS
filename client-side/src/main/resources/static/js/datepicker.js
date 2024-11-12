 $(document).ready(
  function(){
   $("#dobInput").datepicker({
    changeYear: true,
    changeMonth: true,
    yearRange: "1925:2024",
    dateFormat: "yy-mm-dd"
  });
  }
 );
