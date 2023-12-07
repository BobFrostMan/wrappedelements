package ua.foggger.elements;

public class ClickableElement implements IClickableElement {

    private String name;
    private String locator;

    public ClickableElement() {

    }

    public ClickableElement(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getLocator() {
        return locator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }
}
