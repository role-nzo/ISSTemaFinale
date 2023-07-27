%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8022").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
