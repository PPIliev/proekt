package com.example.login1229219;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CurrencyConverter extends AppCompatActivity {
    TextView tv_convertFromDropdown, tv_convertToDropdown, tv_conversionRateText;
    EditText et_amountToConvert;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    Button b_conversion;
    String convertFromValue, convertToValue, conversionValue;
    String[] currency = {"EUR", "GBP"};

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);

        tv_convertFromDropdown = findViewById(R.id.tv_convertFromDropdown);
        tv_convertToDropdown = findViewById(R.id.tv_convertToDropdown);
        tv_conversionRateText = findViewById(R.id.tv_conversionRateText);
        b_conversion = findViewById(R.id.b_conversion);
        et_amountToConvert = findViewById(R.id.et_amountToConvert);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        arrayList = new ArrayList<>();
        for (String i : currency) {
            arrayList.add(i);
        }

        tv_convertFromDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(CurrencyConverter.this);
                fromDialog.setContentView(R.layout.from_spinner);
                fromDialog.getWindow().setLayout(650,800);
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.et_search);
                ListView listView = fromDialog.findViewById(R.id.lv_from);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyConverter.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        tv_convertFromDropdown.setText(adapter.getItem(position));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(position);

                    }
                });

            }
        });
        tv_convertToDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDialog = new Dialog(CurrencyConverter.this);
                toDialog.setContentView(R.layout.to_spinner);
                toDialog.getWindow().setLayout(650, 800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.et_search);
                ListView listView = toDialog.findViewById(R.id.lv_from);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CurrencyConverter.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        tv_convertToDropdown.setText(adapter.getItem(position));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(position);
                    }
                });

            }
        });

        b_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Double amountToConvert = Double.valueOf(CurrencyConverter.this.et_amountToConvert.getText().toString());
                    getConversionRate(convertFromValue, convertToValue, amountToConvert);
                } catch (Exception e) {

                }
            }
        });







    }

        public String getConversionRate(String conversionFrom, String convertTo, Double amountToConvert) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.apilayer.com/exchangerates_data/convert?to=" + convertTo +"&from=" + conversionFrom +"&amount=" + amountToConvert + "&apikey=jiblBbApGOcGGp5MbZOJ4PuhzbkCxWRL";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    double cval = response.getDouble("result");

                    conversionValue = String.valueOf(cval);
                    tv_conversionRateText.setText(conversionValue);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
        return null;
    }



}