package com.dgstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dgstore.databinding.ActivitySignUpBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private ActivitySignUpBinding binding;
    String getUserName;
    String getUserEmail;
    String getUserPassword;
    SharedPreferences sharedPreferences;
    String name;
    String email;
    String password;
    MyDatabase myDatabase;
    List<User> users = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        signUp();

        myDatabase= MyDatabase.getMyDatabase(this);
        users = myDatabase.daoUser().allUsers();
        System.out.println("liste: " + myDatabase.daoUser().allUsers().toString());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            signInResult(task);
        }
    }

    private void signInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Intent signUp = new Intent(SignUpActivity.this, BottomNavigationView.class);
            startActivity(signUp);
            finish();
        }catch (ApiException e) {

        }

    }

    public void signUp(){
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserName = binding.editTextText.getText().toString();
                getUserEmail = binding.editTextTextEmailAddress.getText().toString();
                getUserPassword = binding.editTextTextPassword.getText().toString();
                boolean hasUser = false;

                if (!getUserName.isEmpty() && !getUserEmail.isEmpty() && !getUserPassword.isEmpty()){
                    for (User user : users) {
                        if (user.getName().equalsIgnoreCase(getUserName) && user.getEmail().equalsIgnoreCase(getUserEmail)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setTitle("DgStore");
                            builder.setMessage("Already Have Account");
                            builder.show();
                            hasUser = true;
                        }
                    }
                    if (!hasUser) {
                        User user = new User();
                        user.setName(getUserName);
                        user.setEmail(getUserEmail);
                        user.setPassword(getUserPassword);
                        myDatabase.daoUser().addUser(user);
                        Intent signUp = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(signUp);
                    }
                }else {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(SignUpActivity.this);
                    builder2.setTitle("DgStore");
                    builder2.setMessage("Fill in the blank");
                    builder2.show();
                }
            }
        });

        binding.textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignUpActivity.this, LoginActivity.class);
                signUp.putExtra("emailData",email);
                signUp.putExtra("passwordData",password);
                startActivity(signUp);

            }
        });

        }
    }





//                getUserName = String.valueOf(binding.editTextText.getText());
//                getUserEmail = String.valueOf(binding.editTextTextEmailAddress.getText().toString());
//                getUserPassword = String.valueOf(binding.editTextTextPassword.getText().toString());
//                System.out.println(getUserName);
//                System.out.println(getUserEmail);
//                System.out.println(getUserPassword);
//                SQLiteDatabase database = openOrCreateDatabase("Users", MODE_PRIVATE, null);
//                database.execSQL("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)");
//                ContentValues values = new ContentValues();
//                values.put("name", getUserName);
//                values.put("email", getUserEmail);
//                values.put("password", getUserPassword);
//                database.insert("Users", null, values);