package com.cnmaster;

import com.cnmaster.BackEnd.EstimatedTime;
import com.cnmaster.BackEnd.OAuthTokenFetcher;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println();
        String containerNumber = "MEDU760965";
        String token = OAuthTokenFetcher.getAccessToken();
        String result = EstimatedTime.getETA(token, containerNumber);
        
        System.out.println(result);

    }
}