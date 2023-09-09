package com.dgstore.ui.activity;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dgstore.model.Hash;
import com.dgstore.database.MyDatabase;
import com.dgstore.model.User;
import com.dgstore.databinding.ActivitySignUpBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private ActivitySignUpBinding binding;
    String getUserName;
    String getUserEmail;
    String getUserPassword;
    String email;
    String password;
    MyDatabase myDatabase;
    List<User> users = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    public CallbackManager mCallbackManager;
    public FirebaseAuth mAuth;
    public Hash hash;


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
                signInGoogle();
            }
        });
        
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = binding.facebookButton;
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(TAG, "facebook:onError");
            }
        });

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @SuppressLint("RestrictedApi")
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        }else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);

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
                String hashedString;
                hash = new Hash();

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
                        hashedString = hash.sha256(getUserPassword);
                        System.out.println(hashedString);
                        user.setPassword(hashedString);
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



