package com.example.banty.contextmenu.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.banty.contextmenu.R;
import com.example.banty.contextmenu.model.Superhero;

import java.util.List;

/**
 * Created by banty on 19/08/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnCreateContextMenuListener {

    private static final String TAG = "RecyclerAdapter";

    private final List<Superhero> superheroes;
    private final Context context;

    public RecyclerAdapter(List<Superhero> superheroes, Context context) {
        this.superheroes = superheroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Superhero superhero = superheroes.get(position);

        holder.name.setText(superhero.getTitle());
        holder.franchise.setText(superhero.getProductionCompany());
        holder.realName.setText(superhero.getRealName());
        holder.playedBy.setText(superhero.getPlayedBy());

        Glide.with(context)
                .load(superhero.getImagePath())
                .into(holder.image);
        holder.parentLayout.setOnCreateContextMenuListener(this);

    }

    @Override
    public int getItemCount() {
        return superheroes.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        //add the menu items here
        contextMenu.add("New");
        contextMenu.add("Open");
        contextMenu.add("Delete");

        //context item click is handled in RecyclerFragment
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView franchise;
        TextView realName;
        TextView playedBy;
        ImageView image;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.list_title);
            franchise = itemView.findViewById(R.id.franchise);
            realName = itemView.findViewById(R.id.real_name);
            playedBy = itemView.findViewById(R.id.played_by);
            image = itemView.findViewById(R.id.image);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
