package it.unibo.servicestatusgui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommSystemConfig;
import unibo.basicomm23.utils.CommUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.Socket;

@Controller
@RequestMapping("/")
public class controllerDemo {

    @Value("${spring.application.name}")
    String appName;

    private ServiceStatusUseCaseInterface useCase;

    @PostConstruct
    public void init(){
        useCase = new ServiceStatusUseCase();
        useCase.init();
    }

    @GetMapping("/")
    public String homePage(Model model) throws IOException {
        model.addAttribute("arg", appName);
        return "welcome";
    }

    @GetMapping("/getmap")
    public ResponseEntity<String> getMap(Model model) throws IOException {
        useCase.getMap();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
                "HIControllerDemo ERROR " + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }

}
