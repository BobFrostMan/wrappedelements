package ua.foggger.wrapper.block;

import java.util.HashMap;
import java.util.Map;

//TODO: refactor that, it's a quick experimental implementation
public class WrappedComponentsRepo {

    private static ThreadLocal<Map<String, WrappedBlockMeta>> blocks = new ThreadLocal<>();
    private static ThreadLocal<Map<String, WrappedBlockMeta>> wrappedBlocks = new ThreadLocal<>();

    public static synchronized WrappedBlockMeta getWrappedComponentMeta(String identifier){
        Map<String, WrappedBlockMeta> blocksMap = blocks.get();
        if (blocksMap == null) {
            return null;
        }
        return blocksMap.get(identifier);
    }

    public static synchronized void setWrappedComponentMeta(WrappedBlockMeta wrappedComponentMeta){
        Map<String, WrappedBlockMeta> blocksMap = blocks.get();
        if (blocksMap == null) {
            blocksMap = new HashMap<>();
        }
        blocksMap.put(wrappedComponentMeta.getBlockIdentifier(), wrappedComponentMeta);
        blocks.set(blocksMap);
    }


    public static synchronized WrappedBlockMeta getWrappedBlock(String identifier){
        Map<String, WrappedBlockMeta> blocksMap = wrappedBlocks.get();
        if (blocksMap == null) {
            return null;
        }
        return blocksMap.get(identifier);
    }

    public static synchronized void setWrappedBlock(WrappedBlockMeta wrappedComponentMeta){
        Map<String, WrappedBlockMeta> blocksMap = wrappedBlocks.get();
        if (blocksMap == null) {
            blocksMap = new HashMap<>();
        }
        blocksMap.put(wrappedComponentMeta.getBlockIdentifier(), wrappedComponentMeta);
        wrappedBlocks.set(blocksMap);
    }

    private WrappedComponentsRepo(){

    }
}
