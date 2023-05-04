package org.aston.greenteam.gateway.converter;

import jakarta.xml.ws.Service;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;
import org.aston.web.CurrencySoapController;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ConverterSoapClient {
    private final CurrencySoapController controller;

    public ConverterSoapClient() {
        try {
            QName qName = new QName("http://web.aston.org", "CurrencySoapController");

            Service service = Service.create(new URL("http://currency-server:8082/currency?wsdl"), qName);
            //Service service = Service.create(new URL("http://localhost:8000/currency?wsdl"), qName);
            //Service service = Service.create(new URL("http://localhost:8082/currency?wsdl"), qName);
            //Service service = Service.create(new URL("http://0.0.0.0:8082/currency?wsdl"), qName);
            controller = service.getPort(CurrencySoapController.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException("converter soap controller constructor crushed", e);
        }
    }

    public ConvertingModel convert(ConvertingModel convertingModel) {
        CurrencyEnum baseCurr = convertingModel.getBaseCurr();
        CurrencyEnum quotedCurr = convertingModel.getQuotedCurr();
        Double baseCurrSum = convertingModel.getBaseCurrSum();
        String sum = "0";
        switch (baseCurr) {
            case RUB:
                sum = controller.convertToUSDOrEURFromRub(quotedCurr.getCurrencyName(), baseCurrSum.toString());
                break;
            default:
                sum = controller.convertToRubFromAnother(baseCurr.getCurrencyName(), baseCurrSum.toString());
                break;
        }
        Double quotedCurrSum = Double.parseDouble(sum);
        convertingModel.setQuotedCurrSum(quotedCurrSum);
        return convertingModel;
    }
}
