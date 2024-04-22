package com.reception_management.reception_core.responeObj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FetchedDoctor {
    private String doctorFN;
    private String doctorLN;
    private String doctorId;
}
