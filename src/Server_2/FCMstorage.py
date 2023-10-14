import os
import time
import urllib.request
from google.cloud import storage
from firebase_admin import credentials, storage as firebase_storage
import firebase_admin
from PIL import Image
import io

def picture(image_count, count):

    # 選擇Firebase應用程式
    cred = credentials.Certificate("serviceAccountKey.json")
    if (count == False):
        firebase_admin.initialize_app(cred, options={'storageBucket': 'fcmpython-1cae9.appspot.com'})
    firebase_bucket = firebase_storage.bucket()

    # 檢查檔案是否存在 #路徑需修改
    if os.path.isfile("./results/baby_monitor/capture.jpg"):
        
        # 在Firebase中創建一個新的照片對象
        blob = firebase_bucket.blob(f"{image_count}.jpg")

        # 上傳照片到Firebase #路徑需修改
        with open("./results/baby_monitor/capture.jpg", "rb") as img_file:
            blob.upload_from_file(img_file)

        # 從Firebase下載照片
        downloaded_blob = firebase_bucket.blob(f"{image_count}.jpg")
        image_bytes = downloaded_blob.download_as_bytes()

        # 將照片轉換為PIL Image對象
        img = Image.open(io.BytesIO(image_bytes))

        # # 刪除檔案 #路徑需修改
        # os.remove("./results/baby_monitor/capture.jpg")

    # # 等待10秒鐘
    # time.sleep(10)
