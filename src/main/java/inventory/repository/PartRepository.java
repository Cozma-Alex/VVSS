package inventory.repository;

import inventory.exception.PartNotFoundException;
import inventory.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartRepository {
    private ObservableList<Part> allParts;
    private int autoPartId;

    public PartRepository() {
        this.allParts = FXCollections.observableArrayList();
        this.autoPartId = 0;
    }

    public void addPart(Part part) {
        allParts.add(part);
    }

    public void deletePart(Part part) {
        allParts.remove(part);
    }

    public Part lookupPart(String searchItem) throws PartNotFoundException {
        for(Part p: allParts) {
            if(p.getName().contains(searchItem) || (p.getPartId()+"").equals(searchItem))
                return p;
        }
        throw new PartNotFoundException("Part with identifier/name " + searchItem + " not found");

    }

    public void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public void setAllParts(ObservableList<Part> list) {
        allParts = list;
    }

    public int getAutoPartId() {
        autoPartId++;
        return autoPartId;
    }

    public void setAutoPartId(int id) {
        autoPartId = id;
    }
}
