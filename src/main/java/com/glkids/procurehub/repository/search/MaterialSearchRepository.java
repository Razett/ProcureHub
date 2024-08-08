package com.glkids.procurehub.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MaterialSearchRepository {

    Page<Object[]> searchMaterial(String input, Pageable pageable);
}
