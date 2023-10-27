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

host_ip = "192.168.1.141"
#port = 8022
port = 6527
stopped = False
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
s.connect((host_ip, port))
print ('Socket connected')
#print ('Mqtt client connected ')
##time.sleep(2)

for line in sys.stdin:
    print("ControllerSonar RECEIVES:", line)
    try:
        data = line
        #print(sonardatamsg.replace("N", str(n)).encode())
        s.send(data.encode("utf-8"))
        print("ho inviato", data.encode("utf-8"))

    except:
        print("ControllerMqtt | An exception occurred")


### USAGE
### python sonar.py | python ControllerMqtt.py | python LedDevice.py