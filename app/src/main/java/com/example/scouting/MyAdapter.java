package com.example.scouting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final ArrayList<CardView> itemsList;

    public MyAdapter(ArrayList<CardView> itemsList) {
        this.itemsList = itemsList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView showHideButton;
        public LinearLayout dataHolder;

        public TextView setDateTextView;
        public TextView[] sxTextViews;
        public TextView[] dxTextViews;

        public MyViewHolder(View itemView) {
            super(itemView);

            showHideButton = itemView.findViewById(R.id.show_hide);
            dataHolder = itemView.findViewById(R.id.sets_datas);

            setDateTextView = itemView.findViewById(R.id.setDate);

            sxTextViews = new TextView[]{
                    itemView.findViewById(R.id.opposto_sx_score),
                    itemView.findViewById(R.id.banda_sx_score),
                    itemView.findViewById(R.id.centro_sx_score),
                    itemView.findViewById(R.id.pipe_sx_score),
                    itemView.findViewById(R.id.opposto_sx_pallonetti),
                    itemView.findViewById(R.id.banda_sx_pallonetti),
                    itemView.findViewById(R.id.centro_sx_pallonetti),

                    itemView.findViewById(R.id.servizi_sx)
            };

            dxTextViews = new TextView[]{
                    itemView.findViewById(R.id.opposto_dx_score),
                    itemView.findViewById(R.id.banda_dx_score),
                    itemView.findViewById(R.id.centro_dx_score),
                    itemView.findViewById(R.id.pipe_dx_score),
                    itemView.findViewById(R.id.opposto_dx_pallonetti),
                    itemView.findViewById(R.id.banda_dx_pallonetti),
                    itemView.findViewById(R.id.centro_dx_pallonetti),

                    itemView.findViewById(R.id.servizi_dx)
            };


            showHideButton.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataHolder.getVisibility() == View.VISIBLE) {
                        dataHolder.setVisibility(View.GONE);
                    } else {
                        dataHolder.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CardView currentItem = itemsList.get(position);
        String [] sxDatas = currentItem.getSxDatas();
        String [] dxDatas = currentItem.getDxDatas();

        holder.setDateTextView.setText(currentItem.getSetDate());
        for (int i= 0; i<8;i++) {
            holder.sxTextViews[i].setText(sxDatas[i]);
            holder.dxTextViews[i].setText(dxDatas[i]);
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
