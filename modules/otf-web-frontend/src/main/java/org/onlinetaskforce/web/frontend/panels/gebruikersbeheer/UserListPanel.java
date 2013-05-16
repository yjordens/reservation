package org.onlinetaskforce.web.frontend.panels.gebruikersbeheer;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.DetailUserPage;
import org.onlinetaskforce.web.frontend.panels.GridPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author jordens
 * @since 22/04/14
 */
public class UserListPanel extends GridPanel<Gebruiker, String> {

    private IModel<ZoekGebruikerDto> zoekGebruikerModel;
    private LazyListDataProvider<Gebruiker> gebruikerIDataProvider;

    /**
     * Initializes the DataTable and adds a ActionColumn with edit and delete buttons
     *
     * @param id                   The id
     * @param gebruikerIDataProvider
     *                             The dataprovider containing methods to access the service layer
     * @param zoekGebruikerModel CriteriaDto
     */
    public UserListPanel(final String id, final LazyListDataProvider<Gebruiker> gebruikerIDataProvider, final IModel<ZoekGebruikerDto> zoekGebruikerModel) {
        super(id, gebruikerIDataProvider);
        this.zoekGebruikerModel = zoekGebruikerModel;
        this.gebruikerIDataProvider = gebruikerIDataProvider;
    }

    @Override
    protected List<IColumn<Gebruiker, String>> getColumns() {
        List<IColumn<Gebruiker, String>> columns = new ArrayList<IColumn<Gebruiker, String>>();
        columns.add(new PropertyColumn<Gebruiker, String>(new Model<String>("Gebruikersnaam"), "username"));
        columns.add(new PropertyColumn<Gebruiker, String>(new Model<String>("Naam"), "naam"));
        columns.add(new PropertyColumn<Gebruiker, String>(new Model<String>("Voornaam"), "voornaam"));
        columns.add(new AbstractColumn<Gebruiker, String>(new Model<String>("Actief")) {
            @Override
            public void populateItem(Item<ICellPopulator<Gebruiker>> cellItem, String componentId, IModel<Gebruiker> rowModel) {
                ActiefConverter converter = new ActiefConverter();
                Gebruiker gebruiker = rowModel.getObject();
                String actiefStr = converter.convertToString(gebruiker.getActief(), Locale.getDefault());
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
    protected void detailAction(IModel<Gebruiker> model) {
//        TODO_SMU the grpDetailPage should not be aware of the zoekCriteriaModel
        setResponsePage(new DetailUserPage(model, zoekGebruikerModel, true));
    }
}
