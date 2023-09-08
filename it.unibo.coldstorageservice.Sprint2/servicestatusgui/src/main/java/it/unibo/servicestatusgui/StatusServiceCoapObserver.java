package it.unibo.servicestatusgui;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import unibo.basicomm23.utils.CommUtils;

public class StatusServiceCoapObserver implements CoapHandler {
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
