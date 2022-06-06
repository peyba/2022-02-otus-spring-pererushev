package ru.otus.homework05.shell;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("singleton")
@Data
public class ShellContext {

    private final Map<String, Object> objects = new HashMap<>();
    private WorkingContext context = WorkingContext.ROOT;

    public void addObject(String key, Object o) {
        objects.put(key, o);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        if (clazz.isInstance(objects.get(key))) {
            return clazz.cast(objects.get(key));
        }
        throw new ShellContextTypeCastException(
                "Wrong context object type. " +
                        "At key: " + key +
                        " stored object with type: " + objects.get(key).getClass()
        );
    }

    public void drop() {
        context = WorkingContext.ROOT;
        objects.clear();
    }

    public enum WorkingContext {
        ROOT,
        EDIT_BOOK, CREATE_BOOK
    }
}
