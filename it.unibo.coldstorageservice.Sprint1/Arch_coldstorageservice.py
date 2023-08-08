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
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
     with Cluster('ctxfridgetruck', graph_attr=nodeattr):
          fridgetruck=Custom('fridgetruck','./qakicons/symActorSmall.png')
     with Cluster('ctxfridgetrucktemp', graph_attr=nodeattr):
          fridgetrucktemp=Custom('fridgetrucktemp','./qakicons/symActorSmall.png')
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='blue', style='solid', xlabel='deposit', fontcolor='blue') >> coldstorageservice
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='goMoveToIndoor', fontcolor='blue') >> transporttrolley
     coldstorageservice >> Edge(color='magenta', style='solid', xlabel='waitLoad', fontcolor='magenta') >> transporttrolley
     coldstorageservice >> Edge(color='blue', style='solid', xlabel='goMoveToHome', fontcolor='blue') >> transporttrolley
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='newticket', fontcolor='magenta') >> coldstorageservice
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='ticketrequest', fontcolor='magenta') >> coldstorageservice
     fridgetruck >> Edge(color='magenta', style='solid', xlabel='loaddone', fontcolor='magenta') >> coldstorageservice
     fridgetrucktemp >> Edge(color='magenta', style='solid', xlabel='newticket', fontcolor='magenta') >> coldstorageservice
     fridgetrucktemp >> Edge(color='magenta', style='solid', xlabel='ticketrequest', fontcolor='magenta') >> coldstorageservice
     fridgetrucktemp >> Edge(color='magenta', style='solid', xlabel='loaddone', fontcolor='magenta') >> coldstorageservice
diag
