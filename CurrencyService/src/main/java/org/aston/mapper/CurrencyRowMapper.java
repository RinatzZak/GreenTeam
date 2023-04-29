package org.aston.mapper;

import org.aston.model.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRowMapper implements RowMapper<Currency> {
    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency currency = new Currency();
        currency.setId(rs.getInt("id"));
        currency.setName(rs.getString("name"));
        currency.setCharCode(rs.getString("char_code"));
        currency.setNominal(rs.getString("nominal"));
        currency.setValue(rs.getString("value"));
        return currency;
    }
}
