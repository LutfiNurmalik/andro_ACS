package com.regulasiudara.aircargoshippingguidelines;

class DataModel {
    private String konten, link, judul;

        public DataModel() {
    }

    public DataModel( String judul, String link, String konten) {
        this.judul = judul;
        this.link = link;
        this.konten = konten;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKonten() {
        return konten;
    }

    public void setKonten(String konten) {
        this.konten = konten;
    }
}
