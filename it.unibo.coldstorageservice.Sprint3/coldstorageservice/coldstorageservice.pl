%====================================================================================
% coldstorageservice description   
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8022").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robotposendosimbiotico, ctxbasicrobot, "external").
  qactor( sonar, ctxcoldstorageservice, "sonarHCSR04Support23").
  qactor( datacleaner, ctxcoldstorageservice, "rx.dataCleaner").
  qactor( distancefilter, ctxcoldstorageservice, "rx.distanceFilter").
  qactor( sonar23, ctxcoldstorageservice, "it.unibo.sonar23.Sonar23").
  qactor( sonar23observer, ctxcoldstorageservice, "it.unibo.sonar23observer.Sonar23observer").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( statusservice, ctxcoldstorageservice, "it.unibo.statusservice.Statusservice").
  qactor( emptycoldroom, ctxcoldstorageservice, "it.unibo.emptycoldroom.Emptycoldroom").
  qactor( ticketservice, ctxcoldstorageservice, "it.unibo.ticketservice.Ticketservice").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
