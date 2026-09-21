package com.acme.platform.integration.xml;

import com.acme.platform.domain.Firm;
import com.acme.platform.domain.Unit;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Parses the legacy on-prem export format (firm + units) into domain objects.
 */
@Component
public class XmlFirmImporter {

    public Firm parse(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(
                    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            Element root = doc.getDocumentElement();
            Firm firm = new Firm();
            firm.setName(textOf(root, "name"));
            firm.setTaxNumber(textOf(root, "taxNumber"));
            firm.setBillingEmail(textOf(root, "billingEmail"));

            NodeList unitNodes = root.getElementsByTagName("unit");
            for (int i = 0; i < unitNodes.getLength(); i++) {
                Element unitEl = (Element) unitNodes.item(i);
                Unit unit = new Unit();
                unit.setName(unitEl.getAttribute("name"));
                unit.setCostCenter(unitEl.getAttribute("costCenter"));
                unit.setFirm(firm);
                firm.getUnits().add(unit);
            }
            return firm;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid firm import document", e);
        }
    }

    private String textOf(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) {
            return null;
        }
        return nodes.item(0).getTextContent();
    }
}
