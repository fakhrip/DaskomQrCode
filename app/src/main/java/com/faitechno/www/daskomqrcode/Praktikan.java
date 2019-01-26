package com.faitechno.www.daskomqrcode;

import java.util.Date;

public class Praktikan {

    private Integer nim;
    private String nama;
    private String kelas;
    private Integer status;
    private String updated_at;

    public Praktikan(Integer nim) {
        this.nim = nim;
    }

    public Praktikan(Integer nim, String nama, String kelas, Integer status, String updated_at) {
        this.nim = nim;
        this.nama = nama;
        this.kelas = kelas;
        this.status = status;
        this.updated_at = updated_at;
    }

    public Integer getNim() {
        return nim;
    }

    public void setNim(Integer nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}