package it.unibo.serviceaccessgui;

import java.io.IOException;

public interface ServiceAccessUseCaseInterface {

    public void init();
    public String showticket(String requestTicket, String requestShowTicketFw) throws IOException;
    public String newTicket(String requestFw) throws IOException;
}
