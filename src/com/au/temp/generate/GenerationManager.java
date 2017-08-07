package com.au.temp.generate;

import com.au.temp.model.Model;
import com.intellij.openapi.project.Project;
import com.intellij.util.IncorrectOperationException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lytr777 on 19/07/2017.
 */
public class GenerationManager {

    private Configuration configuration;

    public GenerationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generate(Project project) {
        if (configuration == null) {
            System.out.println("configuration is null");
            throw new NullPointerException("configuration is null");
        }
        try {
            checkReservedFiles();
        } catch (IncorrectOperationException e) {
            e.printStackTrace();
            return;
        }
        Model[] models = configuration.getModels();
        Map<String, String> nameSpace = new LinkedHashMap<>();
        for (Model model : models) {
            nameSpace.putAll(model.getNames());
        }
//        //--
//        for (Map.Entry entry : nameSpace.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        //--
        for (Model model : models) {
            model.generate(project, nameSpace);
        }
    }

    private void checkReservedFiles() {
        for (Model model : configuration.getModels()) {
            model.checkReservation();
        }
    }
}
