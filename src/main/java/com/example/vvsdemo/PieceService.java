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

    public List<Piece> listFilter(Double price) throws NegativeInputException {
        if (price <= 0) {
            throw new NegativeInputException();
        }

        return piecesRepository.findByPriceLessThan(price);

    }

    public Piece add(Piece entity) throws NegativeInputException {
        if (entity.getPrice() <= 0) {
            throw new NegativeInputException();
        }

        return piecesRepository.save(entity);

    }

    public void remove(Long id){
        piecesRepository.deleteById(id);

    }

}
