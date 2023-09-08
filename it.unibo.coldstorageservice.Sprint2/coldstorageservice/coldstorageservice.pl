%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8022").
context(ctxserviceaccessgui, "localhost",  "TCP", "8023").
context(ctxservicestatusgui, "localhost",  "TCP", "8024").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robotposendosimbiotico, ctxbasicrobot, "external").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( statusservice, ctxcoldstorageservice, "it.unibo.statusservice.Statusservice").
  qactor( emptycoldroom, ctxcoldstorageservice, "it.unibo.emptycoldroom.Emptycoldroom").
  qactor( ticketservice, ctxcoldstorageservice, "it.unibo.ticketservice.Ticketservice").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( serviceaccessgui, ctxserviceaccessgui, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( servicestatusgui, ctxservicestatusgui, "it.unibo.servicestatusgui.Servicestatusgui").
