package net.bsfconsulting.gestion_de_produits.repository;

import net.bsfconsulting.gestion_de_produits.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT p FROM Product p JOIN p.category c
        WHERE (:name IS NULL OR (p.name) ILIKE (CONCAT('%', :name, '%')))
        AND (:price IS NULL OR p.price >= :price) 
        AND (:categoryName IS NULL OR (c.name) ILIKE (CONCAT('%', :categoryName, '%')))
    """)
    List<Product> searchProducts(
            @Param("name") String name,
            @Param("price") Double price,
            @Param("categoryName") String categoryName
    );
}
