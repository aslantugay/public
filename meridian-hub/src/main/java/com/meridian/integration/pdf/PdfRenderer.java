package com.meridian.integration.pdf;

public interface PdfRenderer {

    byte[] render(String html);
}
