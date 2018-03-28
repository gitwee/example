package com.fungwen.com.test;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * 通过组合的方式来实现线程安全的集合的扩展。这里需假设客户端代码在使用list集合对象
 * 构建该类对象后不在使用list对象做保证（就像Collections.synchronizedList()方法
 * 一样），否则对list的使用会破坏ImprovedList的线程安全性。
 *
 */
public class ImprovedList<E> implements List<E>{

    private  final  List<E> list;

    public ImprovedList(List<E> list) {
        this.list = list;
    }

    public synchronized boolean putIfAbsent(E e) {
        boolean contain = contains(e);
        if (!contain) {
            list.add(e);
        }
        return contain;
    }

    @Override
    public synchronized void  replaceAll(UnaryOperator<E> operator) {
        list.replaceAll(operator);
    }

    @Override
    public synchronized void sort(Comparator<? super E> c) {
        list.sort(c);
    }

    @Override
    public synchronized Spliterator<E> spliterator() {
        return list.spliterator();
    }

    @Override
    public synchronized int size() {
        return list.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public synchronized Object[] toArray() {
        return list.toArray();
    }

    @Override
    public synchronized  <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public synchronized boolean add(E e) {
        return list.add(e);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public synchronized void clear() {
        list.clear();
    }

    @Override
    public synchronized E get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public synchronized void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public synchronized E remove(int index) {
        return list.remove(index);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public synchronized ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public synchronized ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public static  void  main(String[] args) {
        List<String> list = new ArrayList<>();
        Collections.synchronizedList(list);
        ImprovedList<String> improvedList = new ImprovedList<>(list);

        list.add("abc");


        for (String e: improvedList) {
            System.out.println(e);
        }
    }
}
