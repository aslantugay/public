package com.meridian.integration.report;

public interface ReportShellGateway {

    String export(String sourceName, String format, String targetName);
}
