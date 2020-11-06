package com.example.vvsdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


@Controller
public class ViewController {
    private PieceService pieceService;

    @Autowired
    public ViewController(PieceService pieceService){
        this.pieceService = pieceService;

    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("datetime",new Date());
        model.addAttribute("username","Tit Radu-Dorin");

        return "index";

    }

    @RequestMapping("/new")
    public String newPiece(Model model){
        Piece piece = new Piece();
        model.addAttribute("piece",piece);
        model.addAttribute("datetime",new Date());
        model.addAttribute("username","Tit Radu-Dorin");
        model.addAttribute("priceWarning", "invisible");
        return "new_piece";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPiece(@ModelAttribute("piece") Piece piece, Model model){
        try {
            pieceService.add(piece);
        } catch (NegativeInputException e) {

        } catch(NullPointerException e)  {

        }

        return "redirect:/";

    }

}
