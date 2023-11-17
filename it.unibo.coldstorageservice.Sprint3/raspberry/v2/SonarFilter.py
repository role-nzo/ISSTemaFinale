import sys
import time

limit = 10
last = limit + 1
for line in sys.stdin:
    try:
        vf = float(line)
        v  = int( vf )
        if( v > 300 ): continue
        data = str(v) #misura sonar
        if((last <= limit and v > limit) or (last > limit and v <= limit)):
            #m1 = "distance(${if(v > limit) "HIGH" else "LOW"})"
            print("HIGH" if v>limit else "LOW", flush=True)
        last = v

    except:
        print("SonarFilter | An exception occurred")