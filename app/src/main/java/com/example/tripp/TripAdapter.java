package com.example.tripp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private ArrayList<TripActivity> activityList;

    private Context context;
     private OnItemClickListener listener;




    @Override
    public void onBindViewHolder(TripAdapter.ViewHolder holder, int position) {
        TripActivity activ = activityList.get(position);

        holder.name.setText(
                "City name: " + activ.getName() +
                        "\nActivity name: " + activ.getType()
        );

        holder.date.setText(activ.getDate() + " - " + activ.getTime());
        holder.priority.setText("Priority: " + activ.getPriority());
        holder.reservation.setText(activ.isNeedsReservation() ? "Needs Reservation ✔" : "Needs Reservation ✖");
        holder.weather.setText("Weather: " + activ.getWeather());

        switch (activ.getType()) {
            case "Beach":
                holder.img.setImageResource(R.drawable.beach);
                break;
            case "Hiking":
                holder.img.setImageResource(R.drawable.hike);
                break;
            case "Shopping":
                holder.img.setImageResource(R.drawable.shopping);
                break;
            case "Museum":
                holder.img.setImageResource(R.drawable.museum);
                break;
            default:
                holder.img.setImageResource(R.drawable.default_image);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new ViewHolder(view, listener);
    }
    public TripAdapter(Context context, ArrayList<TripActivity> activityList) {
        this.context = context;
        this.activityList = activityList;
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void updateList(ArrayList<TripActivity> newLis) {
        activityList.clear();
        activityList.addAll(newLis);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView date;
        TextView   priority;
        TextView  reservation;
        TextView       weather;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.activity_image);
            name = itemView.findViewById(R.id.activity_name);
            date = itemView.findViewById(R.id.activity_date);
            priority = itemView.findViewById(R.id.activity_priority);
            reservation = itemView.findViewById(R.id.activity_reservation);
            weather = itemView.findViewById(R.id.activity_weather);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int po = getAdapterPosition();
                    if (po != RecyclerView.NO_POSITION) {
                        listener.onItemClick(po);
                    }
                }
            });
        }
    }
}
