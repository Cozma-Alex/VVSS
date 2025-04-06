package inventory.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory productRepo;
    private Product keyboard;
    private Product monitor;

    @BeforeEach
    void setUp() {
        productRepo = new Inventory();

        // Create test products
        keyboard = new Product(101, "Keyboard", 29.99, 15, 5, 20, FXCollections.observableArrayList());
        monitor = new Product(102, "Monitor", 199.99, 8, 2, 10, FXCollections.observableArrayList());

        // Add products to repository for most tests
        productRepo.addProduct(keyboard);
        productRepo.addProduct(monitor);
    }

    @Test
    void testLookupProduct_FindByName() {
        // TC01: Find product by name
        Product result = productRepo.lookupProduct("Keyboard");

        assertEquals(keyboard, result);
        assertEquals(101, result.getProductId());
        assertEquals("Keyboard", result.getName());
    }

    @Test
    void testLookupProduct_FindByPartialName(){
        // TC01 variant: Find product by partial name
        Product result = productRepo.lookupProduct("Key");

        assertEquals(keyboard, result);
        assertEquals(101, result.getProductId());
    }

    @Test
    void testLookupProduct_FindById() {
        // TC02: Find product by ID
        Product result = productRepo.lookupProduct("102");

        assertEquals(monitor, result);
        assertEquals("Monitor", result.getName());
    }

    @Test
    void testLookupProduct_NonExistentProduct() {
        // TC03: Product not found - should return null;
         var product = productRepo.lookupProduct("XYZ");
         assertNull(product);
    }

    @Test
    void testLookupProduct_EmptyString() {
        // TC04: Empty search string - should return first item.

        var product =  productRepo.lookupProduct("");
        assertEquals("Keyboard", product.getName());

    }

    @Test
    void testLookupProduct_EmptyRepository() {
        // TC05: Empty products list. Should return null
        Inventory emptyRepo = new Inventory();


        var product = emptyRepo.lookupProduct("Keyboard");
        assertNull(product);

    }

    @Test
    void testLookupProduct_NullSearchItem() {
        // Edge case: Null search term
        var product =   productRepo.lookupProduct(null);
        assertNull(product);
    }

}