package com.ben.contactsapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ben.contactsapp.R;
import com.ben.contactsapp.data.Person;
import com.ben.contactsapp.utils.DateUtils;
import com.ben.contactsapp.utils.ImageLoader;

public class DetailFragment extends Fragment {

    private static final String BUNDLE_KEY = "bundle_key";

    private TextView idView;
    private TextView nameView;
    private TextView descriptionView;
    private TextView timeView;
    private ImageView photoView;

    private Person person;

    public static DetailFragment newInstance(Person person) {
        final DetailFragment fragment = new DetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_KEY, person);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DetailFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        idView = view.findViewById(R.id.idView);
        nameView = view.findViewById(R.id.nameView);
        descriptionView = view.findViewById(R.id.descriptionView);
        timeView = view.findViewById(R.id.timeView);
        photoView = view.findViewById(R.id.photoView);

        if (getArguments() != null && getArguments().containsKey(BUNDLE_KEY)) {
            this.person = getArguments().getParcelable(BUNDLE_KEY);
        }
        if (person != null) {
            idView.setText(String.valueOf(person.getId()));
            nameView.setText(person.getName());
            descriptionView.setText(person.getDescription());
            timeView.setText(DateUtils.formatDate(person.getTime()));
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    ImageLoader.getInstance().loadImage(person.getImageUrl(), photoView);
                });
            }
            //ImageLoader.getInstance().loadImage(person.getImageUrl(), photoView);
        }

        return view;
    }
}
