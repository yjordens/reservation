package org.onlinetaskforce.web.frontend.repeater;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxNavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import java.util.List;

/**
 * Basic DataTable implementation
 *
 * @param <T> declaration of a generic type T
 * @author jordens
 * @since 22/4/13
 */
public class DiscimusDataTable<T, S> extends DataTable<T, S> {
    /**
     * Constructor
     *
     * @param id           component id
     * @param iColumns      list of IColumn objects
     * @param dataProvider imodel for data provider
     * @param rowsPerPage  number of rows per page
     */
    public DiscimusDataTable(String id, List<IColumn<T, S>> iColumns, IDataProvider<T> dataProvider, int rowsPerPage) {
        super(id, iColumns, dataProvider, rowsPerPage);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.setOutputMarkupId(true);

        this.add(new AttributeModifier("class", getCssClass()));
        this.add(new AttributeModifier("cellspacing", "1"));

        this.addBottomToolbar(new AjaxNavigationToolbar(this));
        this.addTopToolbar(new AjaxFallbackHeadersToolbar(this, null));
        this.addTopToolbar(new NoRecordsToolbar(this));
    }

    protected String getCssClass() {
        return "spreadsheet";
    }
}
