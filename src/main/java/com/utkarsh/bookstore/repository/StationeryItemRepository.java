package com.utkarsh.bookstore.repository;

import com.utkarsh.bookstore.model.StationeryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StationeryItemRepository extends JpaRepository<StationeryItem, Long>,
        JpaSpecificationExecutor<StationeryItem> {

    // No custom methods required for now.
}