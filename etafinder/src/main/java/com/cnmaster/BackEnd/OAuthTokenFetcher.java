package com.cnmaster.BackEnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

public class OAuthTokenFetcher {
    private static final String API_KEY = "gFBcuu4RdHN9jm8BPc3k1XGtOSF75giV";
    private static final String API_SECRET = "C1NZPOrgSBEqfO7y";
    private static final String TOKEN_URL = "https://api.cn.ca/v1/oauth/jwt-token/accesstokenJWT?grant_type=client_credentials";

    public static String getAccessToken() throws Exception {
        // uri to url.
        URI uri = new URI(TOKEN_URL);
        URL url = uri.toURL();
        // Connect token with above url
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Build request:
        connection.setRequestMethod("POST");// post or POST all work
            // Content-Type->Http rq head
            //In this case we send application/x-www-form-urlencoded type of form
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // x-apikey->from api sample
        connection.setRequestProperty("x-apikey", API_KEY);

        // Creat Basic Authorization Header(Let them know I have access)
        //Form==> Authorization: Basic base64(username:password)
        String authValue = API_KEY + ":" + API_SECRET;
        String encodedAuString = Base64.getEncoder().encodeToString(authValue.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + encodedAuString);

        // Send request
        connection.setDoOutput(true);
        //tell HttpURLConnection send rq body "POST" data must change to "ture"
        // change from "{\"grant_type\":\"client_credentials\"}"; to
        //Above rqbody use for Content-Type: application/json
        //Because I used Content-Type: application/x-www-form-urlencoded  must use below rqBody type
        
        String requestBody = "grant_type=client_credentials";
        try (OutputStream os = connection.getOutputStream()) {
            //rq for the output steam
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            //string to UTF_8 avoid error
            os.write(input, 0, input.length);
            //write into rq body and send to server
        }

        // result
        int responseCode = connection.getResponseCode();
        //If success return 200
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return parseAccessToken(response.toString());
                //Get the token part as string
            }
        } else {
            throw new Exception("Failed to get token: " + responseCode);
        }

    }

    private static String parseAccessToken(String jsonResponse) {
        int startIdx = jsonResponse.indexOf("\"access_token\":\"") + 16;
        int endIdx = jsonResponse.indexOf("\"", startIdx);
        return jsonResponse.substring(startIdx, endIdx);
    }
}
