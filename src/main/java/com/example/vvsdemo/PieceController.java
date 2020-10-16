package com.example.vvsdemo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/pieces")
public class PieceController {
    private List<Piese> piese;

    public PieceController(){
        piese = new ArrayList<Piese>();

        piese.add(new Piese("Motor electric","Continental",500));
        piese.add(new Piese("Motor Diesel","Bosch",550));
        piese.add(new Piese("Motor Otto","General Motors",450));

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Piese> getAllPieces(){
        return piese;

    }

    @RequestMapping(value = "/maiIeftinDecat/{pret}", method = RequestMethod.GET)
    public List<Piese> getPieceByPrice(@PathVariable double pret){
        return piese.stream().filter(x-> x.getPretBucata() <= pret)
                .collect(Collectors.toList());

    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public List<Piese> addPiece(@RequestBody Piese piesa){
        piese.add(piesa);

        return piese;

    }

}
