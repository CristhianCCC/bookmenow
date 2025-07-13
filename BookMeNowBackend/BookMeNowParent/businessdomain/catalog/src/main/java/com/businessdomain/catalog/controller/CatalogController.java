package com.businessdomain.catalog.controller;
import com.businessdomain.catalog.dto.CatalogDTO;
import com.businessdomain.catalog.model.Catalog;
import com.businessdomain.catalog.service.CatalogService;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
public class CatalogController {

    @Autowired
    public CatalogService catalogService;


    @GetMapping
    public ResponseEntity<List<Catalog>> getAllCatalogs () {
        List<Catalog> catalog = catalogService.getAllCatalogs();
        return ResponseEntity.ok().body(catalog);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CatalogDTO> getCatalogById (@PathVariable Long id){
        CatalogDTO catalogFound = catalogService.getCatalogById(id);
        return ResponseEntity.ok().body(catalogFound);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CatalogDTO> findByName (@PathVariable String name){
        CatalogDTO catalogFound = catalogService.findByName(name);
        return ResponseEntity.ok().body(catalogFound);
    }

    //@Valid will be to allow the DTO validations to be exposed in the controller
    @PostMapping
    public ResponseEntity<CatalogDTO> createCatalog (@Valid @RequestBody CatalogDTO catalogDTO){
        CatalogDTO catalogCreated = catalogService.createCatalog(catalogDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogCreated);
    }

    //@Valid will be to allow the DTO validations to be exposed in the controller
    @PutMapping("/{id}")
    public ResponseEntity<CatalogDTO> editCatalog (@PathVariable Long id, @Valid @RequestBody CatalogDTO catalogDTO){
        CatalogDTO catalogEdited = catalogService.editCatalog(id, catalogDTO);
        return ResponseEntity.ok().body(catalogEdited);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatalog (@PathVariable Long id){
        catalogService.deleteCatalog(id);
        return ResponseEntity.noContent().build();
    }
}
