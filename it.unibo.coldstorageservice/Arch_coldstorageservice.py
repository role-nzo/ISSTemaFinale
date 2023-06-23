from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('coldstorageserviceArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
          coldroom=Custom('coldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
     with Cluster('ctxrasp', graph_attr=nodeattr):
          led=Custom('led(ext)','./qakicons/externalQActor.png')
          sonar=Custom('sonar(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxfridgetruck', graph_attr=nodeattr):
          fridgetruck=Custom('fridgetruck','./qakicons/symActorSmall.png')
diag
