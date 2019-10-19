package org.aayush.service;

import org.aayush.dao.InMemoryCardTrxRepository;
import org.aayush.exception.InsufficientCardBalanceException;
import org.aayush.exception.MetroException;
import org.aayush.exception.MinimumCardBalanceException;
import org.aayush.model.CardTransaction;
import org.aayush.model.SmartCard;
import org.aayush.model.Station;
import org.aayush.strategy.FareStrategyFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MetroService {
    private ConcurrentMap<Station, AtomicInteger> stationFootFall = new ConcurrentHashMap<>();

    private InMemoryCardTrxRepository trxRepository = new InMemoryCardTrxRepository();
    private FareCalculator fareCalculator = new FareCalculator();

    public void swipeIn(SmartCard card, Station source, LocalDateTime dateTime) throws MetroException {
        if (card.getBalance() < 5.5) {
            throw new MinimumCardBalanceException("Minimum balance of Rs 5.5 is required at Swipe In");
        }
        stationFootFall.putIfAbsent(source, new AtomicInteger());
        stationFootFall.get(source).incrementAndGet();
        CardTransaction trx = new CardTransaction();
        trx.setSource(source);
        trx.setCard(card);
        trx.setStartDateTime(dateTime);
        trxRepository.addTransientTrx(card, trx);
    }

    public void swipeOut(SmartCard card, Station destination, LocalDateTime dateTime) throws MetroException {
        stationFootFall.putIfAbsent(destination, new AtomicInteger());
        stationFootFall.get(destination).incrementAndGet();
        CardTransaction trx = trxRepository.getTransientTrx(card);
        trx.setDestination(destination);
        trx.setEndDateTime(dateTime);
        trx.setDistance(destination.distance(trx.getSource()));
        trx.setFare(fareCalculator.getFare(trx.getSource(), trx.getDestination(), dateTime));
        if (trx.getFare() > card.getBalance()) {
            throw new InsufficientCardBalanceException("Insufficient balance at Swipe Out, Please recharge card and try again");
        }
        trx.setFareStrategyUsed(FareStrategyFactory.getFareStrategy(dateTime));
        trx.setBalance(card.getBalance() - trx.getFare());
        card.setBalance(card.getBalance() - trx.getFare());
        trxRepository.addCompletedTrx(card, trx);
    }


    public int calculateFootFall(Station station) {
        return stationFootFall.getOrDefault(station, new AtomicInteger(0)).get();
    }

    public List<CardTransaction> cardReport(SmartCard card) {
        List<CardTransaction> trxs = trxRepository.getCompletedTrxs(card);
        trxs.forEach(trx -> {
            System.out.println("trx = " + trx);
        });
        return trxs;
    }
}
