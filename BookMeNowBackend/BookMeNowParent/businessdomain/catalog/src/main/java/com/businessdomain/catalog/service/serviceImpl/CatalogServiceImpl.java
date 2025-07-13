package com.businessdomain.catalog.service.serviceImpl;
import com.businessdomain.catalog.dto.CatalogDTO;
import com.businessdomain.catalog.model.Catalog;
import com.businessdomain.catalog.repository.CatalogRepository;
import com.businessdomain.catalog.service.CatalogService;
import com.businessdomain.catalog.userclient.UserClient;
import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import com.businessdomain.user.model.User;
import com.businessdomain.user.model.enums.UserRole;
import com.businessdomain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    public CatalogRepository catalogRepository;

    @Autowired
    public UserClient userClient;

    //convert DTO to Entity --------------------------------------------------------------------------------------------
    private Catalog toEntity(CatalogDTO dto) {
        Catalog catalog = new Catalog();
        catalog.setName(dto.getName());
        catalog.setDescription(dto.getDescription());
        catalog.setPrice(dto.getPrice());
        catalog.setDurationMinutes(dto.getDurationMinutes());
        catalog.setCategory(dto.getCategory());
        catalog.setAvailableDays(dto.getAvailableDays());
        catalog.setCreatedAt(dto.getCreatedAt());
        catalog.setUpdatedAt(dto.getUpdatedAt());

        // Finding provider by email using Feign client
        UserDTO userDTO = userClient.getUserByEmail(dto.getProviderEmail());
        if (userDTO == null) {
            throw new RuntimeException("Email " + dto.getProviderEmail() + " was not found");
        }

        // Validating that user is a provider, otherwise it won't work
        if (userDTO.getRole() != UserRole.PROVIDER) {
            throw new BusinessRuleException("User is not a PROVIDER");
        }

        // Setting only the provider email (no relation with User entity)
        catalog.setProviderEmail(userDTO.getEmail());

        return catalog;
    }

    //convert Entity to DTO --------------------------------------------------------------------------------------------
    private CatalogDTO toDTO(Catalog catalog) {
        CatalogDTO dto = new CatalogDTO();
        dto.setId(catalog.getId());
        dto.setName(catalog.getName());
        dto.setDescription(catalog.getDescription());
        dto.setPrice(catalog.getPrice());
        dto.setDurationMinutes(catalog.getDurationMinutes());
        dto.setCategory(catalog.getCategory());
        dto.setAvailableDays(catalog.getAvailableDays());
        dto.setCreatedAt(catalog.getCreatedAt());
        dto.setUpdatedAt(catalog.getUpdatedAt());

        // If providerEmail exists, fetch provider info using Feign client
        if (catalog.getProviderEmail() != null && !catalog.getProviderEmail().isEmpty()) {
            try {
                UserDTO userDTO = userClient.getUserByEmail(catalog.getProviderEmail());
                if (userDTO.getRole() == UserRole.PROVIDER) {
                    dto.setProviderName(userDTO.getName());
                    dto.setProviderEmail(userDTO.getEmail());
                    dto.setProviderAdress(userDTO.getAddress());
                    dto.setRole(userDTO.getRole());
                }
            } catch (Exception e) {
                // Handle case where user does not exist or service is unavailable
                System.out.println("Warning: Could not fetch provider info for email: " + catalog.getProviderEmail());
            }
        }
        return dto;
    }

    @Override
    public List<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }

    @Override
    public CatalogDTO getCatalogById(Long id) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID" + id + "wasn't found"));
        return toDTO(catalog);
    }

    @Override
    public CatalogDTO findByName(String name) {
        Catalog catalog = catalogRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Catalog with the name " + name + "was not found"));
        return toDTO(catalog);
    }

    @Override
    public List<CatalogDTO> ListCatalogsByName(String name) {
        List<Catalog> catalogs = catalogRepository.findAllByName(name);
        return catalogs.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CatalogDTO createCatalog(CatalogDTO catalogDTO) {
        Catalog catalog = toEntity(catalogDTO);
        Catalog catalogSaved = catalogRepository.save(catalog);
        return toDTO(catalogSaved);
    }

    @Override
    public CatalogDTO editCatalog(Long id, CatalogDTO catalogDTO) {
        return catalogRepository.findById(id).map(catalogFound -> {
            catalogFound.setName(catalogDTO.getName());
            catalogFound.setDescription(catalogDTO.getDescription());
            catalogFound.setPrice(catalogDTO.getPrice());
            catalogFound.setDurationMinutes(catalogDTO.getDurationMinutes());
            catalogFound.setCategory(catalogDTO.getCategory());
            catalogFound.setAvailableDays(catalogDTO.getAvailableDays());
            catalogFound.setUpdatedAt(LocalDateTime.now());
            Catalog catalogSaved = catalogRepository.save(catalogFound);
            return toDTO(catalogSaved);
        }).orElseThrow(() -> new BusinessRuleException("Something went wrong, please verify all fields and try it again"));
    }

    @Override
    public void deleteCatalog(Long id) {
        Catalog catalog = catalogRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Catalog with id " + id + " was not found"));
        catalogRepository.save(catalog);
        //deleting catalog
        catalogRepository.deleteById(id);
    }
}
