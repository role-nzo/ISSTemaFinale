%====================================================================================
% raspberry description   
%====================================================================================
dispatch( stopevent, stopevent(_) ).
dispatch( resumevent, resumevent(_) ).
dispatch( ledstatuschange, ledstatuschange(STATUS) ).
dispatch( coapUpdate, coapUpdate(RESOURCE,VALUE) ).
%====================================================================================
context(ctxcoldstorageservice, "127.0.0.1",  "TCP", "8022").
context(ctxraspberry, "localhost",  "TCP", "8023").
 qactor( transporttrolley, ctxcoldstorageservice, "external").
  qactor( sonar, ctxraspberry, "Sonar").
  qactor( led, ctxraspberry, "Led").
