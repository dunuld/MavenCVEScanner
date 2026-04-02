package com.cvescanner.service;

import com.cvescanner.data.Dependency;
import com.cvescanner.http.HttpResponseData;
import com.cvescanner.http.IHttpProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CveServiceImpl implements ICveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CveServiceImpl.class);
    private static final String NVD_URL =
            "https://services.nvd.nist.gov/rest/json/cves/2.0?keywordSearch=";

    private final IHttpProvider httpProvider;
    private final ObjectMapper objectMapper;

    public CveServiceImpl() {
        this(IHttpProvider.defaultProvider(), new ObjectMapper());
    }

    public CveServiceImpl(IHttpProvider httpProvider, ObjectMapper objectMapper) {
        this.httpProvider = httpProvider;
        this.objectMapper = objectMapper;
    }

    @Override
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

            HttpResponseData response = httpProvider.send(request);

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
            LOGGER.error("Error while querying NVD API: {}",  e.getMessage());
        }

        return results;
    }
}
