package org.aston.web;

import org.aston.model.Currency;

import javax.jws.WebParam;
import jakarta.jws.WebService;

@WebService(serviceName = "CurrencySoapController", targetNamespace = "http://web.aston.org")
public interface CurrencySoapController {

    Currency getByCharCode(@WebParam String charCode);

    String convertToRubFromAnother(@WebParam String charCode, @WebParam String value);

    String convertToUSDOrEURFromRub(@WebParam String charCode, @WebParam String valueOfRub);
}
