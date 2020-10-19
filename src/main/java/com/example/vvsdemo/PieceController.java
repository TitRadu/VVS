package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Pieces")
public class PieceController {
    private PiecesRepository piecesRepository;

    @Autowired
    public PieceController(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Piese> getAllPieces(){
        return piecesRepository.findAll();

    }

    @RequestMapping(value = "/maiIeftinDecat/{pret}", method = RequestMethod.GET)
    public List<Piese> getPieceByPrice(@PathVariable double pret){
       return piecesRepository.findByPretBucataLessThan(pret);

    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public List<Piese> addPiece(@RequestBody Piese piesa){
        piecesRepository.save(piesa);

        return piecesRepository.findAll();

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public List<Piese> remove(@PathVariable long id){
        piecesRepository.deleteById(id);

        return piecesRepository.findAll();

    }

}
