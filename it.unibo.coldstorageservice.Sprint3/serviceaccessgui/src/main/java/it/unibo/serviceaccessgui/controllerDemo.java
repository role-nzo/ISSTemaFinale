package it.unibo.serviceaccessgui;

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

    private Interaction coapconn;
    public static final int cssPort           = 8022;
    private ServiceAccessUseCase serviceAccessUseCase = new ServiceAccessUseCase();


    @PostConstruct
    public void init(){
        serviceAccessUseCase.init();
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("arg", appName);
        return "welcome";
    }

    @PostMapping("/newticket")
    public ResponseEntity<String> newTicket(Model model, @RequestParam String requestFw) throws Exception {

        String ticket = serviceAccessUseCase.newTicket(requestFw);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/showticket")
    public ResponseEntity<String> showticket(Model model, @RequestParam String requestTicket, @RequestParam String requestShowTicketFw) throws IOException{

        String esito = serviceAccessUseCase.showticket(requestTicket, requestShowTicketFw);

        //model.addAttribute("arg", appName);
        //model.addAttribute("showticket",esito);
        return new ResponseEntity<>(esito, HttpStatus.OK);
    }



    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
                "HIControllerDemo ERROR " + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }

}
