package com.au.temp.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by lytr777 on 28/07/2017.
 */
public class MyToolWindow implements ToolWindowFactory {

    private ToolWindow toolWindow;
    private JPanel contentPane;
    private JTextArea text;

    public MyToolWindow() {
        text.setText("\n" +
                "||=============||     onCreate     |-----------|    onCreateView    |---------------|\n" +
                "||             || ---------------> |           | -----------------> |               |\n" +
                "||  Destroyed  ||     onDestroy    |  Created  |    onDestroyView   |  ViewCreated  |\n" +
                "||             || <--------------- |           | <----------------- |               |\n" +
                "||=============||                  |-----------|                    |---------------|\n" +
                "\n" +
                "\n" +
                "\n" +
                "|-------------||     onCreate     ||-----------|    onCreateView    |---------------|\n" +
                "|             || ===============> ||           | -----------------> |               |\n" +
                "|  Destroyed  ||     onDestroy    ||  Created  |    onDestroyView   |  ViewCreated  |\n" +
                "|             || <--------------- ||           | <----------------- |               |\n" +
                "|-------------||                  ||-----------|                    |---------------|\n" +
                "\n" +
                "\n" +
                "\n" +
                "|-------------|     onCreate     ||===========||    onCreateView    |---------------|\n" +
                "|             | ---------------> ||           || -----------------> |               |\n" +
                "|  Destroyed  |     onDestroy    ||  CREATED  ||    onDestroyView   |  ViewCreated  |\n" +
                "|             | <--------------- ||           || <----------------- |               |\n" +
                "|-------------|                  ||===========||                    |---------------|");
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(contentPane, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
