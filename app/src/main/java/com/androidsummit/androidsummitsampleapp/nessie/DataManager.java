package com.androidsummit.androidsummitsampleapp.nessie;

/**
 * Created by Jose Garcia on 27/08/2016.
 */
public class DataManager {

    private static DataManager instance;

    private DataManager() {

    }

    public static DataManager getInstance() {

        if(instance == null) {
            instance = new DataManager();
        }

        return instance;
    }



}
