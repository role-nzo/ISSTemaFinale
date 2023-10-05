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
print("SonarSimulator execution")
time.sleep(10)
count = -20
cond = True
while cond:
    print(count, flush=True)
    count = count+1
    if count==12:
        count = - 5
    time.sleep(0.25)



### USAGE
### python sonar.py | python ControllerMqtt.py | python LedDevice.py
