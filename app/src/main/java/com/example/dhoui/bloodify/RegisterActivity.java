package com.example.dhoui.bloodify;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private ProgressBar progress ;
    private Button inscrire_button ;
    private EditText nom ;
    private EditText prenom;
    private EditText email ;
    private EditText tel ;
    private EditText region ;
    private EditText grpsanguin ;
    private EditText age;
    private EditText datedonation;
    private EditText password;
    private static String URL_REGIST = "http://192.168.1.12/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom = findViewById(R.id.nom_Text);
        prenom = findViewById(R.id.prenom_Text);
        email = findViewById(R.id.email_Text);
        tel = findViewById(R.id.tel_Text);
        region = findViewById(R.id.region_Text);
        grpsanguin = findViewById(R.id.grpsanguin_Text);
        age = findViewById(R.id.age_Text);
        datedonation = findViewById(R.id.datedonation_Text);
        password = findViewById(R.id.password_Text);
        inscrire_button = findViewById(R.id.inscrire_button);
        progress = findViewById(R.id.progress);



        inscrire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(registerIntent);

            }
});
}

private void Regist() {
        progress.setVisibility(View.VISIBLE);
    inscrire_button.setVisibility(View.GONE);

    final String nom = this.nom.getText().toString().trim();
    final String prenom = this.prenom.getText().toString().trim();
    final String email = this.email.getText().toString().trim();
    final int tel = Integer.parseInt(this.tel.getText().toString().trim());
    final String region = this.region.getText().toString().trim();
    final String grpsanguin =this.grpsanguin.getText().toString().trim();
    final int age = Integer.parseInt(this.age.getText().toString().trim());
    final String datedonation = this.datedonation.getText().toString().trim();
    final String password = this.password.getText().toString().trim();

    StringRequest StringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");

                if(success.equals("1")) {
                    Toast.makeText(RegisterActivity.this, "Compte crée !", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Erreur !"+ e.toString(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.VISIBLE);
                inscrire_button.setVisibility(View.GONE);
            }


        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "Erreur !"+ error.toString(), Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.VISIBLE);
                    inscrire_button.setVisibility(View.GONE);
                }
            })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("nom",nom);
            params.put("prenom",prenom);
            params.put("Email",email);
            params.put("tel",tel+ "");
            params.put("region",region);
            params.put("grpsanguin",grpsanguin);
            params.put("age",age+ "");
            params.put("datedonation",datedonation);
            params.put("password",password);
            return params;


        }
    };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(StringRequest);



}}

