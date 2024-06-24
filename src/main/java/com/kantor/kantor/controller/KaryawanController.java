package com.kantor.kantor.controller;

import com.kantor.kantor.entity.Karyawan;
import com.kantor.kantor.model.request.KaryawanRequest;
import com.kantor.kantor.model.request.SearchKarywanRequest;
import com.kantor.kantor.model.response.KaryawanResponse;
import com.kantor.kantor.service.KaryawanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/karyawan")
public class KaryawanController {
    private final KaryawanService karyawanService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody KaryawanRequest karyawanRequest){
        KaryawanResponse karyawanResponse = karyawanService.create(karyawanRequest);
        return ResponseEntity.ok(karyawanResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String nama,
                                    @RequestParam(required = false, defaultValue = "0") Integer halaman,
                                    @RequestParam(required = false, defaultValue = "10") Integer ukuran){
        SearchKarywanRequest searchKarywanRequest = SearchKarywanRequest.builder()
                .nama(nama)
                .halaman(halaman)
                .ukuran(ukuran)
                .build();
        List<KaryawanResponse> all = karyawanService.getAll(searchKarywanRequest);
        return ResponseEntity.ok(all);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Karyawan karyawan){
        KaryawanResponse update = karyawanService.update(karyawan);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        String delete = karyawanService.delete(id);
        return ResponseEntity.ok(delete);
    }
}
