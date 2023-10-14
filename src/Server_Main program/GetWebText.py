import requests

def VOICE():
    url = "http://192.168.1.5/status"
    html = requests.get(url).text
    #print(html)
    voice = html.split(":")[-1] # voice == 0 / 1
    return (voice)

    exit()
