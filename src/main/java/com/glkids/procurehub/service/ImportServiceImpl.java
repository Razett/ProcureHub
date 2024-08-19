package com.glkids.procurehub.service;


import com.glkids.procurehub.dto.ImportDTO;
import com.glkids.procurehub.dto.ImportInspectionDTO;
import com.glkids.procurehub.entity.ImportInspection;
import com.glkids.procurehub.entity.Imports;
import com.glkids.procurehub.entity.Order;
import com.glkids.procurehub.repository.ImportInspectionRepository;
import com.glkids.procurehub.repository.ImportRepository;
import com.glkids.procurehub.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportServiceImpl implements ImportService {

    private final ImportRepository importRepository;
    private final ImportInspectionRepository importInspectionRepository;

    @Deprecated
    @Override
    public List<ImportDTO> list() {
        List<Imports> imports = importRepository.findAll();
        List<ImportDTO> importDTOList = new ArrayList<>();
        imports.forEach(x -> importDTOList.add(importEntityToDto(x)));
        return importDTOList;
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
}
