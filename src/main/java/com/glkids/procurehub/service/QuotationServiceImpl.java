package com.glkids.procurehub.service;

import com.glkids.procurehub.dto.QuotationDTO;
import com.glkids.procurehub.dto.QuotationFileDTO;
import com.glkids.procurehub.dto.QuotationMtrlDTO;
import com.glkids.procurehub.dto.UserDTO;
import com.glkids.procurehub.entity.*;
import com.glkids.procurehub.repository.QuotationFileRepository;
import com.glkids.procurehub.repository.QuotationMtrlRepository;
import com.glkids.procurehub.repository.QuotationRepository;
import com.glkids.procurehub.status.QuotationStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final QuotationMtrlRepository quotationMtrlRepository;
    private final QuotationFileRepository quotationFileRepository;

    @Override
    @Transactional
    public Quotation saveQuotation(QuotationDTO quotationDTO) {
        Quotation quotation = quotationDtoToEntity(quotationDTO);
        return quotationRepository.save(quotation);
    }

    @Override
    @Transactional
    public Boolean saveQuotationMtrl(List<QuotationMtrlDTO> quotationMtrlDTOList , UserDTO userDTO) {
        List<QuotationMtrl> quotationMtrlList = new ArrayList<>();
        for(QuotationMtrlDTO quotationMtrlDTO : quotationMtrlDTOList) {
            quotationMtrlDTO.setEmp(userDTO.getEmp().getEmpno());
            QuotationMtrl quotationMtrl = quotationMtrlDtoToEntity(quotationMtrlDTO);
            if (quotationMtrlRepository.save(quotationMtrl).getQtmtno()!=null){
                quotationMtrlList.add(quotationMtrl);
            }
            else{
                return null;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public QuotationFile saveQuotationFile(QuotationFileDTO quotationFileDTO) {
        QuotationFile quotationFile = quotationFileDtoToEntity(quotationFileDTO);
        return quotationFileRepository.save(quotationFile);
    }

    @Override
    public List<QuotationMtrlDTO> readQuotationMtrlList(Long qtno) {
        List<QuotationMtrlDTO> quotationMtrlDTOList = new ArrayList<>();

        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression quoExp = qQuotationMtrl.quotation.qtno.eq(qtno);

        quotationMtrlRepository.findAll(builder.and(quoExp)).forEach(x -> quotationMtrlDTOList.add(quotationMtrlEntityToDTO(x)));
        return  quotationMtrlDTOList;
    }

    @Override
    public QuotationMtrlDTO quoread(Long qtmtno) {
        Optional<QuotationMtrl> optional = quotationMtrlRepository.findById(qtmtno);
        if(optional.isPresent()){
            return  quotationMtrlEntityToDTO(optional.get());
        }
        return null;
    }

    @Override
    public QuotationDTO read(Long qtno) {
        Optional<Quotation> opquo = quotationRepository.findById(qtno);
        if(opquo.isPresent()){
            return quotationEntityToDTO(opquo.get());
        }
        return null;
    }

    @Override
    public List<QuotationFileDTO> quotationFileList(Long qtno) {
        List<QuotationFileDTO> quotationFileDTOList = new ArrayList<>();

        QQuotationFile qQuotationFile = QQuotationFile.quotationFile;

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression quotationExp = qQuotationFile.quotation.qtno.eq(qtno)
;
        quotationFileRepository.findAll(builder.and(quotationExp)).forEach(x -> quotationFileDTOList.add(quotationFileEntityToDTO(x)));
        return quotationFileDTOList;
    }

    @Override
    public Boolean isDuplicatedQuotationMtrl(Long qtno, Long corno) {
        QQuotationMtrl qQuotationMtrl = QQuotationMtrl.quotationMtrl;

        BooleanExpression qtnoExp = qQuotationMtrl.quotation.qtno.eq(qtno);
        List<QuotationMtrl> quoMtrlList = new ArrayList<>();
        quotationMtrlRepository.findAll(qtnoExp).forEach(quoMtrlList::add);

        System.out.println(quoMtrlList);  // For debugging

        BooleanBuilder builder = new BooleanBuilder();
        if (!quoMtrlList.isEmpty()) {
            BooleanBuilder qtmtnoExpBuilder = new BooleanBuilder();
            for (QuotationMtrl quotationMtrl : quoMtrlList) {
                BooleanExpression qtmtnoExp = qQuotationMtrl.material.mtrlno.eq(quotationMtrl.getMaterial().getMtrlno());
                qtmtnoExpBuilder.or(qtmtnoExp);
            }
            builder.and(qtmtnoExpBuilder);
        }

        BooleanExpression qtStatusExp = qQuotationMtrl.quotation.status.eq(QuotationStatus.AGREEMENT.ordinal());
        BooleanExpression qtCornoExp = qQuotationMtrl.quotation.contractor.corno.eq(corno);

        List<QuotationMtrl> quoDupList = new ArrayList<>();
        quotationMtrlRepository.findAll(builder.and(qtStatusExp).and(qtCornoExp)).forEach(quoDupList::add);

        System.out.println(quoDupList);  // For debugging

        return !quoDupList.isEmpty();
    }

    @Transactional
    @Override
    public void changeStatus(Long qtno, QuotationStatus quotationStatus) {
        quotationRepository.changeStatus(qtno, quotationStatus.ordinal());
    }

    @Override
    public List<QuotationMtrl> getQuotationsByMaterialNo(Long mtrlno) {
        return quotationMtrlRepository.findByMaterial(mtrlno);
    }
}
