package org.aston.repository;

import org.aston.model.Currency;
import org.aston.parser.ParseXML;
import org.aston.util.exception.GetCurrencyException;
import org.aston.util.exception.SaveAllException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

    /**
     * Method for getting currency by its char code
     *
     * @param charCode - incoming currency code
     * @param date     - incoming date of creation, localDate.now() by default
     * @return {@link Currency}
     */
    @Override
    public Currency getCurrencyByCharCodeAndDate(String charCode, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery(
                    "SELECT c FROM Currency c WHERE c.charCode = :charCode AND c.dateOfCreation = :date", Currency.class);
            query.setParameter("charCode", charCode);
            query.setParameter("date", date);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new GetCurrencyException("Error retrieving currency by char code and date", e);
        }
    }

    /**
     * Method for save parsing list of currencies
     *
     * @param currencies - incoming list with currencies
     */
    @Override
    public void saveAll(List<Currency> currencies) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            for (Currency currency : currencies) {
                session.persist(currency);
            }
            tx.commit();
        } catch (Exception e) {
            throw new SaveAllException("Error saving currencies", e);
        }
    }
}
