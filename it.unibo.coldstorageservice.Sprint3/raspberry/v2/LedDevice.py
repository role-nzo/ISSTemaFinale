#File: LedDevice.py
import RPi.GPIO as GPIO
import sys
import time
import select

GPIO.setmode(GPIO.BCM)
GPIO.setup(25,GPIO.OUT)

blink = 0
state = ""

def led(state):
    global blink
    #print("LedDevice receives: ", state)
    try:
        if state == 'stopped' :
            GPIO.output(25,GPIO.HIGH)
        elif state == 'moving' :
            if blink == 0 :
                GPIO.output(25,GPIO.HIGH)
            else :
                GPIO.output(25,GPIO.LOW)
            blink = (blink + 1) % 2
        elif state == 'home' :
            GPIO.output(25,GPIO.LOW)
    except:
        print("LedDevice | An exception occurred")

GPIO.output(25,GPIO.LOW)
while True:
    if select.select([sys.stdin, ], [], [], 0.0)[0]:
        new_state = sys.stdin.readline()[:-1]
        state = new_state
        print(new_state)

    led(state)
    time.sleep(0.5)