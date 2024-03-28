package io.github.bobfrostman.wrapper.locator.converter;

import io.github.bobfrostman.wrapper.locator.converter.linktext.LinkTextToXpathConverter;
import io.github.bobfrostman.wrapper.locator.converter.name.NameToXpathConverter;
import io.github.bobfrostman.wrapper.locator.converter.text.TextToXpathConverter;
import org.openqa.selenium.By;
import io.github.bobfrostman.wrapper.locator.converter.classname.ClassNameToXPath;
import io.github.bobfrostman.wrapper.locator.converter.css.CssToXpathConverter;
import io.github.bobfrostman.wrapper.locator.converter.id.IdToXpathConverter;
import io.github.bobfrostman.wrapper.locator.converter.tagname.TagToXPath;

import java.util.HashMap;
import java.util.Map;

public class XPathConverter {

    private static final Map<String, LocatorConverter> converters = new HashMap<>();

    static {
        converters.put("By.id: ", new IdToXpathConverter());
        converters.put("By.tagName: ", new TagToXPath());
        converters.put("By.name: ", new NameToXpathConverter());
        converters.put("By.className: ", new ClassNameToXPath());
        converters.put("By.cssSelector: ", new CssToXpathConverter());
        converters.put("By.linkText: ", new LinkTextToXpathConverter());
        converters.put("By.xpath: ", locator -> locator);
        //TODO: text
        converters.put("text", new TextToXpathConverter());
    }

    public String convert(By locator) {
        for (Map.Entry<String, LocatorConverter> entry : converters.entrySet()) {
            String locatorString = locator.toString();
            if (locatorString.startsWith(entry.getKey())) {
                return entry.getValue().convertToXpath(locatorString.replaceAll(entry.getKey(), ""));
            }
        }
        return null;
    }
}
