package org.onlinetaskforce.web.frontend.components.datepicker;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;

import java.util.Date;

/**
 * DateTextField with datePicker behavior already added.
 *
 * @author jordens
 * @since 29/4/2013
 */
public class DatePickerField extends DateTextField {
    /**
     * Construct with a converter, and a null model.
     *
     * @param id The component id
     */
    public DatePickerField(String id) {
        super(id, (DateConverter) WebApplication.get().getConverterLocator().getConverter(Date.class));
    }

    /**
     * Construct with a converter.
     *
     * @param id    The component id
     * @param model The model
     */
    public DatePickerField(String id, IModel<Date> model) {
        super(id, model, (DateConverter) WebApplication.get().getConverterLocator().getConverter(Date.class));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new OtfDatePicker().setShowOnFieldClick(true).setAutoHide(true));
    }
}
