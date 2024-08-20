package com.glkids.procurehub.dto;

import com.glkids.procurehub.status.PrcrStatus;
import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrdcMtrlDetailsDTO {
    private Long mtrlno;
    private String name;
    private String standard;
    private Long quantity;
    private Long procureQuantity;
    private Integer leadtime;
    private Integer status;

    public String getStatusString() {
        if (status != null) {
            for (PrcrStatus prcrStatus : PrcrStatus.values()) {
                if (prcrStatus.ordinal() == status) {
                    return prcrStatus.getValue();
                }
            }
        }
        return null;
    }

}
