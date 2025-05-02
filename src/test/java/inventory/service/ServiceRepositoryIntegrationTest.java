package inventory.service;
import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("integration")
public class ServiceRepositoryIntegrationTest {

    @Mock
    private Product mockProduct;

    @Mock
    private Part mockPart;

    private InventoryRepository repository;
    private InventoryService service;
    private String testDataFile = "data/test_items.txt";

    @BeforeEach
    void setUp() {
        // Make sure test file exists or is created empty

        repository = new InventoryRepository(testDataFile);
        service = new InventoryService(repository);
    }

    @Test
    @DisplayName("Test retrieving products through service")
    void testServiceProductRetrieval() {
        // This test verifies integration between service and repository
        // for products (no stubs used)
        ObservableList<Product> products = service.getAllProducts();

        // Initially there should be no products
        assertNotNull(products);
        assertEquals(0, products.size());
    }

    @Test
    @DisplayName("Test adding a mock product through service")
    void testAddMockProduct() {
        // Stub only the required method for this test
        when(mockProduct.getName()).thenReturn("Mock Product");
        when(mockProduct.getAssociatedParts()).thenReturn(FXCollections.observableArrayList());

        // Add mock product through repository
        repository.addProduct(mockProduct);

        // Verify product was added and can be accessed through service
        ObservableList<Product> products = service.getAllProducts();
        assertTrue(products.contains(mockProduct));

        // Verify lookup works correctly
        Product foundProduct = service.lookupProduct("Mock Product");
        assertSame(mockProduct, foundProduct);
    }
}
