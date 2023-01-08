package org.kehrbusch.entities.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Element {
    private String parent;
    private String key;
    private String data;

    private final List<String> children;

    public Element(){
        this.children = new ArrayList<>();
    }

    public Element(String parent, String data, String key) {
        this.parent = parent;
        this.data = data;
        this.key = key;
        this.children = new ArrayList<>();
    }

    public Element(String parent, String data, String key, List<String> children){
        this.parent = parent;
        this.data = data;
        this.key = key;
        this.children = children;
    }

    public List<String> getChildren(){
        return this.children;
    }

    public String getNextChildKey(String key){
        int index = this.children.indexOf(key);
        return (index + 1) > (this.children.size() - 1) ? null : this.children.get(index + 1);
    }

    public void addChild(String child){
        this.children.add(child);
    }

    public void addAllChildren(List<String> children){
        this.children.addAll(children);
    }

    public boolean hasChildren(){
        return this.children.isEmpty();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static Element buildFromString(String line){
        String[] parentSegments = line.split(",");
        List<String> children;
        if (parentSegments.length >= 4){
            String[] childSegments = parentSegments[3].split(":");
            children = Arrays.asList(childSegments);
        } else {
            children = new ArrayList<>();
        }
        return new Element(parentSegments[1], parentSegments[2], parentSegments[0], children);
    }

    @Override
    public String toString(){
        System.out.println("###");
        System.out.println("Key: " + getKey());
        System.out.println("Data:" + getData());
        System.out.println("Children: " + getChildren());
        System.out.println("Parent: " + getParent());
        System.out.println("###");
        return "";
    }
}
