package org.onlinetaskforce.persistence.helpers;

/**
 * Brackets, a part of the RelationQuery class
 *
 * @author vanlooni
 * @since 15/12/11
 */

class Brackets implements ConditionModification {

    public StringBuilder modify(StringBuilder condition) {
        condition.insert(0, "(");
        condition.append(")");
        return condition;
    }
}