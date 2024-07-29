package com.glkids.procurehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.glkids.procurehub.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
