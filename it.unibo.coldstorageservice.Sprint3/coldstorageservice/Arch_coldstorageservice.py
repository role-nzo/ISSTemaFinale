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
          robotposendosimbiotico=Custom('robotposendosimbiotico(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxcoldstorageservice', graph_attr=nodeattr):
          sonar23=Custom('sonar23','./qakicons/symActorSmall.png')
          sonar23observer=Custom('sonar23observer','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          statusservice=Custom('statusservice','./qakicons/symActorSmall.png')
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
          sonar=Custom('sonar(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
          distancefilter=Custom('distancefilter(coded)','./qakicons/codedQActor.png')
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> sonar23
     sys >> Edge(color='red', style='dashed', xlabel='obstacle', fontcolor='red') >> sonar23
     sonar23 >> Edge(color='blue', style='solid', xlabel='stop', fontcolor='blue') >> transporttrolley
     sonar23 >> Edge(color='blue', style='solid', xlabel='resume', fontcolor='blue') >> transporttrolley
     sonar23 >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sonar23 >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> sonar23observer
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='blue', style='solid', xlabel='depositdone', fontcolor='blue') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='coapUpdate', fontcolor='blue') >> statusservice
     statusservice >> Edge(color='blue', style='solid', xlabel='goMoveToHome', fontcolor='blue') >> transporttrolley
     emptycoldroom >> Edge(color='magenta', style='solid', xlabel='clearColdRoom', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='storefood', fontcolor='magenta') >> ticketservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='goMoveToIndoor', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='waitLoad', fontcolor='magenta') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='goMoveToHome', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatevirtualweight', fontcolor='blue') >> ticketservice
diag
