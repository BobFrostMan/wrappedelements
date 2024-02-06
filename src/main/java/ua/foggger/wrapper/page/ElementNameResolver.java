package ua.foggger.wrapper.page;

import java.lang.reflect.Method;

public class ElementNameResolver {

    public String resolve(Method method) {
        String page = prettify(method.getDeclaringClass().getSimpleName());
        String elementName = prettify(method.getName());
        return elementName + " on " + page;
    }

    public String prettify(String s) {
        String str = s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
        String[] words = str.split(" ");
        StringBuilder builder = new StringBuilder();
        String capitalized = words[0].substring(0, 1).toUpperCase() + words[0].substring(1);
        builder.append(capitalized);
        for (int i = 1; i < words.length; i++) {
            builder.append(" ");
            builder.append(words[i].toLowerCase());
        }
        return builder.toString();
    }
}
