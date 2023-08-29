package com.dgstore.workmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dgstore.R;

public class NotificationWorker extends Worker {
    private static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 1;
    Context myContext;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.myContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        boolean isCartEmpty = checkIfCartIsEmpty();

        if (isCartEmpty) {
            return Result.failure(); // Görev başarısız oldu, çünkü sepet boş
        }

        // Bildirim gönderme işlemleri
        sendNotification();


        return Result.success();
    }

    private boolean checkIfCartIsEmpty() {
        // Burada sepetin boş olup olmadığını kontrol eden kodu yazabilirsiniz
        // Örnek bir koşul: Eğer sepette hiç ürün yoksa true döner, aksi halde false döner

        return false;
    }

    private void sendNotification() {
        // Bildirim oluşturma
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Foreground Service Channel")
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentTitle("Yeni Bildirim")
                .setContentText("Sepetinizde ürünler bulunuyor!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Bildirimi gösterme
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) myContext, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION_PERMISSION);
        } else {
            // İzin verilmişse bildirimi gösterme işlemleri burada yapılır
            notificationManager.notify(1, builder.build());
        }
//        notificationManager.notify(1, builder.build());
    }
}

