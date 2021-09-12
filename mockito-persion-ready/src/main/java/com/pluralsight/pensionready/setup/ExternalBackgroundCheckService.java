package com.pluralsight.pensionready.setup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalBackgroundCheckService implements BackgroundCheckService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SERVICE_URL = "https://100.101.102.104/check";

    public BackgroundCheckResults confirm(String firstName, String lastName, String taxId, LocalDate dob) throws IOException {
        URL url = new URL(SERVICE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        StringBuffer sb = new StringBuffer();
        String result;

        InputStream is = new BufferedInputStream(con.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        result = sb.toString();

        System.out.println(result);
        return objectMapper.readValue(result, BackgroundCheckResults.class);
    }
}
