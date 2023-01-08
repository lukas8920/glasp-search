package org.kehrbusch.entities;

import java.util.ArrayList;
import java.util.List;

public class ElementApi {
    private String parent;
    private String key;
    private String data;

    private final List<String> children;

    public ElementApi(){
        this.children = new ArrayList<>();
    }

    public ElementApi(String parent, String data, String key) {
        this.parent = parent;
        this.data = data;
        this.key = key;
        this.children = new ArrayList<>();
    }

    public ElementApi(String parent, String data, String key, List<String> children){
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
}
