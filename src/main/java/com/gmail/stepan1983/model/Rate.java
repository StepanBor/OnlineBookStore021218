package com.gmail.stepan1983.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Rate {
    private long timestamp;
//    private String date;

    private double USDUAH;
    private double USDUSD;
    private double USDEUR;
    private double USDRUB;

    @JsonProperty("quotes")
    Map<String,Double> quotes;

    public Rate(long timestamp, double USDUAH, double USDUSD, double USDEUR, double USDRUB) {
        this.timestamp = timestamp;
        this.USDUAH = USDUAH;
        this.USDUSD = USDUSD;
        this.USDEUR = USDEUR;
        this.USDRUB = USDRUB;
    }

    public Rate() {
    }

//    @JsonProperty("quotes")
    public void demarshalRates(Map<String,Double> quotes){
        this.USDUAH=quotes.get("USDUAN");
        this.USDUSD=quotes.get("USDUSD");
        this.USDEUR=quotes.get("USDEUR");
        this.USDRUB=quotes.get("USDRUB");
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getUSDUAH() {
        return USDUAH;
    }

    public void setUSDUAH(double USDUAH) {
        this.USDUAH = USDUAH;
    }

    public double getUSDUSD() {
        return USDUSD;
    }

    public void setUSDUSD(double USDUSD) {
        this.USDUSD = USDUSD;
    }

    public double getUSDEUR() {
        return USDEUR;
    }

    public void setUSDEUR(double USDEUR) {
        this.USDEUR = USDEUR;
    }

    public double getUSDRUB() {
        return USDRUB;
    }

    public void setUSDRUB(double USDRUB) {
        this.USDRUB = USDRUB;
    }

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "timestamp=" + timestamp +
                ", USDUAH=" + USDUAH +
                ", USDUSD=" + USDUSD +
                ", USDEUR=" + USDEUR +
                ", USDRUB=" + USDRUB +
                ", quotes=" + quotes +
                '}';
    }
}
