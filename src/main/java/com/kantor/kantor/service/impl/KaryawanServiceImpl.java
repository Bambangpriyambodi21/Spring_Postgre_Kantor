package com.kantor.kantor.service.impl;

import com.kantor.kantor.entity.Karyawan;
import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.model.request.KaryawanRequest;
import com.kantor.kantor.model.request.SearchKarywanRequest;
import com.kantor.kantor.model.response.KaryawanResponse;
import com.kantor.kantor.repository.KaryawanRepository;
import com.kantor.kantor.repository.UserCredentialRepository;
import com.kantor.kantor.service.KaryawanService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KaryawanServiceImpl implements KaryawanService {
    private final KaryawanRepository karyawanRepository;
    private final UserCredentialRepository userCredentialRepository;

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
        Optional<UserCredential> byId = userCredentialRepository.findById(Long.valueOf(karyawanRequest.getId_user()));
        Karyawan karyawan = Karyawan.builder()
                .nama(karyawanRequest.getNama())
                .departemen(karyawanRequest.getDepartemen())
                .jabatan(karyawanRequest.getJabatan())
                .gaji(karyawanRequest.getGaji())
                .userCredential(byId.get())
                .build();
        Karyawan save = karyawanRepository.save(karyawan);
        return convert(save);
    }

    @Override
    public List<KaryawanResponse> getAll(SearchKarywanRequest searchKarywanRequest) {
        PageRequest pageRequest = PageRequest.of(searchKarywanRequest.getHalaman(), searchKarywanRequest.getUkuran());
        Specification<Karyawan> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (searchKarywanRequest.getNama()!=null){
                Predicate predicate = criteriaBuilder.like(root.get("nama"), "%"+searchKarywanRequest.getNama()+"%");
                predicates.add(predicate);
            }

            return query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[]{})).getRestriction();
        };
        Page<Karyawan> all = karyawanRepository.findAll(specification, pageRequest);
        List<KaryawanResponse> karyawanResponses = all.getContent().stream()
                .map(item -> KaryawanResponse.builder()
                        .nama(item.getNama())
                        .jabatan(item.getJabatan())
                        .departemen(item.getDepartemen())
                        .build())
                .collect(Collectors.toList());
        return new ArrayList<>(karyawanResponses);
    }

    @Override
    public KaryawanResponse update(KaryawanRequest karyawan) {
        Optional<UserCredential> byId = userCredentialRepository.findById(karyawan.getId());
        Optional<UserCredential> byId1 = userCredentialRepository.findById(karyawan.getId_user());
        Karyawan karyawanUpdate = Karyawan.builder()
                .id(byId.get().getId())
                .nama(karyawan.getNama())
                .departemen(karyawan.getDepartemen())
                .jabatan(karyawan.getJabatan())
                .gaji(karyawan.getGaji())
                .userCredential(byId1.get())
                .build();
        Karyawan save = karyawanRepository.save(karyawanUpdate);
        return convert(save);
    }

    @Override
    public String delete(Long id) {
        karyawanRepository.deleteById(id);
        return "Data karyawan deleted";
    }
}
