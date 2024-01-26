package ua.foggger.wrapper.element.clickable;

import org.openqa.selenium.By;
import ua.foggger.annotation.WebElement;
import ua.foggger.driver.DriverProvider;
import ua.foggger.helper.IHaveReflectionAccess;
import ua.foggger.wrapper.block.WrappedBlockMeta;
import ua.foggger.wrapper.element.IElementAnnotationProcessor;
import ua.foggger.wrapper.page.LocatorResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListElementProcessorWrapper implements IHaveReflectionAccess {

    private WrappedBlockMeta wrappedBlockMeta;

    public ListElementProcessorWrapper(){
        wrappedBlockMeta = null;
    }

    public ListElementProcessorWrapper(WrappedBlockMeta wrappedBlockMeta) {
        this.wrappedBlockMeta = wrappedBlockMeta;
    }


    public Object wrap(List<ClickableElement> list, IElementAnnotationProcessor decorator, Method method, Object[] args) {
        WebElement annotation = method.getAnnotation(WebElement.class);
        if (annotation == null) {
            return null;
        }
        //TODO: Add parent locator resolution
        By listLocator = new LocatorResolver().resolveLocator(annotation.value(), method, args);
        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            List<org.openqa.selenium.WebElement> listElements = DriverProvider.get().findElements(listLocator);
            if (listElements.size() > 0) {
                for (int i = 0; i < listElements.size(); i++) {
                    org.openqa.selenium.WebElement webElement = listElements.get(i);
                    ClickableElement clickableElement = new ClickableElement();
                    setFieldValue(webElement, "innerElement", webElement);
                    clickableElement.setDetectionFunction((name) -> clickableElement.getWrappedElement());
                    decorator.setValuesFromAnnotation(wrappedBlockMeta, clickableElement, method, args);
                    clickableElement.setName(clickableElement.getName() + " (list item number [" + i + "])");
                    list.add(clickableElement);
                }
            }
            return list;
        } else {
            List<ClickableElement> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ClickableElement clickableElement = list.get(i);
                decorator.setValuesFromAnnotation(wrappedBlockMeta, clickableElement, method, args);
                clickableElement.setName(clickableElement.getName() + " (list item number [" + i + "])");
                result.add(clickableElement);
            }
            return result;
        }
    }
}
