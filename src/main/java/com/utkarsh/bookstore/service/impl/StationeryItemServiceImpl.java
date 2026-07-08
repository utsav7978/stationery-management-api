package com.utkarsh.bookstore.service.impl;

import com.utkarsh.bookstore.exception.ResourceNotFoundException;
import com.utkarsh.bookstore.model.Category;
import com.utkarsh.bookstore.model.StationeryItem;
import com.utkarsh.bookstore.repository.CategoryRepository;
import com.utkarsh.bookstore.repository.StationeryItemRepository;
import com.utkarsh.bookstore.service.StationeryItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StationeryItemServiceImpl implements StationeryItemService {

    @Autowired
    private StationeryItemRepository stationeryItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<StationeryItem> getAllItems(Specification<StationeryItem> spec, Pageable pageable) {
        return stationeryItemRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public StationeryItem getItemById(Long id) {
        return stationeryItemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Stationery item not found with ID: " + id));
    }

    @Override
    @Transactional
    public StationeryItem createItem(StationeryItem item) {

        Long categoryId = item.getCategoryId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with ID: " + categoryId));

        item.setCategory(category);

        return stationeryItemRepository.save(item);
    }

    @Override
    @Transactional
    public StationeryItem updateItem(Long id, StationeryItem itemDetails) {

        StationeryItem existingItem = getItemById(id);

        existingItem.setItemName(itemDetails.getItemName());
        existingItem.setBrand(itemDetails.getBrand());
        existingItem.setPrice(itemDetails.getPrice());
        existingItem.setQuantity(itemDetails.getQuantity());

        Long categoryId = itemDetails.getCategoryId();

        if (categoryId != null) {

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category not found with ID: " + categoryId));

            existingItem.setCategory(category);
        }

        return stationeryItemRepository.save(existingItem);
    }

    @Override
    @Transactional
    public boolean deleteItem(Long id) {

        if (!stationeryItemRepository.existsById(id)) {
            return false;
        }

        stationeryItemRepository.deleteById(id);

        return true;
    }
}