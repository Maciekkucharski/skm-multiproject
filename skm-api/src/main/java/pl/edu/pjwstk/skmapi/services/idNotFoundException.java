package pl.edu.pjwstk.skmapi.services;

public class idNotFoundException extends RuntimeException {
    public idNotFoundException(String msg) {
        super(msg);
    }
}
