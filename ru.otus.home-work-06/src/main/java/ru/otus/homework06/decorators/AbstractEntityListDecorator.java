package ru.otus.homework06.decorators;

import java.util.ArrayList;
import java.util.Map;

public abstract class AbstractEntityListDecorator<T> implements EntityListDecorator<T> {

    private static final String CROSS = "+";
    private static final String HORIZONTAL_BORDER = "-";
    private static final String VERTICAL_BORDER = "|";
    private static final String EMPTY_STARING = "NULL";


    @Override
    public String decorate(Iterable<T> entities) {
        var columns = columns();
        var stringBuilder = new StringBuilder();

        if (hasTitle()) {
            stringBuilder
                    .append(getTitleLine(columns))
                    .append(System.lineSeparator())
                    .append(getTitleText(columns))
                    .append(System.lineSeparator());
        }

        stringBuilder
                .append(getBorderLine(columns))
                .append(System.lineSeparator())
                .append(getHeadersLine(columns))
                .append(System.lineSeparator())
                .append(getBorderLine(columns))
                .append(System.lineSeparator());

        for (var entity : entities) {
            stringBuilder
                    .append(getTable(entity, columns))
                    .append(System.lineSeparator());
        }

        stringBuilder
                .append(getBorderLine(columns))
                .append(System.lineSeparator());

        return stringBuilder.toString();
    }

    protected abstract Map<String, Integer> columns();

    protected abstract Map<String, Object> mapEntity(T entity);

    protected String getTitle() {
        return "";
    }

    protected boolean hasTitle() {
        return false;
    }

    private String getTable(T entity, Map<String, Integer> columns) {
        var values = new ArrayList<>();
        var entityMap = mapEntity(entity);

        for (var column : columns.keySet()) {
            if (entityMap.get(column) != null) {
                values.add(entityMap.get(column));
            } else {
                values.add(EMPTY_STARING);
            }
        }

        return String.format(getFormatStringForLine(columns), values.toArray());
    }

    private String getTitleText(Map<String, Integer> columns) {
        var titleText = getTitle();
        int tableLength = 1;
        for (var key : columns.keySet()) {
            tableLength += columns.get(key) + 2;
        }

        return String.format(getFormatStringForLine(Map.of(titleText, tableLength)), titleText);
    }

    private String getTitleLine(Map<String, Integer> columns) {
        var sb = new StringBuilder();
        sb.append(CROSS);


        for (var key : columns.keySet()) {
            sb.append(HORIZONTAL_BORDER.repeat(columns.get(key) + 3));
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(CROSS);

        return sb.toString();
    }

    private String getBorderLine(Map<String, Integer> columns) {
        var sb = new StringBuilder();

        sb.append(CROSS);

        for (var key : columns.keySet()) {
            sb
                    .append(HORIZONTAL_BORDER.repeat(columns.get(key) + 2))
                    .append(CROSS);
        }

        return sb.toString();
    }

    private String getHeadersLine(Map<String, Integer> columns) {
        return String.format(getFormatStringForLine(columns), columns.keySet().toArray());
    }

    private String getFormatStringForLine(Map<String, Integer> columns) {
        var formatString = new StringBuilder();

        formatString.append(VERTICAL_BORDER);

        for (var key : columns.keySet()) {
            formatString
                    .append(" %-")
                    .append(columns.get(key).toString())
                    .append(".")
                    .append(columns.get(key).toString())
                    .append("s ")
                    .append(VERTICAL_BORDER);
        }

        return formatString.toString();
    }
}
