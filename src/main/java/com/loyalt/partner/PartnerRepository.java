package com.loyalt.partner;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartnerRepository extends JpaRepository<Partner, String> {

        Optional<Partner> findByPartnerIdAndType(String partnerId, String type);

        @Modifying
        @Transactional
        @Query("UPDATE Partner p SET p.value = :value WHERE p.partnerId = :id AND p.type = :type")
        int updateValue(@Param("id") String partnerId, @Param("type") String type, @Param("value") Double value);

        @Modifying
        @Transactional
        void deleteByPartnerIdAndType(String partnerId, String type);

        @Modifying
        @Transactional
        void deleteByPartnerId(String partnerId);
}