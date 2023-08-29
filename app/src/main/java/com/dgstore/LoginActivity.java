package com.dgstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dgstore.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    Intent intent;
    String email;
    String password;
    MyDatabase myDatabase;
    List<User> users = new ArrayList<>();
    public static List<String> userInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        myDatabase= MyDatabase.getMyDatabase(this);
        users = myDatabase.daoUser().allUsers();

        intent = getIntent();
        logIn();
    }
    public void logIn() {
        //System.out.println(intent.getStringExtra("emailData"));
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.editTextTextEmailAddress2.getText().toString();
                password = binding.editTextTextPassword2.getText().toString();
                boolean matchUser = false;

                if (email.isEmpty() && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Alanlar boş olamaz",Toast.LENGTH_SHORT).show();
                }else {
                    for (User user : users) {
                        if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                            matchUser = true;
//                            userInfo.add("Name: " + user.getName() + "\n" + "Email: "+ user.getEmail());
                            userInfo.add("Name: " + user.getName());
                            userInfo.add("Email: "+ user.getEmail());

                            System.out.println("login userinfo: " + userInfo);
                            for (String info : userInfo) {
                                UserInfo.getInstance().getUserInfoList().add(info.toString());
                            }
                        }
                    }
                    if (matchUser) {
                        Intent signUp = new Intent(LoginActivity.this, BottomNavigationView.class);
                        startActivity(signUp);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this,"Hesap yok !!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUp);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
