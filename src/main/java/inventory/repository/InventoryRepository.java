package inventory.repository;

import inventory.exception.InventoryException;
import inventory.exception.PartNotFoundException;
import inventory.factory.PartFactory;
import inventory.factory.ProductFactory;
import inventory.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.StringTokenizer;

public class InventoryRepository {

    private static String filename = "data/items.txt";
    private PartRepository partRepository;
    private ProductRepository productRepository;

    public InventoryRepository(PartRepository partRepository, ProductRepository productRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
    }

    public void readAll() throws InventoryException {
        readParts();
        readProducts();
    }

    public void readParts() throws InventoryException {
        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);
        ObservableList<Part> listP = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Part part = getPartFromString(line);
                if (part != null)
                    listP.add(part);
            }
        } catch (IOException e) {
            throw new InventoryException("Error reading parts from file: " + e.getMessage());
        }
        partRepository.setAllParts(listP);
    }

    private Part getPartFromString(String line) {
        Part item = null;
        if (line == null || line.equals("")) return null;
        StringTokenizer st = new StringTokenizer(line, ",");
        String type = st.nextToken();
        if (type.equals("I")) {
            int id = Integer.parseInt(st.nextToken());
            partRepository.setAutoPartId(id);
            String name = st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            int idMachine = Integer.parseInt(st.nextToken());

            return PartFactory.createInhousePart(id, name, price, inStock, minStock, maxStock, idMachine);
        }
        if (type.equals("O")) {
            int id = Integer.parseInt(st.nextToken());
            partRepository.setAutoPartId(id);
            String name = st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            String company = st.nextToken();
            return PartFactory.createOutsourcedPart(id, name, price, inStock, minStock, maxStock, company);
        }
        return null;
    }

    public void readProducts()throws InventoryException {
        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);

        ObservableList<Product> listP = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = null;
            while ((line = br.readLine()) != null) {
                Product product = getProductFromString(line);
                if (product != null)
                    listP.add(product);
            }
        } catch (IOException e) {
            throw new InventoryException("Error reading products from file: " + e.getMessage());
        }
        productRepository.setProducts(listP);
    }

    private Product getProductFromString(String line) {
        Product product = null;
        if (line == null || line.equals("")) return null;
        StringTokenizer st = new StringTokenizer(line, ",");
        String type = st.nextToken();
        if (type.equals("P")) {
            int id = Integer.parseInt(st.nextToken());
            productRepository.setAutoProductId(id);
            String name = st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            String partIDs = st.nextToken();

            StringTokenizer ids = new StringTokenizer(partIDs, ":");
            ObservableList<Part> list = FXCollections.observableArrayList();
            while (ids.hasMoreTokens()) {
                String idP = ids.nextToken();
                try{
                    Part part = partRepository.lookupPart(idP);
                    list.add(part);
                } catch (PartNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return ProductFactory.createProduct(id, name, price, inStock, minStock, maxStock, list);
        }
        return null;
    }

    public void writeAll() throws InventoryException {

        //ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(filename);

        ObservableList<Part> parts = partRepository.getAllParts();
        ObservableList<Product> products = productRepository.getProducts();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));) {
            for (Part p : parts) {
                System.out.println(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }

            for (Product pr : products) {
                StringBuilder sb = new StringBuilder(pr.toString() + ",");
                ObservableList<Part> list = pr.getAssociatedParts();
                int index = 0;
                while (index < list.size() - 1) {
                    sb.append(list.get(index).getPartId()).append(":");
                    index++;
                }
                if (index == list.size() - 1)
                    sb.append(list.get(index).getPartId());
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new InventoryException("Error writing to file: " + e.getMessage());
        }
    }


}
