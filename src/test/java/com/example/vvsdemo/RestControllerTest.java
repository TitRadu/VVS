package com.example.vvsdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestControllerTest {
    @Autowired
    private PiecesRepository piecesRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void initServerURL() {
        piecesRepository.deleteAll();

    }

    @Test
    public void whenGetAllPiecesWithEmptyDB_thenReturn200AndCorrectResponse() {
        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/all",HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(0, response.getBody().size());

    }


    @Test
    public void whenGetAllPieces_thenReturn200AndCorrectResponse() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/all", HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(pieces) && pieces.containsAll(responsePiecesList)));

    }

   /* @Test
    public void whenGetFilterPiecesWithoutPathVariable_thenReturn404() throws Exception {
       // ResponseEntity<List<Piece>> response = getPieceResponseEntity("/lessThan/",HttpMethod.GET);
       // assertEquals(HttpStatus.valueOf(404), response.getStatusCode());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/lessThan")).andExpect(status().isNotFound());

    }*/

    @Test
    public void whenGetFilterPiecesWithPriceLessThanAll_thenReturn200And0Elements() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/lessThan/200",HttpMethod.GET);
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

        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/lessThan/500",HttpMethod.GET);
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

        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/lessThan/700",HttpMethod.GET);
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(pieces) && pieces.containsAll(responsePiecesList)));


    }

 /*   @Test
    public void whenRemovePieceWithoutPathVariable_thenReturn404() throws Exception {
        //ResponseEntity<List<Piece>> response = getPieceResponseEntity("/remove",HttpMethod.DELETE);
        //assertEquals(HttpStatus.valueOf(404), response.getStatusCode());
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/remove")).andExpect(status().isNotFound());

    }*/

    @Test
    public void whenRemovePieceWithIdInDB_thenReturn202AndCorrectResponseBody() {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/remove/"+ pieces.get(0).getId(),HttpMethod.DELETE);
        List<Piece> expectedList = new ArrayList<>();
        expectedList.add(pieces.get(1));
        expectedList.add(pieces.get(2));

        List<Piece> responsePiecesList = response.getBody();
        assertTrue((responsePiecesList.containsAll(expectedList) && expectedList.containsAll(responsePiecesList)));

    }

    @Test
    public void whenRemovePieceWithIdNotInDB_thenException() throws Exception {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);
        assertThrows(NestedServletException.class,
                () -> {
                    this.mockMvc.perform(MockMvcRequestBuilders.delete("/remove/50"));
        });

    }

  /*  @Test
    public void test(){
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);
        ResponseEntity<List<Piece>> response = getPieceResponseEntity("/remove/"+ pieces.get(0).getId() ,HttpMethod.GET);

    }
*/


    private ResponseEntity<List<Piece>> getPieceResponseEntity(String url,HttpMethod method) {
        return testRestTemplate.exchange(url, method, null, new ParameterizedTypeReference<List<Piece>>() {
        });

    }

}
