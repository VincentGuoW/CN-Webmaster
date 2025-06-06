package com.cnmaster;

import java.util.Map;

import com.cnmaster.BackEnd.EstimatedTime;
import com.cnmaster.BackEnd.EtaToMap;
import com.cnmaster.BackEnd.MapToString;
import com.cnmaster.BackEnd.OAuthTokenFetcher;
import com.cnmaster.FrontEnd.HomePage;

public class Main {
    public static void main(String[] args) throws Exception {
        //System.out.println(); //
        HomePage.Mainpage();
        
        String containerNumber = "EGHU9298856";
        String token = OAuthTokenFetcher.getAccessToken();
        String etaString = EstimatedTime.getETA(token, containerNumber);

        System.out.println(etaString);

        Map<String,Object> etaMap = EtaToMap.parseJsonToMap(etaString);
        MapToString.mapToString(etaMap);
         //*/
    }//

   
}