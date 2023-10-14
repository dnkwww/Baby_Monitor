# 抓取指定網路圖片的方法
from urllib.request import urlretrieve
import shutil
import os

def IMG_PATH():
    # 網路圖片url
    img_url = 'http://192.168.1.5/capture'
    fpath = "imgs/"+img_url.split("/")[-1]      # imgs/capture
    urlretrieve(img_url, fpath)     # 將遠端(網路)資料下載到本地

    # 取得檔案名稱，用於重新命名檔案
    file_name = os.path.splitext(fpath)[0]     # 取得檔案名稱（不包含副檔名）
    new_file_name = file_name + ".jpg"          # 新的檔案名稱
    if os.path.exists('imgs/capture.jpg'):
        os.remove('imgs/capture.jpg')
    # 重新命名檔案
    os.rename(fpath, new_file_name)
    return (new_file_name)
    # r_img = cv2.imread(fpath)   # cv2讀取下載的圖檔
    # cv2.imshow("Net ImageUrl", r_img)   # 以cv2呈現讀取的圖檔
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()
