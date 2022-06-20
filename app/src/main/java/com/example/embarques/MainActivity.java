package com.example.embarques;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.embarques.models.users;
import com.example.embarques.services.usersApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText ctUsername,ctPassword;
    Button btnLogin;
    users _user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        ctUsername = findViewById(R.id.ctUsername);
        ctPassword = findViewById(R.id.ctPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _user = new users();
                _user.setUsername(ctUsername.getText().toString().trim());
                _user.setPassword(ctPassword.getText().toString().trim());

                Retrofit _retrofit = new Retrofit.Builder()
                        .baseUrl("https://api-transportista.herokuapp.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                usersApi _usersApi = _retrofit.create(usersApi.class);

                Call<List<users>> _call = _usersApi.VerificarLogin(_user);
                _call.enqueue(new Callback<List<users>>() {
                    @Override
                    public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                        if(response.code()==404){
                            Toast.makeText(getApplicationContext(),"Bad Credentials",Toast.LENGTH_LONG).show();
                        }
                        else if(response.code()==200){
                            Toast.makeText(getApplicationContext(),"Welcome "+response.body().get(0).getName(),Toast.LENGTH_LONG).show();
                          /*  startActivity(new Intent(MainActivity.this, InicioActivity.class));*/
                        }
                    }

                    @Override
                    public void onFailure(Call<List<users>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"mal",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
}