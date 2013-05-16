package org.onlinetaskforce.web.frontend.validators;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.common.enumerations.TijdEnum;
import org.onlinetaskforce.common.utils.DateTimeUtil;
import org.onlinetaskforce.web.frontend.components.datepicker.DatePickerField;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author jordens
 * @since 1/05/13
 */
public class WagenOntvangstValidator implements IFormValidator {

    /** form components to be checked. */
	private final FormComponent<?>[] components;

    public WagenOntvangstValidator(FormComponent<?>[] components) {
        this.components = components;
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return components;
    }

    @Override
    public void validate(Form<?> form) {
        FormComponent<?>[] depeComponents = getDependentFormComponents();
        DatePickerField ontvangstDatumFc = (DatePickerField)depeComponents[0];
        FormComponent ontvangsttijdFc = depeComponents[1];

        Date date = ontvangstDatumFc.getConvertedInput();
        TijdEnum tijd = (TijdEnum)ontvangsttijdFc.getConvertedInput();

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, DateTimeUtil.getUur(tijd));
        calendar.set(Calendar.MINUTE, DateTimeUtil.getMinuten(tijd));

        Date now = new Date();
        GregorianCalendar today = new GregorianCalendar();
        today.setTime(now);
        today.set(Calendar.MINUTE, today.get(Calendar.MINUTE) + 30);



        if (calendar.getTime().after(today.getTime())) {
            form.error(new StringResourceModel("wagen.ontvangst.tijdstip.failure", form.getPage(), null).getString());
        }
    }
}
