import time
import webbrowser
count = 1
while(count <=3):
    print(time.ctime())
    print(time.gmtime())
    time.sleep(5)
    webbrowser.open("fercastro.com/learning")
    count = count+1