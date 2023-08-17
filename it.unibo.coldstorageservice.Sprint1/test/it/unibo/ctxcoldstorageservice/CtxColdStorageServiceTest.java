package it.unibo.ctxcoldstorageservice;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class CtxColdStorageServiceTest {

    @Test
    public void mainUseCaseFridgeTruckTest(){

        System.out.println("qui");
        try{
            Socket client = new Socket("localhost", 8022);
            System.out.println("socket creata");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String fw = "10";
            //invio messaggio
            out.write("msg(newticket,Request,tester,coldstorageservice,newticket("+fw+"),12)\n");
            out.flush();
            System.out.println("msg(newticket,Request,tester,coldstorageservice,newticket");
            //attesa risposta
            String response = in.readLine();
            System.out.println(response);
            assertTrue(response.contains("newticketaccepted"));
            String ticket= response.split(",")[4].split("\\(")[1].split("\\)")[0];
            String secret= response.split(",")[5].split("\\)")[0];

            //invio elabTicketRequest
            out.write("msg(ticketrequest,request,tester,coldstorageservice,ticketrequest("+ticket+","+fw+"),13)\n");

            //verifica ticket accepted
            response = in.readLine();
            assertTrue(response.contains("ticketaccepted"));

            //invio loadDone
            out.write("msg(loaddone,request,tester,coldstorageservice,loaddone("+fw+"),14)\n");

            //risposta chargetaken
            response = in.readLine();
            assertTrue(response.contains("chargetaken"));

        }catch(Exception e){
            System.out.println(e.getStackTrace());
        }


    }
}
