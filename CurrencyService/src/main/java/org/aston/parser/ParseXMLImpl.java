package org.aston.parser;

import lombok.extern.log4j.Log4j;
import org.aston.model.Currency;
import org.aston.util.exception.CurrencyParsingException;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j
public class ParseXMLImpl implements ParseXML {
    private List<Currency> currencies = new ArrayList<>();

    @Override
    public List<Currency> parseCurrenciesFromCBR(String parsePath) {
        log.info("Begin parse currencies from cbr");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(parsePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Valute");

            for (var valuteIdx = 0; valuteIdx < nodeList.getLength(); valuteIdx++) {
                var node = nodeList.item(valuteIdx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    LocalDate localDate = LocalDate.now();

                    String numCode = element.getElementsByTagName("NumCode").item(0).getTextContent();
                    String charCode = element.getElementsByTagName("CharCode").item(0).getTextContent();
                    String nominal = element.getElementsByTagName("Nominal").item(0).getTextContent();
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String value = element.getElementsByTagName("Value").item(0).getTextContent();

                    currencies.add(new Currency(numCode, charCode, nominal, name, value, localDate));
                }
            }
        } catch (ParserConfigurationException e) {
            log.error("xml parsing error");
            throw new CurrencyParsingException(e);
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
        return currencies;
    }
}
