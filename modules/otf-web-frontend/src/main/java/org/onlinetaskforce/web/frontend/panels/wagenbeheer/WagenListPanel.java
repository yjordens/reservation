package org.onlinetaskforce.web.frontend.panels.wagenbeheer;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.wagenbeheer.DetailWagenPage;
import org.onlinetaskforce.web.frontend.panels.GridPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author jordens
 * @since 22/04/14
 */
public class WagenListPanel extends GridPanel<Wagen, String> {

    private IModel<ZoekWagenDto> zoekWagenModel;
    private LazyListDataProvider<Wagen> wagenIDataProvider;

    /**
     * Initializes the DataTable and adds a ActionColumn with edit and delete buttons
     *
     * @param id                   The id
     * @param wagenIDataProvider
     *                             The dataprovider containing methods to access the service layer
     * @param zoekWagenModel CriteriaDto
     */
    public WagenListPanel(final String id, final LazyListDataProvider<Wagen> wagenIDataProvider, final IModel<ZoekWagenDto> zoekWagenModel) {
        super(id, wagenIDataProvider);
        this.zoekWagenModel = zoekWagenModel;
        this.wagenIDataProvider = wagenIDataProvider;
    }

    @Override
    protected List<IColumn<Wagen, String>> getColumns() {
        List<IColumn<Wagen, String>> columns = new ArrayList<IColumn<Wagen, String>>();
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Nummerplaat"), "nummerplaat"));
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Merk"), "merk"));
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Merktype"), "merktype"));
        columns.add(new AbstractColumn<Wagen, String>(new Model<String>("Actief")) {
            @Override
            public void populateItem(Item<ICellPopulator<Wagen>> cellItem, String componentId, IModel<Wagen> rowModel) {
                ActiefConverter converter = new ActiefConverter();
                Wagen wagen = rowModel.getObject();
                String actiefStr = converter.convertToString(wagen.getActief(), Locale.getDefault());
                cellItem.add(new Label(componentId, actiefStr));
            }
        });
        return columns;
    }

    @Override
    protected boolean isDetailLinkEnabled() {
        return true;
    }

    @Override
    protected void detailAction(IModel<Wagen> model) {
//        TODO_SMU the grpDetailPage should not be aware of the zoekCriteriaModel
        setResponsePage(new DetailWagenPage(model, zoekWagenModel, true));
    }
}
