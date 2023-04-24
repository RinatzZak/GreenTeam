package org.aston.greenteam.gateway.goal;

import lombok.Getter;
import lombok.Setter;
import org.aston.greenteam.gateway.constant.bot.CurrencyEnum;

@Getter
@Setter
public class GoalModel {
    private Long id;
    private String text;
    private CurrencyEnum currency;
    private Long price;
    private Long userId;


    public GoalModel() {
    }

    @Override
    public String toString() {
        return "GoalModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", currency=" + currency +
                ", price=" + price +
                ", userId=" + userId +
                '}';
    }
}
