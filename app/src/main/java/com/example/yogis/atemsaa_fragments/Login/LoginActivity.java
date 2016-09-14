package com.example.yogis.atemsaa_fragments.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    //1. Se crean las variables que retienen la informaciion
    EditText usr, pass;
    String sUsr, sPass;
    Button in;
    TextView link;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //2. se recupera la informacion del layout
        usr = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);
        in = (Button) findViewById(R.id.btn_log);

        link = (TextView) findViewById(R.id.linkReg);

        //3. Se implementa los eventos
        in.setOnClickListener(this);

        link.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_log:
                sUsr = usr.getText().toString();
                sPass = pass.getText().toString();

                if(sUsr.equals("admin")&&sPass.equals("123")){
                            //8. Se hace la navegacion hacia el Main, se describe la accion del intent
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                            //9. Se manda al metodo start
                            startActivity(intent);

                            //10. Se finaliza el activity cuando se da el boton de atras
                            finish();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(getApplication(), "Error, Ingrese de nuevo los datos", Toast.LENGTH_SHORT);
                            toast.show();
                        }




                break;

            case R.id.linkReg:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Login","Entro en onResume");
    }



    @Override
    protected void onStop() {
        Log.i("Login","Entro en onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Login","Entro en onRestart");
    }

    @Override
    protected void onDestroy() {
        Log.i("Login","Entro en onDestroy");
        super.onDestroy();
    }
}
