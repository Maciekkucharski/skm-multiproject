package pl.edu.pjwstk.skmapi.util;

public class Utils {
    public static <T> T fallbackIfNull(T mainChoice, T alternativeChoice) {
        return mainChoice != null
                ? mainChoice
                : alternativeChoice;
    }
}