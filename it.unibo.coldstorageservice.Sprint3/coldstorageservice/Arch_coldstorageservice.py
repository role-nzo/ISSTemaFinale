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
          led23=Custom('led23','./qakicons/symActorSmall.png')
          sonar23=Custom('sonar23','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          statusservice=Custom('statusservice','./qakicons/symActorSmall.png')
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          led=Custom('led(coded)','./qakicons/codedQActor.png')
     led23 >> Edge( label='ledstatuschange', **eventedgeattr, fontcolor='red') >> sys
     sonar23 >> Edge( label='stopevent', **eventedgeattr, fontcolor='red') >> sys
     sonar23 >> Edge( label='resumevent', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge( label='alarm', **eventedgeattr, fontcolor='red') >> sys
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<waitLoad &nbsp; >',  fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot &nbsp; >',  fontcolor='magenta') >> basicrobot
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<storefood &nbsp; >',  fontcolor='magenta') >> ticketservice
     emptycoldroom >> Edge(color='magenta', style='solid', decorate='true', label='<clearColdRoom &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid',  label='<goMoveToIndoor &nbsp; goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> led23
     coldstorageservice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<depositdone &nbsp; >',  fontcolor='blue') >> coldstorageservice
     statusservice >> Edge(color='blue', style='solid',  label='<goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid',  label='<updatevirtualweight &nbsp; >',  fontcolor='blue') >> ticketservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<robotstopfailed &nbsp; robotstop &nbsp; robotresume &nbsp; >',  fontcolor='blue') >> sonar23
diag
