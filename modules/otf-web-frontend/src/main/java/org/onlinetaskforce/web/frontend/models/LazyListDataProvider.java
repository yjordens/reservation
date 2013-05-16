package org.onlinetaskforce.web.frontend.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @param <T> the generic type
 * @author jordens
 * @since 10/04/13
 */
public abstract class LazyListDataProvider<T extends Serializable> implements IDataProvider<T> {
    private static final long serialVersionUID = 1L;

    /**
     * reference to the list used as dataprovider for the dataview
     */
    private List<T> list;

    /**
     * Constructs an empty provider. Useful for lazy loading together with {@linkplain #getData()}
     */
    public LazyListDataProvider() {
        Injector.get().inject(this);
        list = Collections.<T>emptyList();
    }

    /**
     * Subclass to lazy load the list
     *
     * @return The list
     */
    protected abstract List<T> getData();

    /**
     * This size method will not trigger a new Load action
     * Can be useful when loading data from a DAO, in this case we avoid the same query being executed twice
     *
     * @return Total size of the data list
     */
    @Override
    public long size() {
        list = getData();
        return list.size();
    }

    @Override
    public Iterator<? extends T> iterator(long first, long count) {
        long toIndex = first + count;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }

        return list.subList((int)first, (int)toIndex).listIterator();
    }

    @Override
    public IModel<T> model(T object) {
        return new Model<T>(object);
    }

    @Override
    public void detach() {
    }

    public List<T> getList() {
        return list;
    }
}

