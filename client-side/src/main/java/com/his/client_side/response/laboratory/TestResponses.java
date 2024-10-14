package com.his.client_side.response.laboratory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResponses {
    private List<TestResponse> testResponses;
}
