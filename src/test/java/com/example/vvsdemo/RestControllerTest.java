package com.example.vvsdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {
    @Autowired
    private PiecesRepository piecesRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String serverUrl;

    @BeforeEach
    public void initServerURL(){
        this.serverUrl = "http://localhost:" + port;
        piecesRepository.deleteAll();

    }

    @Test
    public void whenGetAllPiecesWithEmptyDB_thenReturn200AndCorrectResponse(){
        ResponseEntity<Piece[]> response = getPieceResponseEntity();
        assertEquals(HttpStatus.valueOf(200),response.getStatusCode());
        assertEquals(0,response.getBody().length);

    }



    @Test
    public void whenGetAllPieces_thenReturn200AndCorrectResponse(){
        List<Piece> pieces = new ArrayList<>();

        pieces.add(new Piece("Motor electric","Ford",500));
        pieces.add(new Piece("Motor Diesel","Bosch",550));
        pieces.add(new Piece("Motor Otto","General Motors",450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<Piece[]> response = getPieceResponseEntity();
        assertEquals(HttpStatus.valueOf(200),response.getStatusCode());
        assertEquals(3,response.getBody().length);

    }

    public void whenGetFilterPiecesWithoutInput_thenReturn404(){


    }

    private ResponseEntity<Piece[]> getPieceResponseEntity(){
        String endpointURL = UriComponentsBuilder.fromHttpUrl(serverUrl)
                .path("all")
                .toUriString();

        return testRestTemplate.getForEntity(endpointURL,Piece[].class);

    }


    private ResponseEntity<Piece[]> getPieceResponseEntityWithParam(String pathVariable){
        String endpointURL = UriComponentsBuilder.fromHttpUrl(serverUrl)
                .path("all")
                .query(pathVariable)
                .toUriString();

        return testRestTemplate.getForEntity(endpointURL,Piece[].class);

    }

}
