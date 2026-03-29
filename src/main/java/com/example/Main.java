package com.example;

import picocli.CommandLine;

@CommandLine.Command(name = "cve-scan", mixinStandardHelpOptions = true,
        description = "Scan pom.xml for vulnerable dependencies")
public class Main implements Runnable {

    @CommandLine.Parameters(index = "0", description = "Path to pom.xml")
    private String pomPath;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Scanning: " + pomPath);

        PomParser parser = new PomParser();
        var dependencies = parser.parse(pomPath);

        CveService cveService = new CveService();

        for (Dependency dep : dependencies) {
            var vulnerabilities = cveService.check(dep);

            if (!vulnerabilities.isEmpty()) {
                System.out.println("[!] " + dep);
                vulnerabilities.forEach(v ->
                        System.out.println("  -> " + v));
            }
        }
    }
}