package com.au.temp.model;

import com.au.temp.utils.ReservedFile;
import com.au.temp.utils.XmlTemplateLoader;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lytr777 on 20/07/2017.
 */
public class LayoutTemplateModel implements Model {

    private PsiDirectory layoutDirectory;
    private String layoutName, templateName, key;
    private ReservedFile reservedFile;

    public static final String EXT = ".xml";

    public LayoutTemplateModel(String key, PsiDirectory layoutDirectory, String layoutName) {
        this.key = key;
        this.layoutDirectory = layoutDirectory;
        this.layoutName = layoutName;
        this.templateName = null;
        reservedFile = new ReservedFile(layoutDirectory, layoutName + EXT);
    }

    @Override
    public Map<String, String> getNames() {
        Map<String, String> names = new LinkedHashMap<>();
        names.put(key, layoutName);
        return names;
    }

    public void setTemplate(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public void checkReservation() throws IncorrectOperationException {
        reservedFile.checkCreate();
    }

    public void generate(Project project, Map<String, String> nameSpace) throws IncorrectOperationException {
        if (templateName == null)
            throw new IncorrectOperationException(new Throwable());
        ThrowableComputable<PsiFile, IncorrectOperationException> tc;
//        Template template;
        String template;
        try {
//            template = XmlTemplateLoader.getInstance().getTemplateByName(templateName);
            template = XmlTemplateLoader.getInstance().getTemplateString(templateName);
        } catch (IOException e) {
            throw new IncorrectOperationException(e.getMessage(), e.getCause());
        }

        tc = () -> {
            PsiFile file = layoutDirectory.createFile(layoutName + EXT);
            Document document = PsiDocumentManager.getInstance(project).getDocument(file);
            if (document != null) {
                document.setText(template);
//                DocumentOutputStream dos = new DocumentOutputStream(document);
//                OutputStreamWriter osw = new OutputStreamWriter(dos);
//                try {
//                    template.process(null, osw);
//                } catch (TemplateException | IOException e) {
//                    throw new IncorrectOperationException(e.getMessage(), e.getCause());
//                }
            }
            return file;
        };

        WriteCommandAction.runWriteCommandAction(project, tc);
    }
}

