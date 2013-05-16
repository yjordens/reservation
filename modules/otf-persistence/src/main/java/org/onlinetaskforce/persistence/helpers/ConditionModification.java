package org.onlinetaskforce.persistence.helpers;

/**
 * ConditionModification, a part of the RelationalQuery class
 *
 * @author vanlooni
 * @since 15/12/11
 */
interface ConditionModification {
    /**
     * Modify a condition to another given condition
     * @param conditionImpl The condition to modify
     * @return The condition as StringBuilder
     */
    StringBuilder modify(StringBuilder conditionImpl);
}