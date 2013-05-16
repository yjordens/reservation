package org.onlinetaskforce.web.frontend.dropdown;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import java.util.Arrays;

/**
 * Util class to display enums. Kept the number of constructors limited, if you need another one, feel free :-)
 *
 * @param <E> declaration of a generic type E
 * @author jordens
 * @since 24/04/13
 */
public class EnumDropDownChoice<E extends Enum<E>> extends DropDownChoice<E> {

    /**
     * Creates a new EnumDropDownChoice
     *
     * @param id              the component ID
     * @param enumElementType the enum to list all values from
     */
    public EnumDropDownChoice(String id, Class<E> enumElementType) {
        super(id);
        setChoices(Arrays.asList(enumElementType.getEnumConstants()));
        setChoiceRenderer(new EnumChoiceRenderer<E>(this));
    }

    /**
     * Creates a new EnumDropDownChoice
     *
     * @param id              the component ID
     * @param enumElementType the enum to list all values from
     * @param choiceRenderer  other/custom choiceRenderer
     */
    public EnumDropDownChoice(String id, Class<E> enumElementType, IChoiceRenderer<E> choiceRenderer) {
        super(id);
        setChoices(Arrays.asList(enumElementType.getEnumConstants()));
        setChoiceRenderer(choiceRenderer);
    }

    /**
     * Creates a new EnumDropDownChoice
     *
     * @param id              the component ID
     * @param enumElementType the enum to list all values from
     * @param resourceSource  the component used to resolve the display value
     */
    public EnumDropDownChoice(String id, Class<E> enumElementType, Component resourceSource) {
        super(id);
        setChoices(Arrays.asList(enumElementType.getEnumConstants()));
        setChoiceRenderer(new EnumChoiceRenderer<E>(resourceSource));
    }
}
