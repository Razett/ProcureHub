package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.PrdcMtrl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PrdcDTO {
    private Long prdcno;
    private String name;

}
