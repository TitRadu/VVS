package com.example.vvsdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {
    @Autowired
    private PiecesRepository piecesRepository;

    @LocalServerPort
    private int port;

    private String serverUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void initServerURL() {
        piecesRepository.deleteAll();
        this.serverUrl = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllPiecesWithEmptyDB_thenReturn200AndCorrectResponse() {
        ResponseEntity<List<Piece>> response = executePieceRequest("/all", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(0, response.getBody().size());

    }


    @Test
    public void whenGetAllPieces_thenReturn200AndCorrectResponse() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = executePieceRequest("/all", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(pieces) && pieces.containsAll(responsePiecesList)));

    }

    @Test
    public void whenGetFilterPiecesWithPriceLessThanAll_thenReturn200And0Elements() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = executePieceRequest("/lessThan/200", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<Piece> responsePiecesList = response.getBody();
        assertEquals(0,responsePiecesList.size());

    }

    @Test
    public void whenGetFilterPiecesWithPriceInDataBase_thenReturn200AndCorrectResponseBody() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = executePieceRequest("/lessThan/500", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<Piece> responsePiecesList = response.getBody();

        List<Piece> expectedList = new ArrayList<>();
        expectedList.add(pieces.get(2));
        assertTrue((responsePiecesList.containsAll(expectedList) && expectedList.containsAll(responsePiecesList)));


    }

    @Test
    public void whenGetFilterPiecesWithPriceBigThanAll_thenReturn200AndCorrectResponseBody() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = executePieceRequest("/lessThan/700", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(pieces) && pieces.containsAll(responsePiecesList)));


    }

    @Test
    public void whenGetFilterPiecesWithoutPathVariable_thenReturn404() {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executePieceRequest("/lessThan", HttpMethod.GET));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void whenRemovePieceWithIdInDB_thenReturn202AndCorrectResponseBody() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = executePieceRequest("/remove/" + pieces.get(0).getId(), HttpMethod.DELETE);
        List<Piece> expectedList = new ArrayList<>();
        expectedList.add(pieces.get(1));
        expectedList.add(pieces.get(2));

        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(expectedList) && expectedList.containsAll(responsePiecesList)));

    }

    @Test
    public void whenRemovePieceWithoutPathVariable_thenReturn404() throws Exception {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executePieceRequest("/remove", HttpMethod.DELETE));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void whenRemovePieceWithIdNotInDB_then500() throws Exception {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        HttpServerErrorException response = assertThrows(HttpServerErrorException.class, () -> executePieceRequest("/remove/500", HttpMethod.DELETE));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void whenRemovePieceWithIdInDBGetMethod_then405(){
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executePieceRequest("/remove/"+pieces.get(0).getId(), HttpMethod.GET));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }

    @Test
    public void whenRemovePieceWithNotInDBGetMethod_then405(){
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executePieceRequest("/remove/500", HttpMethod.GET));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }

    @Test
    public void test(){
        HttpServerErrorException response = assertThrows(HttpServerErrorException.class, () -> executePieceRequest("/lessThan/-500", HttpMethod.GET));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    private ResponseEntity<List<Piece>> executePieceRequest(String url, HttpMethod method) {
        return restTemplate.exchange(serverUrl + url, method, null, new ParameterizedTypeReference<List<Piece>>() {
        });

    }

}
