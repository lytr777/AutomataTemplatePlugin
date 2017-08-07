package com.au.temp.model;

import com.intellij.openapi.project.Project;
import com.intellij.util.IncorrectOperationException;

import java.util.Map;

/**
 * Created by lytr777 on 20/07/2017.
 */
public interface Model {

    Map<String, String> getNames();
    void setTemplate(String templateName);
    void checkReservation() throws IncorrectOperationException;
    void generate(Project project, Map<String, String> nameSpace) throws IncorrectOperationException;
}
