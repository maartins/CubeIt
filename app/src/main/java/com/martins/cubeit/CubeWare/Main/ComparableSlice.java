package com.martins.cubeit.CubeWare.Main;

import com.martins.cubeit.CubeWare.CubeData.Color;

public class ComparableSlice {
    private Color top_l, top_m, top_r;
    private Color mid_l, mid_m, mid_r;
    private Color bot_l, bot_m, bot_r;

    public ComparableSlice(Color top_l, Color top_m, Color top_r, Color mid_l, Color mid_m, Color mid_r, Color bot_l, Color bot_m, Color bot_r) {
        this.top_l = top_l;
        this.top_m = top_m;
        this.top_r = top_r;
        this.mid_l = mid_l;
        this.mid_m = mid_m;
        this.mid_r = mid_r;
        this.bot_l = bot_l;
        this.bot_m = bot_m;
        this.bot_r = bot_r;
    }

    public Color getTop_l() {
        return top_l;
    }

    public Color getTop_m() {
        return top_m;
    }

    public Color getTop_r() {
        return top_r;
    }

    public Color getMid_l() {
        return mid_l;
    }

    public Color getMid_m() {
        return mid_m;
    }

    public Color getMid_r() {
        return mid_r;
    }

    public Color getBot_l() {
        return bot_l;
    }

    public Color getBot_m() {
        return bot_m;
    }

    public Color getBot_r() {
        return bot_r;
    }
}
