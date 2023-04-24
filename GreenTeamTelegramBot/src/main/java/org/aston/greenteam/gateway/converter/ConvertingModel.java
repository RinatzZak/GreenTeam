package org.aston.greenteam.gateway.converter;

import lombok.Getter;
import lombok.Setter;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;

@Getter
@Setter
public class ConvertingModel {
    private CurrencyEnum baseCurr;
    private CurrencyEnum quotedCurr;
    private Long baseCurrSum;
    private Long quotedCurrSum;

    public ConvertingModel() {
    }

    @Override
    public String toString() {
        return "ConvertingModel{" +
                "baseCurr=" + baseCurr +
                ", quotedCurr=" + quotedCurr +
                ", baseCurrSum=" + baseCurrSum +
                ", quotedCurrSum=" + quotedCurrSum +
                '}';
    }
}
