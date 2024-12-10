package com.person.lsj.stock.service;

public interface RedisOpsService {
    public void setValue(String key, String value);
    public String getValue(String key);

    public <T> T getValueObj(String key);

    public void setMapValue(String mapName, String mapKey, String mapValue);

    public <T> T getMapValue(String mapName, String mapKey);
}
