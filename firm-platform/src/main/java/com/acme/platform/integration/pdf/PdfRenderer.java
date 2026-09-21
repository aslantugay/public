package com.acme.platform.integration.pdf;

public interface PdfRenderer {

    byte[] render(String html);
}
