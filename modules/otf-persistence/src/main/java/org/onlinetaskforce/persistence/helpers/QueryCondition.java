package org.onlinetaskforce.persistence.helpers;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is initially based on a corresponding class of the STL project.
 *
 * @author vanlooni
 * @since 24/11/11
 */
public class QueryCondition {

    private String conditionImpl;
    private List<ConditionModification> modifications = new ArrayList<ConditionModification>();

    /**
     * Empty condition. The first modification has 'no effect' on the empty condition.
     * <p>
     * Empty conditions are useful when you need to add modifications 'at random'. For example:
     * <code>
     * QueryCondition c = new QueryCondition();
     * if (condition1) {
     * c.logicalAnd(...);
     * }
     * if (condition2) {
     * c.logicalAnd(...);
     * }
     * query.setCondition(c);
     * </code>
     * </p>
     */
    public QueryCondition() {
    }

    /**
     * Instatiates the QueryCondition
     *
     * @param condition The condition as string
     */
    public QueryCondition(String condition) {
        if (StringUtils.isBlank(condition)) {
            throw new IllegalArgumentException("A condition implementation cannot be empty.");
        }
        this.conditionImpl = condition;
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(conditionImpl);
    }

    /**
     * Add brackets to the current QueryCondition
     * @return The current QueryCondition
     */
    public QueryCondition addBrackets() {
        if (!isEmpty()) {
            modifications.add(new Brackets());
        }
        return this;
    }

    /**
     * Add a new condition with the logical 'and' operator
     * @param operand The condition
     * @return Current condition
     */
    public QueryCondition logicalAnd(QueryCondition operand) {
        if (isEmpty()) {
            conditionImpl = operand.resolve().toString();
        } else {
            modifications.add(new LogicalRelation(LogicalRelation.AND, operand));
        }
        return this;
    }

    /**
     * Add a new condition with the logical 'and' operator
     * @param condition The condition as string
     * @return Current condition
     */
    public QueryCondition logicalAnd(String condition) {
        return logicalAnd(new QueryCondition(condition));
    }

    /**
     * Add a new condition with the logical 'or' operator
     * @param operand The condition
     * @return Current condition
     */
    public QueryCondition logicalOr(QueryCondition operand) {
        if (isEmpty()) {
            conditionImpl = operand.resolve().toString();
        } else {
            modifications.add(new LogicalRelation(LogicalRelation.OR, operand));
        }
        return this;
    }

    /**
     * Add a new condition with the logical 'or' operator
     * @param condition The condition as string
     * @return Current condition
     */
    public QueryCondition logicalOr(String condition) {
        return logicalOr(new QueryCondition(condition));
    }

    /**
     * Resolve the complete condition and return the query as string
     * @return The query as a StringBuilder object
     */
    public StringBuilder resolve() {
        if (isEmpty()) {
            return new StringBuilder();
        } else {
            StringBuilder conditionImplBuf = new StringBuilder(conditionImpl);
            for (ConditionModification modification : modifications) {
                modification.modify(conditionImplBuf);
            }
            return conditionImplBuf;
        }
    }

}
