package com.cvescanner.service;

import com.cvescanner.service.CveServiceImpl;
import com.cvescanner.service.ICveService;
import com.cvescanner.data.Dependency;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cvescanner.http.HttpResponseData;
import com.cvescanner.http.IHttpProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ICveServiceTest {

    @Mock
    private IHttpProvider httpProvider;

    private ICveService cveService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        cveService = new CveServiceImpl(httpProvider, objectMapper);
    }

    @Test
    void testCheckFoundVulnerabilities() throws IOException, InterruptedException {
        String jsonResponse = """
                {
                  "vulnerabilities": [
                    { "cve": { "id": "CVE-2024-1234" } },
                    { "cve": { "id": "CVE-2024-5678" } }
                  ]
                }
                """;

        when(httpProvider.send(any(HttpRequest.class)))
                .thenReturn(new HttpResponseData(200, jsonResponse));

        Dependency dep = new Dependency("org.example", "test-lib", "1.0.0");
        List<String> results = cveService.check(dep);

        assertEquals(2, results.size());
        assertTrue(results.contains("CVE-2024-1234"));
        assertTrue(results.contains("CVE-2024-5678"));
    }

    @Test
    void testCheckNoVulnerabilities() throws IOException, InterruptedException {
        String jsonResponse = "{ \"vulnerabilities\": [] }";

        when(httpProvider.send(any(HttpRequest.class)))
                .thenReturn(new HttpResponseData(200, jsonResponse));

        Dependency dep = new Dependency("org.example", "safe-lib", "1.0.0");
        List<String> results = cveService.check(dep);

        assertTrue(results.isEmpty());
    }

    @Test
    void testCheckHttpError() throws IOException, InterruptedException {
        when(httpProvider.send(any(HttpRequest.class)))
                .thenReturn(new HttpResponseData(500, ""));

        Dependency dep = new Dependency("org.example", "error-lib", "1.0.0");
        List<String> results = cveService.check(dep);

        assertEquals(1, results.size());
        assertTrue(results.get(0).contains("HTTP 500"));
    }
}
