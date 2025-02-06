package net.bsfconsulting.gestion_de_produits.services;

import net.bsfconsulting.gestion_de_produits.dto.ProductDto;
import net.bsfconsulting.gestion_de_produits.dto.ProductSearchDto;
import net.bsfconsulting.gestion_de_produits.entity.Category;
import net.bsfconsulting.gestion_de_produits.entity.Product;
import net.bsfconsulting.gestion_de_produits.mapper.ProductMapper;
import net.bsfconsulting.gestion_de_produits.repository.CategoryRepository;
import net.bsfconsulting.gestion_de_produits.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public ProductDto addProduct(ProductDto productDto) {
        Category category = null;

        if (productDto.getCategoryId() != null) {
            category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Catégorie avec l'ID fourni introuvable"));
        }
        else if (productDto.getCategoryName() != null && !productDto.getCategoryName().isEmpty()) {
            category = categoryRepository.findByName(productDto.getCategoryName().trim());
            if (category == null) {
                category = new Category();
                category.setName(productDto.getCategoryName());
                category = categoryRepository.save(category);
            }
        } else {
            throw new IllegalArgumentException("L'ID ou le nom de la catégorie doit être fourni.");
        }

        Product product = productMapper.productDtoToProduct(productDto);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Produit avec l'ID"+ id + "introuvable"));
        Category category = null;
        if(productDto.getCategoryId() != null){
            category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(()->new IllegalArgumentException("Catégorie avec l'ID fourni intruvable"));
        }
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setCategory(category);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.productToProductDto(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)){
            throw  new IllegalArgumentException("Le produit avec cet id n'existe pas");
        }
        productRepository.deleteById(id);
    }

    public List<ProductDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper :: productToProductDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(ProductSearchDto productSearchDto) {
        List<Product> result = productRepository.searchProducts(
                productSearchDto.getName(),
                productSearchDto.getPrice(),
                productSearchDto.getCategoryName()
        );
        return result.stream().map(productMapper :: productToProductDto).toList();
    }
}

