package com.cvescanner.data;

public record Dependency(String groupId, String artifactId, String version) {
    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }
}