package ua.foggger.wrapper.block;

import org.openqa.selenium.By;

import java.util.Objects;

public class WrappedBlockMeta {

    private String blockIdentifier;
    private String name;
    private By locator;

    public WrappedBlockMeta() {

    }

    public String getBlockIdentifier() {
        return blockIdentifier;
    }

    public void setBlockIdentifier(String blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
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
        return Objects.equals(blockIdentifier, that.blockIdentifier) && Objects.equals(name, that.name) && Objects.equals(locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockIdentifier, name, locator);
    }

    @Override
    public String toString() {
        return "WrappedBlockMeta{" +
                "blockIdentifier='" + blockIdentifier + '\'' +
                ", name='" + name + '\'' +
                ", locator='" + locator + '\'' +
                '}';
    }
}
