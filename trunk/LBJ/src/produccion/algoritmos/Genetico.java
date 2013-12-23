package produccion.algoritmos;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class Genetico {

// Atributos
    private int numTrab;
    private int numMaquinas;
    private int numTurnos;
    private int numProc;
    private Grasp.Asignacion[][] matrizAsignacion;
//    private Poblacion poblacion;
    public Individuo mejorIndividuo;
    private int numHilosPendientes;
    //private int numDuplicados = 0;
    private double tasaCasamiento;
    private double tasaMutacion;
    private int maxCiclosSinCambiar;
    private int tamMaxPoblacion;
    private HiloMigracion hiloMigracion;
    private HiloGenetico[] listaHilos;
    //public static final int numHilos=16;
    public Grasp grasp;

// Clases
    public class Individuo {

        public int[] cromosoma;
        private double valorFitness;

        public Individuo() {
        }

        public Individuo(Individuo a) {
            valorFitness = a.valorFitness;
            cromosoma = new int[numMaquinas * numTurnos];
            System.arraycopy(a.cromosoma, 0, cromosoma, 0, numMaquinas * numTurnos);
        }

        public void evaluarFitness() {
            double produccionXnumPersonas = 0;
            double costoXmerma = 0;
            for (int i = 0; i < cromosoma.length; i++) {
                int trabajador = cromosoma[i];
                if (trabajador != -1) {
                    produccionXnumPersonas += matrizAsignacion[trabajador][i].produccionXnumPersonas;
                    costoXmerma += matrizAsignacion[trabajador][i].costoXmerma;
                }
            }
            valorFitness = produccionXnumPersonas / costoXmerma;
        }
    }

    private class Poblacion {

        private ArrayList<Individuo> listaIndividuos = new ArrayList<>();
        private double valorFitness = 0;
        Individuo mejorIndividuo = null;

        private void add(Individuo individuo) {
            listaIndividuos.add(individuo);
            if (mejorIndividuo == null || individuo.valorFitness > mejorIndividuo.valorFitness) {
                mejorIndividuo = individuo;
            }
            valorFitness += individuo.valorFitness;
        }

        private void remove(Individuo individuo) {
            listaIndividuos.remove(individuo);
            valorFitness -= individuo.valorFitness;
        }

        private void remove(int i) {
            valorFitness -= listaIndividuos.get(i).valorFitness;
            listaIndividuos.remove(i);
        }

        private Individuo get(int i) {
            return listaIndividuos.get(i);
        }

        private int size() {
            return listaIndividuos.size();
        }

        private Individuo RemoverAleatorio() {
            // 1. Se genera una ruleta
//        GenerarRuleta(listIndividuo);
            // Se escoge un numeroAleatorio aleatorio del 0 al 1 
            Random rand = new Random();
            double numeroAleatorio = rand.nextDouble();
            // Se ve entre que asignaciones salio para ver que cromosoma gano
            Individuo individuo=null;
            double aux = 0;
            for (int i = 0; i < size(); i++) {
                //Individuo ant = listIndividuo.get(i - 1);
                individuo = get(i);
                aux += individuo.valorFitness / valorFitness;
                if (aux >= numeroAleatorio) {
                    remove(i);
                    return individuo;
                }
            }
            return individuo;
        }
    }

    private class HiloGenetico extends Thread {

        private Poblacion poblacion = new Poblacion();
        private boolean migrar;

        @Override
        public synchronized void run() {
            int ciclosSinCambiar = 0;
            Poblacion nuevaPoblacion;
            ArrayList<Individuo> listaPadres;
            ArrayList<Individuo> listaHijos;
            int numCasamientos, numSelecciones;
            Individuo individuo, padre1, padre2;

            while (ciclosSinCambiar < maxCiclosSinCambiar) {
                numCasamientos = (int) (poblacion.size() * tasaCasamiento) / 2;
                listaPadres = new ArrayList<>();
                listaHijos = new ArrayList<>();

                for (int j = 0; j < numCasamientos; j++) {
                    // Probabilidad de seleccion proporcional a la funcion fitness
                    padre1 = poblacion.RemoverAleatorio();
                    listaPadres.add(padre1);
                    padre2 = poblacion.RemoverAleatorio();
                    listaPadres.add(padre2);

                    Individuo[] hijos = Casamiento(padre1, padre2);
                    listaHijos.add(hijos[0]);
                    listaHijos.add(hijos[1]);
                }

                for (int i = 0; i < listaHijos.size(); i++) {
                    // Mutar a los individuos con cierta probabilidad
                    Mutacion(listaHijos.get(i), tasaMutacion);
                    // FIX: arreglar los individuos con trabajadores repetidos
                    AlgoritmoArreglo(listaHijos.get(i));
                }

                // Volver a agregar a los padres a la poblacion
                for (int j = 0; j < listaPadres.size(); j++) {
                    poblacion.add(listaPadres.get(j));
                }

                // 3.5 CONTROL DE ABERRACIONES eliminar los individuos repetidos
                for (int j = 0; j < listaHijos.size(); j++) {
                    individuo = listaHijos.get(j);
                    individuo.evaluarFitness();
                    if (ControlDeAberraciones(individuo, poblacion) != null) {
                        poblacion.add(individuo);
                    }
                }

                // 3.6 Seleccion            
                nuevaPoblacion = new Poblacion();
                if (poblacion.size() < tamMaxPoblacion) {
                    numSelecciones = poblacion.size();
                } else {
                    numSelecciones = tamMaxPoblacion;
                }
                Individuo seleccionado;
                for (int i = 0; i < numSelecciones; i++) {
                    seleccionado = poblacion.RemoverAleatorio();
                    nuevaPoblacion.add(seleccionado);
                }

                // 3.7 Elitismo
                if (nuevaPoblacion.mejorIndividuo.valorFitness < poblacion.mejorIndividuo.valorFitness) {
                    nuevaPoblacion.add(poblacion.mejorIndividuo);
                }

                // 3.8 Verificar mejora
                if (nuevaPoblacion.mejorIndividuo.valorFitness == poblacion.mejorIndividuo.valorFitness) {
                    ciclosSinCambiar++;
                } else {
                    ciclosSinCambiar = 0;
                }

                poblacion = nuevaPoblacion;
                if (migrar == true) {
                    migrar = false;
                    hiloMigracion.notificarMigracion();
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        //Logger.getLogger(Genetico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            notificarFin();
            hiloMigracion.interrupt();
//            for (int i = 0; i < listaHilos.length; i++) {
//                    listaHilos[i].notificar();
//                
//            }
        }

        private synchronized void notificar() {
            notify();
        }
    }

    private class HiloMigracion extends Thread {

        private int contador;
        private long tiempo = 100;
        private boolean correr = true;

        @Override
        public synchronized void run() {
            try {
                int num = 0;
                while (correr) {
                    sleep(tiempo);
                    //System.out.println("Migracion " + num++);
                    contador = listaHilos.length;
                    for (int i = 0; i < listaHilos.length; i++) {
                        listaHilos[i].migrar = true;
                    }
                    wait();

                    // Migrar individuos
                    Individuo[] listaMejores = new Individuo[listaHilos.length];
                    for (int i = 0; i < listaHilos.length; i++) {
                        listaMejores[i] = listaHilos[i].poblacion.mejorIndividuo;
                    }
                    for (int i = 0; i < listaHilos.length; i++) {
                        for (int j = 0; j < listaHilos.length; j++) {
                            if (i != j) {
                                if (ControlDeAberraciones(listaMejores[i], listaHilos[j].poblacion) != null) {
                                    listaHilos[j].poblacion.add(listaMejores[i]);
                                }
                            }
                        }
                    }


                    for (int i = 0; i < listaHilos.length; i++) {
                        if (listaHilos[i].isAlive()) {
                            listaHilos[i].notificar();
                        }
                    }

                }
            } catch (InterruptedException ex) {
                for (int i = 0; i < listaHilos.length; i++) {
                    listaHilos[i].notificar();
                }
            }
        }

        private synchronized void notificarMigracion() {
            contador--;
            if (contador == 0) {
                notify();
            }
        }
    }

// Métodos    
    public static void main(String[] args) {
        Genetico genetico = new Genetico();
        genetico.ejecutarAlgoritmo(20, 150, 400, 0.85, 0.01,32);
        genetico.ImprimirSolucion(genetico.mejorIndividuo);
    }

    public synchronized void ejecutarAlgoritmo(int tamPoblacionInicial, int tamMaxPoblacion,
            int maxCiclosSinCambiar, double tasaCasamiento, double tasaMutacion, int numHilos) {

        HiloGenetico hilo;//Runtime.getRuntime().availableProcessors();
        this.tamMaxPoblacion = tamMaxPoblacion;//tamMaxPoblacion / numHilos ;
        this.maxCiclosSinCambiar = maxCiclosSinCambiar;
        this.tasaCasamiento = tasaCasamiento;
        this.tasaMutacion = tasaMutacion;

        try {
            // 1. Se genera la poblacion inicial
            Poblacion poblacion = GenerarPoblacionInicial(tamPoblacionInicial * numHilos);
            //numHilos=Math.round(poblacion.size()/tamPoblacionInicial);

            // 2. Se inicializa la lista de hilos
            numHilosPendientes = numHilos;
            listaHilos = new HiloGenetico[numHilos];
            for (int i = 0; i < numHilos; i++) {
                listaHilos[i] = new HiloGenetico();
            }

            // 3. Preparar Hilos
            for (int i = 0; i < tamPoblacionInicial * numHilos; i++) {
                hilo = listaHilos[i % numHilos];
                hilo.poblacion.add(poblacion.get(i % poblacion.size()));
            }

            // 4. Lanzar hilos
            for (int i = 0; i < numHilos; i++) {
                listaHilos[i].start();
            }

            // 5. Lanzar hiloMigracion;
            hiloMigracion = new HiloMigracion();
            hiloMigracion.start();

            // 5. Esperar que hilos terminen
            wait();
            hiloMigracion.interrupt();
            hiloMigracion.correr = false;


            // 6. Seleccionar el mejor
            Individuo individuo;
            for (int i = 0; i < listaHilos.length; i++) {
                individuo = listaHilos[i].poblacion.mejorIndividuo;
                if (mejorIndividuo == null || mejorIndividuo.valorFitness < individuo.valorFitness) {
                    mejorIndividuo = individuo;
                }
            }

            System.out.print("Genetico terminado");
            if(mejorIndividuo!=null){
                System.out.print(" - Funcion objetivo: "+mejorIndividuo.valorFitness);                
            }
            System.out.println();
        } catch (InterruptedException ex) {
            Logger.getLogger(Genetico.class
                    .getName()).log(Level.SEVERE, null, ex);
            
        }

    }

    private synchronized void notificarFin() {

        numHilosPendientes--;
        if (numHilosPendientes == 0) {
            notify();
        }
    }

    public void escribirSolucionGraspArch(Grasp grasp, int num) {
//        FileWriter fichero = null;
//        PrintWriter pw = null;
//        try {
//            fichero = new FileWriter("datosGenetico" + num + ".txt");
//            pw = new PrintWriter(fichero);
//            grasp.leerDatosArchivo();
//            pw.println(grasp.costoActividad.length);
//            for (int i = 0; i < grasp.costoActividad.length; i++) {
//                pw.print(grasp.costoActividad[i]);
//                pw.print(" ");
//            }
//            pw.println(" ");
//            pw.println(grasp.numEmpleados + " "
//                    + grasp.numMaquinas + " " + grasp.numTurnos);
//            pw.println(grasp.listaSoluciones.get(0).size());
//            for (int j = 0; j < grasp.listaSoluciones.get(0).size(); j++) {
//                Grasp.Asignacion asig = grasp.listaSoluciones.get(0).get(j);
//                pw.println(asig.desempeno.empleado + " " + asig.maquina + " " + asig.desempeno.turno);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != fichero) {
//                    fichero.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
    }

    public Grasp leerSolucionGraspArch(int num) {
//        File archivo = null;
//        FileReader fr = null;
//        BufferedReader br = null;
//        Grasp grasp = new Grasp();
//        try {
//            archivo = new File("datosGenetico" + num + ".txt");
//            fr = new FileReader(archivo);
//            br = new BufferedReader(fr);
//            StringTokenizer linea = new StringTokenizer(br.readLine());
//            int numInt = Integer.parseInt(linea.nextToken());
//            grasp.costoActividad = new double[numInt];
//            linea = new StringTokenizer(br.readLine());
//            for (int i = 0; i < numInt; i++) {
//                grasp.costoActividad[i] = Double.parseDouble(linea.nextToken());
//            }
//            linea = new StringTokenizer(br.readLine());
//            grasp.numEmpleados = Integer.parseInt(linea.nextToken());
//            grasp.numMaquinas = Integer.parseInt(linea.nextToken());
//            grasp.numTurnos = Integer.parseInt(linea.nextToken());
//            Asignacion asig;
//            grasp.listaSoluciones = grasp.new Solucion();
//            linea = new StringTokenizer(br.readLine());
//            Integer cantAsig = Integer.parseInt(linea.nextToken());
//            for (int i = 0; i < cantAsig; i++) {
//                linea = new StringTokenizer(br.readLine());
//                asig = grasp.new Asignacion();
//                asig.desempeno.empleado = Integer.parseInt(linea.nextToken());
//                asig.maquina = Integer.parseInt(linea.nextToken());
//                asig.desempeno.turno = Integer.parseInt(linea.nextToken());
//                grasp.solucion.add(asig);
//            }
//            return grasp;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != fr) {
//                    fr.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
        return null;
    }

    private Poblacion GenerarPoblacionInicial(int tamPoblacionInicial) {

        // 1. Generar soluciones
        Poblacion poblacion = new Poblacion();
        if (grasp == null) {
            grasp = new Grasp();            
            grasp.ejecutarAlgoritmo(0.26, 10000, tamPoblacionInicial);
        }
        
        // 2. Inicializar variables
        numTrab = grasp.numEmpleados;
        numMaquinas = grasp.numMaquinas;
        numTurnos = grasp.numTurnos;
        numProc = Grasp.numActividades;

        // 3. Repetir por cada individuo generado
        for (int i = 0; i < grasp.listaSoluciones.size(); i++) {

            Grasp.Solucion solucion = grasp.listaSoluciones.get(i);
            matrizAsignacion = grasp.matrizAsignacion;
            if (solucion != null) {

                // 3.1 convertir a un individuo
                Individuo individuo = new Individuo();
                individuo.cromosoma = new int[numMaquinas * numTurnos];
                for (int j = 0; j < individuo.cromosoma.length; j++) {
                    individuo.cromosoma[j] = -1;
                }

                for (int j = 0; j < solucion.size(); j++) {
                    Grasp.Asignacion asig = solucion.get(j);
                    individuo.cromosoma[asig.maquina + numMaquinas * asig.desempeno.turno] = asig.desempeno.empleado;
                }

                // 3.2 Evaluar el fitness
                individuo.valorFitness = solucion.fObjetivo;

                // 3.3 agregar a la poblacion
                poblacion.add(individuo);
            }
        }
        return poblacion;
    }

    private Individuo[] Casamiento(Individuo padre1, Individuo padre2) {

        Individuo[] hijos = new Individuo[2];
        hijos[0] = new Individuo();
        hijos[1] = new Individuo();

        //Se genera el numeroAleatorio aleatorio que dira la posicion de corte
        int n = (int) (Math.random() * ((padre1.cromosoma.length - 1) - 0) + 0);
        hijos[0].cromosoma = new int[padre1.cromosoma.length];
        hijos[1].cromosoma = new int[padre1.cromosoma.length];
        // Se les pasan los genes a los hijos 
        for (int i = 0; i < n; i++) {
            hijos[0].cromosoma[i] = padre1.cromosoma[i];
            hijos[1].cromosoma[i] = padre2.cromosoma[i];
        }
        for (int j = n; j < padre1.cromosoma.length; j++) {
            hijos[0].cromosoma[j] = padre2.cromosoma[j];
            hijos[1].cromosoma[j] = padre1.cromosoma[j];
        }
        return hijos;
    }

    private void Mutacion(Individuo individuo, double tasaMutacion) {
        // Probabilidad de mutacion de la asignacion
        Random random = new Random();
        double probMutacion = random.nextDouble();
        if (probMutacion <= tasaMutacion) { // x% de probabilidad de mutacion
            //Si se da la asignacion se seleccionan 2 genes para intercambiarlos
            int num1 = random.nextInt(numMaquinas * numTurnos);
            int num2 = random.nextInt(numMaquinas * numTurnos);
            int t1 = individuo.cromosoma[num1];
            int t2 = individuo.cromosoma[num2];
            individuo.cromosoma[num2] = t1;
            individuo.cromosoma[num1] = t2;
        }
    }

    private Individuo ControlDeAberraciones(Individuo individuo, Poblacion poblacion) {

        //Si hay maquinas repetidas
        //AlgoritmoArreglo(individuo);

        // si es duplicado
        boolean esDuplicado;
        Individuo temp;
        for (int i = 0; i < poblacion.size(); i++) {
            esDuplicado = true;
            temp = poblacion.get(i);
            if (temp.valorFitness == temp.valorFitness) {
                for (int j = 0; (j < individuo.cromosoma.length) && (esDuplicado); j++) {
                    if (individuo.cromosoma[j] != temp.cromosoma[j]) {
                        esDuplicado = false;
                    }
                }
            }
            if (esDuplicado) {
                //numDuplicados++;
                return null;
            }
        }
        return individuo;
    }

    private void AlgoritmoArreglo(Individuo individuo) {

        //1. Inicializar las variables
        Random random = new Random();

        // 2. Crear la lista de maquinas sin usar
        boolean[] trabajadorAsignado = new boolean[numTrab];
        ArrayList<Integer> trabajadoresSinUsar = new ArrayList<>();
        for (int i = 0; i < numMaquinas * numTurnos; i++) {
            if (individuo.cromosoma[i] >= 0) {
                trabajadorAsignado[individuo.cromosoma[i]] = true;
            }
        }
        for (int i = 0; i < numTrab; i++) {
            if (trabajadorAsignado[i] == false) {
                trabajadoresSinUsar.add(i);
            }
        }

        // 3. asignar en maquinas libres
        for (int m = 0; (m < individuo.cromosoma.length) && !trabajadoresSinUsar.isEmpty(); m++) {
            if (individuo.cromosoma[m] == -1) {
                int indiceMejorTrabajador = 0;
                Grasp.Asignacion asignacion = matrizAsignacion[0][m];
                double mejorFitness = asignacion.fObjetivo;
                for (int i = 1; i < trabajadoresSinUsar.size(); i++) {
                    asignacion = matrizAsignacion[trabajadoresSinUsar.get(i)][m];
                    double fitness = asignacion.fObjetivo;
                    if (fitness > mejorFitness) {
                        mejorFitness = fitness;
                        indiceMejorTrabajador = i;
                    }
                }
                individuo.cromosoma[m] = trabajadoresSinUsar.get(indiceMejorTrabajador);
                trabajadoresSinUsar.remove(indiceMejorTrabajador);
            }
        }

        // 4. Buscar duplicados
        for (int m = 0; m < individuo.cromosoma.length - 1; m++) {
            for (int n = m + 1; n < individuo.cromosoma.length; n++) {
                if (individuo.cromosoma[m] == individuo.cromosoma[n] && individuo.cromosoma[m] != -1) {
                    // 4.1 Elegir uno de los duplicados para cambiarlo
                    int[] repetidos = {m, n};
                    int elegido = repetidos[random.nextInt(2)];

                    if (trabajadoresSinUsar.isEmpty()) {
                        // 4.2 Cambiar un duplicado por -1
                        individuo.cromosoma[elegido] = -1;
                    } else {
                        // 4.3 Cambiar un duplicado por maquina de lista de trabajadores sin usar
                        int indiceMejorTrabajador = 0;
                        Grasp.Asignacion asignacion = matrizAsignacion[0][elegido];
                        double mejorFitness = asignacion.fObjetivo;
                        for (int i = 1; i < trabajadoresSinUsar.size(); i++) {
                            asignacion = matrizAsignacion[trabajadoresSinUsar.get(i)][elegido];
                            double fitness = asignacion.fObjetivo;
                            if (fitness > mejorFitness) {
                                mejorFitness = fitness;
                                indiceMejorTrabajador = i;
                            }
                        }
                        individuo.cromosoma[elegido] = trabajadoresSinUsar.get(indiceMejorTrabajador);
                        trabajadoresSinUsar.remove(indiceMejorTrabajador);
                    }
                }
            }
        }
    }

    private void ImprimirSolucion(Individuo individuo) {
        System.out.println("El valor de la funcion objetivo es " + individuo.valorFitness);
//        System.out.println("Repetidos: " + numDuplicados);
        for (int i = 0; i < individuo.cromosoma.length; i++) {
            System.out.println("Turno=" + matrizAsignacion[0][i].desempeno.turno
                    + " Maquina=" + matrizAsignacion[0][i].maquina
                    + " Trabajador=" + individuo.cromosoma[i]);
        }
    }
}
