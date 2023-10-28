### conda install diagrams
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
### see https://renenyffenegger.ch/notes/tools/Graphviz/attributes/label/HTML-like/index
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          basicrobot=Custom('basicrobot(ext)','./qakicons/externalQActor.png')
          robotposendosimbiotico=Custom('robotposendosimbiotico(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          ledsupport=Custom('ledsupport','./qakicons/symActorSmall.png')
          sonarsupport=Custom('sonarsupport','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          statusservice=Custom('statusservice','./qakicons/symActorSmall.png')
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
     with Cluster('ctxserviceaccessgui', graph_attr=nodeattr):
          serviceaccessgui=Custom('serviceaccessgui','./qakicons/symActorSmall.png')
     with Cluster('ctxservicestatusgui', graph_attr=nodeattr):
          servicestatusgui=Custom('servicestatusgui','./qakicons/symActorSmall.png')
     with Cluster('ctxraspberry', graph_attr=nodeattr):
          sonar=Custom('sonar','./qakicons/symActorSmall.png')
          led=Custom('led','./qakicons/symActorSmall.png')
     transporttrolley >> Edge( label='alarm', **eventedgeattr, fontcolor='red') >> sys
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<waitLoad &nbsp; >',  fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot &nbsp; >',  fontcolor='magenta') >> basicrobot
     serviceaccessgui >> Edge(color='magenta', style='solid', decorate='true', label='<newticket &nbsp; storefood &nbsp; loaddone &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<storefood &nbsp; >',  fontcolor='magenta') >> ticketservice
     emptycoldroom >> Edge(color='magenta', style='solid', decorate='true', label='<clearColdRoom &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid',  label='<updatevirtualweight &nbsp; >',  fontcolor='blue') >> ticketservice
     statusservice >> Edge(color='blue', style='solid',  label='<goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     sonar >> Edge(color='blue', style='solid',  label='<sonarstatuschange &nbsp; >',  fontcolor='blue') >> sonarsupport
     transporttrolley >> Edge(color='blue', style='solid',  label='<depositdone &nbsp; >',  fontcolor='blue') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid',  label='<goMoveToIndoor &nbsp; goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
     ledsupport >> Edge(color='blue', style='solid',  label='<ledstatuschange &nbsp; >',  fontcolor='blue') >> led
     transporttrolley >> Edge(color='blue', style='solid',  label='<ledstatuschange &nbsp; >',  fontcolor='blue') >> ledsupport
     statusservice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> serviceaccessgui
     sonarsupport >> Edge(color='blue', style='solid',  label='<stopevent &nbsp; resumevent &nbsp; >',  fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
     statusservice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> servicestatusgui
diag
