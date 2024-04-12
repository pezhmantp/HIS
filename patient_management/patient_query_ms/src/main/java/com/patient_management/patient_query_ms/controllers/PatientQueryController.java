package com.patient_management.patient_query_ms.controllers;


import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.responeObj.PatientResponse;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patientQueries")
public class PatientQueryController {

    private QueryGateway queryGateway;

    @Autowired
    public PatientQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/byNationalId/{nationalId}")
    public ResponseEntity<PatientResponse> findPatientByNationalId(@PathVariable(value = "nationalId") String nationalId) {
        System.out.println("******/////////////////*****************//////////////");
        try {
            FindPatientByNationalIdQuery query = new FindPatientByNationalIdQuery(nationalId);
            System.out.println("From cntlr before " + nationalId);
            PatientResponse response = queryGateway.query(query, ResponseTypes.instanceOf(PatientResponse.class)).join();
            System.out.println("response : " + response);
            System.out.println("From cntlr after");
//            if (response == null || response.getUsers() == null) {
//                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
//            }

            return new ResponseEntity<PatientResponse>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get patient by personalId request";
            System.out.println(e.toString());

            return new ResponseEntity<PatientResponse>(new PatientResponse(null, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @GetMapping("/getUserIdByUsername/{username}")
//    public ResponseEntity<String> getUserIdByUsername(@PathVariable (value = "username") String username)
//    {
//        try {
//            FindUserByUsernameQuery query = new FindUserByUsernameQuery(username);
//            System.out.println("getUserIdByUsername From cntlr before " + username);
//            FindUserResponseMsg response = queryGateway.query(query, ResponseTypes.instanceOf(FindUserResponseMsg.class)).join();
//            System.out.println("getUserIdByUsername response : " + response);
//            System.out.println("getUserIdByUsername From cntlr after");
////            if (response == null || response.getUsers() == null) {
////                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
////            }
//
//            return new ResponseEntity<String>(response.getUser().getId(), HttpStatus.OK);
//        } catch (Exception e) {
//            var safeErrorMessage = "Failed to complete get user by getUserIdByUsername request";
//            System.out.println(e.toString());
//
//            return new ResponseEntity<String>((safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/")
//    public ResponseEntity<FindUserResponseMsg> getUsers()
//    {
//        try {
//            FindAllUsersQuery query = new FindAllUsersQuery();
//            System.out.println("From cntlr before ");
//            FindUserResponseMsg response = queryGateway.query(query, ResponseTypes.instanceOf(FindUserResponseMsg.class)).join();
//            System.out.println("response : " + response);
//            System.out.println("From cntlr after");
//
////            if (response == null || response.getUsers() == null) {
////                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
////            }
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            var safeErrorMessage = "Failed to complete get user by Username request";
//            System.out.println(e.toString());
//
//            return new ResponseEntity<>(new FindUserResponseMsg(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/getTst/{name}")
//    public ResponseEntity<?> getUsers(@PathVariable (value = "name") String name)
//    {
//        try {
//            FindTstQry query = new FindTstQry(name);
//            System.out.println("From cntlr before ");
//            FindtstMsg response = queryGateway.query(query, ResponseTypes.instanceOf(FindtstMsg.class)).join();
//            System.out.println("response : " + response);
//            System.out.println("From cntlr after");
//
////            if (response == null || response.getUsers() == null) {
////                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
////            }
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            var safeErrorMessage = "Failed to complete get user by Username request";
//            System.out.println(e.toString());
//
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
