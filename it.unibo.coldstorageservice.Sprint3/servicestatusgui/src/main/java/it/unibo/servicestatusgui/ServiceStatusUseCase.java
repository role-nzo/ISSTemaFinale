package it.unibo.servicestatusgui;

import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
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

            String addr = "192.168.218.29";
            String path = ctxqakdest+"/"+qakdestination;  //COAP observable resource => basicrobot
            coapconn    = new CoapConnection(addr+":" + cssPort, path);

            CoapConnection statusservicecconn = new CoapConnection(addr+":" + cssPort, ctxqakdest+"/statusservice" );
            statusservicecconn.observeResource( new CoapObserver() );
            CoapConnection transporttrolleycconn = new CoapConnection(addr+":" + cssPort, ctxqakdest+"/transporttrolley" );
            transporttrolleycconn.observeResource( new CoapObserver() );

            client = new Socket(addr, cssPort);
        }catch(Exception e){
            CommUtils.outred("RobotUtils | connectWithRobotUsingTcp ERROR:"+e.getMessage());
        }
    }

    public void getMap() throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        IApplMessage request = CommUtils.buildDispatch("tester", "sendmap", "sendmap(_)","statusservice");

        //invio elabTicketRequest
        out.write(request + "\n");
        out.flush();
    }
}
