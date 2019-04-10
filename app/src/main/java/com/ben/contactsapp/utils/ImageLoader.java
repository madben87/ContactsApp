package com.ben.contactsapp.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private static ImageLoader instance;

    private Map<String, Bitmap> images;

    private ImageLoader() {
        images = new HashMap<>();
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public void loadImage(String url, ImageView view) {
        if (images.containsKey(url)) {
            view.setImageBitmap(images.get(url));
        } else {
            LoadTask task = new LoadTask(url, view);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadTask extends AsyncTask<String, Void, Bitmap> {

        private String url;
        private ImageView view;

        LoadTask(String url, ImageView view) {
            this.url = url;
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                images.put(url, bitmap);
                view.setImageBitmap(bitmap);
            }
        }
    }
}
