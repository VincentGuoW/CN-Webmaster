package com.cnmaster.BackEnd;

import java.io.*;
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
        // uri inclued url.
        URI uri = new URI(TOKEN_URL);
        URL url = uri.toURL();
        // Connect token url
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Build request:
        connection.setRequestMethod("POST");// post or POST all work
        // change from json to x-www-form-urlencoded
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("x-apikey", API_KEY);

        // Creat Basic Authorization Header
        String authValue = API_KEY + ":" + API_SECRET;
        String encodedAuString = Base64.getEncoder().encodeToString(authValue.getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", "Basic " + encodedAuString);

        // Send request
        connection.setDoOutput(true);
        // change from "{\"grant_type\":\"client_credentials\"}"; to
        String requestBody = "grant_type=client_credentials";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // result
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return parseAccessToken(response.toString());
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
