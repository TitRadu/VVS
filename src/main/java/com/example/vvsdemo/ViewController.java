package com.example.vvsdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


@Controller
public class ViewController {
    private PiecesRepository piecesRepository;

    public ViewController(PiecesRepository piecesRepository){
        this.piecesRepository = piecesRepository;

    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("datetime",new Date());
        model.addAttribute("username","Tit Radu-Dorin");
        model.addAttribute("mode","production");

        return "index";

    }

    @RequestMapping("/new")
    public String newPiece(Model model){
        Piece piece = new Piece();
        model.addAttribute("piece",piece);
        model.addAttribute("datetime",new Date());
        model.addAttribute("username","Tit Radu-Dorin");
        return "new_piece";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String addPiece(@ModelAttribute("piece") Piece piece){
        piecesRepository.save(piece);

        return "redirect:/";
    }

}
