package it.ictgroup.config.service;

import java.util.List;

public abstract class AbstractIndexConfig extends AbstractModuleConfig implements IndexConfig {
    public AbstractIndexConfig() {
    }

    public abstract String getIndex();

    public abstract boolean isMultiFieldIndex(String var1);

    public abstract String getIndexValue(String var1);

    public abstract String getIndexKey(String var1);

    public abstract List<String> getAllIndexKeys();
}
