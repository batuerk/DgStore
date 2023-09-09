package com.dgstore.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.dgstore.R;

import java.util.concurrent.TimeUnit;

public class NotificationWorker2 extends Worker {

    private static final String CHANNEL_ID = "1";
    private Context context;
    public NotificationWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;

    }

    @NonNull
    @Override
    public Result doWork() {
        showNotification();
        return Result.success();

    }
    public static Constraints setCons() {
        Constraints constraints = new Constraints.Builder().build();
        return constraints;

    }

    public static void periodicWork() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker2.class, 10, TimeUnit.SECONDS)
                .setInitialDelay(1,TimeUnit.SECONDS)
                .setConstraints(setCons())
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("periodic", ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);
    }

    private void showNotification() {
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "DgStore Service", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        try {

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("DgStore")
                    .setContentText("Check the Bag")
                    .setSmallIcon(R.drawable.ic_favorite,2)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
//                    .setContentIntent(fragmentPendingIntent);  // BagFragment'ı başlatan intent'i ayarla

//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify(1, notificationBuilder.build());


        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
