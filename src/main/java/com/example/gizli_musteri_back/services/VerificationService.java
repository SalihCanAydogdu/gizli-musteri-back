package com.example.gizli_musteri_back.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





@Service
public class VerificationService {

    // Kullanıcı ismi veya ID ile kodları ilişkilendirebiliriz
    private Map<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();

    // Doğrulama kodunu sakla (username ile ilişkilendirerek)
    public void storeVerificationCode(String username, String code) {
        verificationCodes.put(username, new VerificationCode(code, LocalDateTime.now().plusMinutes(2)));
    }

    // Doğrulama kodunu kontrol et (username ile doğrulama yap)
    public boolean isCodeValid(String username, String code) {
        VerificationCode verificationCode = verificationCodes.get(username);

        if (verificationCode == null) {
            return false;
        }

        // Kodun süresi dolmuşsa sil ve geçersiz say
        if (verificationCode.getExpiryTime().isBefore(LocalDateTime.now())) {
            verificationCodes.remove(username);
            return false;
        }

        return verificationCode.getCode().equals(code);
    }

    private static class VerificationCode {
        private String code;
        private LocalDateTime expiryTime;

        public VerificationCode(String code, LocalDateTime expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
    
    public String getUsernameFromCode(String code) {
        // Kodun doğrulanması için gerekli işlemler
        for (Map.Entry<String, VerificationCode> entry : verificationCodes.entrySet()) {
            if (entry.getValue().getCode().equals(code) && entry.getValue().getExpiryTime().isAfter(LocalDateTime.now())) {
                return entry.getKey(); // username'i döndür
            }
        }
        return null;
    }

    
    
}
