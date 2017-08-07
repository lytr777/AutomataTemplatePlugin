package com.au.temp.model;

import com.au.temp.utils.ReservedFile;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lytr777 on 20/07/2017.
 */
public class ActivityTemplateModel extends AbstractModel implements Model {

    private PsiDirectory directory;
    private boolean launcher;
    private String className, templateName, key;
    private LayoutTemplateModel[] layouts;
    private PsiClass parent;
    private ReservedFile reservedFile;

    public final String EXT = ".java";

    public ActivityTemplateModel(String key, PsiDirectory directory, String className, boolean launcher,
                                 @Nullable PsiClass parent, LayoutTemplateModel... layouts) {
        this.key = key;
        this.directory = directory;
        this.className = getCorrectClassName(className);
        this.launcher = launcher;
        this.parent = parent;
        this.layouts = layouts;
        this.templateName = null;
        reservedFile = new ReservedFile(directory, className + EXT);
    }

    @Override
    public  Map<String, String> getNames() {
        Map<String, String> names = new LinkedHashMap<>();
        names.putAll(layouts[0].getNames());
        names.put(key, className);
        names.put(key + "_VAR", getVariableName(className));
        return names;
    }

    public void setTemplate(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public void checkReservation() throws IncorrectOperationException {
        for (LayoutTemplateModel layout : layouts)
            layout.checkReservation();
        reservedFile.checkCreate();
    }

    public void generate(Project project, Map<String, String> nameSpace) throws IncorrectOperationException {
        if (templateName == null)
            throw new IncorrectOperationException(new Throwable());
        for (LayoutTemplateModel layout : layouts)
            layout.generate(project, nameSpace);
        ThrowableComputable<PsiClass, IncorrectOperationException> tc =
                () -> JavaDirectoryService.getInstance().createClass(directory, className, templateName, false, nameSpace);
        PsiClass psiClass = WriteCommandAction.runWriteCommandAction(project, tc);
    }
}
