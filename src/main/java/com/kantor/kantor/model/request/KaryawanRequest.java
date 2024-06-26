package com.kantor.kantor.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KaryawanRequest {
    private Long id;
    private String nama;
    private String jabatan;
    private String departemen;
    private Long gaji;
    private Long id_user;
}
