package com.glkids.procurehub.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExportSearchRepository {
    Page<Object[]> searchExport(String type, String input, Pageable pageable);

}
