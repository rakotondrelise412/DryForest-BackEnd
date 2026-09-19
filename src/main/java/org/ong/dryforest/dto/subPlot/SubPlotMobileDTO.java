package org.ong.dryforest.dto.subPlot;

public class SubPlotMobileDTO {
    private int id_sub_plot;
    private String name;
    private int plantation_block_id;

    public int getId_sub_plot() {
        return id_sub_plot;
    }
    public void setId_sub_plot(int id_sub_plot) {
        this.id_sub_plot = id_sub_plot;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPlantation_block_id() {
        return plantation_block_id;
    }
    public void setPlantation_block_id(int plantation_block_id) {
        this.plantation_block_id = plantation_block_id;
    }
}
