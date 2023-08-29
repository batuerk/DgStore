package com.dgstore.workmanager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.dgstore.MainActivity;
import com.dgstore.R;

import java.util.concurrent.TimeUnit;

public class ForegroundService extends LifecycleService {
    private static final String NOTIFICATION_CHANNEL_ID = "ForegroundServiceChannel";
    private static final int NOTIFICATION_ID = 123;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // Foreground servis olarak ayarlama ve bildirim gösterme
        createNotificationChannel();
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);

        // WorkRequest oluşturma
        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.HOURS)
                        .build();

        // WorkRequest'i WorkManager'a gönderme
        WorkManager.getInstance(this).enqueue(workRequest);

        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Çalışıyor...")
                .setSmallIcon(R.drawable.ic_favorite)
                .setContentIntent(pendingIntent)
                .build();
    }

    @SuppressLint("MissingSuperCall")
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
