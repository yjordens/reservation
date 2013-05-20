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
public class ReservatiePeriodeValidator implements IFormValidator {

    /** form components to be checked. */
	private final FormComponent<?>[] components;

    public ReservatiePeriodeValidator(FormComponent<?>[] components) {
        this.components = components;
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return components;
    }

    @Override
    public void validate(Form<?> form) {
        FormComponent<?>[] depeComponents = getDependentFormComponents();
        DatePickerField begindatumFc = (DatePickerField)depeComponents[0];
        DatePickerField einddatumFc = (DatePickerField)depeComponents[1];
        FormComponent starttijdFc = depeComponents[2];
        FormComponent eindtijdFc = depeComponents[3];

        Date start = begindatumFc.getConvertedInput();
        Date einde = einddatumFc.getConvertedInput();
        TijdEnum startTijd = (TijdEnum)starttijdFc.getConvertedInput();
        TijdEnum eindtTijd = (TijdEnum)eindtijdFc.getConvertedInput();

        GregorianCalendar beginCal = new GregorianCalendar();
        beginCal.setTime(start);
        beginCal.set(Calendar.HOUR, DateTimeUtil.getUur(startTijd));
        beginCal.set(Calendar.MINUTE, DateTimeUtil.getMinuten(startTijd));
        GregorianCalendar eindCal = new GregorianCalendar();
        eindCal.setTime(einde);
        eindCal.set(Calendar.HOUR, DateTimeUtil.getUur(eindtTijd));
        eindCal.set(Calendar.MINUTE, DateTimeUtil.getMinuten(eindtTijd));
        if (eindCal.getTime().before(beginCal.getTime())) {
            form.error(new StringResourceModel("period.end.before.start", form.getPage(), null).getString());
        } else if (eindCal.getTime().equals(beginCal.getTime())) {
            form.error(new StringResourceModel("period.end.equals.start", form.getPage(), null).getString());
        } else if (beginCal.getTime().before(new Date())) {
            form.error(new StringResourceModel("period.start.before.now", form.getPage(), null).getString());
        }
    }
}
