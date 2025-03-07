package inventory.factory;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;

public class PartFactory {
    public static Part createInhousePart(int partId, String name, double price, int inStock, int min, int max, int machineId) {
        return new InhousePart(partId, name, price, inStock, min, max, machineId);
    }

    public static Part createOutsourcedPart(int partId, String name, double price, int inStock, int min, int max, String companyName) {
        return new OutsourcedPart(partId, name, price, inStock, min, max, companyName);
    }

}
