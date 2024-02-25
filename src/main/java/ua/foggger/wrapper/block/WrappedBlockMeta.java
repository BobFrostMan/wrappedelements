package ua.foggger.wrapper.block;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Supplier;

public class WrappedBlockMeta {

    private String name;
    private By locator;

    public WrappedBlockMeta() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public By getLocator() {
        return locator;
    }

    public void setLocator(By locator) {
        this.locator = locator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrappedBlockMeta that = (WrappedBlockMeta) o;
        return Objects.equals(name, that.name) && Objects.equals(locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, locator);
    }

    @Override
    public String toString() {
        return "WrappedBlockMeta{" +
                " name='" + name + '\'' +
                ", locator='" + locator + '\'' +
                '}';
    }
}
