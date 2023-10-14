package com.example.fcmpython;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity8 extends AppCompatActivity {

    // 創建Firebase Storage引用
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

//    // 下載照片
//    StorageReference imageRef = storageRef.child("images/image_6.jpg");
//    final long ONE_MEGABYTE = 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        Button btn_b1 = (Button) findViewById(R.id.btn_b1);
        btn_b1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity8.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });

        //获取指定存储桶中的所有文件
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                //存储文件的List
                List<StorageReference> items = listResult.getItems();

                //将List按时间戳排序（最新的在前面）
                Collections.sort(items, new Comparator<StorageReference>() {
                    @Override
                    public int compare(StorageReference o1, StorageReference o2) {
                        return Long.compare(
                                Long.parseLong(o2.getName().substring(0, o2.getName().lastIndexOf("."))),
                                Long.parseLong(o1.getName().substring(0, o1.getName().lastIndexOf("."))));

                    }
                });

                //下载最新的5张图片
                int numOfPhotosToDownload = Math.min(5, items.size());
                for (int i = 0; i < numOfPhotosToDownload; i++) {
                    final int finalI = i;
                    StorageReference imageRef = items.get(i);
                    final long ONE_MEGABYTE = 1024 * 1024;
                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // 将照片转换为Bitmap对象
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            // 将Bitmap设置为ImageView的内容
                            switch (finalI) {
                                case 0:
                                    ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                                    imageView2.setImageBitmap(bitmap);
                                    break;
                                case 1:
                                    ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
                                    imageView3.setImageBitmap(bitmap);
                                    break;
                                case 2:
                                    ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);
                                    imageView4.setImageBitmap(bitmap);
                                    break;
                                case 3:
                                    ImageView imageView5 = (ImageView) findViewById(R.id.imageView5);
                                    imageView5.setImageBitmap(bitmap);
                                    break;
                                case 4:
                                    ImageView imageView6 = (ImageView) findViewById(R.id.imageView6);
                                    imageView6.setImageBitmap(bitmap);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // 下载失败，处理错误
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // 获取文件列表失败，处理错误
            }
        });



//        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            public void onSuccess(byte[] bytes) {
//                // 將照片轉換為Bitmap對象
//                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//
//                // 創建ImageView對象
//                ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
//
//                // 將Bitmap設置為ImageView的內容
//                imageView2.setImageBitmap(bitmap);
//            }
//
//
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // 下載失敗，處理錯誤
//            }
//        });


    }
}




