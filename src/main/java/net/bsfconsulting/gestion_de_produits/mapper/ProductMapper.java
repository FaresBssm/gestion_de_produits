package net.bsfconsulting.gestion_de_produits.mapper;

import net.bsfconsulting.gestion_de_produits.dto.ProductDto;
import net.bsfconsulting.gestion_de_produits.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDto productToProductDto(Product product);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "categoryName", target = "category.name")
    Product productDtoToProduct(ProductDto productDto);
}
