package inventory.service;

import inventory.model.OutsourcedPart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceTest {

    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    private String partDynamicValue;

    InventoryRepository repo;
    InventoryService service;
    @BeforeEach
    void setUp() {
        repo = new InventoryRepository("data/test_items.txt");
        service = new InventoryService(repo);

        name = "Parte_de_test";
        //Varies depending on the test
        price = 10.0;
        //Varies depending on the test
        inStock = 10;
        min = 5;
        max = 15;
        partDynamicValue = "Firma_de_test";

    }


    @Order(1)
    @Test
    @DisplayName("ECP1: Add outsourced part with valid values - valid middle equivalence class")
    void addOutsourcedPart_withValidValues(){
        // Arrange
        this.min = 20;

        this.inStock = 30;

        this.max = 40;

        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize + 1, service.getAllParts().size());

    }
    @Order(2)
    @DisplayName("ECP2: Add outsourced part with invalid price - negative equivalence class")
    @RepeatedTest(3)
    void addOutsourcedPart_withNegativePrice() {
        // Arrange
        this.price = -100;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize, service.getAllParts().size());
    }

    @Order(3)
    @DisplayName("ECP3: Add outsourced part with invalid inventory - out of range equivalence class")
    @ParameterizedTest
    @CsvSource({"parte1, 10, 10, 20, 300, company_a", "parte1, 10, 400, 20, 300, company_b"})
    void addOutsourcedPart_withInventoryBelowMin(String name, String price,
                                                                 String inventory, String min,
                                                                 String max, String company) {
        // Arrange
        int previousSize = service.getAllParts().size();
        // Act
        service.addOutsourcePart(name, Double.parseDouble(price),
                Integer.parseInt(inventory), Integer.parseInt(min),
                Integer.parseInt(max), company);

        // Assert
        assertEquals(previousSize, service.getAllParts().size());
    }

    @Order(4)
    @DisplayName("ECP4: Add outsourced part with valid price - equivalence class")
    @Test
    void addOutsourcedPart_withValidPrice() {
        // Arrange
        this.price = 100.50;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize + 1, service.getAllParts().size());
    }

    @Order(5)
    @Test
    @DisplayName("BVA1: Add outsourced part with inventory at minimum boundary - valid")
    void addOutsourcedPart_withMinimumValidPrice() {
        // Arrange
        this.inStock = this.min;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize + 1, service.getAllParts().size());
    }

    @Order(6)
    @Test
    @DisplayName("BVA2: Add outsourced part with inventory under minimum boundary - invalid")
    @Timeout(100)
    void addOutsourcedPart_withUnderMinimumValidPrice(){
        // Arrange
        this.inStock = this.min - 1;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize, service.getAllParts().size());
    }
    @Order(7)
    @Test
    @DisplayName("BVA3: Add outsourced part with price maximum possible value - valid")
    void addOutsourcedPart_withMaxPrice() {
        // Arrange
        this.price = Double.MAX_VALUE;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize + 1, service.getAllParts().size());
    }


    @Order(8)
    @Test
    @DisplayName("BVA4: Add outsourced part with price smaller than 0 - invalid")
    void addOutsourcedPart_withSmallerThan0Price() {
        // Arrange
        this.price = -0.000001;
        int previousSize = service.getAllParts().size();

        // Act
        service.addOutsourcePart(name, price, inStock, min, max, partDynamicValue);

        // Assert
        assertEquals(previousSize , service.getAllParts().size());
    }


}

