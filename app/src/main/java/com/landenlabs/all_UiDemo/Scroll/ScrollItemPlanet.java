package com.landenlabs.all_UiDemo.Scroll;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landenlabs.all_UiDemo.R;

/**
 * Scrollable Planet item
 */
public class ScrollItemPlanet extends ScrollItem {

    private String planetName;
    private int distanceFromSun;
    private int gravity;
    private int diameter;

    public ScrollItemPlanet(String planetName, int distanceFromSun, int gravity, int diameter) {
        this.planetName = planetName;
        this.distanceFromSun = distanceFromSun;
        this.gravity = gravity;
        this.diameter = diameter;
    }

    @Override
    public ScrollItemPlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.scroll_item_planet, parent, false);
        return new ScrollItemPlanetHolder(view);
    }

    @Override
    public int getItemType() {
        return ItemType.TYPE_PLANET;
    }

    // ---------------------------------------------------------------------------------------------

    public String getPlanetName() {
        return planetName;
    }

    public int getDistanceFromSun() {
        return distanceFromSun;
    }
    public int getGravity() {
        return gravity;
    }
    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    public int getDiameter() {
        return diameter;
    }

    /*
    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }
    public void setDistanceFromSun(int distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
    */
}
