package com.example.university_application.api;

import android.content.Context;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.university_application.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyApiHelper {
    public interface MyApiCallback {
        void onSuccess(List<ApiResponseModel> dataList);

        void onError(VolleyError error);
    }

    public static void fetchDataFromApi(Context context, final MyApiCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://93670173-d9a2-4f98-9568-b4c6f2ea1a5f.mock.pstmn.io/search";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {

                    List<ApiResponseModel> dataList = new ArrayList<>();

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ApiResponseModel data = new ApiResponseModel();

                            data.setName(jsonObject.getString("name"));
                            data.setCountry(jsonObject.getString("country"));
                            data.setWeb_pages(setWebPages(jsonObject.getJSONArray("web_pages")));

                            dataList.add(data);
                        }

                        callback.onSuccess(dataList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                callback::onError);

        queue.add(jsonArrayRequest);
    }

    public static ArrayList<String> setWebPages(JSONArray jsonArray) {
        ArrayList<String> webPagesList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                webPagesList.add(jsonArray.getString(i));
            }
            return webPagesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
