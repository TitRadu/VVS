package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder  implements CommandLineRunner {
    private PiecesRepository piecesRepository;

    @Autowired
    public DatabaseSeeder(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    @Override
    public void run(String... strings) throws Exception {
        List<Piece> piese = new ArrayList<Piece>();

        piese.add(new Piece("Motor electric","Ford",500));
        piese.add(new Piece("Motor Diesel","Bosch",550));
        piese.add(new Piece("Motor Otto","General Motors",450));

        piecesRepository.saveAll(piese);
    }

}
