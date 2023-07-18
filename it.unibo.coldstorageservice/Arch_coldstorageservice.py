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
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
     with Cluster('ctxrasp', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
     with Cluster('ctxfridgetruck', graph_attr=nodeattr):
          fridgetruck=Custom('fridgetruck','./qakicons/symActorSmall.png')
     sonar >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sonar >> Edge( xlabel='resume', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='alarm', fontcolor='red') >> transporttrolley
     sys >> Edge(color='red', style='dashed', xlabel='resume', fontcolor='red') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='doplan', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='deposit', fontcolor='blue') >> coldroom
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='ticketrequest', fontcolor='magenta') >> coldstorageservice
     serviceaccessgui >> Edge(color='magenta', style='solid', xlabel='showticketrequest', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='domove', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='chargetaken', fontcolor='blue') >> serviceaccessgui
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='guiupdate', fontcolor='blue') >> servicestatusgui
diag
