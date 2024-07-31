package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.dto.MaterialGroupDTO;
import com.glkids.procurehub.dto.MaterialGroupListDTO;
import com.glkids.procurehub.dto.MaterialWarehouseDTO;
import com.glkids.procurehub.entity.Material;
import com.glkids.procurehub.entity.MaterialGroup;
import com.glkids.procurehub.entity.QMaterialGroup;
import com.glkids.procurehub.repository.MaterialGroupRepository;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialGroupRepository materialGroupRepository;
    private final MaterialWarehouseRepository materialWarehouseRepository;

    @Override
    public List<MaterialDTO> list() {

        List<MaterialDTO> materialDTOList = new ArrayList<>();
        materialRepository.findAll().forEach(material -> {materialDTOList.add(materialEntityToDTO(Optional.ofNullable(material)));});

        return materialDTOList;
    }


    @Override
    public MaterialDTO read(Long mtrlno) {
        return materialEntityToDTO(materialRepository.findById(mtrlno));
    }

    @Override
    public Material register(MaterialDTO materialDTO) {
        return materialRepository.save(materialDTOToEntity(materialDTO));
    }

    @Override
    public void update(MaterialDTO materialDTO) {
        materialRepository.save(materialDTOToEntity(materialDTO));
    }

    @Override
    public int delete(Long mtrlno) {
        if (materialRepository.existsById(mtrlno)) { // 삭제할 항목이 존재하는지 확인
            materialRepository.deleteById(mtrlno);
            return 1; // 삭제 성공
        } else {
            return 0; // 삭제할 항목이 없음
        }
    }

    /**
     * 전체 자재 창고 목록을 불러옵니다.
     * @return {@literal List<MaterialWarehouseDTO>} - MaterialWarehouseDTO의 List객체.
     */
    @Override
    public List<MaterialWarehouseDTO> getWarehouses() {
        List<MaterialWarehouseDTO> warehouses = new ArrayList<>();
        materialWarehouseRepository.findAll().forEach(x->warehouses.add(warehouseEntityToDTO(x)));
        return warehouses;
    }

    /**
     * 자재 그룹 코드가 일치하는 자재 그룹 객체를 불러옵니다.
     * @param grpcode 자재 그룹 코드
     * @return {@code MaterialGroupDTO} - 자재 그룹 Entity를 DTO로 변환한 객체
     */
    @Override
    public MaterialGroupDTO readMaterialGroup(String grpcode) {
        return groupEntityToDTO(materialGroupRepository.findById(grpcode).orElse(new MaterialGroup()));
    }

    /**
     * 같은 depth값을 가지는 (같은 그룹 분류 단계) 모든 자재 그룹 목록을 불러옵니다.
     * @param depth 자재 그룹 객체 내의 값으로 현재 자재 그룹의 계층적 단계를 의미합니다.
     * @return {@code List<MaterialGroupDTO>} - 같은 depth값을 가지는 모든 그룹 객체의 목록을 반환합니다.
     */
    @Override
    public List<MaterialGroupDTO> getMaterialGroupsByDepth(Integer depth) {
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression pDepthExpr = qMaterialGroup.depth.eq(depth);

        List<MaterialGroupDTO> materialGroups = new ArrayList<>();
        materialGroupRepository.findAll(builder.and(pDepthExpr)).forEach(x -> {materialGroups.add(groupEntityToDTO(x));});

        return materialGroups;
    }

    /**
     * 동일한 부모 그룹 코드를 가지는 모든 자재 그룹 객체를 불러옵니다.
     * @param pGrpcode 자재 정보에 포함된 자재 그룹 객체 내, 해당 그룹이 속해있는 부모 그룹의 그룹 코드
     * @return {@code List<MaterialGroupDTO>} <p> 같은 부모 그룹코드를 가지는 모든 그룹 객체
     */
    @Override
    public List<MaterialGroupDTO> getChildMaterialGroups(String pGrpcode) {
        QMaterialGroup qMaterialGroup = QMaterialGroup.materialGroup;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression pGrpcodeExpr = qMaterialGroup.pGrpcode.eq(pGrpcode);

        List<MaterialGroupDTO> materialGroups = new ArrayList<>();
        materialGroupRepository.findAll(builder.and(pGrpcodeExpr)).forEach(x -> {materialGroups.add(groupEntityToDTO(x));});

        return materialGroups;
    }

    /**
     * 자재의 그룹 정보를 바탕으로 상위 계층의 그룹 목록들을 가져옵니다.
     * @param materialGroup 현재 자재가 포함된 자재 그룹 객체
     * @return {@code List<MaterialGroupListDTO>} 를 반환. <p> {@code MaterialGroupListDTO}는 {@code List<MaterialGroupDTO>}와 각 계층에 {@code MaterialGroup}이 속하는 값인 {@code String target} 으로 구성됨.
     */
    @Override
    public List<MaterialGroupListDTO> getMaterialGroupLists(MaterialGroup materialGroup) {
        List<MaterialGroupListDTO> materialGroupList = new ArrayList<>();

        String pgrpcode = materialGroup.getGrpcode();
        String grpcode = pgrpcode;

        while (true) {
            int lastIndex = pgrpcode.lastIndexOf("_");

            if (lastIndex == -1) {
                break;
            } else {
                pgrpcode = pgrpcode.substring(0, lastIndex);
                List<MaterialGroupDTO> materialGroupDTOList = getChildMaterialGroups(pgrpcode);
                materialGroupList.add(MaterialGroupListDTO.builder().materialGroupLists(materialGroupDTOList).target(grpcode).build());
                grpcode = pgrpcode;
            }
        }

        if (materialGroupList.size() < 3) {
            materialGroupList.add(MaterialGroupListDTO.builder().materialGroupLists(getMaterialGroupsByDepth(0)).target(grpcode).build());
        }

        Collections.reverse(materialGroupList);

        return materialGroupList;
    }
}
