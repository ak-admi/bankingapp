package com.alok.bankingapp.repository;

import com.alok.bankingapp.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @Modifying
    @Query("""
UPDATE Inventory i 
SET i.stock=i.stock-1 
where i.id=:id 
AND i.stock>0
""")
    int decreaseStock(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i where i.id=:id")
    Optional<Inventory> findByIdWithLock(@Param("id") Long id);
}
