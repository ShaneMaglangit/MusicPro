package com.example.musicpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musicpro.R;
import com.example.musicpro.data.Venue;

import java.util.ArrayList;

public class VenueListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Venue> venues;

    public VenueListAdapter(Context context, ArrayList<Venue> venues) {
        this.context = context;
        this.venues = venues;
    }

    @Override
    public int getCount() {
        return venues.size();
    }

    @Override
    public Object getItem(int position) {
        return venues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.venue_list_item, parent, false);
        }

        // get current venue to be displayed
        Venue venue = (Venue) getItem(position);

        // get the TextView for venue name and venue description
        TextView textName = convertView.findViewById(R.id.text_name);
        TextView textAddress = convertView.findViewById(R.id.text_address);

        //sets the text for venue name and venue description from the current venue object
        textName.setText(venue.getName());
        textAddress.setText(venue.getAddress());

        // returns the view for the current row
        return convertView;
    }
}