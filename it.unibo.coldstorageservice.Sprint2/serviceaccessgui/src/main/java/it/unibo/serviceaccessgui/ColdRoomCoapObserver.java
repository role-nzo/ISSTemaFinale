package it.unibo.serviceaccessgui;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;


public class ColdRoomCoapObserver implements CoapHandler {
    @Override
    public void onLoad(CoapResponse response) {
        CommUtils.outcyan("PlanCoapObserver changed! " + response.getResponseText() );
        //send info over the websocket
        WebSocketConfiguration.wshandler.sendToAll("" + response.getResponseText());
    }

    @Override
    public void onError() {
        CommUtils.outred("PlanCoapObserver observe error!");
    }
}
