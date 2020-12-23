package pl.edu.pjwstk.skmapi.model;

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

    public boolean isLast() {
        return this.ordinal() == (number.length - 1);
    }

    public boolean isFirst() {
        return this.ordinal() == 0;
    }

    public Stations move(boolean toGdynia) {
        if (!toGdynia) {
            int stationIndex = this.ordinal() + 1;
            return number[stationIndex];
        } else {
            int stationIndex = this.ordinal() - 1;
            return number[stationIndex];
        }
    }

    public static Stations randomNextStation(Skm skm) {
        int tempStation = skm.getStation().ordinal();
        if (!skm.getToGdynia()) {
            int endpointIndex = rand.nextInt(15 - tempStation) + tempStation;
            return number[endpointIndex];
        } else {
            int endpointIndex = rand.nextInt(tempStation);
            return number[endpointIndex];
        }
    }


}
