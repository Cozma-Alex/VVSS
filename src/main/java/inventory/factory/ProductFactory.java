package inventory.factory;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.ObservableList;

public class ProductFactory {

    public static Product createProduct(int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> parts) {
        return new Product(productId, name, price, inStock, min, max, parts);
    }
}
