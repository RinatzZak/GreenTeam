package org.aston;

import jakarta.xml.ws.Service;
import org.aston.model.Currency;
import org.aston.web.CurrencySoapController;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws MalformedURLException {
        QName qName = new QName("http://web.aston.org", "CurrencySoapController");
        Service service = Service.create(new URL("http://localhost:8082/currency?wsdl"), qName);
        CurrencySoapController controller = service.getPort(CurrencySoapController.class);
        Currency currency = controller.getByCharCode("USD");
        String valueFromRubToUSD = controller.convertToUSDOrEURFromRub("USD", "10000");
        System.out.println(currency.getName());
        System.out.println(valueFromRubToUSD);
    }
}
