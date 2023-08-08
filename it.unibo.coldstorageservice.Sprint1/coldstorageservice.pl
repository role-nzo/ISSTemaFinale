%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "127.0.0.1",  "TCP", "8022").
context(ctxfridgetruck, "127.0.0.1",  "TCP", "8023").
context(ctxfridgetrucktemp, "localhost",  "TCP", "8024").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
  qactor( fridgetruck, ctxfridgetruck, "it.unibo.fridgetruck.Fridgetruck").
  qactor( fridgetrucktemp, ctxfridgetrucktemp, "it.unibo.fridgetrucktemp.Fridgetrucktemp").
