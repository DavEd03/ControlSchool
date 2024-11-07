package com.develompent.controlschool;

public class EventosModel {
    private String desc;
    private String fecha;

    public EventosModel(){}

    public EventosModel(String desc, String fecha){
        this.desc=desc;
        this.fecha=fecha;
    }

    public String getDesc() {
        return desc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
