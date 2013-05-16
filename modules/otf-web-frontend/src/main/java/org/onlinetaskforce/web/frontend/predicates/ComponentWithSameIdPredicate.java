package org.onlinetaskforce.web.frontend.predicates;

import org.apache.commons.collections15.Predicate;
import org.apache.wicket.Component;

/**
 * This predicate will evaluate to true when it finds a component with the same id
 * @author jordens
 * @since 8/03/13
 */
public class ComponentWithSameIdPredicate implements Predicate<Component> {
    private String id;

    public ComponentWithSameIdPredicate(String id) {
        this.id = id;
    }

    @Override
    public boolean evaluate(Component component) {
        return component.getId().equals(id);
    }
}
