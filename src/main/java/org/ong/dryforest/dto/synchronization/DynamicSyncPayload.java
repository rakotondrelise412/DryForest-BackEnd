package org.ong.dryforest.dto.synchronization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DynamicSyncPayload {
    private Map<String, List<Map<String, Object>>> tables;
}
