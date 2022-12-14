package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.login1229219.DataBases.Dbhelper;
import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.Models.UsersModel;

public class Register extends AppCompatActivity {
    Button b_register;
    EditText et_password2, et_password, et_username, et_email, et_phone;
    TextView tv_passError;
    RadioGroup rg_type;
    RadioButton rb_normal, rb_other;
    int selectedType;
    NavigationHelper nHelper = new NavigationHelper();

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
        et_phone = findViewById(R.id.et_phone);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Register notification", "Register notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                notifyMe();
                                nHelper.goToLogin(getApplicationContext());

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


    //Go to login activity and pass a string for error message
//    public void goToLogin() {
//        String reg = "You have successfully registered, please login to your account";
//        Intent i = new Intent(getApplicationContext(), Login.class);
//        i.putExtra("registered", reg);
//        startActivity(i);
//    }


    public void createUser() {
        UsersModel usersModel = new UsersModel(-1, et_username.getText().toString() ,et_password.getText().toString(),et_email.getText().toString(),checkTypeForDB(selectedType),
                Integer.parseInt(et_phone.getText().toString()));
        Dbhelper dbHelper = new Dbhelper(getApplicationContext());
        dbHelper.insertUser(usersModel);
    }
    //check type of user
    public int checkTypeForDB(int selectedType) {
        if (selectedType == R.id.rb_normal) {
            return 1;
        }
        return 2;
    }

//Error checkers

    //check if username already exists in db
    public boolean checkSameUsername() {
        Dbhelper dbHelper = new Dbhelper(getApplicationContext());
        if (dbHelper.checkSameUsername(et_username.getText().toString())) {
            return true;
        }
        return false;
    }
    //check if email has already been used
    public boolean checkSameEmail() {
        Dbhelper dbHelper = new Dbhelper(getApplicationContext());
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

    //check if email has been used and if its the correct format
    public boolean correctEmail() {
        String email = et_email.getText().toString();
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && checkSameEmail()) {
            return true;
        }
        return false;
    }
    //check if radiobutton has been selected
    public boolean typeOfUserHasBeenSelected() {
        selectedType = rg_type.getCheckedRadioButtonId();
        if (selectedType == -1) {
            return true;
        }
        return false;
    }

    //error message to use for other functions
    public void errorMessage(String error) {
        tv_passError.setText(error);
        tv_passError.setVisibility(View.VISIBLE);
    }

    public void notifyMe () {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Register notification");
        builder.setContentTitle("Welcome to the Team!");
        builder.setContentText("Thank you for successfully registering!");
        builder.setSmallIcon(R.drawable.ic_baseline_emoji_emotions_24);
        builder.setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(1, builder.build());
    }


}