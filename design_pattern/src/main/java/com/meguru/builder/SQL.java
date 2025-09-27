package com.meguru.builder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

// DSL
public class SQL {

    private SQL() {

    }

    public static SelectBuilder select(String... columns) {
        return new SelectBuilder(columns);
    }

    public static TableStage update() {
        return new UpdateBuilder();
    }

    interface TableStage {
        WhereStage table(String table);
    }

    interface WhereStage {
        SetStage where(String where);
    }

    interface SetStage {
        SetStage set(String col, String value);

        String buildSql();
    }

    public static class SelectBuilder {

        private String[] columns;

        private String table;

        private String where;

        public SelectBuilder(String[] columns) {
            this.columns = columns;
        }

        public SelectBuilder from(String table) {
            this.table = table;
            return this;
        }

        public SelectBuilder where(String where) {
            this.where = where;
            return this;
        }

        public String buildSql() {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ").append(String.join(",", columns))
                    .append(" FROM ").append(table);
            if (where != null) {
                sql.append(" WHERE ").append(where);
            }

            return sql.toString();
        }

    }

    public static class UpdateBuilder implements TableStage, WhereStage, SetStage {
        private String table;

        private String where;

        private Map<String, String> setMap = new LinkedHashMap<>();

        @Override
        public WhereStage table(String table) {
            this.table = table;
            return this;
        }

        @Override
        public SetStage where(String where) {
            this.where = where;
            return this;
        }

        @Override
        public SetStage set(String col, String value) {
            setMap.put(col, value);
            return this;
        }

        @Override
        public String buildSql() {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ").append(table).append(" SET ");
            String setString = setMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(","));
            sql.append(setString);
            if (where != null) {
                sql.append(" WHERE ").append(where);
            }
            return sql.toString();
        }
    }


}
