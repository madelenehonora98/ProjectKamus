package com.madelene.projectkamus.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.madelene.projectkamus.Entity.Kamus;
import com.madelene.projectkamus.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.KamusViewholder> {

    private ArrayList<Kamus> listKamuss;
    private Activity activity;
    private ItemClickListener itemClickListener;


    public ArrayList<Kamus> getListKamuss() {
        if(listKamuss==null){
            listKamuss = new ArrayList<Kamus>();

        }

        return listKamuss;
    }

    public void setListKamuss(ArrayList<Kamus> listKamuss){
        this.getListKamuss().clear();
        this.getListKamuss().addAll(listKamuss);
        notifyDataSetChanged();
    }

    @Override
    public KamusViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kata, parent, false);
        return new KamusViewholder(view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(KamusViewholder holder, final int position) {
        holder.tvKata.setText(getListKamuss().get(position).getKata());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(getListKamuss().get(position));
            }
        });

    }

    @Override
    public int getItemCount() {

        return getListKamuss().size();
    }

    public class KamusViewholder extends RecyclerView.ViewHolder{
        TextView tvKata;

        public KamusViewholder(View itemView) {
            super(itemView);
            tvKata = (TextView)itemView.findViewById(R.id.tvKata);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface ItemClickListener {

        void onItemClicked(Kamus kamus);
    }



}
