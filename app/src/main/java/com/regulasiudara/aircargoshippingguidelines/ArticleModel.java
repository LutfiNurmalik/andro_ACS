package com.regulasiudara.aircargoshippingguidelines;

class ArticleModel {
    public String judul;
    public String link;
    public String konten;
    public String isi;

    private String id, nama, privatejudul, privatelink, privatekonten;

    public ArticleModel() {
    }

    public ArticleModel(String id, String nama, String privatejudul, String privatelink, String privatekonten) {
//        this.id = id;
//        this.nama = nama;
        this.privatejudul = privatejudul;
        this.privatelink = privatelink;
        this.privatekonten = privatekonten;
    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getNama() {
//        return nama;
//    }
//
//    public void setNama(String nama) {
//        this.nama = nama;
//    }

    public String getPrivatejudul() {
        return privatejudul;
    }

    public void setPrivatejudul(String privatejudul) {
        this.privatejudul = privatejudul;
    }

    public String getPrivatelink() {
        return privatelink;
    }

    public void setPrivatelink(String privatelink) {
        this.privatelink = privatelink;
    }

    public String getPrivatekonten() {
        return privatekonten;
    }

    public void setPrivatekonten(String privatekonten) {
        this.privatekonten = privatekonten;
    }
}
