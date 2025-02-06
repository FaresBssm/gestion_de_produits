package net.bsfconsulting.gestion_de_produits.services;

import net.bsfconsulting.gestion_de_produits.dto.CategoryDto;
import net.bsfconsulting.gestion_de_produits.entity.Category;
import net.bsfconsulting.gestion_de_produits.mapper.CategoryMapper;
import net.bsfconsulting.gestion_de_produits.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getAllCategory() {
        List<Category>  categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(categoryMapper :: categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
