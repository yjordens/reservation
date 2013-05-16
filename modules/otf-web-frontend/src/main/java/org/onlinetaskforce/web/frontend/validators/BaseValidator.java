package org.onlinetaskforce.web.frontend.validators;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.onlinetaskforce.web.frontend.predicates.ComponentWithSameIdPredicate;

import java.util.Arrays;

/**
 * @author jordens
 * @since 22/05/12
 */
public abstract class BaseValidator extends AbstractFormValidator {
    /**
     * web components (e.g. like Labels)
     */
    private WebComponent[] webComponents = null;
    /**
     * form components to be checked.
     */
    private FormComponent<?>[] formComponents = null;

    /**
     * Default constructor
     */
    protected BaseValidator() {
        super();
    }

    /**
     * Sets the webcomponents
     *
     * @param webComponents WebComponent[]
     */
    public void setWebComponents(WebComponent[] webComponents) {
        this.webComponents = (WebComponent[]) ArrayUtils.clone(webComponents);
    }

    /**
     * Set the form components this validator will use
     *
     * @param formComponents FormComponent[]
     */
    public void setFormComponents(FormComponent<?>[] formComponents) {
        this.formComponents = (FormComponent<?>[]) ArrayUtils.clone(formComponents);
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.formComponents;
    }

    /**
     * Searches a form component based on its given id
     *
     * @param id identifier of the form component
     * @return FormComponent
     */
    public FormComponent findFormComponent(final String id) {
        return CollectionUtils.find(Arrays.asList(formComponents), new ComponentWithSameIdPredicate(id));
    }

    /**
     * Searches a web component based on its given id
     *
     * @param id identifier of the component
     * @return WebComponent
     */
    public WebComponent findWebComponent(String id) {
        return CollectionUtils.find(Arrays.asList(webComponents), new ComponentWithSameIdPredicate(id));
    }
}