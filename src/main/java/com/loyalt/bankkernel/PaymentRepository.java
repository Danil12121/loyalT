package com.loyalt.bankkernel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
    Optional<Payment> findByClientIdAndPartnerIdAndLoyaltyType(Long clientId, String partnerId, String loyaltyType);

    @Modifying
    @Transactional
    @Query("UPDATE Payment a SET " +
       "a.balance = :balance, " +
       "a.loyaltyType = :loyaltyType, " +
       "a.partnerId = :partnerId, " +
       "a.currValue = :currValue, " +
       "a.maxValueOrPercent = :maxValueOrPercent " +
       "WHERE a.clientId = :clientId")
    void update(
            @Param("partnerId") String partnerId,
            @Param("loyaltyType") String loyalType,
            @Param("balance") Double value,
            @Param("currValue") int currValue,
            @Param("maxValueOrPercent") int maxValueorPercent);

    @Modifying
    @Transactional
    @Query("DELETE FROM Payment p WHERE p.partnerId = ?1 AND p.clientId = ?2")
    void delete(String partnerId, Long clientId);
}
