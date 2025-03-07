package inventory.repository;

import inventory.exception.ProductNotFoundException;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductRepository {
    private ObservableList<Product> products;
    private int autoProductId;

    public ProductRepository() {
        this.products = FXCollections.observableArrayList();
        this.autoProductId = 0;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Product lookupProduct(String searchItem) throws ProductNotFoundException {
        for(Product p: products) {
            if(p.getName().contains(searchItem) || (p.getProductId()+"").equals(searchItem))
                return p;
        }
        throw new ProductNotFoundException("Product with identifier " + searchItem + " not found");
    }

    public void updateProduct(int index, Product product) {
        products.set(index, product);
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(ObservableList<Product> list) {
        products = list;
    }

    public int getAutoProductId() {
        autoProductId++;
        return autoProductId;
    }

    public void setAutoProductId(int id) {
        autoProductId = id;
    }
}
