package com.meridian.integration.pdf;

import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Renders an XHTML document to PDF. External resources referenced by the
 * document (images, stylesheets) are resolved during layout.
 */
@Component
public class FlyingSaucerPdfRenderer implements PdfRenderer {

    @Override
    public byte[] render(String html) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("PDF rendering failed", e);
        }
    }
}
