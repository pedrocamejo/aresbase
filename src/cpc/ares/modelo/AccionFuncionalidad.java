package cpc.ares.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cva.pc.demeter.excepciones.ExcAccesoInvalido;



public class AccionFuncionalidad  implements Serializable {
	
	private static final long serialVersionUID = 4229278853811344735L;
	private char[] mascaraAccion;
	
	public AccionFuncionalidad(String mascaraAccion) {
		super();
		this.mascaraAccion = mascaraAccion.toCharArray();
	}

	public List<Accion> extraerAcciones(int tipo){
		List<Accion> acciones = new ArrayList<Accion>();
		Accion accion;
		int i=0;
		try{
			for (char valor : mascaraAccion) {
				accion = new Accion(i);
				if (tipo == 0){
					accion.setDescripcion(Accion.TEXTO[i]);
				}else{
					accion.setDescripcion(Accion.TEXTOREPORTE[i]);
				}
				accion.setEstado(valor != '0');
				acciones.add(accion);
				i++;
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			
		}
		return acciones;  
	}
	
	
	public void permiteCatalogo() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.CATALOGO] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}

	public void permiteGrafico() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.GRAFICO] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	
	public void permitePDF() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.PDF] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteXML() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.XLS] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteReporteWEB() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.WEB] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteAgregar() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.AGREGAR] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteModificar() throws ExcAccesoInvalido	{
		if (mascaraAccion[Accion.EDITAR] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}	
	
	public void permiteEliminar() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.ELIMINAR] =='0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteConsultar() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.CONSULTAR] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteAsociar() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.ASOCIAR] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	public void permiteProcesar() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.PROCESAR] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	public void permiteImprimirItem() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.IMPRIMIR_ITEM] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}

	public void permiteImprimirTodo() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.IMPRIMIR_TODO] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	
	public void permiteCorregir() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.CORREGIR] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}
	public void permiteAnular() throws ExcAccesoInvalido{
		if (mascaraAccion[Accion.ANULAR] == '0'){
			throw new ExcAccesoInvalido("Accion Restringida al usuario");
		}
	}

	public char[] getMascaraAccion() {
		return mascaraAccion;
	}

	public String getMascara() {
		String mascara = String.copyValueOf(mascaraAccion);
		return mascara;
	}
	
	public void setMascaraAccion(char[] mascaraAccion) {
		this.mascaraAccion = mascaraAccion;
	}

	public void setMascaraAccion(int index, boolean mascaraAccion) {
		if (mascaraAccion)
			this.mascaraAccion[index] = '1';
		else
			this.mascaraAccion[index] = '0';
	}
	
	
}
