package co.edu.uptc.model;

import co.edu.uptc.structures.AVLTree;

import java.util.Comparator;
import java.util.List;

public class Township {
    private String name;
    private AVLTree<Inhabitant> inhabitants;

    public Township(String name) {
        this.name = name;
        this.inhabitants = new AVLTree<>(Comparator.comparing(Inhabitant::getName));
    }

    public synchronized boolean addInhabitant(Inhabitant inhabitant) {
        boolean added = false;
        if(inhabitants.searchData(inhabitant) == null){
            added = inhabitants.insert(inhabitant);
        }
        return added;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized List <Inhabitant> getInhabitants() {
        return this.inhabitants.inOrder();
    }

    public synchronized int calculateNumberInhabitants() {
        return this.inhabitants.preOrder().size();
    }







}
