package com.dgstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;

import com.dgstore.databinding.ActivityMainBinding;
import com.dgstore.workmanager.NotificationWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getProductsLiveData().observe(this, new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                displayProducts(stores);

            }
        });

        mainViewModel.fetchProducts();

        WorkRequest workRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 1, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(workRequest);

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.RUNNING) {
                    System.out.println("running");
                } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    System.out.println("succeeded");
                } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                    System.out.println("failed");
                } else if (workInfo.getState() == WorkInfo.State.BLOCKED) {
                    System.out.println("blocked");
                } else if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                    System.out.println("cancelled");
                } else if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    System.out.println("enqueued");
                }

            }
        });
    }
    private void displayProducts(List<Store> stores) {
        StringBuilder sb = new StringBuilder();
        for (Store store : stores) {
            sb.append(store.getId()).append("\n");
//               sb.append(store.getTitle()).append("\n");
//               sb.append(store.getPrice()).append("\n");
//               sb.append(store.getCategory()).append("\n");
//               sb.append(store.getDescription()).append("\n");

        }
        binding.textView.setText(sb.toString());
    }


}



