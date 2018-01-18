package com.venomgamz.ade;

/**
 * Created by VENOM on 1/9/2018.
 */

public class DTPaketan {
    private String id;
    private String nama;
    private String harga;
    private String stok;

    public DTPaketan() {
    }

    public DTPaketan(String id, String nama, String harga, String stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }
}
