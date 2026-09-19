package org.ong.dryforest.dto.synchronization;

import java.util.Map;
import java.util.List;

public class DynamicSyncPayload {
    private Map<String, List<Map<String, Object>>> tables;

    public DynamicSyncPayload() {
    }

    public Map<String, List<Map<String, Object>>> getTables() {
        return tables;
    }
    public void setTables(Map<String, List<Map<String, Object>>> tables) {
        this.tables = tables;
    }
}
