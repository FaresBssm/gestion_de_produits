package net.bsfconsulting.gestion_de_produits.repository;

import net.bsfconsulting.gestion_de_produits.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Category findByName(String name);
}
