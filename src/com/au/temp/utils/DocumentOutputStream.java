package com.au.temp.utils;

import com.intellij.openapi.editor.Document;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lytr777 on 24/07/2017.
 */
public class DocumentOutputStream extends OutputStream {

    private Document document;

    public DocumentOutputStream(Document document) {
        this.document = document;
    }

    @Override
    public void write(int b) throws IOException {
        document.insertString(document.getTextLength(), Character.toString((char) b));
    }

    @Override
    public void write(@NotNull byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(@NotNull byte[] b, int off, int len) throws IOException {
        document.insertString(document.getTextLength(), new String(b, off, len));
    }
}
