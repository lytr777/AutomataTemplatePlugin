package com.au.temp.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lytr777 on 24/07/2017.
 */
public class XmlTemplateLoader {


    private XmlTemplateLoader() {}

    public String getTemplateString(String name) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("fileTemplates/xml/" + name);
        String content = "";
        int length = 1024;
        byte[] buffer = new byte[length];
        int i;
        while ((i = is.read(buffer,0, length)) > 0) {
            content = content.concat(new String(buffer, 0, i));
        }
        return content;
    }

    private static XmlTemplateLoader ourInstance = new XmlTemplateLoader();

    public static XmlTemplateLoader getInstance() {
        return ourInstance;
    }
}
