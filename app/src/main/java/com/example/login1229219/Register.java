package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    Button b_register;
    EditText et_password2, et_password, et_username, et_email;
    TextView tv_passError;
    RadioGroup rg_type;
    RadioButton rb_normal, rb_other;
    int selectedType;

    Dbhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b_register = findViewById(R.id.b_register);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        tv_passError = findViewById(R.id.tv_passsError);
        rg_type = findViewById(R.id.rg_type);
        rb_normal = findViewById(R.id.rb_normal);
        rb_other = findViewById(R.id.rb_other);



        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedType = rg_type.getCheckedRadioButtonId();


                if (typeOfUserHasBeenSelected()) {
                    String nothingSelected = "You must select a type for your account!";
                    errorMessage(nothingSelected);
                }

                //if type has been selected -> continue
                else {
                    if (checkSameUsername()) {

                    if (passwordMatches()) {

                        if (correctEmail()) {
                            createUser();
                            checkType(selectedType);

                        } else {
                            //Invalid Email
                            String errorInvalidEmail = "You must enter a valid email!";
                            errorMessage(errorInvalidEmail);
                        }
                        //correctEmail end
                    } else {
                        //Password does not match
                        tv_passError.setVisibility(View.VISIBLE);
                    }
                    //password end
                    } else {
                        String sameUsername = "The username is taken";
                        errorMessage(sameUsername);
                    }
                    //sameUsername end


                }
            }
        });


    }

    public void goToMain() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void goToDashboardNormal() {
        Intent i = new Intent(getApplicationContext(),Nuser.class);
        startActivity(i);
    }

    public void goToDashboardOther() {
        Intent i = new Intent(getApplicationContext(),Ouser.class);
        startActivity(i);
    }


    public void createUser() {
        UsersModel usersModel = new UsersModel(-1, et_username.getText().toString() ,et_password.getText().toString(),et_email.getText().toString(),checkTypeForDB(selectedType));
        Dbhelper dbHelper = new Dbhelper(getApplicationContext());
        dbHelper.insertUser(usersModel);
    }

    public int checkTypeForDB(int selectedType) {
        if (selectedType == R.id.rb_normal) {
            return 1;
        }
        return 2;
    }

//Error checkers
    public void checkType(int selectedType) {
        if (selectedType == R.id.rb_normal) {
            goToDashboardNormal();
        }
        goToDashboardOther();
    }


    public boolean checkSameUsername() {
        if (dbHelper.checkSameUsername(et_username.getText().toString())) {
            return true;
        }
        return false;
    }

    public boolean checkSameEmail() {
        if (dbHelper.checkSameEmail(et_email.getText().toString())) {
            return true;
        }
        return false;

    }

    public boolean passwordMatches() {
        if (et_password.getText().toString().equals(et_password2.getText().toString())) {
            return true;
        }
        return false;
    }

    public boolean correctEmail() {
        String email = et_email.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && checkSameEmail()) {
            return true;
        }
        return false;
    }

    public boolean typeOfUserHasBeenSelected() {
        selectedType = rg_type.getCheckedRadioButtonId();
        if (selectedType == -1) {
            return false;
        }
        return true;
    }

    public void errorMessage(String error) {
        tv_passError.setText(error);
        tv_passError.setVisibility(View.VISIBLE);
    }


}