package com.example.gizli_musteri_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gizli_musteri_back.requests.DateRangeRequest;
import com.example.gizli_musteri_back.services.ExcelService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/gizli-musteriler/export")
    public ResponseEntity<InputStreamResource> exportGizliMusterilerToExcel(@RequestBody DateRangeRequest dateRangeRequest) throws IOException {

        ByteArrayInputStream in = excelService.exportGizliMusteriByDateRange(dateRangeRequest.getStartDate(), dateRangeRequest.getEndDate());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=gizli_musteriler.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));
    }
}
