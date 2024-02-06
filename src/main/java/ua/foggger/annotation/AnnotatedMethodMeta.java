package ua.foggger.annotation;

import org.openqa.selenium.By;
import ua.foggger.wrapper.interactor.IElementInteractor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Represents all information that framework receives from method that produces wrapped element object.
 */
public class AnnotatedMethodMeta {

    private String value;
    private String name;
    private String waitUntil;
    private int timeout;
    private By resolvedLocator;
    private String resolvedName;
    private IElementInteractor resolvedInteractor;
    private Method method;
    private Object[] args;
    private Map<String, Object> additionalInfo;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWaitUntil() {
        return waitUntil;
    }

    public void setWaitUntil(String waitUntil) {
        this.waitUntil = waitUntil;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public By getResolvedLocator() {
        return resolvedLocator;
    }

    public void setResolvedLocator(By resolvedLocator) {
        this.resolvedLocator = resolvedLocator;
    }

    public String getResolvedName() {
        return resolvedName;
    }

    public void setResolvedName(String resolvedName) {
        this.resolvedName = resolvedName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public IElementInteractor getResolvedInteractor() {
        return resolvedInteractor;
    }

    public void setResolvedInteractor(IElementInteractor resolvedInteractor) {
        this.resolvedInteractor = resolvedInteractor;
    }

    public Map<String, Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Map<String, Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "AnnotatedMethodMeta{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                ", waitUntil='" + waitUntil + '\'' +
                ", timeout=" + timeout +
                ", resolvedLocator=" + resolvedLocator +
                ", resolvedName='" + resolvedName + '\'' +
                ", resolvedInteractor=" + resolvedInteractor +
                ", method=" + method +
                ", args=" + Arrays.toString(args) +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}
