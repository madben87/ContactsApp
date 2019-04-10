package com.ben.contactsapp.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataManagerImpl implements DataManager {

    private static final String TAG = "DataManager";

    private static DataManagerImpl instance;
    private OkHttpClient client;
    private List<Person> personList;

    private DataManagerImpl() {
        this.client = new OkHttpClient();
        personList = new ArrayList<>();
    }

    public static DataManagerImpl getInstance() {
        if (instance == null) {
            instance = new DataManagerImpl();
        }
        return instance;
    }

    @Override
    public void loadData(String url, final DownloadListener listener) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d(TAG, e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    if (listener != null) {
                        String resp = null;
                        try {
                            if (response.body() != null) {
                                resp = response.body().string();
                                Gson gson = new GsonBuilder().create();
                                Type listType = new TypeToken<List<Person>>() {
                                }.getType();
                                List<Person> personList = gson.fromJson(resp, listType);
                                DataManagerImpl.this.personList = personList;
                                listener.dataUpdate(personList);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> getData() {
        return personList;
    }
}
