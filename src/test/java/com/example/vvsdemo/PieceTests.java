package com.example.vvsdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PieceTests {
    @Autowired
    private PiecesRepository piecesRepository;

    @BeforeEach
    public void initDataBase(){
        piecesRepository.deleteAll();

    }

    @Test
    public void testAddPieceWhenDBIsEmpty(){
        Piece piece = new Piece("Ulei motor","Castrol",100.50);
        Piece addedPiece = null;
        addedPiece = piecesRepository.save(piece);
        List<Piece> pieces = piecesRepository.findAll();
        assertNotNull(addedPiece);
        assertEquals(1,pieces.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsLessThanAll(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        double price = 300;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(0,piecesList.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsBigThanAll(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        double price = 600;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(3,piecesList.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsInDataBase(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        double price = 500;
        List<Piece> piecesList = null;
        piecesList = piecesRepository.findByPriceLessThan(price);

        assertEquals(1,piecesList.size());

    }

    @Test
    public void testDeletePieceByIdWhenIdExist(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        Long id = new Long(17);
        piecesRepository.deleteById(id);

        assertEquals(Optional.empty(),piecesRepository.findById(id));
        assertEquals(2,piecesRepository.findAll().size());

    }

    @Test
    public void testDeletePieceByIdWhenIdNotExist(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        Long id = new Long(50);
        assertThrows(EmptyResultDataAccessException.class, () -> {piecesRepository.deleteById(id);});

    }

}

