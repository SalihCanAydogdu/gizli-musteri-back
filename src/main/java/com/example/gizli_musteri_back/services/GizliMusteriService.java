package com.example.gizli_musteri_back.services;

import com.example.gizli_musteri_back.entities.GizliMusteri;
import com.example.gizli_musteri_back.repositories.GizliMusteriRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

@Service
public class GizliMusteriService {

    @Autowired
    private GizliMusteriRepository gizliMusteriRepository;
    
    @Value("${app.upload.gizliMusteriFotografi.dir}")
    private String fotoKlasoru;



public GizliMusteri saveGizliMusteriWithPhoto(GizliMusteri gizliMusteri, MultipartFile file) throws IOException {
    // Fotoğrafı kaydetme işlemi
    if (!file.isEmpty()) {
        // 1. Dosya türünü kontrol et
        String contentType = file.getContentType();
        if (contentType == null || 
            !(contentType.equalsIgnoreCase("image/jpeg") || contentType.equalsIgnoreCase("image/jpg"))) {
            throw new IllegalArgumentException("Yalnızca JPEG dosyaları kabul edilmektedir.");
        }

        // 2. Dosya uzantısını kontrol et
        String fileExtension = getFileExtension(file.getOriginalFilename()).toLowerCase();
        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg")) {
            throw new IllegalArgumentException("Dosya uzantısı sadece .jpg veya .jpeg olmalıdır.");
        }

        // 3. Dosyanın gerçekten bir JPEG olup olmadığını kontrol et
        try (InputStream is = file.getInputStream()) {
            BufferedImage image = ImageIO.read(is);
            if (image == null) {
                throw new IllegalArgumentException("Geçerli bir resim dosyası değil.");
            }

            // Ek olarak, resmin formatını doğrulamak için daha ileri kontroller ekleyebilirsiniz
            // Ancak ImageIO.read zaten dosyanın geçerli bir resim olup olmadığını kontrol eder
        }

        
     // İsim ve soyad içindeki özel karakterleri temizleyin
        String sanitizedIsim = gizliMusteri.getIsim().replaceAll("[^a-zA-Z0-9]", "");
        String sanitizedSoyad = gizliMusteri.getSoyad().replaceAll("[^a-zA-Z0-9]", "");

        // Güvenli bir dosya ismi oluşturun
        String fileName = sanitizedIsim + "_" + sanitizedSoyad + "_" + UUID.randomUUID().toString() + "." + fileExtension;

        
        

        // 5. Klasör yolunu belirleyin ve klasörü oluşturun
        Path uploadPath = Paths.get(fotoKlasoru).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // 6. Dosyayı kaydedin
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 7. Dosya adını gizli müşteri entity'sine ekleyin
        gizliMusteri.setFotografDosyaAdi(fileName);
    }

    return gizliMusteriRepository.save(gizliMusteri);
}

private String getFileExtension(String fileName) {
    if (fileName == null) {
        return "";
    }
    String[] parts = fileName.split("\\.");
    return parts.length > 1 ? parts[parts.length - 1] : "";
}


    public List<GizliMusteri> getAllGizliMusteriler() {
        return gizliMusteriRepository.findAll();
    }

    public Optional<GizliMusteri> getGizliMusteriById(Long id) {
        return gizliMusteriRepository.findById(id);
    }

    public void deleteGizliMusteri(Long id) {
        gizliMusteriRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return gizliMusteriRepository.existsByEmail(email);
    }
    

}