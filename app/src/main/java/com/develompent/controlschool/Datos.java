package com.develompent.controlschool;

public class Datos {
    private String nombre;
    private String apellidos;
    private String carrera;
    private String correo;
    private String cuatrimestre;
    private String matricula;
public Datos (String nombre, String apellidos, String carrera, String correo, String cuatrimestre, String matricula){
    this.nombre=nombre;
    this.apellidos=apellidos;
    this.carrera = carrera;
    this.correo= correo ;
    this.cuatrimestre = cuatrimestre;
    this.matricula = matricula;
}
public String getNombre() {return nombre;}
    public String getApellidos(){return apellidos;}
    public String getCarrera(){return carrera;}
    public String getCorreo() {return correo;}
    public String getCuatrimestre() {return cuatrimestre;}
    public String getMatricula() {return matricula;}
}

