package cpc.ares.interfaz;

import cva.pc.demeter.excepciones.ExcEntradaInconsistente;
import cva.pc.demeter.excepciones.ExcEntradaInvalida;

public interface IControlador {
	void validarEntrada() throws ExcEntradaInvalida, ExcEntradaInconsistente;
}
