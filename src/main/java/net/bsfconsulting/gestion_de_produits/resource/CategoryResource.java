package net.bsfconsulting.gestion_de_produits.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bsfconsulting.gestion_de_produits.dto.CategoryDto;
import net.bsfconsulting.gestion_de_produits.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryResource {
    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(summary = "Obtenir toutes les catégories ", description = "Récupère la liste complète des catégories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des catégories",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))})
    })
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtos =  categoryService.getAllCategory();
        return ResponseEntity.ok(categoryDtos);
    }
}
