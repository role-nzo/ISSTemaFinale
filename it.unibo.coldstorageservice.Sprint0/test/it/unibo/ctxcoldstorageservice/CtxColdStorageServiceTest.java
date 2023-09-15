package it.unibo.ctxcoldstorageservice;

import org.junit.AfterClass;
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
    public void mainUseCaseServiceAccessGUITest() throws IOException, InterruptedException {

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
        out.write("msg(storefood,request,tester,coldstorageservice,storefood("+ticket+","+fw+"),13)\n");
        out.flush();

        //verifica ticket accepted
        response = in.readLine();
        assertTrue(response.contains("chargetaken"));

    }

    @AfterClass
    public static void close() throws IOException {
        client.close();
    }
}
