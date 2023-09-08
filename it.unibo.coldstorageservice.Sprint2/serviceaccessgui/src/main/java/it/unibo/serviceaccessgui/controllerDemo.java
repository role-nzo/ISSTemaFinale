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


    @PostConstruct
    public void init(){
        System.out.println("init");
        try {
            CommSystemConfig.tracing = true;
            String ctxqakdest       = "ctxcoldstorageservice";
            String qakdestination 	= "coldstorageservice";
            String addr = "127.0.0.1";
            String path   = ctxqakdest+"/"+qakdestination;  //COAP observable resource => basicrobot

            CoapConnection planexecconn = new CoapConnection(addr+":"+cssPort, ctxqakdest+"/coldstorageservice" );
            planexecconn.observeResource( new ColdRoomCoapObserver() );



        }catch(Exception e){
            CommUtils.outred("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("arg", appName);
        return "welcome";
    }

    @PostMapping("/newticket")
    public ResponseEntity<String> newTicket(Model model, @RequestParam String requestFw) throws Exception {

        Socket client = new Socket("127.0.0.1", 8022);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        out.write("msg(newticket,request,tester,coldstorageservice,newticket("+requestFw+"),12)\n");
        out.flush();

        String response = in.readLine();
        String ticket = "Error";
        if(response.contains("newticketaccepted")) {
            ticket = response.split(",")[4].split("\\(")[1].split("\\)")[0];
        }

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/showticket")
    public ResponseEntity<String> showticket(Model model, @RequestParam String requestTicket, @RequestParam String requestShowTicketFw) throws IOException{

        Socket client = new Socket("localhost", 8022);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //invio elabTicketRequest
        out.write("msg(ticketrequest,request,tester,coldstorageservice,ticketrequest("+requestTicket+","+requestShowTicketFw+"),13)\n");
        out.flush();
        String response = "";

        //verifica ticket accepted
        response = in.readLine();
        String esito = "Error";

        System.out.println(response);

        if(response.contains("ticketaccepted")){

            //invio loadDone
            out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+requestShowTicketFw+"),14)\n");
            out.flush();
            //risposta chargetaken
            response = in.readLine();
            if(response.contains("chargetaken")){
                esito = "chargetaken";
            }

        }
        if (response.contains("ticketrejected")){
            esito = "ticket rifiutato";
        }

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
