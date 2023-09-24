package com.example.university_application;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.university_application.api.ApiResponseModel;
import com.example.university_application.api.MyApiHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView data;
    RecyclerView recyclerView;
    List<Item> items;


//    ProgressBar progressBar = findViewById(R.id.progressBar1);

    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loading = ProgressDialog.show(this, "Loading", "Wait while loading...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        Intent serviceIntent = new Intent(this, DataRefreshForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        }

        recyclerView = findViewById(R.id.recyclerView);
        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    private void loadData() {
        MyApiHelper.fetchDataFromApi(this, new MyApiHelper.MyApiCallback() {
            @Override
            public void onSuccess(List<ApiResponseModel> dataList) {
                loading.hide();
                for (int i = 0; i < 4; i++) {
                    ApiResponseModel item = dataList.get(i);
                    Log.d("ApiResponse", item.toString());
                    items.add(new Item(item.getName(), item.getCountry(), item.getWeb_pages().get(0)));
                }
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("Api Error: ", error.getMessage());
            }
        });
    }
}