package com.businessdomain.catalog.service;
import com.businessdomain.catalog.dto.CatalogDTO;
import com.businessdomain.catalog.model.Catalog;
import java.util.List;

public interface CatalogService {

    public List<Catalog> getAllCatalogs ();

    public CatalogDTO getCatalogById (Long id);

    public CatalogDTO findByName (String name);

    public List<CatalogDTO> ListCatalogsByName(String name);

    public CatalogDTO createCatalog (CatalogDTO catalogDTO);

    public CatalogDTO editCatalog (Long id, CatalogDTO catalogDTO);

    public void deleteCatalog (Long id);

}
