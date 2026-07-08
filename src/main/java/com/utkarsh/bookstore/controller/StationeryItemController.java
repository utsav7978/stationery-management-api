package com.utkarsh.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.utkarsh.bookstore.exception.ResourceNotFoundException;
import com.utkarsh.bookstore.model.StationeryItem;
import com.utkarsh.bookstore.service.StationeryItemService;

import jakarta.persistence.criteria.Predicate;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class StationeryItemController {

    private static final Logger logger = LoggerFactory.getLogger(StationeryItemController.class);

    @Autowired
    private StationeryItemService stationeryItemService;

    @GetMapping
    public ResponseEntity<Page<StationeryItem>> getAllItems(

            @RequestParam(required = false) String itemName,
            @RequestParam(required = false) String brand,
            Pageable pageable) {

        Specification<StationeryItem> spec = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (itemName != null && !itemName.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("itemName")),
                        "%" + itemName.toLowerCase() + "%"));
            }

            if (brand != null && !brand.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("brand")),
                        "%" + brand.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<StationeryItem> items =
                stationeryItemService.getAllItems(spec, pageable);

        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationeryItem> getItemById(@PathVariable Long id) {

        try {

            StationeryItem item = stationeryItemService.getItemById(id);

            return ResponseEntity.ok(item);

        } catch (ResourceNotFoundException e) {

            logger.warn("Could not find stationery item with id: {}", id);

            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<StationeryItem> createItem(@RequestBody StationeryItem item) {

        try {

            if (item.getItemName() == null
                    || item.getItemName().trim().isEmpty()
                    || item.getCategoryId() == null) {

                return ResponseEntity.badRequest().build();
            }

            StationeryItem createdItem =
                    stationeryItemService.createItem(item);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdItem);

        } catch (Exception e) {

            logger.error("Error creating stationery item: {}", e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationeryItem> updateItem(
            @PathVariable Long id,
            @RequestBody StationeryItem itemDetails) {

        try {

            if (itemDetails.getItemName() == null
                    || itemDetails.getItemName().trim().isEmpty()) {

                return ResponseEntity.badRequest().build();
            }

            StationeryItem updatedItem =
                    stationeryItemService.updateItem(id, itemDetails);

            return ResponseEntity.ok(updatedItem);

        } catch (ResourceNotFoundException e) {

            logger.warn("Attempted to update a non-existent stationery item with id: {}", id);

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {

        if (stationeryItemService.deleteItem(id)) {

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}