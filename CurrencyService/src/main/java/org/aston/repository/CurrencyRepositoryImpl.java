package org.aston.repository;

import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {
    private final SessionFactory sessionFactory;

    private final ParseXML parseXML;

    public CurrencyRepositoryImpl(SessionFactory sessionFactory, ParseXML parseXML) {
        this.sessionFactory = sessionFactory;
        this.parseXML = parseXML;
    }

    @Override
    public Currency getCurrencyByCharCodeAndDate(String charCode, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery("SELECT c FROM Currency c WHERE c.charCode = :charCode AND c.dateOfCreation = :date", Currency.class);
            query.setParameter("charCode", charCode);
            query.setParameter("date", date);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving currency by char code and date", e);
        }
    }

    @Override
    public void saveAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            List<Currency> currencies = parseXML.parseCurrenciesFromCBR("https://cbr.ru/scripts/XML_daily.asp");
            for (Currency currency : currencies) {
                session.persist(currency);
            }
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving currencies", e);
        }
    }
}
