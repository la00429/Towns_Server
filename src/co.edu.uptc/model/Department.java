package co.edu.uptc.model;

import co.edu.uptc.structures.BinaryTree;

import java.util.Comparator;

public class Department {
    private BinaryTree<Township> townships;

    public Department() {
        this.townships = new BinaryTree<>(Comparator.comparing(Township::getName));
    }

    public synchronized boolean addInhabitantInTown(String townshipName, Inhabitant inhabitant) {
        boolean added = false;
        if(searchTownship(townshipName) == null){
            Township township = new Township(townshipName.toUpperCase().replace(" ", ""));
            this.townships.insert(township);
            added = township.addInhabitant(inhabitant);
        }else {
            added = searchTownship(townshipName).addInhabitant(inhabitant);
        }
        return added;
    }

    public synchronized String calculateMostPopulous(){
        return this.townships.inOrder().stream().max(Comparator.comparing(Township::calculateNumberInhabitants)).get().getName();
    }

    public synchronized String calculateMinorPopulous(){
        return this.townships.inOrder().stream().min(Comparator.comparing(Township::calculateNumberInhabitants)).get().getName();
    }

    public synchronized Township searchTownship(String townshipName) {
        return this.townships.searchData(new Township(townshipName.toUpperCase().replace(" ", "")));
    }
}
