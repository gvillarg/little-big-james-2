/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package produccion.algoritmos;

//import controlador.almacen.controladoralmacen;
import produccion.controlador.ControladorProduccion;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import modelos.Actividad;
import modelos.Asignacionxproducto;
import modelos.AsignacionxproductoId;
import modelos.Empleado;

/**
 *
 * @author Guti
 */
public class Algoritmo {

    private static controlador.almacen.controladoralmacen controladoralmacen = new controlador.almacen.controladoralmacen();
    private static controlador.configuracion.controladorconfiguracion controladorconfiguracion = new controlador.configuracion.controladorconfiguracion();
    private static float alfa;// = 0.26;
    private static int numIteraciones;// = 10000;
    private static int tamPoblacionInicial;// = 20;
    private static int tamMaxPoblacion;// = 150;
    private static int maxCiclosSinCambiar;// = 400;
    private static float tasaCasamiento;// = 0.85;
    private static float tasaMutacion;// = 0.01;
    private static int numHilos;
    private static String errorMsg;

    public static class AsignacionAlgoritmo {

        public modelos.Asignacion asignacion;
        public ArrayList<modelos.Asignacionxempleado> listaAsignacionxempleado;
        public ArrayList<modelos.Asignacionxproducto> listaAsignacionxproducto;
    }

    private static int indiceActividad(List<Actividad> listaActividades, Actividad actividad) {
        int indice = 0;
        while (indice < listaActividades.size()
                && listaActividades.get(indice).getIdactividad() != actividad.getIdactividad()) {
            indice++;
        }
        return indice;
    }

    private static Asignacionxproducto asignarProducto(ArrayList<Asignacionxproducto> listaAsignacionxproducto) {
        Asignacionxproducto mayor = listaAsignacionxproducto.get(0);
        Asignacionxproducto asignacionxproducto;

        for (int i = 1; i < listaAsignacionxproducto.size(); i++) {
            asignacionxproducto = listaAsignacionxproducto.get(i);
            if ((asignacionxproducto.getCantidadrequerida() - asignacionxproducto.getProduccionestimada())
                    > mayor.getCantidadrequerida() - mayor.getProduccionestimada()) {
                mayor = asignacionxproducto;
            }
        }

        return mayor;
    }

    public static void main(String[] args) {
//        controladoralmacen.sacaidreceta("blabla");
//        ControladorProduccion.seleccionarProductosConAlmacen();
        ControladorProduccion.generarEmpleados(30);
//        ControladorProduccion.seleccionarActividades();
//        Grasp grasp = new Grasp();
//        grasp.ejecutarAlgoritmo(alfa, numIteraciones, 1);
//        grasp.imprimirSolucion(grasp.listaSoluciones.get(0));
    }

