package org.aston.web;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.aston.model.Currency;
import org.aston.service.CurrencyService;
import org.aston.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


@WebService
@RequiredArgsConstructor
public class CurrencySoapController {

    private final CurrencyService currencyService;

    @WebMethod
    public Currency getByCharCode(String charCode) {
        return currencyService.getByCharCode(charCode);
    }

    public void publishEndpoint(String url) {
       Endpoint.publish(url, this);
        System.out.println("CurrencySoapController start on " + url);
    }
}
