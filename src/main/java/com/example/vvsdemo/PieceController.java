package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PieceController {
    private final PieceService pieceService;

    @Autowired
    public PieceController(PieceService pieceService){
        this.pieceService = pieceService;

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Piece> listAllPieces(){
        return pieceService.listAll();

    }

    @RequestMapping(value = "/lessThan/{price}", method = RequestMethod.GET)
    public List<Piece> listFilterPieces(@PathVariable Double price){
        try {
            return pieceService.listFilter(price);

        } catch (NegativeInputException e) {
            e.printStackTrace();

        }

        return null;

    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public List<Piece> removePiece(@PathVariable long id){
        pieceService.remove(id);

        return pieceService.listAll();

    }

}
