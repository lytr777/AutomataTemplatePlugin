package com.au.temp.utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.ThrowableComputable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Created by lytr777 on 20/07/2017.
 */
public class VirtualFileProvider {

    @Nullable
    private static VirtualFile get(Project project, String additionalPath) {
        return VirtualFileManager.getInstance().findFileByUrl(project.getBaseDir().getUrl() + additionalPath);
    }

    @Nullable
    public static VirtualFile getApp(Project project) {
        return get(project, "/app");
    }

    @Nullable
    public static VirtualFile getSrc(Project project) {
        return get(project, "/app/src");
    }

    @Nullable
    public static VirtualFile getMain(Project project) {
        return get(project, "/app/src/main");
    }

    @Nullable
    public static VirtualFile getJava(Project project) {
        return get(project, "/app/src/main/java");
    }

    @Nullable
    public static VirtualFile getRes(Project project) {
        return get(project, "/app/src/main/res");
    }

    @Nullable
    public static VirtualFile getJavaPackage(Project project, String dotedPackage) {
        return get(project, "/app/src/main/java/" + dotedPackage.replace('.', '/'));
    }

    @Nullable
    private static VirtualFile tryCreate(Project project, VirtualFile parent, String name) throws IOException {
        if (parent != null) {
            ThrowableComputable<VirtualFile, IOException> tc = () -> parent.createChildDirectory(project, name);
            return WriteCommandAction.runWriteCommandAction(project, tc);
        }
        return null;
    }

    @Nullable
    public static VirtualFile getJavaUtilsPackage(Project project, String dotedPackage) throws IOException {
        VirtualFile file = get(project, "/app/src/main/java/" + dotedPackage.replace('.', '/')
                + "/utils");
        if (file == null)
            return tryCreate(project, getJavaPackage(project, dotedPackage), "utils");
        return file;
    }

    @Nullable
    public static VirtualFile getLayout(Project project) throws IOException {
        VirtualFile file = get(project, "/app/src/main/res/layout");
        if (file == null)
            return tryCreate(project, getRes(project), "layout");
        return file;
    }

    @Nullable
    public static VirtualFile getLayoutPort(Project project) throws IOException {
        VirtualFile file = get(project, "/app/src/main/res/layout-port");
        if (file == null)
            return tryCreate(project, getRes(project), "layout-port");
        return file;
    }

    @Nullable
    public static VirtualFile getLayoutLand(Project project) throws IOException {
        VirtualFile file = get(project, "/app/src/main/res/layout-land");
        if (file == null)
            return tryCreate(project, getRes(project), "layout-land");
        return file;
    }
}
