package com.example.vvsdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiecesRepository extends JpaRepository<Piece, Long> {
    List<Piece> findByPriceLessThan(double price);


}
