package org.onlinetaskforce.persistence.helpers;

/**
 * LogicalRelation, a part of the RelationQuery class
 *
 * @author vanlooni
 * @since 15/12/11
*/
class LogicalRelation implements ConditionModification {

    public static final String AND = "A";
    public static final String OR = "O";

    private QueryCondition operand;
    private String operatorType;

    /**
     * Instantiates the logical relation
     * @param operatorType Type of operator
     * @param operand The condition
     */
    public LogicalRelation(String operatorType, QueryCondition operand) {
        this.operatorType = operatorType;
        this.operand = operand;
    }

    /**
     * Moify the current condition
     * @param condition The new condition
     * @return The condition as string
     */
    public StringBuilder modify(StringBuilder condition) {
        if (operatorType.equals(AND)) {
            condition.append(" and ");
        } else {
            condition.append(" or ");
        }
        condition.append(operand.resolve());
        return condition;
    }
}