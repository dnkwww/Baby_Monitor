import time
import firebase_admin
from firebase_admin import credentials, initialize_app, messaging
from firebase_admin.messaging import AndroidConfig, AndroidNotification

def send_message(flag, topic, count):
    # 設定firebase憑據
    cred = credentials.Certificate('serviceAccountKey.json')  # credentials.Certificate()讀取firebase憑證
    '''
    第一次跑才要初始化firebase應用程式，否則會報錯:
    The default Firebase app already exists. 
    This means you called initialize_app() more than once without providing an app name as the second argument. 
    In most cases you only need to call initialize_app() once. 
    But if you do want to initialize multiple apps, pass a second argument to initialize_app() to give each app a unique name.
    因為Firebase只允許在同一程式中初始化一次。
    '''
    if (count == False):
        firebase_admin.initialize_app(cred, options={'storageBucket': 'fcmpython-1cae9.appspot.com'})  # 初始化Firebase應用程式
        
    # 判斷flag來決定要發送哪種危險情況
    if (flag == 0):  # lying on belly
        message = messaging.Message(
            android=AndroidConfig(
                notification=AndroidNotification(
                    title='Warning',
                    body='Your child is in the state of lying on belly!',
                    color='#ff0000',
                    click_action='com.example.FCMpython.MainActivity5',
                )
            ),
            data={
                'timestamp': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            },
            topic='warning'
        )
    elif (flag == 1):
        message = messaging.Message(
            android=AndroidConfig(
                notification=AndroidNotification(
                    title='Warning',
                    body='Your child is not covered with a quilt!',
                    color='#ff0000',
                    click_action='com.example.FCMpython.MainActivity6',
                )
            ),
            data={
                'timestamp': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            },
            topic='warning'
        )
    elif (flag == 2):
        message = messaging.Message(
            android=AndroidConfig(
                notification=AndroidNotification(
                    title='Warning',
                    body='Your child face is being covered!',
                    color='#ff0000',
                    click_action='com.example.FCMpython.MainActivity4',
                )
            ),
            data={
                'timestamp': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            },
            topic='warning'
        )
    elif (flag == 3):
        message = messaging.Message(
            android=AndroidConfig(
                notification=AndroidNotification(
                    title='Warning',
                    body='Your child is spitting up milk!',
                    color='#ff0000',
                    click_action='com.example.FCMpython.MainActivity3',
                )
            ),
            data={
                'timestamp': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            },
            topic='warning'
        )
    elif (flag == 4):
        message = messaging.Message(
            android=AndroidConfig(
                notification=AndroidNotification(
                    title='Warning',
                    body='Your child is in a high-decibel environment!',
                    color='#ff0000',
                    click_action='com.example.FCMpython.MainActivity7',
                )
            ),
            data={
                'timestamp': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            },
            topic='warning'
        )

    response = messaging.send(message)
    print('Successfully sent message:', response)