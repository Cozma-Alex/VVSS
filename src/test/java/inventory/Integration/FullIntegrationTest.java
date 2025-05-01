package inventory.Integration;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FullIntegrationTest {

    private InventoryRepository repository;
    private InventoryService service;
    private String testDataFile = "data/full_integration_test.txt";

    @BeforeEach
    void setUp() throws IOException {
        // Clean up and ensure the test file exists
        File file = new File(testDataFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        Files.write(Paths.get(testDataFile), "".getBytes());

        // Initialize the repository and service with test file
        repository = new InventoryRepository(testDataFile);
        service = new InventoryService(repository);
    }

    @Test
    @DisplayName("Test full integration - Part CRUD operations")
    void testPartCrudOperations() {
        // 1. Create and add an InhousePart
        String partName = "Test CPU";
        double partPrice = 199.99;
        int partStock = 10;
        int partMin = 5;
        int partMax = 20;
        int machineId = 101;

        service.addInhousePart(partName, partPrice, partStock, partMin, partMax, machineId);

        // Verify part was added correctly
        Part addedPart = service.lookupPart(partName);
        assertNotNull(addedPart);
        assertEquals(partName, addedPart.getName());
        assertEquals(partPrice, addedPart.getPrice());
        assertEquals(partStock, addedPart.getInStock());
        assertTrue(addedPart instanceof InhousePart);
        assertEquals(machineId, ((InhousePart) addedPart).getMachineId());

        // Verify lookup by ID works
        int partId = addedPart.getPartId();
        Part foundByIdPart = service.lookupPart(String.valueOf(partId));
        assertSame(addedPart, foundByIdPart);

        // Delete the part
        service.deletePart(addedPart);

        // Verify part was deleted
        assertNull(service.lookupPart(partName));
    }

    @Test
    @DisplayName("Test full integration - Product with associated parts")
    void testProductWithAssociatedParts() {
        // 1. Add parts to use in product
        service.addInhousePart("CPU", 249.99, 15, 5, 30, 201);
        service.addOutsourcePart("RAM", 99.99, 20, 10, 40, "Memory Inc");

        // Get the parts
        Part cpu = service.lookupPart("CPU");
        Part ram = service.lookupPart("RAM");
        assertNotNull(cpu);
        assertNotNull(ram);

        // 2. Create a product with associated parts
        ObservableList<Part> computerParts = FXCollections.observableArrayList();
        computerParts.add(cpu);
        computerParts.add(ram);

        String productName = "Test Computer";
        double productPrice = 899.99;
        int productStock = 5;
        int productMin = 1;
        int productMax = 10;

        service.addProduct(productName, productPrice, productStock, productMin, productMax, computerParts);

        // 3. Verify product was created correctly
        Product computer = service.lookupProduct(productName);
        assertNotNull(computer);
        assertEquals(productName, computer.getName());
        assertEquals(productPrice, computer.getPrice());
        assertEquals(productStock, computer.getInStock());

        // 4. Verify associated parts
        ObservableList<Part> associatedParts = computer.getAssociatedParts();
        assertEquals(2, associatedParts.size());

        boolean foundCpu = false;
        boolean foundRam = false;
        for (Part part : associatedParts) {
            if (part.getName().equals("CPU")) foundCpu = true;
            if (part.getName().equals("RAM")) foundRam = true;
        }

        assertTrue(foundCpu);
        assertTrue(foundRam);

        // 5. Delete the product and verify deletion
        service.deleteProduct(computer);
        assertNull(service.lookupProduct(productName));

        // 6. Verify parts still exist after product deletion
        assertNotNull(service.lookupPart("CPU"));
        assertNotNull(service.lookupPart("RAM"));
    }
}