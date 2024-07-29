package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.entity.Material;

import java.util.List;

public interface MaterialService {

    List<MaterialDTO> list();

    MaterialDTO read(Long mtrlno);

    void register(MaterialDTO materialDTO);

    int update(MaterialDTO materialDTO);

    int delete(Long mtrlno);
}
