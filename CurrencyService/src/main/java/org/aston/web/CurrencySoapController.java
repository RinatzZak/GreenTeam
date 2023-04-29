package org.aston.web;

import org.aston.model.Currency;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class CurrencySoapController {

    @Autowired
    private CurrencyService currencyService;

    @WebMethod
    public Currency getByCharCode(String charCode) {
        return currencyService.getByCharCode(charCode);
    }

    public void publishEndpoint(String url) {
        Endpoint.publish(url, this);
        System.out.println("CurrencySoapController start on " + url);
    }
}
