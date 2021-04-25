package com.mocomp.prayertimes.setting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mocomp.prayertimes.R;

import java.util.ArrayList;

public class settingadapter extends ArrayAdapter<settings> {

    public settingadapter(Activity context, ArrayList<settings> setting) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, setting);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListItemView = convertView;
        if(ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        settings currentsetting = getItem(position);
        TextView primarytext = (TextView) ListItemView.findViewById(R.id.primarytext);
        primarytext.setText(currentsetting.getprimarytext());

        TextView secondarytext = (TextView) ListItemView.findViewById(R.id.secondarytext);
        secondarytext.setText(currentsetting.getsecondarytext());

        ImageView theimages = (ImageView) ListItemView.findViewById(R.id.imagee);
        theimages.setImageResource(currentsetting.getMimage());
        if (currentsetting.getsecondarytext() == ""){
            secondarytext.setVisibility(View.GONE);
        }

        return ListItemView;
    }
}
