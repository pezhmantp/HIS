package com.patient_management.patient_query_ms.handlers;

import com.patient_management.patient_core.model.Patient;
import com.patient_management.patient_core.queries.FindPatientByNationalIdQuery;
import com.patient_management.patient_core.repository.PatientRepository;
import com.patient_management.patient_core.responeObj.PatientResponse;
import org.axonframework.queryhandling.QueryHandler;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientQueryHandlerImpl implements PatientQueryHandler {
    private final PatientRepository patientRepository;

    private DozerBeanMapper mapper;
    private static final Logger log= LoggerFactory.getLogger(PatientQueryHandlerImpl.class);

    @Autowired
    public PatientQueryHandlerImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }


    @QueryHandler
    @Override
    public PatientResponse findPatientByNationalId(FindPatientByNationalIdQuery query) {
        log.info("findPatientByNationalId() -> received nationalId: " + query.getNationalId());
        Patient fetchedPatient=new Patient();
        try{
            fetchedPatient = patientRepository.findByNationalId(query.getNationalId());
        }
        catch (Exception e)
        {
            log.error("findPatientByNationalId() -> patientRepository.findByNationalId() : nationalId = " + query.getNationalId());
        }

        if (fetchedPatient != null) {
            log.info("findPatientByNationalId() -> patient fetched from patientRepository: " + fetchedPatient.toString());
            mapper = new DozerBeanMapper();
            try{
                Patient patient = mapper.map(fetchedPatient, Patient.class);
                log.info("findPatientByNationalId() -> patient mapped by DozerBeanMapper: " + patient.toString());
                PatientResponse patientResponse = new PatientResponse(patient,"Patient found");
                log.info("findPatientByNationalId() -> patientResponse initialized to return it to the caller: "
                        + patientResponse.toString());
                return patientResponse;
            }
            catch (Exception e)
            {
                log.error("Error at findPatientByNationalId() -> mapping operations: " + e.getMessage());
            }
        }
        log.info("findPatientByNationalId() -> patient not found: nationalId = " + query.getNationalId());
        return new PatientResponse(null,"Patient not found");
    }


}
