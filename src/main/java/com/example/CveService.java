package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CveService {
    private static final Logger logger = LoggerFactory.getLogger(CveService.class);
    private static final String NVD_URL =
            "https://services.nvd.nist.gov/rest/json/cves/2.0?keywordSearch=";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> check(Dependency dep) {
        List<String> results = new ArrayList<>();

        try {
            String keyword = dep.artifactId();
            String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String url = NVD_URL + encodedKeyword;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                results.add("NVD API request failed with HTTP " + response.statusCode());
                return results;
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode vulnerabilities = root.path("vulnerabilities");

            for (JsonNode vulnerability : vulnerabilities) {
                JsonNode cve = vulnerability.path("cve");
                String cveId = cve.path("id").asText();

                if (!cveId.isEmpty()) {
                    results.add(cveId);
                }
            }

        } catch (Exception e) {
            logger.error("Error while querying NVD API: {}",  e.getMessage());
        }

        return results;
    }
}