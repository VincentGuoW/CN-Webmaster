package com.cnmaster;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import com.cnmaster.BackEnd.EstimatedTime;
import com.cnmaster.BackEnd.EtaToMap;
import com.cnmaster.BackEnd.MapToString;
import com.cnmaster.BackEnd.OAuthTokenFetcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println();
        String containerNumber = "TRHU644101,MEDU760965,CBHU4191355";
        String token = OAuthTokenFetcher.getAccessToken();
        String etaString = EstimatedTime.getETA(token, containerNumber);

        //System.out.println(etaString);

        Map<String,Object> etaMap = EtaToMap.parseJsonToMap(etaString);
        MapToString.mapToString(etaMap);
    }
}