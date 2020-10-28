package pl.edu.pjwstk.skmclient.model;

import java.util.Random;

public enum Stations {
    GDY_GL,
    GDY_WZG,
    GDY_RED,
    GDY_ORL,
    SOP_KPT,
    SOP_GL,
    SOP_WYS,
    GD_ZAB,
    GD_OLI,
    GD_UNI,
    GD_ZAS,
    GD_WRZ,
    GD_POL,
    GD_STO,
    GD_GL;
    private static Random rand = new Random();
    public static Stations[] number = values();
    public static int numberOfEnums = 15;

    Stations() {
    }


}
