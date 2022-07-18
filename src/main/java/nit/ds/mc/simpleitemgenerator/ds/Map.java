package nit.ds.mc.simpleitemgenerator.ds;

import java.util.LinkedList;

public class Map {

    private class Entry {
        private int key;
        private double value;

        public Entry(int key,double value) {
            this.key = key;
            this.value = value;
        }
    }

    private LinkedList<Entry>[] entries = new LinkedList[3];

    public Map() {

    }


    public void put(int key, double value) {
        int index = Hash(key);
        if(entries[index] == null) {
            entries[index] = new LinkedList<>();
            Entry entry = new Entry(key,value);
            entries[index].add(entry);
        }
        else {
            for(var entry:entries[index])
                if(entry.key == key){
                    entry.value = value;
                    return;
                }
            Entry entry = new Entry(key,value);
            entries[index].add(entry);
        }
    }


    public double get(int key) {
        int index = Hash(key);
        if(entries[index] != null){
            for(var entry:entries[index])
                if(entry.key == key)
                    return entry.value;
        }
        return 0;
    }

    private int Hash(int key) {
        return key%3;
    }
}

