package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(name = "cve-scan", mixinStandardHelpOptions = true,
        description = "Scan pom.xml for vulnerable dependencies")
public class Main implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @CommandLine.Parameters(index = "0", description = "Path to pom.xml")
    private String pomPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    private PomParser parser = new PomParserImpl();
    private CveService cveService = new CveServiceImpl();

    public void setParser(PomParser parser) {
        this.parser = parser;
    }

    public void setCveService(CveService cveService) {
        this.cveService = cveService;
    }

    @Override
    public void run() {
        logger.info("Scanning: {}", pomPath);

        var dependencies = parser.parse(pomPath);

        for (Dependency dep : dependencies) {
            var vulnerabilities = cveService.check(dep);

            if (!vulnerabilities.isEmpty()) {
                logger.warn("[!] Found vulnerabilities for {}", dep);
                vulnerabilities.forEach(v ->
                        logger.warn("  -> {}", v));
            }
        }
    }
}