package com.regulasiudara.aircargoshippingguidelines;

class DataModel extends ArticleModel {
    private String id, judul;

    public DataModel() {
    }

    public DataModel( String judul) {
        this.judul = judul;
    }

    public String getNama() {
        return judul;
    }

    public void setNama(String judul) {
        this.judul = judul;
    }
}
