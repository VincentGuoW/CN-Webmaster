package com.cnmaster.BackEnd;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

public class EstimatedTime {
    public static String getETA(String token, String containerNumber)
            throws URISyntaxException, MalformedURLException, IOException {

        if (containerNumber.length() > 4 + 6) {
            containerNumber = containerNumber.substring(0, containerNumber.length() - 1);
        }

        String apiUrl = "https://api.cn.ca/customers/v1/shipments/tracking?equipmentIds=" +
                URLEncoder.encode(containerNumber, "UTF-8");

        URI uri = new URI(apiUrl);
        URL url = uri.toURL();

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return response.toString();
            }
        } else {
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                throw new IOException("Request error: " + responseCode + "\n" + errorResponse);
            }
        }

    }
}
