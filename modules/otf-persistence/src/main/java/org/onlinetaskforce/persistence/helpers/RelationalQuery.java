package org.onlinetaskforce.persistence.helpers;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class is initially based on a corresponding class of the STL project.
 * Only generics is added to the STL implementation
 *
 * @author vanlooni
 * @since 24/11/11
 */
public class RelationalQuery {

    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final char DOT = '.';

    private List<String> selectFields = new ArrayList<String>();
    private String from = null;
    /**
     * The content of this instance variabele specifies the[OF cols] part of the following structure:
     * SELECT cols FROM tables [WHERE...] FOR UPDATE [OF cols] [NOWAIT];
     */
    private String forUpdateColsEntity = null;
    private List<String> join = new ArrayList<String>();
    private QueryCondition condition = null;
    private List<String> orderByFields = new ArrayList<String>();
    private List<String> groupByFields = new ArrayList<String>();
    private boolean forUpdate = false;

    /**
     * Add a field to the query selection
     * @param field The field to add
     */
    public void addSelectField(String field) {
        if (StringUtils.isBlank(field)) {
            throw new IllegalArgumentException("The added field should not be empty.");
        }
        selectFields.add(field);
    }

    /**
     * Set the table to select from
     * @param entity The tablename
     */
    public void setFrom(String entity) {
        if (StringUtils.isBlank(entity)) {
            throw new IllegalArgumentException("The from should not be empty.");
        }
        from = entity;
    }

    /**
     * Add a join to the query (don't forget to add the 'join' keyword)
     *      eg. query.addJoinedEntity('join dummytable on ...');
     * @param joinedEntity The table to join with
     */
    public void addJoinedEntity(String joinedEntity) {
        if (StringUtils.isBlank(joinedEntity)) {
            throw new IllegalArgumentException("The joinedEntity should not be empty.");
        }
        join.add(joinedEntity);
    }

    /**
     * Set the queryCondition
     * @param condition A QueryCondition isntance
     */
    public void setCondition(QueryCondition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("The condition should not be null.");
        }
        this.condition = condition;
    }

    /**
     * Add an orderBy field
     * @param field The order by field(s)
     */
    public void addOrderByField(String field) {
        if (StringUtils.isBlank(field)) {
            throw new IllegalArgumentException("The added field should not be empty.");
        }
        orderByFields.add(field);
    }

    /**
     * Add an gropuBy field
     * @param field The group by field(s)
     */
    public void addGroupByField(String field) {
        if (StringUtils.isBlank(field)) {
            throw new IllegalArgumentException("The added field should not be empty.");
        }
        groupByFields.add(field);
    }

    public boolean getForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(boolean forUpdate) {
        this.forUpdate = forUpdate;
    }


    public String getForUpdateColsEntity() {
        return forUpdateColsEntity;
    }

    public void setForUpdateColsEntity(String forUpdateColsEntity) {
        this.forUpdateColsEntity = forUpdateColsEntity;
    }

    /**
     * Resolve the complete query and return the sql statement as a string
     * @return The query as string
     */
    public String resolve() {
        boolean orderResults = orderByFields.size() > 0;
        boolean groupResults = groupByFields.size() > 0;

        StringBuilder rq = new StringBuilder();
        if (selectFields.size() > 0) {
            appendWithSpaces(rq, "select");
            appendSeparated(rq, selectFields, COMMA);
        }
        appendWithSpaces(rq, "from");
        appendWithSpaces(rq, from);
        if (join.size() > 0) {
            appendSeparated(rq, join, SPACE);
        }
        if ((condition != null) && !condition.isEmpty()) {
            appendWithSpaces(rq, "where");
            appendWithSpaces(rq, condition.resolve());
        }
        if (groupResults) {
            appendWithSpaces(rq, "group by");
            appendSeparated(rq, groupByFields, COMMA);
        }
        if (orderResults) {
            appendWithSpaces(rq, "order by");
            appendSeparated(rq, orderByFields, COMMA);
        }
        if (forUpdate) {
            appendWithSpaces(rq, "for update");
            String forUpdateCols = getForUpdateColsEntity();
            if (forUpdateCols != null && forUpdateCols.trim().length() > 0) {
                rq.append("of");
                appendWithSpaces(rq, forUpdateCols);
            }
        }
        return rq.toString();
    }

    protected String translateSelectedFieldToAlias(String selectedField, Map aliases) {
        return (String) aliases.get(selectedField.trim().toUpperCase(Locale.getDefault()));
    }

    protected String addColumnAlias(String column, Map separatedAliases) {
        column = column.trim();
        // heeft deze kolom reeds een alias?
        int index = column.indexOf(" as ");
        int aliasIndex = index + 4; // dus vanaf na de ' as '
        if (index == -1) {
            index = column.indexOf(SPACE);
            aliasIndex = index + 1; // dus vanaf na de spatie
        }
        if (index == -1) { // heel eenvoudige check; aanpassen indien nodig
            // geen alias aanwezig; gebruik eventuele table alias als prefix in de kolom alias
            String alias = SPACE + column.replace(DOT, '_');
            separatedAliases.put(column.trim().toUpperCase(Locale.getDefault()), alias);
            return column + alias;
        } else {
            separatedAliases.put(column.trim().toUpperCase(Locale.getDefault()), column.substring(aliasIndex));
            return column;
        }
    }

    protected StringBuilder appendSeparated(StringBuilder ql, List<String> fields, String separator) {
        if (fields.size() > 0) {
            ql.append(fields.get(0));
            for (int i = 1; i < fields.size(); i++) {
                ql.append(separator);
                String field = fields.get(i);
                ql.append(field);
            }
        }
        return ql;
    }

    protected StringBuilder appendSeparated(StringBuilder ql, String field, String separator) {
        ql.append(separator);
        ql.append(field);
        return ql;
    }

    protected StringBuilder appendWithSpaces(StringBuilder hql, String str) {
        hql.append(SPACE);
        hql.append(str);
        hql.append(SPACE);
        return hql;
    }

    protected StringBuilder appendWithSpaces(StringBuilder hql, StringBuilder str) {
        hql.append(SPACE);
        hql.append(str);
        hql.append(SPACE);
        return hql;
    }

    public List<String> getSelectFields() {
        return selectFields;
    }

    public String getFrom() {
        return from;
    }

    public List<String> getJoin() {
        return join;
    }

    /**
     * Get the main condition
     * @return The condition
     */
    public QueryCondition getCondition() {
        if (condition == null) {
            condition = new QueryCondition();
        }
        return condition;
    }

    public List<String> getOrderByFields() {
        return orderByFields;
    }

}
