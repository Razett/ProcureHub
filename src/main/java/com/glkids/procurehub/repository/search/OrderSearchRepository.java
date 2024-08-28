package com.glkids.procurehub.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderSearchRepository {
    Page<Object[]> searchOrder(String type, String input, Pageable pageable);

}
