package com.kantor.kantor.service;

import com.kantor.kantor.entity.Karyawan;
import com.kantor.kantor.model.request.KaryawanRequest;
import com.kantor.kantor.model.response.KaryawanResponse;

import java.util.List;

public interface KaryawanService {
    KaryawanResponse create(KaryawanRequest karyawanRequest);
    List<KaryawanResponse> getAll();
    KaryawanResponse update(Karyawan karyawan);
    String delete(Long id);
}
