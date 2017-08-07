package com.au.temp.model;

import com.au.temp.utils.ReservedFile;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.IncorrectOperationException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lytr777 on 20/07/2017.
 */
public class ItemTemplateModel extends AbstractModel implements Model {

    private PsiDirectory directory;
    private String className, templateName, key;
    private LayoutTemplateModel layout;
    private ReservedFile reservedFile;

    public final String EXT = ".java";

    public ItemTemplateModel(String key, PsiDirectory directory, String className, LayoutTemplateModel layout) {
        this.key = key;
        this.directory = directory;
        this.className = getCorrectClassName(className);
        this.layout = layout;
        this.templateName = null;
        reservedFile = new ReservedFile(directory, className + EXT);
    }

    @Override
    public Map<String, String> getNames() {
        Map<String, String> names = new LinkedHashMap<>();
        names.putAll(layout.getNames());
        names.put(key + "_VAR", getVariableName(className));
        names.put(key, className);
        return names;
    }

    @Override
    public void setTemplate(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public void checkReservation() throws IncorrectOperationException {
        layout.checkReservation();
        reservedFile.checkCreate();
    }

    @Override
    public void generate(Project project, Map<String, String> nameSpace) throws IncorrectOperationException {
        if (templateName == null)
            throw new IncorrectOperationException(new Throwable());
        layout.generate(project, nameSpace);
        ThrowableComputable<PsiClass, IncorrectOperationException> tc =
                () -> JavaDirectoryService.getInstance().createClass(directory, className, templateName, false, nameSpace);
        PsiClass psiClass = WriteCommandAction.runWriteCommandAction(project, tc);
    }
}
