package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.web.frontend.columns.RadioColumn;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.panels.GridPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordens
 * @since 22/04/14
 */
public class ZoekAutomatischListPanel extends GridPanel<Wagen, String> {

    private LazyListDataProvider<Wagen> wagenIDataProvider;
    @SpringBean
    private GebruikerService gebruikerService;

    private RadioGroup radioGroup;

    /**
     * Initializes the DataTable and adds a ActionColumn with edit and delete buttons
     *
     * @param id                   The id
     * @param wagenIDataProvider
     *                             The dataprovider containing methods to access the service layer
     */
    public ZoekAutomatischListPanel(final String id, RadioGroup radioGroup, final LazyListDataProvider<Wagen> wagenIDataProvider) {
        super(id, wagenIDataProvider);
        this.wagenIDataProvider = wagenIDataProvider;
        this.radioGroup = radioGroup;
    }

    @Override
    protected List<IColumn<Wagen, String>> getColumns() {
        List<IColumn<Wagen, String>> columns = new ArrayList<IColumn<Wagen, String>>();
        columns.add(new RadioColumn<Wagen, String> (new Model<String>(), "id", radioGroup));
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Nummerplaat"), "nummerplaat"));
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Merk"), "merk"));
        columns.add(new PropertyColumn<Wagen, String>(new Model<String>("Type"), "merktype"));
        return columns;
    }
}
