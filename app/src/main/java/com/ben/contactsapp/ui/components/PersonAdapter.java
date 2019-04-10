package com.ben.contactsapp.ui.components;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ben.contactsapp.R;
import com.ben.contactsapp.data.Person;
import com.ben.contactsapp.listeners.ItemClick;
import com.ben.contactsapp.utils.DateUtils;
import com.ben.contactsapp.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    private List<Person> personList;
    private ItemClick itemClick;

    public PersonAdapter() {
        personList = new ArrayList<>();
    }

    public void setData(List<Person> personList) {
        this.personList = personList;
        notifyDataSetChanged();
    }

    public void setListener(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.persone_item, viewGroup, false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int i) {
        Person person = personList.get(i);
        holder.nameView.setText(person.getName());
        ImageLoader.getInstance().loadImage(person.getImageUrl(), holder.photoView);
        holder.dateView.setText(DateUtils.formatDate(person.getTime()));
        holder.root.setId(i);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView root;
        private TextView nameView;
        private TextView dateView;
        private ImageView photoView;

        PersonHolder(@NonNull View itemView) {
            super(itemView);

            root = (CardView) itemView.findViewById(R.id.root);
            root.setOnClickListener(this);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
            dateView = (TextView) itemView.findViewById(R.id.timeView);
            photoView = (ImageView) itemView.findViewById(R.id.photoView);
        }

        @Override
        public void onClick(View v) {
            if (itemClick != null) {
                itemClick.onItemClick(v, v.getId());
            }
        }
    }
}
