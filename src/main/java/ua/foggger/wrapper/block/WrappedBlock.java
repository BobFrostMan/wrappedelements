package ua.foggger.wrapper.block;

import org.openqa.selenium.By;

public class WrappedBlock implements IWrappedBlock {

    private By rootLocator;

    @Override
    public String getName() {
        return WrappedBlock.class.getName();
    }

    void setRootElementLocator(By locator) {
        rootLocator = locator;
    }

}
