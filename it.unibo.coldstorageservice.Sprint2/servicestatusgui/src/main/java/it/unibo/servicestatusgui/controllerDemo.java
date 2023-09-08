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

    private Interaction coapconn;
    public int cssPort           = 8022;


    @PostConstruct
    public void init(){
        System.out.println("init");
        try {
            CommSystemConfig.tracing = true;
            String ctxqakdest       = "ctxcoldstorageservice";
            String qakdestination 	= "coldstorageservice";

            String addr = "127.0.0.1";
            String path   = ctxqakdest+"/"+qakdestination;  //COAP observable resource => basicrobot
            coapconn                = new CoapConnection(addr+":"+cssPort, path);

            CoapConnection statusservicecconn = new CoapConnection(addr+":"+cssPort, ctxqakdest+"/statusservice" );
            statusservicecconn.observeResource( new StatusServiceCoapObserver() );


        }catch(Exception e){
            CommUtils.outred("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    @GetMapping("/")
    public String homePage(Model model) throws IOException {
        model.addAttribute("arg", appName);
        return "welcome";
    }

    @GetMapping("/getmap")
    public ResponseEntity<String> getMap(Model model) throws IOException {

        Socket client = new Socket("localhost", 8022);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //invio elabTicketRequest
        out.write("msg(sendmap,dispatch,tester,statusservice,sendmap(_),13)\n");
        out.flush();
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
