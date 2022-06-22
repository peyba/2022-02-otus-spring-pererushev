package ru.otus.homework06.shell;

import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimplePromptProvider implements PromptProvider {

    private final ShellContext shellContext;

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(shellContext.getContext().toString().toLowerCase() + ":>");
    }
}
