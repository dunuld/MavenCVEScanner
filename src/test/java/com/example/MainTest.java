package com.example;

import com.cvescanner.pomparser.IPomParser;
import com.cvescanner.service.ICveService;
import com.cvescanner.Main;
import com.cvescanner.data.Dependency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    private IPomParser parser;

    @Mock
    private ICveService cveService;

    @InjectMocks
    private Main main;

    @Test
    void testRunFlow() {
        Dependency dep = new Dependency("g", "a", "v");
        when(parser.parse(anyString())).thenReturn(List.of(dep));
        when(cveService.check(dep)).thenReturn(List.of("CVE-2024-0001"));

        CommandLine cmd = new CommandLine(main);
        cmd.execute("pom.xml");

        verify(parser).parse("pom.xml");
        verify(cveService).check(dep);
    }

    @Test
    void testRunFlowNoVulnerabilities() {
        Dependency dep = new Dependency("g", "a", "v");
        when(parser.parse(anyString())).thenReturn(List.of(dep));
        when(cveService.check(dep)).thenReturn(List.of());

        CommandLine cmd = new CommandLine(main);
        cmd.execute("my-pom.xml");

        verify(parser).parse("my-pom.xml");
        verify(cveService).check(dep);
    }
}
