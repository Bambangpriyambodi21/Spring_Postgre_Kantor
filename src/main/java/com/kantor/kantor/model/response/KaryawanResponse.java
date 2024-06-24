package com.kantor.kantor.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KaryawanResponse {
    private String nama;
    private String jabatan;
    private String departemen;
}
