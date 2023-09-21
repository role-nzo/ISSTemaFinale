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

    @BeforeClass
    public static void init() throws IOException {
        client = new Socket("localhost", 8022);
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Test
    public void mainUseCaseFridgeTruckTest() throws IOException, InterruptedException {

        String fw = "10";

        //invio messaggio
        out.write("msg(newticket,request,tester,coldstorageservice,newticket("+fw+"),12)\n");
        out.flush();

        //attesa risposta
        String response = in.readLine();
        assertTrue(response.contains("newticketaccepted"));
        String ticket= response.split(",")[4].split("\\(")[1].split("\\)")[0];
        //String secret= response.split(",")[5].split("\\)")[0];

        //invio elabTicketRequest
        out.write("msg(ticketrequest,request,tester,coldstorageservice,ticketrequest("+ticket+","+fw+"),13)\n");
        out.flush();

        //verifica ticket accepted
        response = in.readLine();
        assertTrue(response.contains("ticketaccepted"));

        //invio loadDone
        out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+fw+"),14)\n");
        out.flush();

        //risposta chargetaken
        response = in.readLine();
        assertTrue(response.contains("chargetaken"));

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
