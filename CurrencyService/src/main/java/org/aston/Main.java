package org.aston;

import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.parser.ParseXMLImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ParseXML parseXML = new ParseXMLImpl();
        List<Currency> currencies = parseXML.parseCurrenciesFromCBR();
        System.out.println(currencies);
    }
}