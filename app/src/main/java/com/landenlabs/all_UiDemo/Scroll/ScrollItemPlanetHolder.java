package com.landenlabs.all_UiDemo.Scroll;

import android.view.View;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;

import java.util.Locale;

/**
 * Recycler View Holder for Planet item
 */
public class ScrollItemPlanetHolder extends ScrollItemHolder {

    private TextView txtName, txtDistance, txtGravity, txtDiameter;

    public ScrollItemPlanetHolder(View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.txtName);
        txtDistance = itemView.findViewById(R.id.txtDistance);
        txtGravity = itemView.findViewById(R.id.txtGravity);
        txtDiameter = itemView.findViewById(R.id.txtDiameter);
    }

    public void onBindViewHolder(ScrollItem item) {
        if (item instanceof ScrollItemPlanet) {
            ScrollItemPlanet planet = (ScrollItemPlanet) item;
            txtName.setText(planet.getPlanetName());
            txtDistance.setText(String.format(Locale.US, "Distance from Sun : %d Million KM",
                    planet.getDistanceFromSun()));
            txtGravity.setText(
                    String.format(Locale.US, "Surface Gravity : %d N/kg", planet.getGravity()));
            txtDiameter.setText(String.format(Locale.US, "Diameter : %d KM", planet.getDiameter()));
        }
    }

}