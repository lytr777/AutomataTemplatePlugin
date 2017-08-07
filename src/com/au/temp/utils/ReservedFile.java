package com.au.temp.utils;

import com.intellij.psi.PsiDirectory;
import com.intellij.util.IncorrectOperationException;

/**
 * Created by lytr777 on 20/07/2017.
 */
public class ReservedFile {

    private PsiDirectory directory;
    private String name;

    public ReservedFile(PsiDirectory directory, String name) {
        this.directory = directory;
        this.name = name;
    }

    public void checkCreate() throws IncorrectOperationException {
        directory.checkCreateFile(name);
        System.out.println(directory + "/" + name + " not exist");
    }
}
