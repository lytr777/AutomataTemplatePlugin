package com.au.temp.generate;

import com.au.temp.model.Model;

/**
 * Created by lytr777 on 19/07/2017.
 */
public class Configuration {

    private Model[] models;

    public Configuration(Model... models) {
        this.models = models;
    }

    public Model[] getModels() {
        return models;
    }
}
