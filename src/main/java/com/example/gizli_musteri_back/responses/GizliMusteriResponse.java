package com.example.gizli_musteri_back.responses;


import java.time.LocalDate;




public class GizliMusteriResponse {

    private Long id;
    private String isim;
    private String soyad;
    private String dogumYeri;
    private LocalDate dogumTarihi;
    private String medeniDurum;
    private Long cocukSayisi;
    private String acikAdres;
    private String semt;
    private String il;
    private String ilce;
    private String evTelefonu;
    private String isTelefonu;

 
    
    
	private String fotografDosyaAdi;
    
    public String getFotografDosyaAdi() {
		return fotografDosyaAdi;
	}
	public void setFotografDosyaAdi(String fotografDosyaAdi) {
		this.fotografDosyaAdi = fotografDosyaAdi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsim() {
		return isim;
	}
	public void setIsim(String isim) {
		this.isim = isim;
	}
	public String getSoyad() {
		return soyad;
	}
	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}
	public String getDogumYeri() {
		return dogumYeri;
	}
	public void setDogumYeri(String dogumYeri) {
		this.dogumYeri = dogumYeri;
	}
	public LocalDate getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(LocalDate dogumTarihi) {
		this.dogumTarihi = dogumTarihi;
	}
	public String getMedeniDurum() {
		return medeniDurum;
	}
	public void setMedeniDurum(String medeniDurum) {
		this.medeniDurum = medeniDurum;
	}
	public Long getCocukSayisi() {
		return cocukSayisi;
	}
	public void setCocukSayisi(Long cocukSayisi) {
		this.cocukSayisi = cocukSayisi;
	}
	public String getAcikAdres() {
		return acikAdres;
	}
	public void setAcikAdres(String acikAdres) {
		this.acikAdres = acikAdres;
	}
	public String getSemt() {
		return semt;
	}
	public void setSemt(String semt) {
		this.semt = semt;
	}
	public String getIl() {
		return il;
	}
	public void setIl(String il) {
		this.il = il;
	}
	public String getIlce() {
		return ilce;
	}
	public void setIlce(String ilce) {
		this.ilce = ilce;
	}
	public String getEvTelefonu() {
		return evTelefonu;
	}
	public void setEvTelefonu(String evTelefonu) {
		this.evTelefonu = evTelefonu;
	}
	public String getIsTelefonu() {
		return isTelefonu;
	}
	public void setIsTelefonu(String isTelefonu) {
		this.isTelefonu = isTelefonu;
	}
	public String getMobilTelefon() {
		return mobilTelefon;
	}
	public void setMobilTelefon(String mobilTelefon) {
		this.mobilTelefon = mobilTelefon;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEgitimDurumu() {
		return egitimDurumu;
	}
	public void setEgitimDurumu(String egitimDurumu) {
		this.egitimDurumu = egitimDurumu;
	}
	public String getBildigiYabanciDiller() {
		return bildigiYabanciDiller;
	}
	public void setBildigiYabanciDiller(String bildigiYabanciDiller) {
		this.bildigiYabanciDiller = bildigiYabanciDiller;
	}
	public String getUniversiteBilgileri() {
		return universiteBilgileri;
	}
	public void setUniversiteBilgileri(String universiteBilgileri) {
		this.universiteBilgileri = universiteBilgileri;
	}
	public String getCalismaDurumu() {
		return calismaDurumu;
	}
	public void setCalismaDurumu(String calismaDurumu) {
		this.calismaDurumu = calismaDurumu;
	}
	public String getCalistigiSektor() {
		return calistigiSektor;
	}
	public void setCalistigiSektor(String calistigiSektor) {
		this.calistigiSektor = calistigiSektor;
	}
	public String getBoyBedenBilgileri() {
		return boyBedenBilgileri;
	}
	public void setBoyBedenBilgileri(String boyBedenBilgileri) {
		this.boyBedenBilgileri = boyBedenBilgileri;
	}
	public String getGozlukKullaniyorMu() {
		return gozlukKullaniyorMu;
	}
	public void setGozlukKullaniyorMu(String gozlukKullaniyorMu) {
		this.gozlukKullaniyorMu = gozlukKullaniyorMu;
	}
	public String getLensKullaniyorMu() {
		return lensKullaniyorMu;
	}
	public void setLensKullaniyorMu(String lensKullaniyorMu) {
		this.lensKullaniyorMu = lensKullaniyorMu;
	}
	public String getSigaraKullaniyorMu() {
		return sigaraKullaniyorMu;
	}
	public void setSigaraKullaniyorMu(String sigaraKullaniyorMu) {
		this.sigaraKullaniyorMu = sigaraKullaniyorMu;
	}
	public String getEhliyetVarMi() {
		return ehliyetVarMi;
	}
	public void setEhliyetVarMi(String ehliyetVarMi) {
		this.ehliyetVarMi = ehliyetVarMi;
	}
	public String getAktifAracKullaniyorMu() {
		return aktifAracKullaniyorMu;
	}
	public void setAktifAracKullaniyorMu(String aktifAracKullaniyorMu) {
		this.aktifAracKullaniyorMu = aktifAracKullaniyorMu;
	}
	public String getOtomobilVarMi() {
		return otomobilVarMi;
	}
	public void setOtomobilVarMi(String otomobilVarMi) {
		this.otomobilVarMi = otomobilVarMi;
	}
	public Double getCekirdekAileGeliri() {
		return cekirdekAileGeliri;
	}
	public void setCekirdekAileGeliri(Double cekirdekAileGeliri) {
		this.cekirdekAileGeliri = cekirdekAileGeliri;
	}
	public String getHobiler() {
		return hobiler;
	}
	public void setHobiler(String hobiler) {
		this.hobiler = hobiler;
	}
	public String getDahaOnceGizliMusteri() {
		return dahaOnceGizliMusteri;
	}
	public void setDahaOnceGizliMusteri(String dahaOnceGizliMusteri) {
		this.dahaOnceGizliMusteri = dahaOnceGizliMusteri;
	}
	public String getCalisabilecegiZamanDilimi() {
		return calisabilecegiZamanDilimi;
	}
	public void setCalisabilecegiZamanDilimi(String calisabilecegiZamanDilimi) {
		this.calisabilecegiZamanDilimi = calisabilecegiZamanDilimi;
	}
	public String getCalisabilecegiBolgeler() {
		return calisabilecegiBolgeler;
	}
	public void setCalisabilecegiBolgeler(String calisabilecegiBolgeler) {
		this.calisabilecegiBolgeler = calisabilecegiBolgeler;
	}
	public String getTamAliciAdi() {
		return tamAliciAdi;
	}
	public void setTamAliciAdi(String tamAliciAdi) {
		this.tamAliciAdi = tamAliciAdi;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getKimlikNumarasi() {
		return kimlikNumarasi;
	}
	public void setKimlikNumarasi(String kimlikNumarasi) {
		this.kimlikNumarasi = kimlikNumarasi;
	}
	private String mobilTelefon;
    private String email;
    private String egitimDurumu;
    private String bildigiYabanciDiller;
    private String universiteBilgileri;
    private String calismaDurumu;
    private String calistigiSektor;
    private String boyBedenBilgileri;
    private String gozlukKullaniyorMu;
    private String lensKullaniyorMu;
    private String sigaraKullaniyorMu;
    private String ehliyetVarMi;
    private String aktifAracKullaniyorMu;
    private String otomobilVarMi;
    private Double cekirdekAileGeliri;
    private String hobiler;
    private String dahaOnceGizliMusteri;
    private String calisabilecegiZamanDilimi;
    private String calisabilecegiBolgeler;
    private String tamAliciAdi;
    private String iban;
    private String kimlikNumarasi;

    // Getters ve Setters
}