package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryModelTest {
    private Inventory inventory;
    private Part testPart;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Initialize inventory
        inventory = new Inventory();

        // Create test part
        testPart = new InhousePart(1, "Test Part", 15.0, 10, 5, 20, 101);

        // Create test product
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(testPart);
        testProduct = new Product(1, "Test Product", 50.0, 5, 2, 10, parts);
    }

    @Test
    void testAddAndRetrievePart() {
        // Add part to inventory
        inventory.addPart(testPart);

        // Retrieve part using lookup
        Part retrievedPart = inventory.lookupPart("Test Part");

        // Verify part was added and can be retrieved
        assertNotNull(retrievedPart);
        assertEquals("Test Part", retrievedPart.getName());
        assertEquals(15.0, retrievedPart.getPrice());
        assertEquals(10, retrievedPart.getInStock());
    }

    @Test
    void testAddAndRetrieveProduct() {
        // Add product to inventory
        inventory.addProduct(testProduct);

        // Retrieve product using lookup
        Product retrievedProduct = inventory.lookupProduct("Test Product");

        // Verify product was added and can be retrieved
        assertNotNull(retrievedProduct);
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals(50.0, retrievedProduct.getPrice());
        assertEquals(5, retrievedProduct.getInStock());
    }

    @Test
    void testUpdatePart() {
        // Add part to inventory
        inventory.addPart(testPart);

        // Create updated part
        Part updatedPart = new InhousePart(1, "Updated Part", 25.0, 15, 5, 20, 102);

        // Update part
        inventory.updatePart(0, updatedPart);

        // Verify part was updated
        Part retrievedPart = inventory.getAllParts().get(0);
        assertEquals("Updated Part", retrievedPart.getName());
        assertEquals(25.0, retrievedPart.getPrice());
        assertEquals(15, retrievedPart.getInStock());
    }
}