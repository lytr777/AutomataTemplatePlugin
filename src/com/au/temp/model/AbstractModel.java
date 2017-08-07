package com.au.temp.model;

/**
 * Created by lytr777 on 26/07/2017.
 */
public abstract class AbstractModel implements Model {

    public String getCorrectClassName(String className) {
        char[] chars = className.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public String getVariableName(String className) {
        char[] chars = className.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
