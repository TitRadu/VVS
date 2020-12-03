package com.example.vvsdemo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pieceName;
    private String producer;
    private Double price;

    public Piece() {
    }

    public Piece(Long id, String pieceName, String producer, double price) {
        this.id = id;
        this.pieceName = pieceName;
        this.producer = producer;
        this.price = price;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return id != null && Double.compare(piece.price, price) == 0 &&
                Objects.equals(id, piece.id) &&
                Objects.equals(pieceName, piece.pieceName) &&
                Objects.equals(producer, piece.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pieceName, producer, price);
    }

}
