package com.kantor.kantor.service.impl;

import com.kantor.kantor.entity.Karyawan;
import com.kantor.kantor.model.request.KaryawanRequest;
import com.kantor.kantor.model.response.KaryawanResponse;
import com.kantor.kantor.repository.KaryawanRepository;
import com.kantor.kantor.service.KaryawanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KaryawanServiceImpl implements KaryawanService {
    private final KaryawanRepository karyawanRepository;

    private KaryawanResponse convert(Karyawan karyawan){
        KaryawanResponse karyawanResponse = KaryawanResponse.builder()
                .nama(karyawan.getNama())
                .departemen(karyawan.getDepartemen())
                .jabatan(karyawan.getJabatan())
                .build();
        return karyawanResponse;
    }

    @Override
    public KaryawanResponse create(KaryawanRequest karyawanRequest) {
        Karyawan karyawan = Karyawan.builder()
                .nama(karyawanRequest.getNama())
                .departemen(karyawanRequest.getDepartemen())
                .jabatan(karyawanRequest.getJabatan())
                .gaji(karyawanRequest.getGaji())
                .build();
        Karyawan save = karyawanRepository.save(karyawan);
        return convert(save);
    }

    @Override
    public List<KaryawanResponse> getAll() {
        List<Karyawan> all = karyawanRepository.findAll();
        List<KaryawanResponse> karyawanResponses = new ArrayList<>();
        for (int i =0; i<all.size();i++){
            karyawanResponses.add(convert(all.get(i)));
        }
        return karyawanResponses;
    }

    @Override
    public KaryawanResponse update(Karyawan karyawan) {
        Karyawan karyawanUpdate = Karyawan.builder()
                .id(karyawan.getId())
                .nama(karyawan.getNama())
                .departemen(karyawan.getDepartemen())
                .jabatan(karyawan.getJabatan())
                .gaji(karyawan.getGaji())
                .build();
        Karyawan save = karyawanRepository.save(karyawan);
        return convert(save);
    }

    @Override
    public String delete(Long id) {
        karyawanRepository.deleteById(id);
        return "Data karyawan deleted";
    }
}
