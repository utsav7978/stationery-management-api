package com.utkarsh.bookstore.service;

import com.utkarsh.bookstore.model.StationeryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service interface for managing stationery items.
 * This defines the contract for business logic operations
 * related to stationery inventory.
 */
public interface StationeryItemService {

    /**
     * Retrieves a paginated and filtered list of all stationery items.
     *
     * @param spec The specification used for filtering.
     * @param pageable Pagination and sorting information.
     * @return A page of StationeryItem objects.
     */
    Page<StationeryItem> getAllItems(Specification<StationeryItem> spec, Pageable pageable);

    /**
     * Finds a stationery item by its ID.
     *
     * @param id Item ID.
     * @return The matching stationery item.
     */
    StationeryItem getItemById(Long id);

    /**
     * Creates a new stationery item.
     *
     * @param item Stationery item to create.
     * @return Saved stationery item.
     */
    StationeryItem createItem(StationeryItem item);

    /**
     * Updates an existing stationery item.
     *
     * @param id Item ID.
     * @param itemDetails Updated item details.
     * @return Updated stationery item.
     */
    StationeryItem updateItem(Long id, StationeryItem itemDetails);

    /**
     * Deletes a stationery item.
     *
     * @param id Item ID.
     * @return true if deleted successfully.
     */
    boolean deleteItem(Long id);
}