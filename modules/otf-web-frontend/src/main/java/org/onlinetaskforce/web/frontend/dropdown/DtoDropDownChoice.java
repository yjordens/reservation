package org.onlinetaskforce.web.frontend.dropdown;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.onlinetaskforce.web.frontend.dto.DropDownDto;

import java.util.List;

/**
 * @author jordens
 * @since 8/03/13
 * @param <T> Extends DropDownDto
 */
public class DtoDropDownChoice<T extends DropDownDto> extends DropDownChoice<T> {

    /**
     * Constructs the dropdown
     * @param id Component's id
     * @param choices List with all choices
     */
    public DtoDropDownChoice(String id, IModel<? extends List<? extends T>> choices) {
        super(id, choices);
    }

    /**
     * Constructs the dop-down
     *
     * @param id Components's id
     * @param dropDownDefault the default option that is selected
     * @param choices List with all choices
     */
    public DtoDropDownChoice(String id, Model<T> dropDownDefault, IModel<? extends List<? extends T>> choices) {
        super(id, dropDownDefault, choices);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ChoiceRenderer<DropDownDto> choiceRenderer = new ChoiceRenderer<DropDownDto>("display", "value");
        setChoiceRenderer(choiceRenderer);
    }

    @Override
    public String getModelValue() {
        final T object = getModelObject();
        if (object != null) {
            int index = -1;
            for(int i = 0; i < getChoices().size(); i++) {
                if(getChoices().get(i).getValue().equals(object.getValue())) {
                    index = i;
                }
            }
            if (index < 0) {
                return "";
            }
            return getChoiceRenderer().getIdValue(object, index);
        } else {
            return "";
        }
    }
}
