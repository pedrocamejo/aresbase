package cpc.ares.modelo;

import java.io.Serializable;

public class Accion  implements Serializable {
   
	 
	private static final long serialVersionUID = 28533883372803789L;	
	
	public static final int CATALOGO 		= 0;
	public static final int GRAFICO			= 1;
	public static final int PDF 			= 2;
	public static final int XLS		 		= 3;
	public static final int WEB      		= 4;
	
	public static final int AGREGAR 		= 0;
	public static final int EDITAR 			= 1;
	public static final int ELIMINAR 		= 2;
	public static final int CONSULTAR 		= 3;
	public static final int ASOCIAR 		= 4;
	public static final int PROCESAR 		= 5;	
	public static final int IMPRIMIR_ITEM	= 6;
	public static final int IMPRIMIR_TODO 	= 7;
	public static final int CORREGIR	 	= 8;
	public static final int ANULAR		 	= 9;
	
	public static final String[] TEXTO = {"AGREGAR", "EDITAR", "ELIMINAR","CONSULTAR","ASOCIAR","PROCESAR","IMPRIMIR ITEM","IMPRIMIR TODO", "CORREGIR", "ANULAR"};
	public static final String[] TEXTOREPORTE = {"CATALOGO", "GRAFICO", "PDF","XLS","WEB"};
	
	private int 	idaccion;   
	private String 	nombre;   
	private String 	descripcion;   
	private String 	imagen;
	private boolean estado;

	public Accion(int idaccion) {
		super();
		this.idaccion = idaccion;
		//buscar_data
	}   
	
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdaccion() {
		return idaccion;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public boolean esActivo() {
		return estado==true;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	   
   
}