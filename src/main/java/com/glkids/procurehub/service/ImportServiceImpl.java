package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.ImportInspectionDTO;
import com.glkids.procurehub.dto.MaterialDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.ImportInspectionRepository;
import com.glkids.procurehub.repository.ImportRepository;
import com.glkids.procurehub.repository.MaterialRepository;
import com.glkids.procurehub.repository.PrcrRepository;
import com.glkids.procurehub.status.ImportStatus;
import com.glkids.procurehub.status.InspectionStatus;
import com.glkids.procurehub.status.OrderStatus;
import com.glkids.procurehub.status.PrcrStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final ImportRepository importRepository;
    private final ImportInspectionRepository importInspectionRepository;
    private final MaterialService materialService;
    private final MaterialRepository materialRepository;
    private final PrcrRepository prcrRepository;

    @Deprecated
    @Override
    public List<ImportDTO> list() {
        List<Imports> imports = importRepository.findAll();
        List<ImportDTO> importDTOList = new ArrayList<>();
        imports.forEach(x -> importDTOList.add(importEntityToDto(x)));
        return importDTOList;
    }

    @Override
    public List<ImportDTO> getImportListBefore() {
        List<ImportDTO> list = new ArrayList<>();
        QImports qImports = QImports.imports;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qImports.status.lt(ImportStatus.CONTINUING.ordinal());

        importRepository.findAll(builder.and(statusExp), Sort.by(Sort.Direction.ASC, "importno")).forEach(x -> list.add(importEntityToDto(x)));
        return list;
    }

    @Override
    public List<ImportDTO> getImportListAfter() {
        List<ImportDTO> list = new ArrayList<>();
        QImports qImports = QImports.imports;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression statusExp = qImports.status.gt(ImportStatus.CONTINUING.ordinal());
        BooleanExpression statusExp2 = qImports.status.lt(ImportStatus.INSPECTING_WITH_FAILED.ordinal());

        importRepository.findAll(builder.and(statusExp).and(statusExp2), Sort.by(Sort.Direction.ASC, "importno")).forEach(x -> list.add(importEntityToDto(x)));
        return list;
    }

    @Override
    public List<ImportDTO> totalList(String type, String input) {
        List<ImportDTO> importDTOList = new ArrayList<>();

        Page<Object[]> result = importRepository.searchImport(type, input, PageRequest.of(0, 500, Sort.by(Sort.Direction.DESC, "importno")));

        result.getContent().forEach(object -> {
            importDTOList.add(importObjectToDTO(object));
        });
        return importDTOList;
    }

    @Override
    public List<ImportDTO> readImportForm(Long importno) {
        List<ImportDTO> list = new ArrayList<>();
        Imports im = importRepository.findById(importno).orElseGet(null);

        if (im != null) {
            importRepository.getImportForm(im.getOrder().getQuotationmtrl().getQuotation().getContractor().getCorno(), im.getApprovedate().toLocalDate(), OrderStatus.OK.ordinal()).forEach(x -> {
                list.add(importEntityToDto(x));
            });
        }
        return list;
    }

    @Override
    public ImportDTO readRestful(Long importno) {
        ImportDTO importDTO = new ImportDTO();
        Imports imports = importRepository.findById(importno).orElseGet(null);

        if (imports != null) {
            importDTO.setImportno(imports.getImportno());
            importDTO.setApprovedate(imports.getApprovedate());
            importDTO.setArrivaldate(imports.getArrivaldate());
            importDTO.setReceiver(imports.getReceiver());
            importDTO.setApprover(imports.getApprover());
            importDTO.setQuantity(imports.getQuantity());
            importDTO.setStatus(imports.getStatus());

            Order order;
            if (imports.getOrder() != null) {
                if (imports.getOrder().getOrderdate() != null) {
                    order = Order.builder().orderno(imports.getOrder().getOrderno())
                            .orderdate(imports.getOrder().getOrderdate())
                            .quantity(imports.getOrder().getQuantity())
                            .material(imports.getOrder().getMaterial()).build();
                } else {
                    order = Order.builder().orderno(imports.getOrder().getOrderno())
                            .orderdate(imports.getOrder().getOrderdate())
                            .quantity(imports.getOrder().getQuantity())
                            .material(imports.getOrder().getMaterial()).build();
                }
                importDTO.setOrder(order);
            }


            if (imports.getOrder().getQuotationmtrl() != null) {
                importDTO.setContractor(imports.getOrder().getQuotationmtrl().getQuotation().getContractor());
            }
        }
        return importDTO;
    }

    @Override
    public ImportInspectionDTO readInspection(Long importno) {
        List<ImportInspection> list = importInspectionRepository.findByImportNo(importno);

        if (!list.isEmpty()) {
            ImportInspection inspection = list.get(0);
            return importInspectionEntityToDTO(inspection);
        } else {
            return null;
        }
    }

    @Override
    public Boolean updateInspection(ImportInspectionDTO inspectionDTO, Emp emp) {
        Imports imports = importRepository.findById(inspectionDTO.getImportno()).orElseGet(null);
        ImportDTO importDTO = importEntityToDto(imports);

        // 입고 검수 결과에 따른 입고 객체 상태 업데이트
        if (inspectionDTO.getStatus() == InspectionStatus.OK.ordinal()) {
            importDTO.setStatus(ImportStatus.OK.ordinal());
        } else {
            importDTO.setStatus(ImportStatus.INSPECTING_WITH_FAILED.ordinal());
        }
        // 입고 객체의 입고 처리일 및 입고 처리자 업데이트
        importDTO.setApprovedate(LocalDateTime.now());
        importDTO.setApprover(emp);
        importRepository.save(importDtoToEntity(importDTO));

        // 자재 수량 업데이트
        MaterialDTO materialDTO = materialService.materialEntityToDTO(Optional.of(imports.getOrder().getMaterial()));
        materialDTO.setQuantity(materialDTO.getQuantity() + imports.getQuantity() - inspectionDTO.getDfcQuantity());
        materialRepository.save(materialService.materialDTOToEntity(materialDTO));

        // 입고 검수 객체 업데이트
        inspectionDTO.setEmp(emp);
        inspectionDTO.setDuedate(LocalDateTime.now());
        inspectionDTO.setImports(imports);
        if (inspectionDTO.getContent() == null) {
            inspectionDTO.setContent("");
        }
        importInspectionRepository.save(importInspectionDTOToEntity(inspectionDTO));
        return true;
    }

    @Transactional
    @Override
    public void changeStatus(Long importno, ImportStatus status) {
        importRepository.changeStatus(importno, status.ordinal());
    }

    @Transactional
    @Override
    public Boolean executeImport(List<ImportDTO> importDTOList, Emp receiver) {
        for (ImportDTO importDTO : importDTOList) {
            Imports imports = importRepository.findById(importDTO.getImportno()).orElseGet(null);
            Imports newImports = Imports.builder()
                    .importno(imports.getImportno())
                    .order(imports.getOrder())
                    .quantity(imports.getQuantity())
                    .arrivaldate(LocalDateTime.now())
                    .receiver(receiver)
                    .status(ImportStatus.NEEDS_INSPECTION.ordinal()).build();

            importRepository.save(newImports);

            ImportInspection importInspection = ImportInspection.builder()
                    .imports(Imports.builder().importno(importDTO.getImportno()).build())
                    .duedate(LocalDateTime.now().plusDays(1))
                    .status(InspectionStatus.NOT_YET.ordinal())
                    .dfcQuantity(0)
                    .build();
            importInspectionRepository.save(importInspection);
        }
        return true;
    }

    @Transactional
    public long countRedStatus() {
        return prcrRepository.countByStatusIn(Arrays.asList(
                PrcrStatus.RED_ORDER_ADDED.ordinal()
        ));
    }

    // 경고 상태 (YELLOW 및 YELLOW_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countYellowStatus() {
        return importRepository.countByStatusIn(Arrays.asList(
                OrderStatus.AUTO_GENERATED.ordinal()
        ));
    }

    // 경고 상태 (YELLOW 및 YELLOW_ORDER_ADDED)의 prcr 개수 반환
    @Transactional
    public long countBlueStatus() {
        return importRepository.countByStatusIn(Arrays.asList(
                OrderStatus.AUTO_MODIFIED.ordinal()
        ));
    }

    // 전체 입고계획의 개수 반환
    @Transactional
    public long countTotalImport() {
        return importRepository.count();
    }
}
