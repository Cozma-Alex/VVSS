package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InventoryRepositoryTest {

    @Mock
    private Inventory mockInventory;

    private InventoryRepository repository;
    private Part testPart;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Create test data
        testPart = new InhousePart(1, "Test Part", 15.0, 10, 5, 20, 101);

        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(testPart);
        testProduct = new Product(1, "Test Product", 50.0, 5, 2, 10, parts);

        // Set up repository with mock inventory
        repository = new InventoryRepository("data/test_items.txt") {
            @Override
            public void readParts() {
                // Override to prevent actual file reading
            }

            @Override
            public void readProducts() {
                // Override to prevent actual file reading
            }

            @Override
            public void writeAll() {
                // Override to prevent actual file writing
            }

            @Override
            public Inventory getInventory() {
                return mockInventory;
            }
        };

        repository.setInventory(mockInventory);
    }

    @Test
    void testAddPart() {
        // Call method
        repository.addPart(testPart);

        // Verify interaction with the mocked inventory
        verify(mockInventory).addPart(testPart);
    }

    @Test
    void testGetAllParts() {
        // Setup mock
        ObservableList<Part> expectedParts = FXCollections.observableArrayList(testPart);
        when(mockInventory.getAllParts()).thenReturn(expectedParts);

        // Call method
        ObservableList<Part> result = repository.getAllParts();

        // Verify
        assertEquals(expectedParts, result);
        verify(mockInventory).getAllParts();
    }
}