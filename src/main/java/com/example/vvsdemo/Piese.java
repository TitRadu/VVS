package com.example.vvsdemo;

public class Piese {
    private String numePiesa;
    private String numeProducator;
    private double pretBucata;

    public Piese(){}

    public Piese(String numePiesa, String numeProducator, double pretBucata) {
        this.numePiesa = numePiesa;
        this.numeProducator = numeProducator;
        this.pretBucata = pretBucata;
    }

    public String getNumePiesa() {
        return numePiesa;
    }

    public void setNumePiesa(String numePiesa) {
        this.numePiesa = numePiesa;
    }

    public String getNumeProducator() {
        return numeProducator;
    }

    public void setNumeProducator(String numeProducator) {
        this.numeProducator = numeProducator;
    }

    public double getPretBucata() {
        return pretBucata;
    }

    public void setPretBucata(int pretBucata) {
        this.pretBucata = pretBucata;
    }

}