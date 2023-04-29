package org.aston;

import org.aston.service.CurrencyService;
import org.aston.web.CurrencySoapController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;

public class Client {
    public static void main(String[] args) throws IOException, SOAPException {
        QName serviceName = new QName("http://web.aston.org/CurrencySoapControllerService");
        Service service = Service.create(new URL( "http://localhost:8083/currency?wsdl"), serviceName);
        CurrencySoapController ss = service.getPort(CurrencySoapController.class);
        System.out.println(ss.getByCharCode("USD"));
    }
}
