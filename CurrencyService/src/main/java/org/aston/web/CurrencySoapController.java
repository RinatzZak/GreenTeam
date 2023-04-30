package org.aston.web;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.aston.model.Currency;
import org.aston.service.CurrencyService;
import org.aston.util.exception.BadRequestForCharCodeException;


@Log4j
@WebService
@RequiredArgsConstructor
public class CurrencySoapController {

    private final CurrencyService currencyService;


    /**
     * Method for getting currency by its char code
     *
     * @param charCode - incoming currency code
     * @return {@link Currency}
     */
    @WebMethod
    public Currency getByCharCode(String charCode) {
        log.info("start getByCharCode for - " + charCode);
        return currencyService.getByCharCode(charCode);
    }


    /**
     * Method for converting from USD and EUR to rubles
     *
     * @param charCode - incoming currency code
     * @param value    - incoming value for converting
     * @return The string value of the conversion
     */
    @WebMethod
    public String convertToRubFromAnother(String charCode, String value) {
        log.info("start convert to rub from " + charCode);
        Currency currency = currencyService.getByCharCode(charCode);
        Double currencyValue = Double.valueOf(currency.getValue().replace(",", "."));
        Double requestValue = Double.valueOf(value.replace(",", "."));
        double response = currencyValue * requestValue;
        log.info("end convert to rub from " + charCode + " . Return: " + String.format("%.2f", response));
        return String.format("%.2f", response);
    }


    /**
     * Method for converting from RUB to EUR or USD
     *
     * @param charCode   - incoming currency code
     * @param valueOfRub - incoming value for converting
     * @return The string value of the conversion
     */
    @WebMethod
    public String convertToUSDOrEURFromRub(String charCode, String valueOfRub) {
        log.info("start convert to " + charCode + " from rub");
        Currency currency;
        switch (charCode) {
            case "USD":
                currency = currencyService.getByCharCode("USD");
                break;
            case "EUR":
                currency = currencyService.getByCharCode("EUR");
                break;
            default:
                throw new BadRequestForCharCodeException("Char code is not valid");
        }
        Double currencyValue = Double.valueOf(currency.getValue().replace(",", "."));
        Double requestValue = Double.valueOf(valueOfRub.replace(",", "."));
        double response = requestValue / currencyValue;
        log.info("end convert to " + charCode + " from rub. Return: "
                + charCode + " " + String.format("%.2f", response));
        return String.format("%.2f", response);
    }

    public void publishEndpoint(String url) {
        Endpoint.publish(url, this);
        System.out.println("CurrencySoapController start on " + url);
    }
}
