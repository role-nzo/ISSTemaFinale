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
          ledobserver=Custom('ledobserver','./qakicons/symActorSmall.png')
          sonarhandler=Custom('sonarhandler','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          statusservice=Custom('statusservice','./qakicons/symActorSmall.png')
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          sonarsupport=Custom('sonarsupport(coded)','./qakicons/codedQActor.png')
          ledsupport=Custom('ledsupport(coded)','./qakicons/codedQActor.png')
     transporttrolley >> Edge( label='alarm', **eventedgeattr, fontcolor='red') >> sys
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<waitLoad &nbsp; >',  fontcolor='magenta') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', decorate='true', label='<engage &nbsp; moverobot &nbsp; >',  fontcolor='magenta') >> basicrobot
     coldstorageservice >> Edge(color='magenta', style='solid', decorate='true', label='<storefood &nbsp; >',  fontcolor='magenta') >> ticketservice
     emptycoldroom >> Edge(color='magenta', style='solid', decorate='true', label='<clearColdRoom &nbsp; >',  fontcolor='magenta') >> coldstorageservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<robotstopfailed &nbsp; robotstop &nbsp; robotresume &nbsp; >',  fontcolor='blue') >> sonarhandler
     coldstorageservice >> Edge(color='blue', style='solid',  label='<updatevirtualweight &nbsp; >',  fontcolor='blue') >> ticketservice
     ledobserver >> Edge(color='blue', style='solid',  label='<ledstatuschange &nbsp; >',  fontcolor='blue') >> ledsupport
     statusservice >> Edge(color='blue', style='solid',  label='<goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='blue', style='solid',  label='<depositdone &nbsp; >',  fontcolor='blue') >> coldstorageservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> ledobserver
     coldstorageservice >> Edge(color='blue', style='solid',  label='<goMoveToIndoor &nbsp; goMoveToHome &nbsp; >',  fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
     transporttrolley >> Edge(color='blue', style='solid',  label='<coapUpdate &nbsp; >',  fontcolor='blue') >> statusservice
diag
