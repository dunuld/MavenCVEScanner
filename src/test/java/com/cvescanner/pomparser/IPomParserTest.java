package com.cvescanner.pomparser;

import com.cvescanner.data.Dependency;
import com.cvescanner.pomparser.IPomParser;
import com.cvescanner.pomparser.PomParserImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IPomParserTest {

    @TempDir
    Path tempDir;

    @Test
    void testParseValidPom() throws IOException {
        Path pomPath = tempDir.resolve("pom.xml");
        String content = """
                <project>
                    <dependencies>
                        <dependency>
                            <groupId>org.junit.jupiter</groupId>
                            <artifactId>junit-jupiter-api</artifactId>
                            <version>5.10.2</version>
                        </dependency>
                        <dependency>
                            <groupId>org.mockito</groupId>
                            <artifactId>mockito-core</artifactId>
                            <version>5.11.0</version>
                        </dependency>
                    </dependencies>
                </project>
                """;
        Files.writeString(pomPath, content);

        IPomParser parser = new PomParserImpl();
        List<Dependency> dependencies = parser.parse(pomPath.toString());

        assertEquals(2, dependencies.size());
        assertEquals("org.junit.jupiter", dependencies.get(0).groupId());
        assertEquals("junit-jupiter-api", dependencies.get(0).artifactId());
        assertEquals("5.10.2", dependencies.get(0).version());
        
        assertEquals("org.mockito", dependencies.get(1).groupId());
        assertEquals("mockito-core", dependencies.get(1).artifactId());
        assertEquals("5.11.0", dependencies.get(1).version());
    }

    @Test
    void testParseIncompleteDependency() throws IOException {
        Path pomPath = tempDir.resolve("invalid-pom.xml");
        String content = """
                <project>
                    <dependencies>
                        <dependency>
                            <groupId>only-group</groupId>
                        </dependency>
                    </dependencies>
                </project>
                """;
        Files.writeString(pomPath, content);

        IPomParser parser = new PomParserImpl();
        List<Dependency> dependencies = parser.parse(pomPath.toString());

        assertTrue(dependencies.isEmpty());
    }

    @Test
    void testParseNonExistentFile() {
        IPomParser parser = new PomParserImpl();
        List<Dependency> dependencies = parser.parse("non-existent-file.xml");
        assertTrue(dependencies.isEmpty());
    }
}
