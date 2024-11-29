
package dtos;

public class BedelDTO {
    
    private int id_bedel;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private String turno;

    public BedelDTO(String nombreUsuario, String nombre, String apellido, String contrasenia, String turno) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.turno = turno;
    }
    
    public BedelDTO(){}

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public int getId_bedel() {
        return id_bedel;
    }

    public void setId_bedel(int id_bedel) {
        this.id_bedel = id_bedel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    
    
}
