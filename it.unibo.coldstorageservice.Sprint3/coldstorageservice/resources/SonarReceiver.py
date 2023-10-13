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
port = 6526
stopped = False
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((host_ip, port))
s.listen(1)
conn, addr = s.accept()

print(f"Connected by {addr}")
while True :
    data = conn.recv(1024)
    distance = data.decode("utf-8")
    print(distance, flush=True)
    time.sleep(0.25)





### USAGE
### python sonar.py | python ControllerMqtt.py | python LedDevice.py
