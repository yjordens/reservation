package org.onlinetaskforce.web.frontend.components.datepicker;

import org.apache.wicket.Component;
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * DatePicker with overriden getDatePattern() method because the original wicket-extensions implementations throws
 * a ClassCastException
 *
 * @author jordens
 * @since 20/04/13
 */
public class OtfDatePicker extends DatePicker {

    private Component component;

    @Override
    public void bind(Component component) {
        this.component = component;
        super.bind(component);
    }

    @Override
    protected String getDatePattern() {
        String format = null;
        if (component instanceof AbstractTextComponent.ITextFormatProvider) {
            format = ((AbstractTextComponent.ITextFormatProvider) component).getTextFormat();
            // it is possible that components implement ITextFormatProvider but
            // don't provide a format
        }

        if (format == null) {
            IConverter<?> converter = component.getConverter(DateTime.class);
            if (!(converter instanceof DateConverter)) {
                converter = component.getConverter(Date.class);
            }
            format = ((DateConverter) converter).getDatePattern(component.getLocale());
        }

        return format;
    }

    @Override
	protected CharSequence getIconUrl() {
		return RequestCycle.get().urlFor(new ResourceReferenceRequestHandler(
                new PackageResourceReference(getClass(), "../../images/cal.gif")));
	}

    @Override
    protected String getAdditionalJavaScript()
    {
        return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
    }
}
