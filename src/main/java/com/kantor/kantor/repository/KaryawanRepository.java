package com.kantor.kantor.repository;

import com.kantor.kantor.entity.Karyawan;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KaryawanRepository extends JpaRepository<Karyawan, Long>, JpaSpecificationExecutor<Karyawan> {
}
