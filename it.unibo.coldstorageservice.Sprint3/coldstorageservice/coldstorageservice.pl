%====================================================================================
% coldstorageservice description   
%====================================================================================
dispatch( coapUpdate, coapUpdate(RESOURCE,VALUE) ).
event( alarm, alarm(X) ).
request( engage, engage(OWNER) ).
dispatch( disengage, disengage(ARG) ).
dispatch( setrobotstate, setpos(X,Y,D) ).
dispatch( setdirection, dir(D) ).
request( doplan, doplan(PATH,OWNER,STEPTIME) ).
request( moverobot, moverobot(TARGETX,TARGETY) ).
dispatch( goMoveToIndoor, goMoveToIndoor(_) ).
dispatch( goMoveToColdRoom, goMoveToColdRoom(_) ).
dispatch( goMoveToHome, goMoveToHome(_) ).
request( newticket, newticket(FW) ).
request( storefood, storefood(TICKET,FW) ).
request( loaddone, loaddone(FW) ).
request( waitLoad, waitLoad(_) ).
dispatch( depositdone, depositdone(_) ).
dispatch( updatevirtualweight, updatevirtualweight(FW) ).
request( clearColdRoom, clearColdRoom(_) ).
dispatch( sendmap, sendmap(_) ).
event( sonardata, distance(D) ).
event( obstacle, obstacle(D) ).
dispatch( resume, resume(_) ).
dispatch( stop, stop(D) ).
event( stopevent, stopevent(_) ).
event( resumevent, resumevent(_) ).
dispatch( robotstop, robotstop(_) ).
dispatch( robotstopfailed, robotstopfailed(_) ).
dispatch( robotresume, robotresume(_) ).
event( ledstatuschange, ledstatuschange(STATUS) ).
%====================================================================================
context(ctxbasicrobot, "127.0.0.1",  "TCP", "8020").
context(ctxcoldstorageservice, "localhost",  "TCP", "8022").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robotposendosimbiotico, ctxbasicrobot, "external").
  qactor( sonar, ctxcoldstorageservice, "sonarHCSR04Support23").
  qactor( led23, ctxcoldstorageservice, "it.unibo.led23.Led23").
  qactor( led, ctxcoldstorageservice, "ledSupport23").
  qactor( sonar23, ctxcoldstorageservice, "it.unibo.sonar23.Sonar23").
  qactor( transporttrolley, ctxcoldstorageservice, "it.unibo.transporttrolley.Transporttrolley").
  qactor( statusservice, ctxcoldstorageservice, "it.unibo.statusservice.Statusservice").
  qactor( emptycoldroom, ctxcoldstorageservice, "it.unibo.emptycoldroom.Emptycoldroom").
  qactor( ticketservice, ctxcoldstorageservice, "it.unibo.ticketservice.Ticketservice").
  qactor( coldstorageservice, ctxcoldstorageservice, "it.unibo.coldstorageservice.Coldstorageservice").
