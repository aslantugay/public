package com.acme.platform.integration.report;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Bridges to the platform's native reporting toolchain (wkhtmltopdf / gnuplot)
 * for exports that the JVM renderer does not support.
 */
@Service
public class ReportShellService {

    private final String wkhtmlBinary;

    public ReportShellService(@Value("${app.reports.wkhtml-binary}") String wkhtmlBinary) {
        this.wkhtmlBinary = wkhtmlBinary;
    }

    public String exportInvoice(Long invoiceId, String format, String targetName) {
        String command = wkhtmlBinary + " --format " + format
                + " /srv/reports/invoice-" + invoiceId + ".html"
                + " /srv/out/" + targetName;
        return runShell(command);
    }

    private String runShell(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append('\n');
                }
            }
            process.waitFor();
            return output.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Report export failed", e);
        }
    }
}
