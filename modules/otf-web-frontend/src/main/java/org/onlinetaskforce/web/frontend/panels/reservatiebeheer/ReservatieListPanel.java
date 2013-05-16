package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.DetailReservatiePage;
import org.onlinetaskforce.web.frontend.panels.GridPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordens
 * @since 22/04/14
 */
public class ReservatieListPanel extends GridPanel<Reservatie, String> {

    private IModel<ZoekReservatieDto> zoekReservatieModel;
    private LazyListDataProvider<Reservatie> reservatieIDataProvider;
    @SpringBean
    private GebruikerService gebruikerService;

    /**
     * Initializes the DataTable and adds a ActionColumn with edit and delete buttons
     *
     * @param id                   The id
     * @param reservatieIDataProvider
     *                             The dataprovider containing methods to access the service layer
     * @param zoekReservatieModel CriteriaDto
     */
    public ReservatieListPanel(final String id, final LazyListDataProvider<Reservatie> reservatieIDataProvider, final IModel<ZoekReservatieDto> zoekReservatieModel) {
        super(id, reservatieIDataProvider);
        this.zoekReservatieModel = zoekReservatieModel;
        this.reservatieIDataProvider = reservatieIDataProvider;
    }

    @Override
    protected List<IColumn<Reservatie, String>> getColumns() {
        List<IColumn<Reservatie, String>> columns = new ArrayList<IColumn<Reservatie, String>>();
        columns.add(new PropertyColumn<Reservatie, String>(new Model<String>("Reservatienummer"), "reservatieNummer"));
        columns.add(new PropertyColumn<Reservatie, String>(new Model<String>("Wagen"), "wagen.nummerplaat"));
        columns.add(new PropertyColumn<Reservatie, String>(new Model<String>("GeBruiker"), "creatieGebruikerId") {
            @Override
            public void populateItem(Item<ICellPopulator<Reservatie>> iCellPopulatorItem, String componentId, IModel<Reservatie> rowModel) {
                Gebruiker gebruiker = gebruikerService.getGebruikerById(rowModel.getObject().getCreatieGebruikerId());
                    iCellPopulatorItem.add(new Label(componentId, gebruiker.getFullName()));
            }
        });
        columns.add(new PropertyColumn<Reservatie, String>(new Model<String>("Datum van"), "beginDatum"));
        columns.add(new PropertyColumn<Reservatie, String>(new Model<String>("Datum tot"), "eindDatum"));
        return columns;
    }

    @Override
    protected boolean isDetailLinkEnabled() {
        return true;
    }

    @Override
    protected void detailAction(IModel<Reservatie> model) {
        setResponsePage(new DetailReservatiePage(model, zoekReservatieModel, true));
    }
}
