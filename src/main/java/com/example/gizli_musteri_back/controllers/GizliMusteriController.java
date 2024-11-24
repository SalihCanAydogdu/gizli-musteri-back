package com.example.gizli_musteri_back.controllers;

import com.example.gizli_musteri_back.entities.GizliMusteri;
import com.example.gizli_musteri_back.repositories.GizliMusteriRepository;
import com.example.gizli_musteri_back.requests.DateRangeRequest;
import com.example.gizli_musteri_back.requests.GizliMusteriRequest;
import com.example.gizli_musteri_back.responses.GizliMusteriResponse;
import com.example.gizli_musteri_back.services.GizliMusteriService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;


import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



@RestController
@RequestMapping("api/gizli-musteri")
public class GizliMusteriController {

 @Autowired
 private GizliMusteriService gizliMusteriService;
 
 @Autowired 
 private GizliMusteriRepository gizliMusteriRepository;
 
 // Yapılandırma dosyasından dosya yolunu enjekte edin
 @Value("${app.upload.gizliMusteriFotografi.dir}")
 private String fotoKlasoru;

 @PostMapping("/kaydet")
 public ResponseEntity<?> saveGizliMusteriWithPhoto(
         @Valid GizliMusteriRequest gizliMusteriRequest,
         @RequestParam("file") MultipartFile file) throws IOException {

     try {
         // Gizli müşteri kaydetme
         GizliMusteri gizliMusteri = convertToEntity(gizliMusteriRequest);

         // E-posta zaten var mı kontrolü
         if (gizliMusteriService.existsByEmail(gizliMusteri.getEmail())) {
             return ResponseEntity.badRequest().body("Bu e-posta adresi zaten kayıtlı.");
         }

         // Fotoğrafı kaydedip gizli müşteri ile ilişkilendir
         GizliMusteri savedGizliMusteri = gizliMusteriService.saveGizliMusteriWithPhoto(gizliMusteri, file);

         // Kaydedilen gizli müşteri response
         GizliMusteriResponse response = convertToResponse(savedGizliMusteri);
         return ResponseEntity.ok(response);

     } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body("Geçersiz dosya: " + e.getMessage());
     } catch (Exception e) {
         return ResponseEntity.status(500).body("Bir hata oluştu: " + e.getMessage());
     }
 }


 // Entity'yi Request'ten dönüştüren metot
 private GizliMusteri convertToEntity(GizliMusteriRequest request) {
     GizliMusteri gizliMusteri = new GizliMusteri();
     gizliMusteri.setIsim(request.getIsim());
     gizliMusteri.setSoyad(request.getSoyad());
     gizliMusteri.setDogumYeri(request.getDogumYeri());
     gizliMusteri.setDogumTarihi(request.getDogumTarihi());
     gizliMusteri.setMedeniDurum(request.getMedeniDurum());
     gizliMusteri.setCocukSayisi(request.getCocukSayisi());
     gizliMusteri.setAcikAdres(request.getAcikAdres());
     gizliMusteri.setSemt(request.getSemt());
     gizliMusteri.setIl(request.getIl());
     gizliMusteri.setIlce(request.getIlce());
     gizliMusteri.setEvTelefonu(request.getEvTelefonu());
     gizliMusteri.setIsTelefonu(request.getIsTelefonu());
     gizliMusteri.setMobilTelefon(request.getMobilTelefon());
     gizliMusteri.setEmail(request.getEmail());
     gizliMusteri.setEgitimDurumu(request.getEgitimDurumu());
     gizliMusteri.setBildigiYabanciDiller(request.getBildigiYabanciDiller());
     gizliMusteri.setUniversiteBilgileri(request.getUniversiteBilgileri());
     gizliMusteri.setCalismaDurumu(request.getCalismaDurumu());
     gizliMusteri.setCalistigiSektor(request.getCalistigiSektor());
     gizliMusteri.setBoyBedenBilgileri(request.getBoyBedenBilgileri());
     gizliMusteri.setGozlukKullaniyorMu(request.getGozlukKullaniyorMu());
     gizliMusteri.setLensKullaniyorMu(request.getLensKullaniyorMu());
     gizliMusteri.setSigaraKullaniyorMu(request.getSigaraKullaniyorMu());
     gizliMusteri.setEhliyetVarMi(request.getEhliyetVarMi());
     gizliMusteri.setAktifAracKullaniyorMu(request.getAktifAracKullaniyorMu());
     gizliMusteri.setOtomobilVarMi(request.getOtomobilVarMi());
     gizliMusteri.setCekirdekAileGeliri(request.getCekirdekAileGeliri());
     gizliMusteri.setHobiler(request.getHobiler());
     gizliMusteri.setDahaOnceGizliMusteri(request.getDahaOnceGizliMusteri());
     gizliMusteri.setCalisabilecegiZamanDilimi(request.getCalisabilecegiZamanDilimi());
     gizliMusteri.setCalisabilecegiBolgeler(request.getCalisabilecegiBolgeler());
     gizliMusteri.setTamAliciAdi(request.getTamAliciAdi());
     gizliMusteri.setIban(request.getIban());
     gizliMusteri.setKimlikNumarasi(request.getKimlikNumarasi());
     gizliMusteri.setFotografDosyaAdi(request.getFotografDosyaAdi());
     return gizliMusteri;
 }

 // Response oluşturma metodu
 private GizliMusteriResponse convertToResponse(GizliMusteri gizliMusteri) {
     GizliMusteriResponse response = new GizliMusteriResponse();
     response.setId(gizliMusteri.getId());
     response.setIsim(gizliMusteri.getIsim());
     response.setSoyad(gizliMusteri.getSoyad());
     response.setDogumYeri(gizliMusteri.getDogumYeri());
     response.setDogumTarihi(gizliMusteri.getDogumTarihi());
     response.setMedeniDurum(gizliMusteri.getMedeniDurum());
     response.setCocukSayisi(gizliMusteri.getCocukSayisi());
     response.setAcikAdres(gizliMusteri.getAcikAdres());
     response.setSemt(gizliMusteri.getSemt());
     response.setIl(gizliMusteri.getIl());
     response.setIlce(gizliMusteri.getIlce());
     response.setEvTelefonu(gizliMusteri.getEvTelefonu());
     response.setIsTelefonu(gizliMusteri.getIsTelefonu());
     response.setMobilTelefon(gizliMusteri.getMobilTelefon());
     response.setEmail(gizliMusteri.getEmail());
     response.setEgitimDurumu(gizliMusteri.getEgitimDurumu());
     response.setBildigiYabanciDiller(gizliMusteri.getBildigiYabanciDiller());
     response.setUniversiteBilgileri(gizliMusteri.getUniversiteBilgileri());
     response.setCalismaDurumu(gizliMusteri.getCalismaDurumu());
     response.setCalistigiSektor(gizliMusteri.getCalistigiSektor());
     response.setBoyBedenBilgileri(gizliMusteri.getBoyBedenBilgileri());
     response.setGozlukKullaniyorMu(gizliMusteri.getGozlukKullaniyorMu());
     response.setLensKullaniyorMu(gizliMusteri.getLensKullaniyorMu());
     response.setSigaraKullaniyorMu(gizliMusteri.getSigaraKullaniyorMu());
     response.setEhliyetVarMi(gizliMusteri.getEhliyetVarMi());
     response.setAktifAracKullaniyorMu(gizliMusteri.getAktifAracKullaniyorMu());
     response.setOtomobilVarMi(gizliMusteri.getOtomobilVarMi());
     response.setCekirdekAileGeliri(gizliMusteri.getCekirdekAileGeliri());
     response.setHobiler(gizliMusteri.getHobiler());
     response.setDahaOnceGizliMusteri(gizliMusteri.getDahaOnceGizliMusteri());
     response.setCalisabilecegiZamanDilimi(gizliMusteri.getCalisabilecegiZamanDilimi());
     response.setCalisabilecegiBolgeler(gizliMusteri.getCalisabilecegiBolgeler());
     response.setTamAliciAdi(gizliMusteri.getTamAliciAdi());
     response.setIban(gizliMusteri.getIban());
     response.setKimlikNumarasi(gizliMusteri.getKimlikNumarasi());
     response.setFotografDosyaAdi(gizliMusteri.getFotografDosyaAdi());
     return response;
 }



 @PostMapping("/downloadFotosAsZipByDate")
 public ResponseEntity<InputStreamResource> downloadFotosAsZipByDate(@RequestBody DateRangeRequest dateRangeRequest) throws IOException {
     
     // DTO'dan tarihleri al
     LocalDate startDate = dateRangeRequest.getStartDate();
     LocalDate endDate = dateRangeRequest.getEndDate();
     
     // Tarih aralığına göre kaydolmuş gizli müşterileri bul
     List<GizliMusteri> musteriList = gizliMusteriRepository.findByKaydolmaTarihiBetween(startDate, endDate);
     
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     
     // Ziplenecek dosyaların dizinini al
     try (ZipOutputStream zs = new ZipOutputStream(baos)) {
         for (GizliMusteri musteri : musteriList) {
             String fotoDosyaAdi = musteri.getFotografDosyaAdi();
             if (fotoDosyaAdi != null) {
                 Path fotoPath = Paths.get(fotoKlasoru, fotoDosyaAdi);
                 if (Files.exists(fotoPath)) {
                     ZipEntry zipEntry = new ZipEntry(fotoDosyaAdi);
                     zs.putNextEntry(zipEntry);
                     Files.copy(fotoPath, zs);
                     zs.closeEntry();
                 }
             }
         }
     }

     ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
     HttpHeaders headers = new HttpHeaders();
     headers.add("Content-Disposition", "attachment; filename=fotograflar_" + startDate + "_to_" + endDate + ".zip");

     return ResponseEntity.ok()
             .headers(headers)
             .contentType(MediaType.APPLICATION_OCTET_STREAM)
             .body(new InputStreamResource(bis));
 }
}