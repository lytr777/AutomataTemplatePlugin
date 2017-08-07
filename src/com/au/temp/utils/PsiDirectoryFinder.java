package com.au.temp.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Created by lytr777 on 21/07/2017.
 */
public class PsiDirectoryFinder {

    @Nullable
    private static PsiDirectory get(Project project, VirtualFile file) {
        if (file != null)
            return PsiManager.getInstance(project).findDirectory(file);
        return null;
    }

    @Nullable
    public static PsiDirectory getJavaPackage(Project project, String dotedPackage) {
        VirtualFile file = VirtualFileProvider.getJavaPackage(project, dotedPackage);
        return get(project, file);
    }

    @Nullable
    public static PsiDirectory getJavaUtilsPackage(Project project, String dotedPackage) throws IOException {
        VirtualFile file = VirtualFileProvider.getJavaUtilsPackage(project, dotedPackage);
        return get(project, file);
    }

    @Nullable
    public static PsiDirectory getLayout(Project project) throws IOException {
        VirtualFile file = VirtualFileProvider.getLayout(project);
        return get(project, file);
    }

    @Nullable
    public static PsiDirectory getLayoutPort(Project project) throws IOException {
        VirtualFile file = VirtualFileProvider.getLayoutPort(project);
        return get(project, file);
    }

    @Nullable
    public static PsiDirectory getLayoutLand(Project project) throws IOException {
        VirtualFile file = VirtualFileProvider.getLayoutLand(project);
        return get(project, file);
    }
}
