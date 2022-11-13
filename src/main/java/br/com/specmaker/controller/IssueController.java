package br.com.specmaker.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
public class IssueController {

    @Value("${teste}")
    private String variavel;

    @GetMapping
    public String getTest(@RequestParam String var ){
        return "Esse é o parametro >>"
                .concat(var)
                .concat("\nEssa é a variavel: >> ")
                .concat(variavel);
    }
}
