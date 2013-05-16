package org.onlinetaskforce.web.frontend.components;

import org.apache.wicket.Component;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.iterator.ComponentHierarchyIterator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Extended DateTimeField component with customized messages.
 *
 * @author verstrap
 * @since 17/06/12
 */
public class DateTimeField extends org.apache.wicket.extensions.yui.calendar.DateTimeField {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT = DATE_FORMAT + " HH:mm";

    private Map<DateTimeFieldEnum, IModel<String>> labels;

    /**
     * Constructor
     *
     * @param id component id
     */
    public DateTimeField(String id) {
        super(id);
        labels = new HashMap<DateTimeFieldEnum, IModel<String>>();
    }

    /**
     * Constructor
     *
     * @param id    component id
     * @param model component model
     */
    public DateTimeField(String id, IModel<Date> model) {
        super(id, model); // NOSONAR
        labels = new HashMap<DateTimeFieldEnum, IModel<String>>();
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        // modify inner labels
        labels.get(DateTimeFieldEnum.DATE);
        if (getLabel() != null) {
            FormComponent dateField = findChildComponentById("date");
            dateField.setLabel(constructLabelModel(DateTimeFieldEnum.DATE));
            FormComponent hoursField = findChildComponentById("hours");
            hoursField.setLabel(constructLabelModel(DateTimeFieldEnum.HOURS));
            FormComponent minutesField = findChildComponentById("minutes");
            minutesField.setLabel(constructLabelModel(DateTimeFieldEnum.MINUTES));
        }
    }

    @Override
    public String getInput() {
        SimpleDateFormat sfd = new SimpleDateFormat(DATETIME_FORMAT);
        return sfd.format(getConvertedInput());
    }

    @Override
    protected DateTextField newDateTextField(String id, PropertyModel<Date> dateFieldModel) {
        return DateTextField.withConverter(id, dateFieldModel, new PatternDateConverter(DATE_FORMAT, true));
    }

    @Override
    protected boolean use12HourFormat() {
        return false;
    }

    /**
     * Method to add custom labels to the component parts
     *
     * @param key   identifier of the component part
     * @param label label for the component part
     */
    public void addLabel(DateTimeFieldEnum key, IModel<String> label) {
        labels.put(key, label);
    }

    private IModel<String> constructLabelModel(DateTimeFieldEnum key) {
        StringBuffer label = new StringBuffer();

        label.append((getLabel() != null && getLabel().getObject() != null) ? getLabel().getObject() : getId());
        label.append(".");

        if (labels.containsKey(key)) {
            label.append(labels.get(key).getObject());
        } else {
            label.append(key.toString());
        }

        return new Model<String>(label.toString());
    }

    private FormComponent findChildComponentById(String id) {
        ComponentHierarchyIterator iterator = this.visitChildren(FormComponent.class);
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof Component) {
                FormComponent component = (FormComponent) next;
                if (id.equalsIgnoreCase(component.getId())) {
                    return component;
                }
            }
        }
        return null;
    }

    /**
     * Component part keys.
     */
    public static enum DateTimeFieldEnum {
        /**
         * Date part key
         */
        DATE,
        /**
         * Hours part key
         */
        HOURS,
        /**
         * Minutes part key
         */
        MINUTES;
    }
}
