package net.bsfconsulting.gestion_de_produits.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Long categoryId;
    private String categoryName;
}
