package org.ong.dryforest.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sync")
public class SyncProperties {
    private List<String> pullableTablesPatrol;
    private List<String> pullableTablesReforestation;

    public List<String> getPullableTablesPatrol() {
        return pullableTablesPatrol;
    }
    public void setPullableTablesPatrol(List<String> pullableTablesPatrol) {
        this.pullableTablesPatrol = pullableTablesPatrol;
    }

    public List<String> getPullableTablesReforestation() {
        return pullableTablesReforestation;
    }
    public void setPullableTablesReforestation(List<String> pullableTablesReforestation) {
        this.pullableTablesReforestation = pullableTablesReforestation;
    }
}
