package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class PieceController {
    private PiecesRepository piecesRepository;

    @Autowired
    public PieceController(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Piece> getAllPieces(){
        return piecesRepository.findAll();

    }

    @RequestMapping(value = "/lessThan/{pret}", method = RequestMethod.GET)
    public List<Piece> getPieceByPrice(@PathVariable double pret){
       return piecesRepository.findByPriceLessThan(pret);

    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public List<Piece> addPiece(@RequestBody Piece piece){
        piecesRepository.save(piece);

        return piecesRepository.findAll();

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public List<Piece> remove(@PathVariable long id){
        piecesRepository.deleteById(id);

        return piecesRepository.findAll();

    }

}
