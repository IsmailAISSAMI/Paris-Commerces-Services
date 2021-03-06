package com.example.maps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maps.models.ApiCommerces;
import com.example.maps.utils.Constant;
import com.example.maps.utils.FastDialog;
import com.example.maps.utils.Network;
import com.google.gson.Gson;

public class InformationActivity extends AppActivity {
    private EditText editTextCity;
    private TextView textViewNomDuCommerce;
    private TextView textViewAdresse;
    private TextView textViewTelephone;
    private TextView textViewMail;
    private TextView textViewTypeDeCommerce;
    private TextView textViewServices;
    private TextView textViewFabriqueAParis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        editTextCity = findViewById(R.id.editTextCity);
        textViewNomDuCommerce = findViewById(R.id.textViewNomDuCommerce);
        textViewAdresse = findViewById(R.id.textViewAdresse);
        textViewTelephone = findViewById(R.id.textViewTelephone);
        textViewMail = findViewById(R.id.textViewMail);
        textViewTypeDeCommerce = findViewById(R.id.textViewTypeDeCommerce);
        textViewServices = findViewById(R.id.textViewServices);
        textViewFabriqueAParis = findViewById(R.id.textViewFabriqueAParis);

    }

    public void submit(View view) {
        if(editTextCity.getText().toString().isEmpty()) {
            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez renseigner une ville"
            );
            return;
        }
        if(!Network.isNetworkAvailable(InformationActivity.this)) {
            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez être connecté"
            );
            return;
        }
        // requête HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format(Constant.URL, editTextCity.getText().toString(), "metric");
        Log.e("volley", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("volley", "onResponse: "+response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "onErrorResponse: "+error);
                parseJson(new String(error.networkResponse.data));
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void parseJson(String json) {
        textViewNomDuCommerce.setText(null);
        textViewAdresse.setText(null);
        textViewTelephone.setText(null);
        textViewMail.setText(null);
        textViewTypeDeCommerce.setText(null);
        textViewServices.setText(null);
        textViewFabriqueAParis.setText(null);
        // GSON
        ApiCommerces api = new Gson().fromJson(json, ApiCommerces.class);
        if(api.getNhits() > 0) {

            textViewNomDuCommerce.setText(api.getRecords().get(0).getFields().getNom_du_commerce());
            textViewAdresse.setText(api.getRecords().get(0).getFields().getAdresse());
            textViewFabriqueAParis.setText(api.getRecords().get(0).getFields().getFabrique_a_paris());
            textViewMail.setText(api.getRecords().get(0).getFields().getMail());
            textViewServices.setText(api.getRecords().get(0).getFields().getServices());
            textViewTelephone.setText(api.getRecords().get(0).getFields().getTelephone());
            textViewTypeDeCommerce.setText(api.getRecords().get(0).getFields().getType_de_commerce());


        } else {


            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Pas de résultat"
            );
        }
    }
    public void showInformation(View view) {
        Intent intentInformation = new Intent(this, MapsActivity.class);

        //passage d'un paramètre
        //intentInformation.putExtra("isInformation", false);

        startActivity(intentInformation);
    }
}