    public static AsignacionAlgoritmo ejecutar(Date fechaInicial, Date fechaFinal,
            ArrayList<Empleado> listaEmpleados, int[] numMaquinasActividad) {

        Grasp grasp = new Grasp();
        Genetico genetico = new Genetico();
        modelos.Asignacion asignacion = new modelos.Asignacion();

        // 1. Leer parametros
        modelos.Parametrosalgoritmo parametros = ControladorProduccion.seleccionarParametroAlgoritmo();
        alfa = parametros.getAlfa();
        numIteraciones = parametros.getNumiteraciones();
        tamPoblacionInicial = parametros.getTampoblacioninicial();
        tamMaxPoblacion = parametros.getTammaxpoblacion();
        maxCiclosSinCambiar = parametros.getMaxciclossincambiar();
        tasaCasamiento = parametros.getTasacasamiento();
        tasaMutacion = parametros.getTasamutacion();
        numHilos = parametros.getNumhilos();

        // 2. Inicializar datos generales
        grasp.numEmpleados = listaEmpleados.size();
        grasp.numMaquinasActividad = numMaquinasActividad;

        List<modelos.Actividad> listaActividades = ControladorProduccion.seleccionarActividades();

        List<modelos.Turno> listaTurnos = ControladorProduccion.seleccionarTurnos();
        grasp.numTurnos = listaTurnos.size();

        // 3. Leer requerimientos
        Grasp.Requerimiento[] requerimientos = new Grasp.Requerimiento[Grasp.numActividades];
        double[] costoActividad = new double[Grasp.numActividades];
        for (int i = 0; i < Grasp.numActividades; i++) {
            Grasp.Requerimiento req = grasp.new Requerimiento();
            req.actividad = i;
            requerimientos[i] = req;
        }
        // 3.1 Obtener orden de produccion
        modelos.Ordenproduccion orden = ControladorProduccion.buscarOrdenProduccionPorFecha(fechaInicial);
        if (orden != null) {
            asignacion.setOrdenproduccion(orden);
            
            // Calcular lista de dias laborables
            int numDias = 0;
            Calendar cal = Calendar.getInstance();
            cal.setTime(orden.getFechainicio());
            while (utils.Utils.compararFechas(cal.getTime(), orden.getFechafin()) <= 0) {
                numDias++;
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }

            // 3.2 Obtener lista de productos
            ArrayList<modelos.Asignacionxproducto> listaAsignacionxproducto = new ArrayList<>();
            modelos.Asignacionxproducto axp;
            ArrayList<ArrayList<modelos.Asignacionxproducto>> listaProductoXActividad = new ArrayList<>();
            for (int i = 0; i < Grasp.numActividades; i++) {
                listaProductoXActividad.add(new ArrayList<modelos.Asignacionxproducto>());
            }

            ArrayList<modelos.Detalleordenproduccion> detalleOrden =
                    ControladorProduccion.buscarDetalleOrdenProduccion(orden.getIdordenproduccion());

            for (int i = 0; i < detalleOrden.size(); i++) {
                axp = new modelos.Asignacionxproducto();

                AsignacionxproductoId id = new AsignacionxproductoId();
                id.setIdproducto(detalleOrden.get(i).getProducto().getIdproducto());

                axp.setId(id);
                axp.setProducto(detalleOrden.get(i).getProducto());
                //productos.add(detalleOrden.get(i).getProducto());
                axp.setCantidadrequerida(detalleOrden.get(i).getCantidad() / numDias);
                //cantidadesProductos.add(new Float(detalleOrden.get(i).getCantidadaproducir()));
                axp.setProduccionestimada(0);
                axp.setMermaestimada(0);
                listaAsignacionxproducto.add(axp);
            }

            //ArrayList<modelos.Receta> listaRecetas = new ArrayList<>();
            for (int i = 0; i < listaAsignacionxproducto.size(); i++) {
                // 3.2.1 Buscar receta
                axp = listaAsignacionxproducto.get(i);
                modelos.Receta receta = ControladorProduccion.buscarRecetaPorIdproducto(axp.getProducto().getIdproducto());
                //listaRecetas.add(receta);

                // 3.2.2 Agregar cantidad a actividad
                if (receta != null) {
                    int indice = indiceActividad(listaActividades, receta.getActividad());
                    listaProductoXActividad.get(indice).add(axp);
                    requerimientos[indice].requerimiento += axp.getCantidadrequerida();
                    costoActividad[indice] += axp.getProducto().getCosto() * axp.getCantidadrequerida();

                    // 3.2.3 Agregar productos intermedios a la lista
                    List<modelos.Productoxreceta> listaProductoXReceta =
                            ControladorProduccion.buscarProductoxReceta(receta.getIdreceta());
                    for (int j = 0; j < listaProductoXReceta.size(); j++) {
                        int idTipoProducto = listaProductoXReceta.get(j).getProducto().getTipoproducto().getIdtipoproducto();
                        if (idTipoProducto == 3 || idTipoProducto == 4) {
                            modelos.Asignacionxproducto temp = new modelos.Asignacionxproducto();

                            AsignacionxproductoId id = new AsignacionxproductoId();
                            id.setIdproducto(listaProductoXReceta.get(j).getProducto().getIdproducto());

                            temp.setId(id);
                            temp.setProducto(listaProductoXReceta.get(j).getProducto());
                            temp.setCantidadrequerida(listaProductoXReceta.get(j).getCantidad() * axp.getCantidadrequerida());
                            temp.setProduccionestimada(0);
                            temp.setMermaestimada(0);
                            listaAsignacionxproducto.add(temp);

//                        productos.add(listaProductoXReceta.get(j).getProducto());
//                        cantidadesProductos.add(listaProductoXReceta.get(j).getCantidad() * cantidadesProductos.get(i));
                        }
                    }
                }
            }
            grasp.requerimientos = requerimientos;

            // 3.3 Calcular costos por actividad
            for (int i = 0; i < Grasp.numActividades; i++) {
                if (requerimientos[i].requerimiento > 0) {
                    costoActividad[i] /= requerimientos[i].requerimiento;
                } else {
                    costoActividad[i] = 1;
                    numMaquinasActividad[i] = 0;
                }
            }
            grasp.numMaquinas = 0;
            for (int i = 0; i < Grasp.numActividades; i++) {
                grasp.numMaquinas += numMaquinasActividad[i];
            }
            grasp.costoActividad = costoActividad;

            ArrayList<modelos.Maquina> listaMaquinas = new ArrayList<>();
            for (int i = 0; i < Grasp.numActividades; i++) {
                ArrayList<modelos.Maquina> tempMaquinas = ControladorProduccion.buscarMaquinasPorIdactividad(listaActividades.get(i).getIdactividad());
                numMaquinasActividad[i] = Math.min(numMaquinasActividad[i], tempMaquinas.size());
                for (int j = 0; j < numMaquinasActividad[i]; j++) {
                    listaMaquinas.add(tempMaquinas.get(j));
                }
            }

            // 3.4 Generar matriz de desempeno
            Grasp.Desempeno[][] matrizDesempeno = new Grasp.Desempeno[grasp.numEmpleados][Grasp.numActividades * grasp.numTurnos];
            Grasp.Desempeno des;
            for (int i = 0; i < listaEmpleados.size(); i++) {
                for (int j = 0; j < listaTurnos.size(); j++) {
                    for (int k = 0; k < Grasp.numActividades; k++) {
                        des = grasp.new Desempeno();
                        des.empleado = i;
                        des.actividad = k;
                        des.turno = j;

                        modelos.Desempeno desempeno = ControladorProduccion.buscarDesempeno(
                                listaEmpleados.get(i).getIdempleado(), listaActividades.get(k).getIdactividad(),
                                listaTurnos.get(j).getIdturno());
                        des.produccion = desempeno.getProduccion();
                        des.merma = desempeno.getMerma();
                        if (des.merma == 0) {
                            des.merma = 0.1f;
                        }
                        matrizDesempeno[i][Grasp.numActividades * j + k] = des;
                    }
                }
            }
            grasp.matrizDesempeno = matrizDesempeno;

            //4. Ejecutar Algoritmo
            grasp.ejecutarAlgoritmo(alfa, numIteraciones, tamPoblacionInicial * numHilos);
            if (grasp.listaSoluciones.size() > 0) {
                genetico.grasp = grasp;
                genetico.ejecutarAlgoritmo(tamPoblacionInicial, tamMaxPoblacion, maxCiclosSinCambiar, tasaCasamiento, tasaMutacion, numHilos);


                // 5. Convertir datos
                asignacion.setFechainicial(fechaInicial);
                asignacion.setFechafinal(fechaFinal);
                asignacion.setEstado(produccion.vistas.Asignacion.PENDIENTE);
                ArrayList<modelos.Asignacionxempleado> listaAsignacionxempleado = new ArrayList<>();

                int[] cromosoma = genetico.mejorIndividuo.cromosoma;
                for (int i = 0; i < cromosoma.length; i++) {
                    if (cromosoma[i] >= 0) {
                        modelos.Asignacionxempleado asignacionxempleado = new modelos.Asignacionxempleado();

                        modelos.AsignacionxempleadoId id = new modelos.AsignacionxempleadoId();
                        id.setIdempleado(listaEmpleados.get(cromosoma[i]).getIdempleado());
//                        id.setIdmaquina(listaMaquinas.get(i % grasp.numMaquinas).getIdmaquina());
//                        id.setIdturno(listaTurnos.get(i / grasp.numMaquinas).getIdturno());

                        asignacionxempleado.setId(id);
                        asignacionxempleado.setAsignacion(asignacion);
                        asignacionxempleado.setEmpleado(listaEmpleados.get(cromosoma[i]));
                        asignacionxempleado.setMaquina(listaMaquinas.get(i % grasp.numMaquinas));
                        asignacionxempleado.setTurno(listaTurnos.get(i / grasp.numMaquinas));
                        asignacionxempleado.setProduccionestimada(grasp.matrizAsignacion[cromosoma[i]][i].desempeno.produccion);
                        asignacionxempleado.setMermaestimada(grasp.matrizAsignacion[cromosoma[i]][i].desempeno.merma);

                        //listaActividades.indexOf(i);
                        int indice = indiceActividad(listaActividades, asignacionxempleado.getMaquina().getActividad());
                        axp = asignarProducto(listaProductoXActividad.get(indice));
                        axp.setProduccionestimada(axp.getProduccionestimada() + asignacionxempleado.getProduccionestimada());
                        axp.setMermaestimada(axp.getMermaestimada() + asignacionxempleado.getMermaestimada());
                        asignacionxempleado.setProducto(axp.getProducto());

                        listaAsignacionxempleado.add(asignacionxempleado);
                    }
                }

                // 6. Devolver los datos
                AsignacionAlgoritmo resultado = new AsignacionAlgoritmo();
                resultado.asignacion = asignacion;
                resultado.listaAsignacionxempleado = listaAsignacionxempleado;
                resultado.listaAsignacionxproducto = listaAsignacionxproducto;

                errorMsg = null;     //no hay error
                return resultado;
            } else {
                errorMsg = "Los recursos son insuficientes para alcanzar el plan de produccion";     //no se alcanza la produccion
                return null;
            }
        } else {
            errorMsg = "No se encuentra un plan de producción";
            return null;        //no hay plan de produccion
        }
    }

    public static String getErrorMsg() {
        return errorMsg;
    }
}
