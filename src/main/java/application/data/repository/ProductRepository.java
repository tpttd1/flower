package application.data.repository;

import application.data.model.Category;
import application.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM tbl_products p WHERE p.name LIKE :name")
    Collection<Product> findAllByName(@Param("name") String name);

    @Query("select p from tbl_products p where p.category.id = :id")
    ArrayList<Product> find(@Param("id") int id);


}
