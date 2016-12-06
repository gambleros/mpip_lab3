package com.example.aleksandar.lab3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleksandar.lab3.Data.Maps;
import com.example.aleksandar.lab3.Data.Result;

/**
 * Created by Aleksandar on 06.12.2016.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private Maps mMaps;

    static class LocationViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;
        public LocationViewHolder(View itemView) {
            super(itemView);
            mTitleTextView=(TextView) itemView;
        }
    }

    public LocationAdapter(Maps maps) {
        mMaps = maps;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Result res=mMaps.results.get(position);
        holder.mTitleTextView.setText(res.name);
    }

    @Override
    public int getItemCount() {
        return mMaps.results.size();
    }


}
