package com.ben.contactsapp.data;

import java.util.List;

public interface DataManager {

    void loadData(String url, DownloadListener listener);

    List<Person> getData();

    interface DownloadListener {
        void dataUpdate(List<Person> personList);
    }
}
