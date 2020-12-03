package com.example.vvsdemo.controllers;

import com.example.vvsdemo.exceptions.ResourceNotFoundDeleteException;
import com.example.vvsdemo.services.PieceService;
import com.example.vvsdemo.entities.Piece;
import com.example.vvsdemo.exceptions.NegativeInputException;
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
    public List<Piece> listFilterPieces(@PathVariable Double price) throws NegativeInputException {
        return pieceService.listFilter(price);

    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public List<Piece> removePiece(@PathVariable long id) throws ResourceNotFoundDeleteException {
        pieceService.remove(id);

        return pieceService.listAll();

    }

}
