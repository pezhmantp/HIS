package com.his.client_side.service;

public interface VisitService {
   String createNewVisit(String receptionId,String jwtAccessToken);
   String getVisitIdByReceptionId(String receptionId,String jwtAccessToken);
}
