package com.loyalt.partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

        Optional<Partner> findByPartnerIdAndType(Long partnerId, String type);

        @Modifying
        @Query("UPDATE Partner p SET p.value = :value WHERE p.partnerId = :id AND p.type = :type")
        int updateValue(@Param("id") Long partnerId, @Param("type") String type, @Param("value") Double value);

        @Modifying
        @Transactional
        void deleteByPartnerIdAndType(Long partnerId, String type);

        @Modifying
        @Transactional
        void deleteByPartnerId(Long partnerId);
}