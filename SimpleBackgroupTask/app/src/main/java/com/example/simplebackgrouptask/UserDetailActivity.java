package com.example.simplebackgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextEmail;
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupStatus;
    private Button buttonSave;
    private Button buttonDelete;
    private Context context;
    private String gender;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        editTextId = findViewById(R.id.editTextId);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int genderId) {
                switch (genderId) {
                    case R.id.radioGenderFemale:
                        gender = "female";
                        break;
                    case R.id.radioGenderMale:
                        gender = "male";
                        break;
                    default:
                        gender = "other";
                        break;
                }
            }
        });
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        radioGroupStatus.setOnCheckedChangeListener((radioGroup, statusId) -> {
            switch (statusId) {
                case R.id.radioStatusActive:
                    status = "active";
                    break;
                case R.id.radioStatusInactive:
                    status = "inactive";
                    break;
                default:
                    break;
            }
        });

        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        context = this;

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        if (userId != 0) {
            User user = getUserById(userId);
            editTextId.setText(user.id + "");
            editTextName.setText(user.name);
            editTextEmail.setText(user.email);

            switch (user.gender.toLowerCase(Locale.ROOT)) {
                case "female":
                    radioGroupGender.check(R.id.radioGenderFemale);
                    break;
                case "male":
                    radioGroupGender.check(R.id.radioGenderMale);
                    break;
                default:
                    radioGroupGender.check(R.id.radioGenderOther);
                    break;
            }

            switch (user.status.toLowerCase(Locale.ROOT)) {
                case "active":
                    radioGroupStatus.check(R.id.radioStatusActive);
                    break;
                case "inactive":
                    radioGroupStatus.check(R.id.radioStatusInactive);
                    break;
                default:
                    radioGroupStatus.check(R.id.radioStatusInactive);
                    break;
            }

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editUser();
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteUser(Integer.parseInt(editTextId.getText().toString()));
                    Intent intent = new Intent(context, UserListActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            buttonSave.setOnClickListener(view -> {
                addUser();
            });
        }


    }

    private User getUserById(int id) {
        try {
            return ApiClient.getAPI().getUserById(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addUser() {
        try {
            User user = new User();
            user.name = editTextName.getText().toString();
            user.email = editTextEmail.getText().toString();
            user.gender = gender;
            user.status = status;
            ApiClient.getAPI().addUser(user).execute();
            startActivity(new Intent(context, UserListActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
            startActivity(new Intent(context, UserListActivity.class));
        }
    }

    private void editUser() {
        try {
            User user = new User();
            int id = Integer.parseInt(editTextId.getText().toString());
            user.name = editTextName.getText().toString();
            user.email = editTextEmail.getText().toString();
            user.gender = gender;
            user.status = status;
            ApiClient.getAPI().editUser(id, user).execute().body();
            startActivity(new Intent(context, UserListActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
            startActivity(new Intent(context, UserListActivity.class));
        }
    }

    private void deleteUser(int id) {
        try {
            ApiClient.getAPI().deleteUser(id).execute().isSuccessful();
            startActivity(new Intent(context, UserListActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
            startActivity(new Intent(context, UserListActivity.class));
        }
    }
}