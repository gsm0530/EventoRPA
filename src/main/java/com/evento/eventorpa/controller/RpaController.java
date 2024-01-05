package com.evento.eventorpa.controller;

import com.evento.eventorpa.AdminSelenium;
import com.evento.eventorpa.EventoSelenium;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RpaController {

    @GetMapping("/start")
    public String test() throws MalformedURLException {

        EventoSelenium evento = new EventoSelenium();
        evento.Start();

        return "Hello World!";
    }

    @GetMapping("/admin")
    public String test2() throws MalformedURLException {

        AdminSelenium evento = new AdminSelenium();
        evento.Start();

        return "Hello World!";
    }
}
