package com.example.wk01_hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private TextView errorMessage;
    private EditText password;
    private Button login;
    private List<User> validatedUsers = new ArrayList<>();
    private String passwordText = "admin";
    private static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        errorMessage = findViewById(R.id.errorMessage);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<User>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(!response.isSuccessful()) {
                    errorMessage.setText("Code: "+ response.code());
                }
                List<User> users = response.body();
                validatedUsers.addAll(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                errorMessage.setText(t.getMessage());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUserValid = true;
                boolean isPasswordValid = true;
                errorMessage.setText("");
                if (!validateUser(validatedUsers, username.getText().toString())) {
                    isUserValid = false;
                    errorMessage.setText("Invalid username");
                    //source for how to change bg color: https://stackoverflow.com/questions/18693639/change-background-color-of-edittext-in-android
                    username.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
                if(!validPassword(passwordText, password.getText().toString())){
                    isPasswordValid = false;
                    errorMessage.setText("Invalid password");
                    password.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
                if(isUserValid && isPasswordValid){
                    goToMain(view);
                }
            }
        });
    }

    public static boolean validateUser(List<User> validUsers, String inputtedUsername){
        for (User validUser: validUsers) {
            if (validUser.getUsername().equals(inputtedUsername)) {
                id = validUser.getId();
                return true;
            }
        }
        return false;
    }
    public static boolean validPassword(String password, String inputtedPassword){
        if (password.equals(inputtedPassword)) {
            return true;
        }
        return false;
    }

    public void goToMain(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class );
        intent.putExtra("id", id);
        intent.putExtra("username", username.getText().toString());
        startActivity(intent);
    }

}