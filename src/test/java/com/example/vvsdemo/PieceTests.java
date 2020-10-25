package com.example.vvsdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PieceTests {
    @Autowired
    private PiecesRepository piecesRepository;

    @Test
    public void testAddPiece(){
        Piece piece = new Piece("Ulei motor","Castrol",100.50);
        Piece addedPiece = null;
        addedPiece = piecesRepository.save(piece);

        assertNotNull(addedPiece);

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsLessThanAll(){
        double price = 300;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(0,piecesList.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsBigThanAll(){
        double price = 600;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(3,piecesList.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsInDataBase(){
        double price = 500;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(1,piecesList.size());

    }

    @Test
    public void testDeletePieceByIdWhenIdExist(){
        Long id = new Long(1);
        piecesRepository.deleteById(id);

        assertEquals(Optional.empty(),piecesRepository.findById(id));

    }

    @Test
    public void testDeletePieceByIdWhenIdNotExist(){
        Long id = new Long(5);
        assertThrows(EmptyResultDataAccessException.class, () -> {piecesRepository.deleteById(id);});

    }

}

