package com.example.simplebackgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpURLConnectionActivity extends AppCompatActivity {
    private TextView textViewHttp;
    private Button button;

    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextEmail;

    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupStatus;

    String gender;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radioGenderMale:
                        gender = (getString(R.string.gender_male));
                        break;
                    case R.id.radioGenderFemale:
                        gender = (getString(R.string.gender_female));
                        break;
                    default:
                        gender = (getString(R.string.gender_other));
                        break;
                }
            }
        });

        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        radioGroupStatus.setOnCheckedChangeListener((RadioGroup radioGroup, int checkedId) -> {
            switch (checkedId) {
                case R.id.radioStatusActive:
                    status = (getString(R.string.status_active));
                    break;
                default:
                    status = (getString(R.string.status_inactive));
            }
        });

        button = this.findViewById(R.id.button);
        button.setOnClickListener(
                (view) -> {
                    callAPI();
                }
        );
        textViewHttp = this.findViewById(R.id.textViewHttp);
    }

    private void callAPI() {

        if (!editTextId.getText().toString().equals("") ) {
            User user = new User(
                    Integer.parseInt(editTextId.getText().toString()),
                    editTextName.getText().toString(),
                    editTextEmail.getText().toString(),
                    gender,
                    status
            );
            if (user.name != null && user.email != null && user.gender != null && user.status != null) {
                addUser(user);
            } else {
                getUserById(user.id);
            }
        } else {
            getAllUsers();
        }

    }

    private void addUser(User user) {
        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                textViewHttp.setText(user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textViewHttp.setText(t.getMessage());
            }
        });
    }

    private void getUserById(int id) {
        ApiClient.getAPI().getUserById(id).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        textViewHttp.setText(user != null ? user.toString(): null);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        textViewHttp.setText(t.getMessage());
                    }
                });
    }

    private void getAllUsers() {
        ApiClient.getAPI().getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> userList = (ArrayList<User>) response.body();
                textViewHttp.setText("Number of Users: " + userList.size());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textViewHttp.setText("Error: " + t.getMessage());
            }
        });
    }

}