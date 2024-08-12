package com.glkids.procurehub.dto;

import com.glkids.procurehub.entity.MaterialGroup;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MaterialGroupNode {
    private MaterialGroup group;
    private List<MaterialGroupNode> children = new ArrayList<>();

    public MaterialGroupNode(MaterialGroup group) {
        this.group = group;
    }

}
