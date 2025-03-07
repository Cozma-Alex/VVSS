package inventory.service;

import inventory.exception.InventoryException;
import inventory.exception.PartNotFoundException;
import inventory.exception.ProductNotFoundException;
import inventory.exception.ValidationException;
import inventory.factory.PartFactory;
import inventory.factory.ProductFactory;
import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.repository.PartRepository;
import inventory.repository.ProductRepository;
import javafx.collections.ObservableList;

public class InventoryService {

    private PartRepository partRepo;
    private ProductRepository productRepo;
    private InventoryRepository repo;
    public InventoryService(InventoryRepository repo, PartRepository partRepo, ProductRepository productRepo){
        this.repo =repo;
        this.partRepo = partRepo;
        this.productRepo = productRepo;

        try{
            repo.readAll();
        } catch(InventoryException e){
            System.err.println("Error initializing inventory: " + e.getMessage());
        }
    }

    public void addInhousePart(String name, double price, int inStock, int min, int max, int machineId) throws ValidationException {
        String errorMessage = Part.isValidPart(name, price, inStock, min, max, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Part part = PartFactory.createInhousePart(partRepo.getAutoPartId(), name, price, inStock, min, max, machineId);
        partRepo.addPart(part);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error saving part: " + e.getMessage());
        }
    }
    public void addOutsourcePart(String name, double price, int inStock, int min, int max, String companyName) throws ValidationException {
        String errorMessage = Part.isValidPart(name, price, inStock, min, max, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Part part = PartFactory.createOutsourcedPart(partRepo.getAutoPartId(), name, price, inStock, min, max, companyName);
        partRepo.addPart(part);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error saving part: " + e.getMessage());
        }
    }

    public void addProduct(String name, double price, int inStock, int min, int max, ObservableList<Part> parts) throws ValidationException {
        String errorMessage = Product.isValidProduct(name, price, inStock, min, max, parts, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Product product = ProductFactory.createProduct(productRepo.getAutoProductId(), name, price, inStock, min, max, parts);
        productRepo.addProduct(product);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error saving product: " + e.getMessage());
        }
    }

    public ObservableList<Part> getAllParts() {
        return partRepo.getAllParts();
    }

    public ObservableList<Product> getAllProducts() {
        return productRepo.getProducts();
    }

    public Part lookupPart(String search) throws PartNotFoundException {
        return partRepo.lookupPart(search);
    }

    public Product lookupProduct(String search) throws ProductNotFoundException {
        return productRepo.lookupProduct(search);
    }

    public void updateInhousePart(int partIndex, int partId, String name, double price, int inStock, int min, int max, int machineId) throws ValidationException {
        String errorMessage = Part.isValidPart(name, price, inStock, min, max, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Part part = PartFactory.createInhousePart(partId, name, price, inStock, min, max, machineId);
        partRepo.updatePart(partIndex, part);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error updating part: " + e.getMessage());
        }
    }

    public void updateOutsourcedPart(int partIndex, int partId, String name, double price, int inStock, int min, int max, String companyName) throws ValidationException {
        String errorMessage = Part.isValidPart(name, price, inStock, min, max, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Part part = PartFactory.createOutsourcedPart(partId, name, price, inStock, min, max, companyName);
        partRepo.updatePart(partIndex, part);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error updating part: " + e.getMessage());
        }
    }

    public void updateProduct(int productIndex, int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> parts) throws ValidationException {
        String errorMessage = Product.isValidProduct(name, price, inStock, min, max, parts, "");
        if (!errorMessage.isEmpty()) {
            throw new ValidationException(errorMessage);
        }

        Product product = ProductFactory.createProduct(productId, name, price, inStock, min, max, parts);
        productRepo.updateProduct(productIndex, product);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    public void deletePart(Part part) {
        partRepo.deletePart(part);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error deleting part: " + e.getMessage());
        }
    }

    public void deleteProduct(Product product) {
        productRepo.removeProduct(product);

        try {
            repo.writeAll();
        } catch (InventoryException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

}