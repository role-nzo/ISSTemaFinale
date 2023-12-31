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
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
     with Cluster('ctxfridgetruck', graph_attr=nodeattr):
          fridgetruck=Custom('fridgetruck','./qakicons/symActorSmall.png')
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='depositdone', fontcolor='blue') >> coldstorageservice
     emptycoldroom >> Edge(color='magenta', style='solid', xlabel='clearColdRoom', fontcolor='magenta') >> coldstorageservice
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='storefood', fontcolor='magenta') >> ticketservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='goMoveToIndoor', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='waitLoad', fontcolor='magenta') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='updatevirtualweight', fontcolor='blue') >> ticketservice
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='newticket', fontcolor='magenta') >> coldstorageservice
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='storefood', fontcolor='magenta') >> coldstorageservice
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='loaddone', fontcolor='magenta') >> coldstorageservice
diag
