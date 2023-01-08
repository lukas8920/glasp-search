package org.kehrbusch.util;

import org.kehrbusch.entities.loader.Node;

import java.util.*;

public class ElementList<T extends Node> implements NodeList<T> {
    private final List<T> nodes;

    public ElementList(){
        this.nodes = new ArrayList<>();
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public boolean isEmpty(){
        return this.nodes.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return nodes.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return nodes.iterator();
    }

    @Override
    public Object[] toArray() {
        return nodes.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return nodes.toArray(ts);
    }

    @Override
    public boolean add(T node) {
        return nodes.add(node);
    }

    @Override
    public boolean remove(Object o) {
        return nodes.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return nodes.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return nodes.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        return nodes.addAll(i, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return nodes.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return nodes.retainAll(collection);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public T get(int i) {
        return nodes.get(i);
    }

    @Override
    public T set(int i, T node) {
        return nodes.set(i, node);
    }

    @Override
    public void add(int i, T node) {
        nodes.add(i, node);
    }

    @Override
    public T remove(int i) {
        return nodes.remove(i);
    }

    @Override
    public int indexOf(Object o) {
        return nodes.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return nodes.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return nodes.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return nodes.listIterator(i);
    }

    @Override
    public List<T> subList(int i, int i1) {
        return nodes.subList(i, i1);
    }

    @Override
    public int getLength() {
        return nodes.size();
    }
}
