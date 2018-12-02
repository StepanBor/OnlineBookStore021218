package com.gmail.stepan1983.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quotes {
    @JsonProperty("USDUAH")
    private double USDUAH;
    @JsonProperty("USDUSD")
    private double USDUSD;
    @JsonProperty("USDEUR")
    private double USDEUR;
    @JsonProperty("USDRUB")
    private double USDRUB;

    public Quotes(double USDUAH, double USDUSD, double USDEUR, double USDRUB) {
        this.USDUAH = USDUAH;
        this.USDUSD = USDUSD;
        this.USDEUR = USDEUR;
        this.USDRUB = USDRUB;
    }

    public Quotes() {
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

    @Override
    public String toString() {
        return "Quotes{" +
                "USDUAH=" + USDUAH +
                ", USDUSD=" + USDUSD +
                ", USDEUR=" + USDEUR +
                ", USDRUB=" + USDRUB +
                '}';
    }
}
