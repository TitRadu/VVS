package com.example.vvsdemo;

import com.example.vvsdemo.entities.Piece;
import com.example.vvsdemo.exceptions.EmptyInputException;
import com.example.vvsdemo.exceptions.ResourceNotFoundDeleteException;
import com.example.vvsdemo.exceptions.NegativeInputException;
import com.example.vvsdemo.repositories.PiecesRepository;
import com.example.vvsdemo.services.PieceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PieceTests {
    @Autowired
    private PiecesRepository piecesRepository;

    @Autowired
    private PieceService pieceService ;

    @BeforeEach
    public void initDataBase(){
        piecesRepository.deleteAll();

    }

    @Test
    public void testListAllPiecesWhenDBIsEmpty(){
        List<Piece> pieces = pieceService.listAll();
        assertEquals(0,pieces.size());

    }

    @Test
    public void testListAllPiecesWhenDBIsNotEmpty(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));
        piecesRepository.saveAll(pieces);

        List<Piece> piecesList = pieceService.listAll();
        assertTrue((pieces.containsAll(piecesList) && piecesList.containsAll(pieces)));

    }


    @Test
    public void testAddPieceWhenDBIsEmpty() throws NegativeInputException, EmptyInputException {
        Piece piece = new Piece("Ulei motor","Castrol",100.50);
        Piece addedPiece;
        addedPiece = pieceService.add(piece);
        List<Piece> pieces = pieceService.listAll();
        assertNotNull(addedPiece);
        assertEquals(1,pieces.size());
        assertEquals(addedPiece, pieces.get(0));

    }

    @Test
    public void testAddPieceWhenDBIsNotEmpty() throws NegativeInputException, EmptyInputException {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));
        piecesRepository.saveAll(pieces);

        Piece piece = new Piece("Ulei motor", "Castrol", 100.50);
        pieces.add(piece);
        pieceService.add(piece);
        List<Piece> piecesList = pieceService.listAll();
        assertTrue((pieces.containsAll(piecesList) && piecesList.containsAll(pieces)));

    }


    @Test
    public void testAddPieceWithNegativePrice(){
        Piece piece = new Piece("Motor electric","Ford",-5.0);
        assertThrows(NegativeInputException.class,() -> pieceService.add(piece));

    }

    @Test
    public void testAddPieceWithEmptyName(){
        Piece piece = new Piece("","Ford",-5.0);
        assertThrows(EmptyInputException.class,() -> pieceService.add(piece));

    }

    @Test
    public void testAddPieceWithEmptyProducer(){
        Piece piece = new Piece("Motor electric","       ",-5.0);
        assertThrows(EmptyInputException.class,() -> pieceService.add(piece));

    }


    @Test
    public void testListFilterWhenPriceIsLessThanAll() throws NegativeInputException {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));

        piecesRepository.saveAll(pieces);

        double price = 300;
        List<Piece> piecesList;
        piecesList = pieceService.listFilter(price);

        assertEquals(0,piecesList.size());

    }

    @Test
    public void testListFilterWhenPriceIsBigThanAll() throws NegativeInputException {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));

        piecesRepository.saveAll(pieces);

        double price = 600;
        List<Piece> piecesList;
        piecesList = pieceService.listFilter(price);

        assertTrue((pieces.containsAll(piecesList) && piecesList.containsAll(pieces)));

    }

    @Test
    public void testListFilterWhenPriceIsInDataBase() throws NegativeInputException {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));

        piecesRepository.saveAll(pieces);

        double price = 500;
        List<Piece> piecesList;
        piecesList = pieceService.listFilter(price);

        pieces.remove(0);
        pieces.remove(0);
        assertTrue((pieces.containsAll(piecesList) && piecesList.containsAll(pieces)));

    }

    @Test
    public void testListFilterWithNegativePrice(){
        assertThrows(NegativeInputException.class,() -> pieceService.listFilter(-5.0));

    }

    @Test
    public void testRemovePieceWhenIdExist() throws ResourceNotFoundDeleteException {
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500.0));
        pieces.add(new Piece("Motor Diesel","Bosch",550.0));
        pieces.add(new Piece("Motor Otto","General Motors",450.0));

        piecesRepository.saveAll(pieces);

        Long id = pieces.get(0).getId();
        pieceService.remove(id);
        pieces.remove(0);

        List<Piece> piecesList = piecesRepository.findAll();

        assertTrue((pieces.containsAll(piecesList) && piecesList.containsAll(pieces)));

    }

    @Test
    public void testRemovePieceWhenIdNotExist(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(pieces);

        Long id = 50L;
        assertThrows(ResourceNotFoundDeleteException.class, () -> {pieceService.remove(id);});

    }

}

