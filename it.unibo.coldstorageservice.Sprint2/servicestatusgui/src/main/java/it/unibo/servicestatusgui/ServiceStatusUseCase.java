package it.unibo.servicestatusgui;

import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.utils.CommSystemConfig;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.interfaces.Interaction;

import java.io.*;
import java.net.Socket;

public class ServiceStatusUseCase implements ServiceStatusUseCaseInterface {

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
            String path = ctxqakdest+"/"+qakdestination;  //COAP observable resource => basicrobot
            coapconn    = new CoapConnection(addr+":" + cssPort, path);

            CoapConnection statusservicecconn = new CoapConnection(addr+":" + cssPort, ctxqakdest+"/statusservice" );
            statusservicecconn.observeResource( new StatusServiceCoapObserver() );

            client = new Socket("localhost", 8022);
        }catch(Exception e){
            CommUtils.outred("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    public void getMap() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        System.out.println(CommUtils.buildDispatch("tester", "sendmap", "sendmap(_)","statusservice"));
        //invio elabTicketRequest
        out.write("msg(sendmap,dispatch,tester,statusservice,sendmap(_),13)\n");
        out.flush();
    }
}
