package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.web.frontend.repeater.DiscimusDataTable;

import java.util.List;

/**
 * Abstract CRUD Gridview panel with add, edit and delete links
 * Extend this Panel and override add, edit and delete actions
 *
 * @param <T> the generic type
 * @author jordens
 * @since 03/05/13
 */
public abstract class GridPanel<T, S> extends BasicPanel {

    /**
     * Default items per page used by the paging mechanism
     */
    public static final int ITEMS_PER_PAGE = 20;

    private IDataProvider<T> dataProvider;

    /**
     * Initializes the DataTable and adds a ActionColumn with edit and delete buttons
     *
     * @param id           The id
     * @param dataProvider The dataprovider containing methods to access the service layer
     */
    protected GridPanel(final String id, final IDataProvider<T> dataProvider) {
        super(id);
        this.dataProvider = dataProvider;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<IColumn<T, S>> columns = getColumns();
        if (isDetailLinkEnabled() || isEditLinkEnabled() || isDeleteLinkEnabled()) {
            columns.add(this.createActionColumn());
        }

        this.add(createDataTable("datatable", columns, getDataProvider(), ITEMS_PER_PAGE));
        this.add(createAddLink());
    }

    /**
     * Creates the column containing the possible action links.
     *
     * @return the column
     */
    protected AbstractColumn<T, S> createActionColumn() {
        return new AbstractColumn<T, S>(new Model<String>(new StringResourceModel("label.acties", this, null).getString())) { // NOSONAR
            @Override
            public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> model) {
                cellItem.add(new ActionPanel<T, S>(componentId, model, GridPanel.this));
            }
        };
    }

    /**
     * Creates the DataTable. Overridable when necessary.
     *
     * @param id           the table id
     * @param columns      the table columns
     * @param dataProvider the dataprovider
     * @param rowsPerPage  the number of rows per page
     * @return the created table.
     */
    protected DataTable<T, S> createDataTable(String id, List<IColumn<T, S>> columns, IDataProvider<T> dataProvider, int rowsPerPage) {
        return new DiscimusDataTable<T, S>(id, columns, dataProvider, rowsPerPage);
    }

    private Link createAddLink() {
        return new Link("lnkAdd") { //NOSONAR
            @Override
            public void onClick() {
                createNewAddPanel();
            }

            @Override
            protected void onConfigure() {
                this.setVisibilityAllowed(isAddLinkEnabled());
            }
        };
    }

    /**
     * Enforce the subclass to provide columns for the DataTable
     *
     * @return a list with IColumn objects
     */
    protected abstract List<IColumn<T, S>> getColumns();

    /**
     * Enforce the concrete subclass to provide a new AddPanel instance
     *
     * @return The Add Page
     */
    protected PojoFormPanel createNewAddPanel() {
        return null;
    }

    /**
     * Enforce the concrete subclass to provide a new PojoFormPanel instance
     *
     * @param model The model of the selected row
     * @return The Edit Page
     */
    protected PojoFormPanel createNewEditPanel(IModel<T> model) {
        return null;
    }

    /**
     * Enforce the concrete subclass to provide a Delete Action
     * This method is called after asking a deletion confirmation
     *
     * @param model The model of the selected row
     */
    protected void deleteAction(IModel<T> model) {
    }

    /**
     * Enforce the concrete subclass to provide a Detail Action
     * This method is called after asking a deletion confirmation
     *
     * @param model The model that gets deleted
     */
    protected void detailAction(IModel<T> model) {
    }

    protected boolean isAddLinkEnabled() {
        return false;
    }

    protected boolean isEditLinkEnabled() {
        return false;
    }

    protected boolean isDeleteLinkEnabled() {
        return false;
    }

    protected boolean isDetailLinkEnabled() {
        return false;
    }

    protected final IDataProvider<T> getDataProvider() {
        return this.dataProvider;
    }
}