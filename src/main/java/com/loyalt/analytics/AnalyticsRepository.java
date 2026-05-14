package com.loyalt.analytics;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AnalyticsRepository extends JpaRepository<Analytics, String>{
        
    Optional<Analytics> findByPartnerId(String partnerId);

    @Transactional
    @Modifying
    @Query("UPDATE Analytics a SET " +
       "a.totalClients = :totalClients, " +
       "a.newClients = :newClients, " +
       "a.totalTransactions = :totalTransactions, " +
       "a.date = :date " +
       "WHERE a.partnerId = :id")
    int updateValue(@Param("id") String partnerId, 
                @Param("totalClients") int totalClients, 
                @Param("newClients") int newClients, 
                @Param("totalTransactions") Long totalTransactions, 
                @Param("date") LocalDateTime date);
                
    @Modifying
    @Transactional
    void deleteByPartnerId(String partnerId);
}
