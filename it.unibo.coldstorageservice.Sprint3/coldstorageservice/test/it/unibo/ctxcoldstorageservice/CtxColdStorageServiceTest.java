package it.unibo.ctxcoldstorageservice;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.utils.CommUtils;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class CtxColdStorageServiceTest {

    static Socket client;
    static BufferedWriter out;
    static BufferedReader in;

    /*
    -richiedere un nuovo ticket (newticket) [HOME]
    -richiesta di loaddone [tra HOME-INDOOR]
    -richiesta nuovoticket(dopo loaddone)[INDOOR-COLDROOM]
    -richiesta di loaddone [INDOOR-COLDROOM]
    -scarico del primo ticket [COLDROOM]
    -ritorno in indoor [COLDROOM-INDOOR]
    -carico del secondo ticket [INDOOR]
    -1 sec dopo invio STOP [INDOOR-COLDROOM]
    -invio RESUME
    -invio STOP per farlo ignorare
    -scarico secondo ticket [COLDROOM]
    -invio STOP [COLDROOM-HOME]


     */
    @BeforeClass
    public static void init() throws IOException {
        client = new Socket("localhost", 8022);
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Test
    public void mainUseCaseFridgeTruckTest() throws IOException, InterruptedException {

        String fw1 = "10";
        String fw2 = "43";

        //invio messaggio
        out.write("msg(newticket,request,tester,coldstorageservice,newticket("+fw1+"),12)\n");
        out.flush();

        //attesa risposta
        String response = in.readLine();
        assertTrue(response.contains("newticketaccepted"));
        String ticket= response.split(",")[4].split("\\(")[1].split("\\)")[0];
        //String secret= response.split(",")[5].split("\\)")[0];

        //invio elabTicketRequest
        out.write("msg(storefood,request,tester,coldstorageservice,storefood("+ticket+","+fw1+"),13)\n");
        out.flush();

        //verifica ticket accepted
        response = in.readLine();
        assertTrue(response.contains("storefoodaccepted"));

        //invio loadDone
        out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+fw1+"),14)\n");
        out.flush();

        //risposta chargetaken
        response = in.readLine();
        assertTrue(response.contains("chargetaken"));

        //SECONDO TICKET
        out.write("msg(newticket,request,tester,coldstorageservice,newticket("+fw2+"),12)\n");
        out.flush();

        //attesa risposta
        response = in.readLine();
        assertTrue(response.contains("newticketaccepted"));
        ticket= response.split(",")[4].split("\\(")[1].split("\\)")[0];
        //String secret= response.split(",")[5].split("\\)")[0];

        //invio elabTicketRequest
        out.write("msg(storefood,request,tester,coldstorageservice,storefood("+ticket+","+fw2+"),13)\n");
        out.flush();

        //verifica ticket accepted
        response = in.readLine();
        assertTrue(response.contains("storefoodaccepted"));

        //invio loadDone
        out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+fw2+"),14)\n");
        out.flush();

        //risposta chargetaken
        response = in.readLine();
        assertTrue(response.contains("chargetaken"));

        Thread.sleep(1500);
        out.write("msg(stopevent,dispatch,tester,transporttrolley,stopevent(_),14)\n");
        out.flush();
        Thread.sleep(3000);
        out.write("msg(resumevent,dispatch,tester,transporttrolley,resumevent(_),14)\n");
        out.flush();
        Thread.sleep(1000);
        out.write("msg(stopevent,dispatch,tester,transporttrolley,stopevent(_),14)\n");
        out.flush();
        Thread.sleep(8000);
        out.write("msg(stopevent,dispatch,tester,transporttrolley,stopevent(_),14)\n");
        out.flush();
        Thread.sleep(1500);
        out.write("msg(resumevent,dispatch,tester,transporttrolley,resumevent(_),14)\n");
        out.flush();
        //TICKET REJECTED
        out.write("msg(storefood,request,tester,coldstorageservice,storefood("+ticket+","+fw2+"),13)\n");
        out.flush();

        response = in.readLine();
        assertTrue(response.contains("storefoodrejected"));

    }

    @Test
    public void emptyColdRoom() throws IOException {
        //invio messaggio
        out.write("msg(clearColdRoom,request,tester,coldstorageservice,clearColdRoom(0),12)\n");
        out.flush();

        //attesa risposta
        String response = in.readLine();
        assertTrue(response.contains("coldRoomCleared"));
    }
}
