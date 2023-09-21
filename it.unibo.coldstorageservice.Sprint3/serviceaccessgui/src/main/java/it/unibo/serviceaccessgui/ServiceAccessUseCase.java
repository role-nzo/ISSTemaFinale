package it.unibo.serviceaccessgui;

import org.springframework.web.bind.annotation.RequestParam;
import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.utils.CommSystemConfig;
import unibo.basicomm23.utils.CommUtils;

import java.io.*;
import java.net.Socket;

public class ServiceAccessUseCase implements ServiceAccessUseCaseInterface {

    private Socket client;
    private Interaction coapconn;
    private int cssPort = 8022;

    public void init() {
        System.out.println("init");
        try {
            CommSystemConfig.tracing = true;
            String ctxqakdest       = "ctxcoldstorageservice";
            String qakdestination 	= "coldstorageservice";
            String addr = "127.0.0.1";
            String path   = ctxqakdest+"/"+qakdestination;  //COAP observable resource => basicrobot

            CoapConnection planexecconn = new CoapConnection(addr+":"+cssPort, ctxqakdest+"/statusservice" );
            planexecconn.observeResource( new ColdRoomCoapObserver() );

            client = new Socket("localhost", 8022);


        }catch(Exception e){
            CommUtils.outred("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }


    @Override
    public String showticket(String requestTicket, String requestShowTicketFw) throws IOException {

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //invio elabTicketRequest
        out.write("msg(storefood,request,tester,coldstorageservice,storefood("+requestTicket+","+requestShowTicketFw+"),13)\n");
        out.flush();
        String response = "";

        //verifica ticket accepted
        response = in.readLine();
        String esito = "Error";

        System.out.println(response);

        if(response.contains("storefoodaccepted")){

            //invio loadDone
            out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+requestShowTicketFw+"),14)\n");
            out.flush();
            //risposta chargetaken
            response = in.readLine();
            if(response.contains("chargetaken")){
                esito = "chargetaken";
            }

        }
        if (response.contains("storefoodrejected")){
            esito = "ticket rifiutato";
        }
        return esito;
    }

    @Override
    public String newTicket(String requestFw) throws IOException {


        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        out.write("msg(newticket,request,tester,coldstorageservice,newticket("+requestFw+"),12)\n");
        out.flush();

        String response = in.readLine();
        String ticket = "Error";
        if(response.contains("newticketaccepted")) {
            ticket = response.split(",")[4].split("\\(")[1].split("\\)")[0];
        }
        return  ticket;

    }
}
