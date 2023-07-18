%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8022").
context(ctxrasp, "IPRASP",  "TCP", "1111").
context(ctxfridgetruck, "127.0.0.1",  "TCP", "8023").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( sonar, ctxrasp, "it.unibo.sonar.Sonar").
  qactor( led, ctxrasp, "it.unibo.led.Led").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( serviceaccessgui, ctxcoldstorageservice, "it.unibo.serviceaccessgui.Serviceaccessgui").
  qactor( coldroom, ctxcoldstorageservice, "it.unibo.coldroom.Coldroom").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( servicestatusgui, ctxcoldstorageservice, "it.unibo.servicestatusgui.Servicestatusgui").
  qactor( fridgetruck, ctxfridgetruck, "it.unibo.fridgetruck.Fridgetruck").
