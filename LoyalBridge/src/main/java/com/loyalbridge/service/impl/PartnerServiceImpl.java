package com.loyalbridge.service.impl;

import com.loyalbridge.model.Partner;
import com.loyalbridge.repository.PartnerRepository;
import com.loyalbridge.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Partner createPartner(Partner partner) {
        if (partnerRepository.existsByName(partner.getName())) {
            throw new RuntimeException("Partner with this name already exists");
        }
        return partnerRepository.save(partner);
    }

    @Override
    public Partner updatePartner(Long id, Partner partner) {
        Partner existingPartner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        
        existingPartner.setName(partner.getName());
        existingPartner.setApiUrl(partner.getApiUrl());
        existingPartner.setAuthenticationMethod(partner.getAuthenticationMethod());
        existingPartner.setConversionRate(partner.getConversionRate());
        
        return partnerRepository.save(existingPartner);
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partner> getPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Partner> getAllPartners(Pageable pageable) {
        return partnerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partner> getEnabledPartners() {
        return partnerRepository.findByIsEnabled(true);
    }

    @Override
    public void enablePartner(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        partner.setEnabled(true);
        partnerRepository.save(partner);
    }

    @Override
    public void disablePartner(Long id) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
        partner.setEnabled(false);
        partnerRepository.save(partner);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return partnerRepository.existsByName(name);
    }
} 