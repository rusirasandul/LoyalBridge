package com.loyalbridge.service;

import com.loyalbridge.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PartnerService {
    Partner createPartner(Partner partner);
    Partner updatePartner(Long id, Partner partner);
    void deletePartner(Long id);
    Optional<Partner> getPartnerById(Long id);
    Page<Partner> getAllPartners(Pageable pageable);
    List<Partner> getEnabledPartners();
    void enablePartner(Long id);
    void disablePartner(Long id);
    boolean existsByName(String name);
} 