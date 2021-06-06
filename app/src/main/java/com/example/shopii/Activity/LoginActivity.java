package com.example.shopii.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopii.R;
import com.example.shopii.ultil.showToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin , btnRegister;
    EditText email, password;
    TextView forgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitView();
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }
        });
    }

    private void userLogin(){
        String txtemail = email.getText().toString().trim();
        String txtpassword = password.getText().toString().trim();

        if(txtemail.isEmpty()){
            email.setError("Nhập email của bạn");
            email.requestFocus();
            return;
        }
        if(txtpassword.isEmpty()){
            password.setError("Nhập password của bạn");
            password.requestFocus();
            return;
        }
        if(txtpassword.length() < 6 ){
            password.setError("password phải lớn hơn 6 ký tự");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(txtemail).matches()){
            email.setError("Vui lòng nhập đúng email");
            email.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(txtemail,txtpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    showToast.showToast_Short(getApplicationContext(), "Đăng nhập thành công");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    showToast.showToast_Short(getApplicationContext(), "Đăng nhập thất bại");
                }
            }
        });
    }

    private void InitView() {
        btnLogin = findViewById(R.id.login);
        email = findViewById(R.id.editTextEmail);
        password =findViewById(R.id.editTextPassWord);

        btnRegister = findViewById(R.id.register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword = findViewById(R.id.forgotPassword);
    }
}