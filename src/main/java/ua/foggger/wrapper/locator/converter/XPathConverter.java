package ua.foggger.wrapper.locator.converter;

import org.openqa.selenium.By;
import ua.foggger.wrapper.locator.converter.classname.ClassNameToXPath;
import ua.foggger.wrapper.locator.converter.css.CssToXpathConverter;
import ua.foggger.wrapper.locator.converter.id.IdToXpathConverter;
import ua.foggger.wrapper.locator.converter.linktext.LinkTextToXpathConverter;
import ua.foggger.wrapper.locator.converter.name.NameToXpathConverter;
import ua.foggger.wrapper.locator.converter.tagname.TagToXPath;
import ua.foggger.wrapper.locator.converter.text.TextToXpathConverter;

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
