package org.aston.greenteam.gateway.saving;

import lombok.Getter;
import lombok.Setter;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;

@Getter
@Setter
public class SavingModel {
    private Long id;
    private CurrencyEnum currency;
    private Double sum;
    private Long userId;

    public SavingModel() {
    }

    @Override
    public String toString() {
        return "SavingModel{" +
                "id=" + id +
                ", currency=" + currency +
                ", sum=" + sum +
                ", userId=" + userId +
                '}';
    }
}
