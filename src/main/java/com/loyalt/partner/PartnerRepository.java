package com.loyalt.partner;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PartnerRepository extends JpaRepository<Partner, String> {

        Optional<Partner> findByIdAndType(String id, String type);

        @Modifying
        @Transactional
        @Query("UPDATE Partner p SET p.type = :type, p.value = :value WHERE p.id = :id")
        int updateValue(@Param("id") String partnerId, @Param("type") String type, @Param("value") Double value);

        @Modifying
        @Transactional
        void deleteByIdAndType(String id, String type);

        @Modifying
        @Transactional
        void deleteById(String id);
}