package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.*;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.MaterialFileRepository;
import com.glkids.procurehub.repository.MaterialGroupRepository;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.MaterialWarehouseRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialGroupRepository materialGroupRepository;
    private final MaterialWarehouseRepository materialWarehouseRepository;
    private final MaterialFileRepository materialFileRepository;

    @Override
    public List<MaterialDTO> list(String input) {
        List<MaterialDTO> materialDTOList = new ArrayList<>();
//        materialRepository.findAll().forEach(material -> {materialDTOList.add(materialEntityToDTO(Optional.ofNullable(material)));});

        Page<Object[]> result = materialRepository.searchMaterial(input, PageRequest.of(0, 500, Sort.by(Sort.Direction.ASC, "mtrlno")));
        result.getContent().forEach(object -> {
            materialDTOList.add(materialObjectToDto(object));
        });
        return materialDTOList;
    }

    @Override
    public MaterialDTO read(Long mtrlno) {
        return materialEntityToDTO(materialRepository.findById(mtrlno));
    }

    @Override
    public MaterialDTO readByFetch(Long mtrlno) {
        Optional<Material> materialOpt = materialRepository.findByIdFetch(mtrlno);
        return materialEntityToDTO(materialOpt);
    }

    /**
     * 입력 한 값을 포함하는 자재 명을 가지는 자재 검색
     * @param name 자재 명 검색 입력 값
     * @return 자재 목록
     */
    @Override
    public List<Material> findByNameContaining(String name) {
        return materialRepository.findByMtrlContaining(name);
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
     * 자재 창고를 데이터베이스에 추가합니다.
     * @param materialWarehouseDTO 추가할 자재 창고 객체
     * @return 자재 창고 등록 성공 시 자재 창고 객체, 실패 시 null.
     */
    @Override
    public MaterialWarehouseDTO registerMaterialWarehouse(MaterialWarehouseDTO materialWarehouseDTO) {
        materialWarehouseRepository.save(warehouseDtoToEntity(materialWarehouseDTO));
        return materialWarehouseRepository.existsById(materialWarehouseDTO.getWrhscode()) ? materialWarehouseDTO : null;
    }

    @Override
    public Boolean deleteMaterialWarehouse(String wrhscode) {
        QMaterial qMaterial = QMaterial.material;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression eqWrhscodeExpr = qMaterial.materialWarehouse.eq(MaterialWarehouse.builder().wrhscode(wrhscode).build());

        List<MaterialDTO> materialDtoList = new ArrayList<>();
        List<Material> materialList = new ArrayList<>();
        MaterialWarehouse materialWarehouse = MaterialWarehouse.builder().wrhscode("ETC").build();
        materialRepository.findAll(builder.and(eqWrhscodeExpr)).forEach(x->materialDtoList.add(materialEntityToDTO(Optional.ofNullable(x))));

        materialDtoList.forEach(x -> {
            x.setMaterialWarehouse(materialWarehouse);
            materialList.add(materialDTOToEntity(x));
        });

        materialRepository.saveAll(materialList);
        materialWarehouseRepository.delete(MaterialWarehouse.builder().wrhscode(wrhscode).build());

        return !materialWarehouseRepository.existsById(wrhscode);
    }

    /**
     *
     * @param wrhscode 중복을 확인할 자재 창고 코드
     * @return 중복 시 false, 사용가능 시 true.
     */
    @Override
    public Boolean verifyWrhscode(String wrhscode) {
        return !materialWarehouseRepository.existsById(wrhscode);
    }

    /**
     *
     * @param grpcode 중복을 확인할 자재 그룹 코드
     * @return 중복시 false, 사용 가능 시 true.
     */
    @Override
    public Boolean verifyGrpcode(String grpcode) {
        return !materialGroupRepository.existsById(grpcode);
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

    /**
     * 현재 자재 그룹 객체의 상위 경로를 가져옵니다.
     * @param materialGroup 현재 자재 그룹 정보 객체
     * @return 현재 자재 그룹 객체를 포함하는 상위 그룹 객체들
     */
    @Override
    public List<MaterialGroupDTO> getMaterialGroupDirection(MaterialGroup materialGroup) {
        List<MaterialGroupDTO> direction = new ArrayList<>();
        direction.add(groupEntityToDTO(materialGroup));

        String pgrpcode = materialGroup.getPGrpcode();

        for (int i = 0; i < materialGroup.getDepth(); i++) {
            MaterialGroupDTO dto = readMaterialGroup(pgrpcode);
            direction.add(dto);
            pgrpcode = dto.getPGrpcode();
        }
        Collections.reverse(direction);
        return direction;
    }

    public List<MaterialGroupNode> getAllMaterialGroups() {
        List<MaterialGroup> allGroups = materialGroupRepository.findAllMaterialGroups();

        // 맵을 사용하여 그룹 코드와 노드 간의 매핑을 생성
        Map<String, MaterialGroupNode> groupMap = new HashMap<>();

        // 노드 생성
        for (MaterialGroup group : allGroups) {
            groupMap.put(group.getGrpcode(), new MaterialGroupNode(group));
        }

        // 자식 노드를 부모 노드에 추가
        for (MaterialGroupNode node : groupMap.values()) {
            String parentCode = node.getGroup().getPGrpcode();
            if (parentCode != null) {
                MaterialGroupNode parent = groupMap.get(parentCode);
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        // 최상위 노드만 추출
        List<MaterialGroupNode> topNodes = groupMap.values().stream()
                .filter(node -> node.getGroup().getPGrpcode() == null)
                .collect(Collectors.toList());

        return topNodes;
    }

    /**
     * 자재 그룹을 추가합니다.
     * @param materialGroupDTO 추가할 자재 그룹
     * @return
     */
    @Override
    public Boolean registerMaterialGroup(MaterialGroupDTO materialGroupDTO) {
        if (materialGroupDTO.getPGrpcode() == null || materialGroupDTO.getPGrpcode().isEmpty()) { // 부모 그룹이 없는 경우
            materialGroupDTO.setDepth(0);
            materialGroupDTO.setPGrpcode(null);
        } else { // 부모 그룹이 있는 경우
            Integer depth = materialGroupRepository.findById(materialGroupDTO.getPGrpcode()).get().getDepth() + 1;
            materialGroupDTO.setDepth(depth);
        }

        materialGroupRepository.save(groupDTOToEntity(materialGroupDTO));
        return materialGroupRepository.existsById(materialGroupDTO.getGrpcode());
    }

    /**
     * 자재 그룹의 이름을 업데이터 합니다.
     * @param materialGroupDTO 자재 그룹 이름을 변경할 그룹 객체
     * @return 변경된 행의 수를 통해 변경 시 true, 미 변경 시 false를 리턴합니다.
     */
    @Transactional
    @Override
    public Boolean updateMaterialGroup(MaterialGroupDTO materialGroupDTO) {
        Integer i = materialGroupRepository.updateMaterialGroup(materialGroupDTO.getName(), materialGroupDTO.getGrpcode());
        return i > 0;
    }

    @Override
    public MaterialFile saveMaterialFile(MaterialFileDTO materialFileDTO) {
        MaterialFile materialFile = materialFileDTOToEntity(materialFileDTO);
        return materialFileRepository.save(materialFile).getMtrlfno() != null ? materialFile : null;
    }

    @Override
    public List<MaterialFileDTO> materialFileList(Long mtrlno) {
        List<MaterialFileDTO> materialFileDTOList = new ArrayList<>();

        QMaterialFile qMaterialFile = QMaterialFile.materialFile;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression mtrlExp = qMaterialFile.material.mtrlno.eq(mtrlno);

        materialFileRepository.findAll(builder.and(mtrlExp)).forEach(x -> materialFileDTOList.add(materialFileEntityToDTO(x)));
        return materialFileDTOList;
    }
}
