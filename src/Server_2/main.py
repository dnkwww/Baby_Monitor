import subprocess
import os
import shutil
import time
import GetWebImage
import GetWebText
import firebase
import FCMstorage

duration = 3600 # 60分鐘
start_time = time.time()
image_count = 0 # 用來給img編號
count = False # count是False(代表沒有初始化過firebase)才會初始化firebase應用程式，因為同一個系統下只能初始化一次
while time.time() - start_time < duration:

    time.sleep(5) # 每5秒執行一次
    print("System is running.")

    tokens = ["cSyrmaBLS7utW8EQebQCDZ:APA91bH-FmI2LLpLskrBZqzdnT1QJ72iq-iLEkymhz0qruaYrHkGK5Cbj68UgbFBLq4UR1CHkWPPf_5ypGZdJSyS-sO4omnMxmQp1m_PZsyrJW0-Pr7PpWBRMkptsU58-hekYbRZaB_p"]
    # 讀取聲音訊息&處理警訊通知
    voice = int(GetWebText.VOICE())
    if (voice == 1): 
        firebase.send_message(4, tokens, count)
        count = True


    # 讀取圖像訊息&detect
    # 宣告detect.py的參數
    weights = 'runs/train/yolov5m/weights/best.pt'
    img_size = 640
    conf_threshold = 0.5 # 置信值==0.5
    data_path = 'data/baby.yaml'
    source_path = GetWebImage.IMG_PATH()
    output_dir = 'results'
    project_name = 'baby_monitor'
    device = '0'

    '''
    # 如果要備份上一次結果:
    先備份上一次的結果
    接著檢查backup是否存在，存在則刪除
    再接著檢查上一次的結果是否存在，存在則複製到backup

    backup_dir = os.path.join(output_dir, 'backup')
    if os.path.exists(output_dir + '/' + backup_dir):
        shutil.rmtree(output_dir + '/' + backup_dir)
    if os.path.exists(os.path.join(output_dir, project_name)):
        shutil.copytree(os.path.join(output_dir, project_name), backup_dir)
    '''

    # 如果之前的結果存在，就把他刪掉
    if os.path.exists(output_dir + '/' + project_name):
        shutil.rmtree(output_dir + '/' + project_name)

    # 如果檔名一樣的話，他不會把結果覆蓋，而是繼續往下新增detect結果(labels跟crops)
    # 執行detect.py
    command = f'python detect.py --weights {weights} --data {data_path} --conf {conf_threshold} --img {img_size} --source {source_path} --project {output_dir} --name {project_name} --save-txt --save-crop --exist-ok --update --device {device}'
    subprocess.run(command, shell=True)

    '''
    這裡yolov5 detect出來的結果會存到results\output底下
    在results\output\labels會有偵測出危險情形的txt檔(空的不會存)
    在results\output\crops底下存有將危險情形的box切割出來的圖片
    '''

    # 處理detect出來的結果
    folder_path = 'results/baby_monitor/labels'
    files = os.listdir(folder_path)
    label = []

    if len(files) != 0: 
        for file_name in files:
            with open(os.path.join(folder_path, file_name), "r") as f:
                contents = f.readlines()
                label = [line[0] for line in contents]
                # print(contents)
    # print(label)

    img_send = False # 用來記危險情況的圖片是否傳送，避免複數危險情況會重複傳同一張照片
    for i in range(len(label)):
        # print(type(label[i]))
        if (label[i] == '0'): # lying
            # print("lying on belly")
            if (img_send == False):
                FCMstorage.picture(image_count, count)
                image_count = image_count + 1
                count = True
                img_send = True
            firebase.send_message(0, tokens, count)
            time.sleep(2)
        elif (label[i] == '1'):
            # print("no quilt")
            if (img_send == False):
                FCMstorage.picture(image_count, count)
                image_count = image_count + 1
                count = True
                img_send = True
            firebase.send_message(1, tokens, count)
            time.sleep(2)
        elif (label[i] == '2'):
            # print("cover")
            if (img_send == False):
                FCMstorage.picture(image_count, count)
                image_count = image_count + 1
                count = True
                img_send = True
            firebase.send_message(2, tokens, count)
            time.sleep(2)
        elif (label[i] == '3'):
            # print("milk regurgitation")
            if (img_send == False):
                FCMstorage.picture(image_count, count)
                image_count = image_count + 1
                count = True
                img_send = True
            firebase.send_message(3, tokens, count)
            time.sleep(2)

