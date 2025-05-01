package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceMockTest {

    @Mock
    private InventoryRepository mockRepository;

    private InventoryService service;
    private ObservableList<Part> testParts;

    @BeforeEach
    void setUp() {
        // Initialize service with mock repository
        service = new InventoryService(mockRepository);

        // Create test parts list
        testParts = FXCollections.observableArrayList();
        testParts.add(new InhousePart(1, "Test Part", 15.0, 10, 5, 20, 101));
    }

    @Test
    void testAddInhousePart() {
        // Setup mock
        when(mockRepository.getAutoPartId()).thenReturn(1);

        // Call method
        service.addInhousePart("Test Part", 15.0, 10, 5, 20, 101);

        // Verify repository methods were called
        verify(mockRepository).getAutoPartId();
        verify(mockRepository).addPart(any(InhousePart.class));
    }

    @Test
    void testAddOutsourcedPartWithValidValues() {
        // Setup mock
        when(mockRepository.getAutoPartId()).thenReturn(1);

        // Call method
        service.addOutsourcePart("Test Part", 15.0, 10, 5, 20, "Test Company");

        // Verify repository methods were called
        verify(mockRepository).getAutoPartId();
        verify(mockRepository).addPart(any(OutsourcedPart.class));
    }
}