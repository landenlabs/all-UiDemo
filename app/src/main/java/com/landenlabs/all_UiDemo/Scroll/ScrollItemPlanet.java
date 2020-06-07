package com.landenlabs.all_UiDemo.Scroll;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;

/**
 * Scrollable Planet item
 */
public class ScrollItemPlanet extends ScrollItem {

    private final String planetName;
    private final int distanceFromSun;
    private int gravity;
    private final int diameter;
    private final int layout;
    private static int cardCnt = 0;

    public ScrollItemPlanet(String planetName, int distanceFromSun, int gravity, int diameter) {
        this.planetName = planetName;
        this.distanceFromSun = distanceFromSun;
        this.gravity = gravity;
        this.diameter = diameter;
        this.layout = ((cardCnt&1) == 1) ? R.layout.scroll_item_planet : R.layout.scroll_item_card;

        cardCnt++;
    }

    @Override
    public ScrollItemPlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater
                .from(parent.getContext()).inflate(layout, parent, false);
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
