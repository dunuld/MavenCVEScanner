package com.cvescanner.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DependencyTest {

    @Test
    void testToString() {
        Dependency dep = new Dependency("org.example", "test-artifact", "1.0.0");
        assertEquals("org.example:test-artifact:1.0.0", dep.toString());
    }

    @Test
    void testRecordProperties() {
        Dependency dep = new Dependency("g", "a", "v");
        assertEquals("g", dep.groupId());
        assertEquals("a", dep.artifactId());
        assertEquals("v", dep.version());
    }
}
