package it.ictgroup.service.matrix.entity;

import it.ictgroup.utils.exceptions.EmmaServiceException;
import java.io.Serializable;

@SuppressWarnings("unused")
public interface MatrixParInterface extends Serializable{

    String getValue(String key) throws EmmaServiceException;

    String getValueOrDefault(String key, String defaultValue) throws EmmaServiceException;

    String getValue(String key, String... domains) throws EmmaServiceException;

    String getValueOrDefault(String key, String defaultValue, String... domains) throws EmmaServiceException;

    String getValueWithAlternative(String[] keys, String... domains) throws EmmaServiceException;

    Boolean getBooleanValue(String key) throws EmmaServiceException;

    Boolean getBooleanValueOrDefault(String key, Boolean defaultValue) throws EmmaServiceException;

    Boolean getBooleanValue(String key, String... domains) throws EmmaServiceException;

    Boolean getBooleanValueOrDefault(String key, Boolean defaultValue, String... domains) throws EmmaServiceException;

    Boolean getBooleanValueWithAlternative(String[] keys, String... domains) throws EmmaServiceException;

    Integer getIntegerValue(String key) throws EmmaServiceException;

    Integer getIntegerValueOrDefault(String key, Integer defaultValue) throws EmmaServiceException;

    Integer getIntegerValue(String key, String... domains) throws EmmaServiceException;

    Integer getIntegerValueOrDefault(String key, Integer defaultValue, String... domains) throws EmmaServiceException;

    Integer getIntegerValueWithAlternative(String[] keys, String... domains) throws EmmaServiceException;

    void refreshValue() throws EmmaServiceException;
}
