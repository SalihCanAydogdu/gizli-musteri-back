package com.example.gizli_musteri_back.services;

import org.springframework.stereotype.Service;

import com.example.gizli_musteri_back.entities.GizliMusteri;
import com.example.gizli_musteri_back.repositories.GizliMusteriRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelService {

    private final GizliMusteriRepository gizliMusteriRepository;

    public ExcelService(GizliMusteriRepository gizliMusteriRepository) {
        this.gizliMusteriRepository = gizliMusteriRepository;
    }

    public ByteArrayInputStream exportGizliMusteriByDateRange(LocalDate startDate, LocalDate endDate) throws IOException {
        List<GizliMusteri> musteriList = gizliMusteriRepository.findByKaydolmaTarihiBetween(startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("GizliMusteriler");

            // Header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("İsim");
            headerRow.createCell(2).setCellValue("Soyad");
            headerRow.createCell(3).setCellValue("Doğum Yeri");
            headerRow.createCell(4).setCellValue("Doğum Tarihi");
            headerRow.createCell(5).setCellValue("Medeni Durum");
            headerRow.createCell(6).setCellValue("Çocuk Sayısı");
            headerRow.createCell(7).setCellValue("Açık Adres");
            headerRow.createCell(8).setCellValue("Semt");
            headerRow.createCell(9).setCellValue("İl");
            headerRow.createCell(10).setCellValue("İlçe");
            headerRow.createCell(11).setCellValue("Ev Telefonu");
            headerRow.createCell(12).setCellValue("İş Telefonu");
            headerRow.createCell(13).setCellValue("Mobil Telefon");
            headerRow.createCell(14).setCellValue("E-mail");
            headerRow.createCell(15).setCellValue("Eğitim Durumu");
            headerRow.createCell(16).setCellValue("Bildiği Yabancı Diller");
            headerRow.createCell(17).setCellValue("Üniversite Bilgileri");
            headerRow.createCell(18).setCellValue("Çalışma Durumu");
            headerRow.createCell(19).setCellValue("Çalıştığı Sektör");
            headerRow.createCell(20).setCellValue("Boy Beden Bilgileri");
            headerRow.createCell(21).setCellValue("Gözlük Kullanıyor mu?");
            headerRow.createCell(22).setCellValue("Lens Kullanıyor mu?");
            headerRow.createCell(23).setCellValue("Sigara Kullanıyor mu?");
            headerRow.createCell(24).setCellValue("Ehliyet Var mı?");
            headerRow.createCell(25).setCellValue("Aktif Araç Kullanıyor mu?");
            headerRow.createCell(26).setCellValue("Otomobil Var mı?");
            headerRow.createCell(27).setCellValue("Çekirdek Aile Geliri");
            headerRow.createCell(28).setCellValue("Hobiler");
            headerRow.createCell(29).setCellValue("Daha Önce Gizli Müşteri?");
            headerRow.createCell(30).setCellValue("Çalışabileceği Zaman Dilimi");
            headerRow.createCell(31).setCellValue("Çalışabileceği Bölgeler");
            headerRow.createCell(32).setCellValue("Tam Alıcı Adı");
            headerRow.createCell(33).setCellValue("IBAN");
            headerRow.createCell(34).setCellValue("Kimlik Numarası");
            headerRow.createCell(35).setCellValue("Kaydolma Tarihi");
            headerRow.createCell(36).setCellValue("Fotoğraf Dosya Adı");  // Yeni fotoğraf dosya adı sütunu

            // Data rows
            int rowIdx = 1;
            for (GizliMusteri musteri : musteriList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(musteri.getId());
                row.createCell(1).setCellValue(musteri.getIsim());
                row.createCell(2).setCellValue(musteri.getSoyad());
                row.createCell(3).setCellValue(musteri.getDogumYeri());
                row.createCell(4).setCellValue(musteri.getDogumTarihi() != null ? musteri.getDogumTarihi().toString() : "");
                row.createCell(5).setCellValue(musteri.getMedeniDurum());
                row.createCell(6).setCellValue(musteri.getCocukSayisi() != null ? musteri.getCocukSayisi().toString() : "");
                row.createCell(7).setCellValue(musteri.getAcikAdres());
                row.createCell(8).setCellValue(musteri.getSemt());
                row.createCell(9).setCellValue(musteri.getIl());
                row.createCell(10).setCellValue(musteri.getIlce());
                row.createCell(11).setCellValue(musteri.getEvTelefonu());
                row.createCell(12).setCellValue(musteri.getIsTelefonu());
                row.createCell(13).setCellValue(musteri.getMobilTelefon());
                row.createCell(14).setCellValue(musteri.getEmail());
                row.createCell(15).setCellValue(musteri.getEgitimDurumu());
                row.createCell(16).setCellValue(musteri.getBildigiYabanciDiller());
                row.createCell(17).setCellValue(musteri.getUniversiteBilgileri());
                row.createCell(18).setCellValue(musteri.getCalismaDurumu());
                row.createCell(19).setCellValue(musteri.getCalistigiSektor());
                row.createCell(20).setCellValue(musteri.getBoyBedenBilgileri());
                row.createCell(21).setCellValue(musteri.getGozlukKullaniyorMu());
                row.createCell(22).setCellValue(musteri.getLensKullaniyorMu());
                row.createCell(23).setCellValue(musteri.getSigaraKullaniyorMu());
                row.createCell(24).setCellValue(musteri.getEhliyetVarMi());
                row.createCell(25).setCellValue(musteri.getAktifAracKullaniyorMu());
                row.createCell(26).setCellValue(musteri.getOtomobilVarMi());
                row.createCell(27).setCellValue(musteri.getCekirdekAileGeliri() != null ? musteri.getCekirdekAileGeliri().toString() : "");
                row.createCell(28).setCellValue(musteri.getHobiler());
                row.createCell(29).setCellValue(musteri.getDahaOnceGizliMusteri());
                row.createCell(30).setCellValue(musteri.getCalisabilecegiZamanDilimi());
                row.createCell(31).setCellValue(musteri.getCalisabilecegiBolgeler());
                row.createCell(32).setCellValue(musteri.getTamAliciAdi());
                row.createCell(33).setCellValue(musteri.getIban());
                row.createCell(34).setCellValue(musteri.getKimlikNumarasi());
                row.createCell(35).setCellValue(musteri.getKaydolmaTarihi() != null ? musteri.getKaydolmaTarihi().toString() : "");
                row.createCell(36).setCellValue(musteri.getFotografDosyaAdi() != null ? musteri.getFotografDosyaAdi() : "");  // Fotoğraf dosya adı hücresi
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
