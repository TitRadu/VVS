package com.example.vvsdemo.services;

import com.example.vvsdemo.exceptions.ResourceNotFoundDeleteException;
import com.example.vvsdemo.repositories.PiecesRepository;
import com.example.vvsdemo.entities.Piece;
import com.example.vvsdemo.exceptions.EmptyInputException;
import com.example.vvsdemo.exceptions.NegativeInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PieceService {
    private final PiecesRepository piecesRepository;

    @Autowired
    public  PieceService(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    public List<Piece> listAll(){
        return piecesRepository.findAll();

    }

    public List<Piece> listFilter(Double price) throws NegativeInputException {
        if (price <= 0) {
            throw new NegativeInputException("Price can not be negative or zero!");
        }

        return piecesRepository.findByPriceIsLessThanEqual(price);

    }

    public Piece add(Piece entity) throws NegativeInputException, EmptyInputException {
        if(entity.getPieceName().matches("^( )*$")){
            throw new EmptyInputException("Piece input is empty!");

        }

        if(entity.getProducer().matches("^( )*$")){
            throw new EmptyInputException("Producer input is empty!");

        }

        if(entity.getPrice() == null){
            throw new NegativeInputException("Price input is empty!");

        }

        if (entity.getPrice() <= 0) {
            throw new NegativeInputException("Price can not be negative or zero!");

        }

        return piecesRepository.save(entity);

    }


    public void remove(Long id) throws ResourceNotFoundDeleteException {
        if(!piecesRepository.findById(id).isPresent()){
            throw new ResourceNotFoundDeleteException("Resource not found for {" + id + "}!");

        }
        
        piecesRepository.deleteById(id);

    }

}
