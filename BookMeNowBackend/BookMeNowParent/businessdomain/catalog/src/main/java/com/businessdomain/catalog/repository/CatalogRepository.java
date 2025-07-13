package com.businessdomain.catalog.repository;

import com.businessdomain.catalog.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByName (String name);

    List<Catalog> findAllByName (String name);

}
