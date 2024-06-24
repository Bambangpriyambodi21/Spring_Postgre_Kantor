package com.kantor.kantor.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchKarywanRequest {
    private String nama;
    private Integer halaman;
    private Integer ukuran;
}
