package org.ong.dryforest.dto.zone;

public class ZoneNeedDTO {

    private int id;
    private String uuid;
    private int id_zone;

    public ZoneNeedDTO() {
    }

    public ZoneNeedDTO(int id, String uuid, int id_zone) {
        this.id = id;
        this.uuid = uuid;
        this.id_zone = id_zone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId_zone() {
        return id_zone;
    }

    public void setId_zone(int id_zone) {
        this.id_zone = id_zone;
    }
}
