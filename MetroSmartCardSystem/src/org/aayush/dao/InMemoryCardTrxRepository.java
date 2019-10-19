package org.aayush.dao;

import org.aayush.model.CardTransaction;
import org.aayush.model.SmartCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryCardTrxRepository {

    private ConcurrentMap<SmartCard, CardTransaction> transientTrxStore = new ConcurrentHashMap<>();
    private ConcurrentMap<SmartCard, List<CardTransaction>> completedTrxStore = new ConcurrentHashMap<>();


    public void addCompletedTrx(SmartCard card, CardTransaction trx) {
        completedTrxStore.putIfAbsent(card, new ArrayList<>());
        completedTrxStore.get(card).add(trx);
    }

    public void addTransientTrx(SmartCard card, CardTransaction trx) {
        transientTrxStore.put(card, trx);
    }

    public CardTransaction getTransientTrx(SmartCard card) {
        return transientTrxStore.remove(card);
    }

    public List<CardTransaction> getCompletedTrxs(SmartCard card) {
        return completedTrxStore.getOrDefault(card, Collections.emptyList());
    }
}
