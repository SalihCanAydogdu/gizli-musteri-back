package com.example.gizli_musteri_back.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gizli_musteri_back.requests.DateRangeRequest;
import com.example.gizli_musteri_back.services.GizliMusteriWordService;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/api/gizliMusteriWord")
public class GizliMusteriWordController {

    private final GizliMusteriWordService gizliMusteriWordService;

    public GizliMusteriWordController(GizliMusteriWordService gizliMusteriWordService) {
        this.gizliMusteriWordService = gizliMusteriWordService;
    }

    @PostMapping("/downloadZip")
    public ResponseEntity<FileSystemResource> downloadZip(@RequestBody DateRangeRequest dateRange) {
        try {
            File zipFile = gizliMusteriWordService.generateWordAndZipFiles(dateRange.getStartDate(), dateRange.getEndDate());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(new FileSystemResource(zipFile));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}