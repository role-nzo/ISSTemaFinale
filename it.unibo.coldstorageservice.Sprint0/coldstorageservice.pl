%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "127.0.0.1",  "TCP", "8022").
context(ctxfridgetruck, "localhost",  "TCP", "8023").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( serviceaccessgui, ctxfridgetruck, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( servicestatusgui, ctxcoldstorageservice, "it.unibo.servicestatusgui.Servicestatusgui").
  qactor( fridgetruck, ctxfridgetruck, "it.unibo.fridgetruck.Fridgetruck").
