package org.onlinetaskforce.web.frontend.columns;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

/**
 * RadioColumn to use in DataTables to create a selection column
 *
 * @param <T> Model object type
 *
 * @author jordens
 * @since 03/05/13
 */
public class RadioColumn<T, S> extends AbstractColumn<T, S> {

    private String propertyExpression;
    private RadioGroup radioGroup;

    /**
     * Constructs the column
     * @param displayModel The model
     */
    public RadioColumn(IModel<String> displayModel) {
        super(displayModel);
    }

    /**
     * Constructs the column
     * @param displayModel The model
     * @param propertyExpression Property expression used by the radio button
     */
    public RadioColumn(IModel<String> displayModel, String propertyExpression, RadioGroup radioGroup) {
        super(displayModel);
        this.propertyExpression = propertyExpression;
        this.radioGroup = radioGroup;
    }

    @Override
    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
        cellItem.add(new RadioPanel(componentId, rowModel, radioGroup));
    }

    /**
     * The panel containing the radiobutton
     */
    private class RadioPanel extends BasicPanel {

        private IModel<T> rowModel;
        private RadioGroup radioGroup;

        /**
         * Constructs the Panel
         * @param id Component's id
         * @param rowModel The Model
         */
        public RadioPanel(String id, IModel<T> rowModel, RadioGroup radioGroup) {
            super(id, rowModel);
            this.rowModel = rowModel;
            this.radioGroup = radioGroup;
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            add(new Radio<T>("radio", new PropertyModel<T>(rowModel, propertyExpression), radioGroup));
        }
    }
}
