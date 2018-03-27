package com.example.vuclip.cardview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuclip.cardview.R;
import com.example.vuclip.cardview.model.DataModel;

import java.util.List;

/**
 * Created by Banty on 26/03/18.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";

    private List<DataModel> dataSet;
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
        }
    };

    public CustomAdapter(List<DataModel> data) {
        this.dataSet = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(onClick);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(dataSet.get(position).getName());
        holder.textViewVersion.setText(dataSet.get(position).getVersion());
        holder.imageViewIcon.setImageResource(dataSet.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewVersion = itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = itemView.findViewById(R.id.imageView);
        }
    }
}
