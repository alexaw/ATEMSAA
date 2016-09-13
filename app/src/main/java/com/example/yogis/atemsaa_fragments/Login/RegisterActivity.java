package com.example.yogis.atemsaa_fragments.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name, lastname, numId, usr, pass;
    String sName, sLastname, sNumId, sUsr, sPass;
    Button reg;
    ImageButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        lastname = (EditText) findViewById(R.id.lastname);
        numId = (EditText) findViewById(R.id.numId);
        usr = (EditText) findViewById(R.id.usr);
        pass = (EditText) findViewById(R.id.pass);
        floatButton = (ImageButton)findViewById(R.id.imButton);

        reg = (Button) findViewById(R.id.btnReg);

        reg.setOnClickListener(this);

        floatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnReg:

                sName = name.getText().toString();
                sLastname = lastname.getText().toString();
                sNumId = numId.getText().toString();
                sUsr = usr.getText().toString();
                sPass = pass.getText().toString();

                ParseUser user = new ParseUser();

                //put para guardar un nuevo objeto
                user.put("Name", sName);
                user.put("Lastname", sLastname);
                user.put("Ident", sNumId);

                //objetos propios de parse
                user.setUsername(sUsr);
                user.setPassword(sPass);

                //Para poder guardarlos

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Registro Fallido", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                });
            break;

            case R.id.imButton:
                Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Register","Entro en onResume");
    }



    @Override
    protected void onStop() {
        Log.i("Register","Entro en onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Register","Entro en onRestart");
    }

    @Override
    protected void onDestroy() {
        Log.i("Register","Entro en onDestroy");
        super.onDestroy();
    }
}
