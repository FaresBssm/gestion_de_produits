package net.bsfconsulting.gestion_de_produits.mapper;

import net.bsfconsulting.gestion_de_produits.dto.CategoryDto;
import net.bsfconsulting.gestion_de_produits.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto( Category category);
}
