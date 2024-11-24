package com.example.gizli_musteri_back.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import com.example.gizli_musteri_back.entities.GizliMusteri;
import com.example.gizli_musteri_back.repositories.GizliMusteriRepository;
import com.example.gizli_musteri_back.requests.DateRangeRequest;




@RestController
@RequestMapping("/api/zip")
public class ZipController {

    
    @Autowired GizliMusteriRepository gizliMusteriRepository;
	

    @Value("${app.upload.gizliMusteriFotografi.dir}")
    private String fotoKlasoru;

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
