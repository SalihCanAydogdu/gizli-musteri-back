package com.example.gizli_musteri_back.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GizliMusteri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String isim;

    @Size(max = 50)
    private String soyad;

    @Size(max = 100)
    private String dogumYeri;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dogumTarihi;

    @Size(max = 20)
    private String medeniDurum;

    @Max(15)
    private Long cocukSayisi;

    @Size(max = 375)
    private String acikAdres;

    @Size(max = 100)
    private String semt;

    @Size(max = 50)
    private String il;

    @Size(max = 50)
    private String ilce;

    @Size(max = 15)
    private String evTelefonu;

    @Size(max = 15)
    private String isTelefonu;

    @Size(max = 15)
    private String mobilTelefon;

    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 50)
    private String egitimDurumu;

    @Size(max = 100)
    private String bildigiYabanciDiller;

    @Size(max = 255)
    private String universiteBilgileri;

    @Size(max = 50)
    private String calismaDurumu;

    @Size(max = 50)
    private String calistigiSektor;

    @Size(max = 50)
    private String boyBedenBilgileri;

    @Size(max = 5)
    private String gozlukKullaniyorMu;

    @Size(max = 5)
    private String lensKullaniyorMu;

    @Size(max = 5)
    private String sigaraKullaniyorMu;

    @Size(max = 5)
    private String ehliyetVarMi;

    @Size(max = 5)
    private String aktifAracKullaniyorMu;

    @Size(max = 5)
    private String otomobilVarMi;

    private Double cekirdekAileGeliri;

    @Size(max = 255)
    private String hobiler;

    @Size(max = 5)
    private String dahaOnceGizliMusteri;

    @Size(max = 120)
    private String calisabilecegiZamanDilimi;

    @Size(max = 100)
    private String calisabilecegiBolgeler;

    @Size(max = 20)
    private String tamAliciAdi;

    @Size(max = 26)
    private String iban;

    @Size(max = 11)
    private String kimlikNumarasi;

    // Kaydolma tarihi alanı
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate kaydolmaTarihi;

    @PrePersist
    protected void onCreate() {
        this.kaydolmaTarihi = LocalDate.now();
    }


    // Fotoğraf dosya adı
    @Size(max = 255)
    private String fotografDosyaAdi;
}
