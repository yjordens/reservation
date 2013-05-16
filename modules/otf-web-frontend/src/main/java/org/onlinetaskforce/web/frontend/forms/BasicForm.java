package org.onlinetaskforce.web.frontend.forms;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;

/**
 * @param <T> type of the model
 * @author jordens
 * @since 10/03/13
 */
public class BasicForm<T> extends Form<T> {
    private static final CssResourceReference OTF_PAGE_FORMS_CSS = new CssResourceReference(HomeLoginPage.class, "../ui-themes/aristo/jquery.ui.all.css");
    private static final CssResourceReference OTF_PAGE_FORMS_SPINNER_CSS = new CssResourceReference(HomeLoginPage.class, "../components/spinner/ui.spinner.css");

    private static final JavaScriptResourceReference OTF_PAGE_JS_WEBFORMS2 = new JQueryPluginResourceReference(HomeLoginPage.class, "../webforms2/webforms2-p.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_JQUERY_UI = new JQueryPluginResourceReference(HomeLoginPage.class, "../jquery-ui-1.8.5.min.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_JQUERY_SPINNER = new JQueryPluginResourceReference(HomeLoginPage.class, "../components/spinner/ui.spinner.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_JQUERY_PLACEHOLD = new JQueryPluginResourceReference(HomeLoginPage.class, "../components/placeholder/jquery.placehold-0.2.min.js");


    /**
     * Instantiates the BasicForm
     * @param id Component's id
     */
    public BasicForm(String id) {
        super(id);
    }

    /**
     * Instantiates the BasicForm
     * @param id Component's id
     * @param tiModel Model
     */
    public BasicForm(String id, IModel<T> tiModel) {
        super(id, tiModel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(OTF_PAGE_FORMS_CSS));
        response.render(CssHeaderItem.forReference(OTF_PAGE_FORMS_SPINNER_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_WEBFORMS2));
        response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_JQUERY_UI));
        response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_JQUERY_SPINNER));
        response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_JQUERY_PLACEHOLD));
    }

    /**
     * Convinience method that adds feedback labels for each field of the components
     *
     * @param fc The component
     */
    public void addFormField(FormComponent fc) {
        // Add the component to the components
        this.add(fc);

        // Add feedback label for this editForm
        if(!(fc instanceof RadioGroup || fc instanceof DropDownChoice)) {
            this.add(new ComponentFeedbackPanel(fc.getId() + "-feedback", fc).add(new AttributeModifier("class", Model.of("componentFeedbackPanel"))));
        }
    }

}
