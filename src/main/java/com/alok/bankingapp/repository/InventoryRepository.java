package com.alok.bankingapp.repository;

import com.alok.bankingapp.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
