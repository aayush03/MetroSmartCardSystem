package org.aayush.model;

import org.aayush.strategy.FareStrategy;

import java.time.LocalDateTime;

public class CardTransaction {

    long id;
    SmartCard card;

    Station source;
    Station destination;

    int distance;

    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    double balance;
    double fare;

    FareStrategy fareStrategyUsed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SmartCard getCard() {
        return card;
    }

    public void setCard(SmartCard card) {
        this.card = card;
    }

    public Station getSource() {
        return source;
    }

    public void setSource(Station source) {
        this.source = source;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public FareStrategy getFareStrategyUsed() {
        return fareStrategyUsed;
    }

    public void setFareStrategyUsed(FareStrategy fareStrategyUsed) {
        this.fareStrategyUsed = fareStrategyUsed;
    }
}
