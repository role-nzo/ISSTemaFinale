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
          sendevent=Custom('sendevent','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
          statusservice=Custom('statusservice','./qakicons/symActorSmall.png')
          emptycoldroom=Custom('emptycoldroom','./qakicons/symActorSmall.png')
          ticketservice=Custom('ticketservice','./qakicons/symActorSmall.png')
          coldstorageservice=Custom('coldstorageservice','./qakicons/symActorSmall.png')
     sendevent >> Edge( xlabel='sonardata', **eventedgeattr, fontcolor='red') >> sys
     sendevent >> Edge( xlabel='resume', **eventedgeattr, fontcolor='red') >> sys
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='engage', fontcolor='magenta') >> basicrobot
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='moverobot', fontcolor='magenta') >> basicrobot
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> transporttrolley
     transporttrolley >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
     sys >> Edge(color='red', style='dashed', xlabel='resume', fontcolor='red') >> transporttrolley
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