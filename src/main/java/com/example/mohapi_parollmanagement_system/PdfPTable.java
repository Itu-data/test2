package com.example.mohapi_parollmanagement_system;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ElementListener;

import java.util.List;

public class PdfPTable implements Element {
    private int widthPercentage;

    public PdfPTable(int i) {
    }

    public void setWidthPercentage(int widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    public void addCell(String description) {
    }

    @Override
    public boolean process(ElementListener elementListener) {
        return false;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public boolean isContent() {
        return false;
    }

    @Override
    public boolean isNestable() {
        return false;
    }

    @Override
    public List<Chunk> getChunks() {
        return List.of();
    }
}
