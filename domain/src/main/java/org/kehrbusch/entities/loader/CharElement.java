package org.kehrbusch.entities.loader;

import org.kehrbusch.util.ElementList;

import java.util.List;

public class CharElement implements Node {
    private Long id;
    private Boolean isRoot;
    private CharElement parent;
    private List<CharElement> childNodes;
    private Long length;
    private String data;

    public CharElement(){
        this.childNodes = new ElementList<>();
    }

    public CharElement(Boolean isRoot, CharElement parent, String data){
        this.isRoot = isRoot;
        this.childNodes = new ElementList<>();
        this.parent = parent;
        this.data = data;
        if (parent != null) this.parent.appendChild(this);
    }

    public CharElement(Boolean isRoot, CharElement parent, String data, List<CharElement> elementList){
        this.isRoot = isRoot;
        this.parent = parent;
        this.data = data;
        this.childNodes = elementList;
    }

    public CharElement(Boolean isRoot, String data, List<CharElement> elementList){
        this.isRoot = isRoot;
        this.data = data;
        this.childNodes = elementList;
    }

    @Override
    public String getNodeValue() {
        return this.data;
    }

   @Override
    public List<CharElement> getChildNodes() {
        return this.childNodes;
    }

    public void setChildNodes(List<CharElement> childNodes){
        this.childNodes = childNodes;
    }

    @Override
    public Node getFirstChild() {
        return this.childNodes.get(0);
    }

    @Override
    public void appendChild(CharElement node) {
        this.childNodes.add(node);
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParent(CharElement parent) {
        this.parent = parent;
    }

    public CharElement getParent(){
        return this.parent;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }
}
