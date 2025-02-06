package net.bsfconsulting.gestion_de_produits.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.bsfconsulting.gestion_de_produits.dto.ProductDto;
import net.bsfconsulting.gestion_de_produits.dto.ProductSearchDto;
import net.bsfconsulting.gestion_de_produits.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/products")
public class ProductResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }
    @Operation(summary = "Ajouter un produit", description = "Ajoute un nouveau produit dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit ajouté avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        ProductDto savedProductDto = productService.addProduct(productDto);
        return ResponseEntity.ok(savedProductDto);
    }
    @Operation(summary = "Mettre à jour un produit", description = "Met à jour un produit existant avec un ID donné.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        ProductDto updateProduct = productService.updateProduct(id,productDto);
        return ResponseEntity.ok(updateProduct);
    }
    @Operation(summary = "Supprimer un produit", description = "Supprime un produit existant par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Obtenir toutes les produits ", description = "Récupère la liste complète des produits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = productService.getAllProduct();
        return ResponseEntity.ok(productDtos);
    }
    @Operation(summary = "Rechercher des produits", description = "Recherche des produits en fonction de plusieurs critères (nom, prix, catégorie).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits filtrés",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDto.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String categoryName
    ){
        ProductSearchDto productSearchDto = new ProductSearchDto(name, price, categoryName);
        return ResponseEntity.ok(productService.searchProducts(productSearchDto));
    }
}
