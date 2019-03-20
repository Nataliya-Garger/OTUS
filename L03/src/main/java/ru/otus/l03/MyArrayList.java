package ru.otus.l03;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayList<E> implements List<E> {

    private int size;

    private Object[] elementData;

    private static final int MAX_ELEMENTS_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_DATA = {};

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0)
            this.elementData = new Object[initialCapacity];
        else if (initialCapacity == 0)
            this.elementData = EMPTY_DATA;
        else
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
    }

    public MyArrayList() {
        this.elementData = EMPTY_DATA;
    }

    public MyArrayList(Collection<? extends E> collection) {
        elementData = collection.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            elementData = EMPTY_DATA;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    @Override
    public ListIterator listIterator() {
        return new MyListMyIterator(0);
    }

    @Override
    public ListIterator listIterator(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        return new MyListMyIterator(index);
    }


    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        } else {
            System.arraycopy(elementData, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        }
    }

    @Override
    public boolean add(E e) {
        add(e, elementData, size);
        return true;
    }

    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow(size + 1);
        elementData[s] = e;
        size = s + 1;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.elementData).length)
            elementData = grow(size + 1);
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    removeFromIndex(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    removeFromIndex(index);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        Object[] cArray = collection.toArray();
        int cArrayLength = cArray.length;
        if (cArrayLength == 0)
            return false;
        Object[] elementData = this.elementData;
        final int s = size;
        if (cArrayLength > elementData.length - s)
            elementData = grow(s + cArrayLength);

        int numMoved = s - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index,
                    elementData, index + cArrayLength,
                    numMoved);
        System.arraycopy(cArray, 0, elementData, index, cArrayLength);
        size = s + cArrayLength;
        return true;
    }

    @Override
    public boolean addAll(Collection collection) {
        Object[] cArray = collection.toArray();
        int cArrayLength = cArray.length;
        if (cArrayLength == 0)
            return false;
        Object[] elementData = this.elementData;
        final int s = size;
        if (cArrayLength > elementData.length - s)
            elementData = grow(s + cArrayLength);
        System.arraycopy(cArray, 0, elementData, s, cArrayLength);
        size = s + cArrayLength;
        return true;
    }


    @Override
    public void clear() {
        final Object[] ed = elementData;
        for (int i = 0; i < size; i++) {
            ed[i] = null;
        }
        size = 0;

    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index, size);
        return (E) elementData[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        checkIndex(index, size);
        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index, size);
        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null;

        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null)
                    return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elementData[i].equals(o))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null)
                    return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i].equals(o))
                    return i;
            }
        }
        return -1;
    }


    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new RuntimeException("Method not allowed");
    }

    @Override
    public boolean retainAll(Collection collection) {
        return batchRemove(collection, true, size);
    }

    @Override
    public boolean removeAll(Collection collection) {
        return batchRemove(collection, false, size);
    }

    public boolean containsAll(Collection collection) {
        for (Object e : collection)
            if (!contains(e))
                return false;
        return true;
    }

    private void removeFromIndex(int index) {
        int count = size - index - 1;
        if (count > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, count);
            elementData[--size] = null;
        }

    }


    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
                newCapacity(minCapacity));
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elementData == EMPTY_DATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0)
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - (Integer.MAX_VALUE - 8) <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }


    private int hugeCapacity(int minCapacity) {
        if (minCapacity < 0)
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ELEMENTS_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ELEMENTS_SIZE;
    }


    private void checkIndex(int index, int length) {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }


    private boolean batchRemove(Collection<?> c, boolean complement, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = elementData;
        final boolean modified;
        int r;
        for (r = 0; r < end && c.contains(es[r]) == complement; r++)
            ;
        if (modified = (r < end)) {
            int w = r++;
            try {
                for (Object e; r < end; r++)
                    if (c.contains(e = es[r]) == complement)
                        es[w++] = e;
            } catch (Throwable ex) {
                System.arraycopy(es, r, es, w, end - r);
                w += end - r;
                throw ex;
            } finally {
                System.arraycopy(es, end, es, w, size - end);
                for (int to = size, i = (size -= end - w); i < to; i++)
                    es[i] = null;
            }
        }
        return modified;
    }


    private class MyIterator implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = elementData;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size; i++)
                    action.accept((E) es[i]);
                cursor = i;
                lastRet = i - 1;
            }
        }
    }


    private class MyListMyIterator extends MyIterator implements ListIterator<E> {

        MyListMyIterator(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E e) {
            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
