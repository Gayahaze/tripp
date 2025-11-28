package com.example.tripp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context tcon;
    private ArrayList<Country> countryList;
    private OnItemClickListener listen;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listen = listener;
    }

    public CountryAdapter(Context context, ArrayList<Country> countryList) {
        this.tcon = context;
        this.countryList = countryList;
    }
    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int pos) {
        Country co = countryList.get(pos);
        holder.namet.setText(co.getName());
        holder.img.setImageResource(co.getImageRes());
    }
    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(tcon).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(v, listen);
    }



    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView namet;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.country_image);
            namet = itemView.findViewById(R.id.country_name);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
