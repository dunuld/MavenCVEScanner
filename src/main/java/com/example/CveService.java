package com.example;

import java.util.*;

public class CveService {

    public List<String> check(Dependency dep) {
        // TODO: Replace with real API call
        List<String> results = new ArrayList<>();

        // Fake example
        if (dep.artifactId().contains("spring")) {
            results.add("CVE-XXXX-1234 (Example vulnerability)");
        }

        return results;
    }
}