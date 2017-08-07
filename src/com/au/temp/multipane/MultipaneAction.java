package com.au.temp.multipane;

import com.au.temp.generate.GenerationManager;
import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;

/**
 * Created by lytr777 on 17/07/2017.
 */
public class MultipaneAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        MultipaneDialog dialog = new MultipaneDialog(e.getProject());
        dialog.show();
        if (dialog.getExitCode() == 0) {
            GenerationManager manager = new GenerationManager(dialog.getConfiguration());
            manager.generate(e.getProject());
            DaemonCodeAnalyzer.getInstance(e.getProject()).restart();
        }
    }
}
