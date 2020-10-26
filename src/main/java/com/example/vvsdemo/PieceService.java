package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceService {
    private PiecesRepository piecesRepository;

    @Autowired
    public  PieceService(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    public List<Piece> listAll(){
        return piecesRepository.findAll();

    }

    public List<Piece> listFilter(double price){
        return piecesRepository.findByPriceLessThan(price);

    }

    public void add(Piece entity){
        piecesRepository.save(entity);

    }

    public void remove(Long id){
        piecesRepository.deleteById(id);

    }

}
