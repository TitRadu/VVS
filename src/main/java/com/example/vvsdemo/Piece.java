package com.example.vvsdemo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String pieceName;
    private String producer;
    private double price;

    public Piece(){}

   /* public Piece(Long id, String pieceName, String producer, double price) {
        this.id =id;
        this.pieceName = pieceName;
        this.producer = producer;
        this.price = price;
    }*/

    public Piece(String pieceName, String producer, double price) {
        this.pieceName = pieceName;
        this.producer = producer;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
