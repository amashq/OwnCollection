import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;

    private Object[] elementData = new Object[10];

    @Override
    public final boolean add(final E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    @Override
    public final Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public final boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if ((elementData[i] == null && o == null) || (o != null && o.equals(elementData[i]))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public final <T> T[] toArray(final T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size);
        } else {
            System.arraycopy(elementData, 0, a, 0, size);
            return a;
        }
    }

    @Override
    public final boolean remove(final Object o) {
        for (int i = 0; i < size; i++) {
            if ((elementData[i] == null && o == null) || (elementData[i] != null && elementData[i].equals(o))) {
                System.arraycopy(elementData, i + 1, elementData, i, size - 1 - i);
                elementData[size--] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean addAll(final Collection<? extends E> c) {
        for (Object o : c) {
            add((E) o);
        }
        return true;
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        boolean result = false;
        for (Object o : c) {
            while (contains(o)) {
                remove(o);
                result = true;
            }
        }
        return result;
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        boolean result = false;
        boolean isExist;
        for (int i = 0; i < size; i++) {
            isExist = false;
            for (Object o : c) {
                if ((elementData[i] == null && o == null)
                        || (elementData[i] != null && elementData[i].equals(o))) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                remove(elementData[i--]);
                result = true;
            }
        }
        return result;
    }

    @Override
    public final void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {
        private int cursor = 0;
        private boolean checkRemove = false;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            checkRemove = true;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (checkRemove) {
                System.arraycopy(elementData, cursor, elementData, cursor - 1, size - cursor);
                size--;
                elementData[size] = null;
                cursor--;
                checkRemove = false;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
