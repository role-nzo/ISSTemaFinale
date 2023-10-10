# File: ControllerMqtt.py
import time
import sys
import socket

n         = 1
#brokerAddr= "mqtt.eclipseprojects.io" #"broker.hivemq.com" #"mqtt.eclipseprojects.io"
#resumemsg = "msg(resume,event,sonar,none,resume(V),N)"
#sonardatamsg = "msg(sonardistance,event,sonar,none,distance(V),N)"
#client    = paho.Client("controller")
#client.connect(brokerAddr, 1883, 60)

host_ip = "192.168.1.140"
#port = 8022
port = 6524
stopped = False
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
s.connect((host_ip, port))
print ('Socket connected')
#print ('Mqtt client connected ')
##time.sleep(2)

for line in sys.stdin:
    print("ControllerMqtt RECEIVES:", line)
    try:
        vf = float(line)
        v  = int( vf )
        n = n + 1
        data = str(v)
        #print(sonardatamsg.replace("N", str(n)).encode())
        s.send(data.encode("utf-8"))
        print("ho inviato", data.encode("utf-8"))

    except:
        print("ControllerMqtt | An exception occurred")


### USAGE
### python sonar.py | python ControllerSonar.py