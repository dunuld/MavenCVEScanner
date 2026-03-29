package com.example;

public record Dependency(String groupId, String artifactId, String version) {
    @Override
    public String toString() {
        return groupId + ":" + artifactId + ":" + version;
    }
}