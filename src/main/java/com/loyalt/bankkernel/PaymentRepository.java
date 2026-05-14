package com.loyalt.bankkernel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
    Optional<Payment> findByClientIdAndPartnerIdAndType(Long clientId, String partnerId, String type);

    @Modifying
    @Transactional
    @Query("UPDATE Analytics a SET " +
       "a.value = :value, " +
       "a.loyalType = :loyaltype, " +
       "a.partnerId = :partnerId, " +
       "a.value = :value, " +
       "a.currValue = :currValue, " +
       "a.maxValueorPercent = :maxValueorPercent " +
       "WHERE a.clientId = :clientId")
    void update(@Param("id") Long clientId,
            @Param("partnerId") String partnerId,
            @Param("loyalType") String loyalType,
            @Param("value") Double value,
            @Param("currValue") int currValue,
            @Param("maxValueorPercent") int maxValueorPercent);

    @Modifying
    @Transactional
    void deleteByClientId(Long clientId);

    @Modifying
    @Transactional
    void deleteByPartnerId(String partnerId);   
}
