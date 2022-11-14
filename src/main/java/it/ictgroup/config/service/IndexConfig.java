package it.ictgroup.config.service;

import java.util.List;

public interface IndexConfig extends ModuleConfig {
    String getIndex();

    boolean isMultiFieldIndex(String var1);

    String getIndexValue(String var1);

    String getIndexKey(String var1);

    List<String> getAllIndexKeys();
}
