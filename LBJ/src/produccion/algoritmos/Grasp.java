package produccion.algoritmos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Grasp {

//Atributos
    public static final int numActividades = 4;
    public final double[] reqGalletas = {2, 2, 1, 1};
    public int numGalletas;
    public int numEmpleados;
    public int[] numMaquinasActividad;
    public int numMaquinas;
    public double[] costoActividad;
    public int numTurnos;
    public Desempeno[][] matrizDesempeno;
    public Asignacion[][] matrizAsignacion;
    public ArrayList<Solucion> listaSoluciones;
    public int iteracionesPendientes = 0;
    private int numSolucionesGeneradas = 0;
    public Requerimiento[] requerimientos;
    private int numSoluciones;

//Clases
    public class Asignacion {

        public Desempeno desempeno;
        public double fObjetivo;
        public double produccionXnumPersonas;
        public double costoXmerma;
        public int maquina;
        //private boolean removido;

        public Asignacion() {
        }

        public Asignacion(Asignacion asignacion) {
            desempeno = asignacion.desempeno;
            fObjetivo = asignacion.fObjetivo;
            produccionXnumPersonas = asignacion.produccionXnumPersonas;
            costoXmerma = asignacion.costoXmerma;
            maquina = asignacion.maquina;
            //removido = asignacion.removido;
        }
    }

    public class Desempeno {

        public int empleado;
        public int actividad;
        public int turno;
        public float produccion;
        public float merma;
    }

    public class Requerimiento {

        public int actividad;
        public double requerimiento;
        public double prodPromedio;
        public double mermaPromedio;
        public double numPersonas;
        public int numDatos;

        public Requerimiento() {
        }

        public Requerimiento(Requerimiento requerimiento) {
            actividad = requerimiento.actividad;
            this.requerimiento = requerimiento.requerimiento;
            prodPromedio = requerimiento.prodPromedio;
            mermaPromedio = requerimiento.mermaPromedio;
            numPersonas = requerimiento.numPersonas;
            numDatos = requerimiento.numDatos;
        }
    }

    public class Solucion {

        public ArrayList<Asignacion> listaAsignaciones = new ArrayList<>();
        public double fObjetivo;
        public double produccionXnumPersonas = 0;
        public double costoXmerma = 0;
        //public Requerimiento[] requerimientos;// = new Requerimiento[numActividades];
        public double[] listaProduccionTotal = new double[numActividades];

        public void add(Asignacion a) {
            listaAsignaciones.add(a);
        }

        public int size() {
            return listaAsignaciones.size();
        }

        public Asignacion get(int i) {
            return listaAsignaciones.get(i);
        }
    }

//Métodos
    //pruebas de alfas
    public static double calibrarAlfa(int numIteraciones, int numDecimales, double exactitud) {

        // 1. Declarar variables
        int mejor = 0;
        double variacion = 0.1;
        double[] alfa = new double[11];
        double aux = 0;
        double[] fObjetivo = new double[11];
        Grasp grasp = new Grasp();

        // 2. Para cada decimal de precisión 
        for (int i = 0; i < numDecimales; i++) {
            int numMuestrasAnt = 0;
            // 2.1 Evaluar cada alfa
            for (int j = 0; j < 11; j++) {
                alfa[j] = aux;
                double diferencia;

                //grasp.ejecutarAlgoritmo(alfa[j], numIteraciones);
                double promedio = 0;//grasp.solucion.fObjetivo;
                double promedioAnterior;
                int numMuestras = 0;

                // 2.1.1 Hasta que el promedio no cambie
                do {
                    promedioAnterior = promedio;
                    grasp.ejecutarAlgoritmo(alfa[j], numIteraciones, 1);
                    promedio *= numMuestras;
                    promedio += grasp.listaSoluciones.get(0).fObjetivo;
                    numMuestras++;
                    promedio /= numMuestras;
                    System.out.println("Funcion Objetivo: " + grasp.listaSoluciones.get(0).fObjetivo);
                    //+" Promedio: "+promedio);
                    diferencia = promedio - promedioAnterior;
                    if (diferencia < 0) {
                        diferencia *= -1;
                    }
                } while (diferencia > exactitud * promedioAnterior
                        || numMuestrasAnt > numMuestras); //0.01%

                // 2.1.2 Imprimir resultado
                String cad = String.format("%." + numDecimales + "f", alfa[j]);
                System.out.println("Alfa: " + cad + "\tIteraciones: " + numIteraciones
                        + "\tFuncion Objetivo: " + promedio + "\tNumero de Muestras: " + numMuestras);
                fObjetivo[j] = grasp.listaSoluciones.get(0).fObjetivo;
                aux = alfa[j] + variacion;
                numMuestrasAnt = numMuestras;
            }

            //2.2 Comparar resultados
            mejor = 0;
            for (int j = 1; j < fObjetivo.length; j++) {
                if (fObjetivo[j] > fObjetivo[mejor]) {
                    mejor = j;
                }
            }
            if (mejor == 0) {
                aux = alfa[0];
            } else if (mejor == 10) {
                aux = alfa[9];
            } else if (fObjetivo[mejor - 1] > fObjetivo[mejor + 1]) {
                aux = alfa[mejor - 1];
            } else {
                aux = alfa[mejor];
            }
            variacion /= 10;
            //exactitud /= 10;
        }
        return alfa[mejor];
    }

    //metodo para resolver el problema de asignacion
    public synchronized void ejecutarAlgoritmo(double alfa, int numIteraciones, int numSoluciones) {

        try {
            // 0. Declarar datos
            listaSoluciones = new ArrayList<>();
            Requerimiento req;
            Asignacion asig;
            this.numSoluciones = numSoluciones;

            // 1. Leer los datos
            if (costoActividad == null || matrizDesempeno == null
                    || numMaquinasActividad == null || numEmpleados == 0 || numTurnos == 0) {
                leerDatosArchivo();
            }
            matrizAsignacion = generarMatrizAsignacion();
            iteracionesPendientes = numIteraciones;

            // 2. Calcular requerimientos
            if (requerimientos == null) {
                requerimientos = new Requerimiento[numActividades];
                for (int i = 0; i < numActividades; i++) {
                    req = new Requerimiento();
                    req.actividad = i;
                    req.requerimiento = numGalletas * reqGalletas[i];
                    req.numDatos = 0;
                    req.mermaPromedio = 0;
                    req.prodPromedio = 0;
                    requerimientos[i] = req;
                }
            }

            // 3. calcular numero de personas requeridas por actividad
            for (int i = 0; i < numEmpleados; i++) {
                for (int j = 0; j < numTurnos; j++) {
                    for (int k = 0; k < numMaquinas; k++) {
                        asig = matrizAsignacion[i][j * numMaquinas + k];
                        req = requerimientos[asig.desempeno.actividad];
                        req.mermaPromedio += asig.desempeno.merma;
                        req.prodPromedio += asig.desempeno.produccion;
                        req.numDatos++;
                    }
                }
            }
            for (int i = 0; i < numActividades; i++) {
                req = requerimientos[i];
                req.mermaPromedio /= req.numDatos;
                req.prodPromedio /= req.numDatos;
                if (req.requerimiento > 0.1) {
                    req.numPersonas = req.requerimiento / (req.prodPromedio - req.mermaPromedio);
                } else {
                    req.numPersonas = 0.1;
                }
            }

            // 4. Inicializar posibles asignaciones
            ArrayList<Asignacion> posiblesAsignaciones = new ArrayList<>();
            for (int i = 0; i < numEmpleados; i++) {
                for (int j = 0; j < numTurnos; j++) {
                    for (int k = 0; k < numMaquinas; k++) {
                        asig = matrizAsignacion[i][j * numMaquinas + k];
                        //asig.removido = false;
                        posiblesAsignaciones.add(asig);
                    }
                }
            }

            //5. Calcular valor de funcion objetivo
            for (int i = 0; i < posiblesAsignaciones.size(); i++) {
                asig = posiblesAsignaciones.get(i);
                asig.produccionXnumPersonas = asig.desempeno.produccion * requerimientos[asig.desempeno.actividad].numPersonas;
                asig.costoXmerma = costoActividad[asig.desempeno.actividad] * asig.desempeno.merma;
                asig.fObjetivo = asig.produccionXnumPersonas / asig.costoXmerma;

            }

            // 6. Ordenar lista por funcion objetivo
            Asignacion temp;
            boolean cambio;
            for (int i = 0; i < posiblesAsignaciones.size(); i++) {
                cambio = false;
                for (int j = posiblesAsignaciones.size() - 1; j > i; j--) {
                    if (posiblesAsignaciones.get(j - 1).fObjetivo < posiblesAsignaciones.get(j).fObjetivo) {
                        temp = posiblesAsignaciones.get(j);
                        posiblesAsignaciones.set(j, posiblesAsignaciones.get(j - 1));
                        posiblesAsignaciones.set(j - 1, temp);
                        cambio = true;
                    }
                }
                if (!cambio) {
                    break;
                }
            }

            //7. Lanzar hilos
            int numHilos = Runtime.getRuntime().availableProcessors();
            HiloGrasp hilo;
            int cociente = numIteraciones / numHilos;
            int residuo = numIteraciones % numHilos;
            for (int i = 0; i < numHilos; i++) {
                if (i < residuo) {
                    hilo = new HiloGrasp(alfa, cociente + 1, matrizAsignacion, posiblesAsignaciones);
                } else {
                    hilo = new HiloGrasp(alfa, cociente, matrizAsignacion, posiblesAsignaciones);
                }
                hilo.start();
            }

            //8. Esperar hilos
            wait();

            System.out.print("Grasp terminado");
            if (listaSoluciones.size() > 0) {
                System.out.print(" - Funcion objetivo: " + listaSoluciones.get(0).fObjetivo);
            }
            System.out.println();
        } catch (InterruptedException ex) {
            Logger.getLogger(Grasp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //metodo para leer datos del archivo
    public void leerDatosArchivo() {

        //se abre el archivo
        try (FileReader archivo = new FileReader("datos.txt")) {

            BufferedReader br = new BufferedReader(archivo);

            //se lee el numero de empleados
            StringTokenizer linea = new StringTokenizer(br.readLine());
            numEmpleados = Integer.parseInt(linea.nextToken());

            // se lee la segunda linea con en numero de maquinas por actividad
            linea = new StringTokenizer(br.readLine());
            numMaquinasActividad = new int[numActividades];
            for (int i = 0; i < numActividades; i++) {
                numMaquinasActividad[i] = Integer.parseInt(linea.nextToken());
            }

            // se lee la tercera linea con en costo por actividad
            linea = new StringTokenizer(br.readLine());
            costoActividad = new double[numActividades];
            for (int i = 0; i < numActividades; i++) {
                costoActividad[i] = Double.parseDouble(linea.nextToken());
            }

            //se lee la tercera linea con el numero de turnos
            linea = new StringTokenizer(br.readLine());
            numTurnos = Integer.parseInt(linea.nextToken());

            //se lee la cantidad de galletas por hacer
            linea = new StringTokenizer(br.readLine());
            numGalletas = Integer.parseInt(linea.nextToken());

            //se inicializa la matriz de empleado x actividad
            matrizDesempeno = new Desempeno[numEmpleados][numActividades * numTurnos];

            //se inicializa la matriz de empleado x maquina
            numMaquinas = 0;
            for (int i = 0; i < numActividades; i++) {
                numMaquinas += numMaquinasActividad[i];
            }
//            matrizAsignacion = new Asignacion[numEmpleados][numMaquinas * numTurnos];

            //se lee el desempeño y llenan las matrices de desempeño por actividad y por máquina
            Desempeno des;
            Asignacion asig;
            int numAcumuladoMaquinas;
            for (int i = 0; i < numEmpleados; i++) {
                linea = new StringTokenizer(br.readLine());
                for (int j = 0; j < numTurnos; j++) {
//                    numAcumuladoMaquinas = 0;
                    for (int k = 0; k < numActividades; k++) {
                        des = new Desempeno();
                        des.empleado = i;
                        des.actividad = k;
                        des.turno = j;
                        des.produccion = Integer.parseInt(linea.nextToken());
                        des.merma = Integer.parseInt(linea.nextToken());
                        if (des.merma == 0) {
                            des.merma = 0.1f;
                        }
                        matrizDesempeno[i][numActividades * j + k] = des;
                    }
                }
            }

            //se cierra el archivo
            archivo.close();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    //generar una matriz asignacion local para cada hilo
    public Asignacion[][] generarMatrizAsignacion() {
        Asignacion[][] nuevaMatrizAsignacion = new Asignacion[numEmpleados][numMaquinas * numTurnos];
        Desempeno des;
        Asignacion asig;
        int numAcumuladoMaquinas;
        for (int i = 0; i < numEmpleados; i++) {
            for (int j = 0; j < numTurnos; j++) {
                numAcumuladoMaquinas = 0;
                for (int k = 0; k < numActividades; k++) {
                    des = matrizDesempeno[i][numActividades * j + k];
                    for (int l = 0; l < numMaquinasActividad[k]; l++) {
                        asig = new Asignacion();
                        asig.desempeno = des;
                        asig.maquina = numAcumuladoMaquinas + l;
                        nuevaMatrizAsignacion[i][asig.maquina + numMaquinas * j] = asig;
                    }
                    numAcumuladoMaquinas += numMaquinasActividad[k];
                }
            }
        }
        return nuevaMatrizAsignacion;
    }

    //clase que implementa Thread y construye soluciones en paralelo
    private class HiloGrasp extends Thread {

        //Atributos
        private double alfa;
        private int numIteraciones;
        private Asignacion[][] matrizAsignacion;
        private ArrayList<Asignacion> posiblesAsignacionesOriginal;
        private boolean removido[][];

        //Métodos
        private HiloGrasp(double alfa, int numIteraciones, Asignacion[][] matrizAsignacion, ArrayList<Asignacion> posiblesAsignaciones) {
            this.alfa = alfa;
            this.numIteraciones = numIteraciones;
            posiblesAsignacionesOriginal = posiblesAsignaciones;
            this.matrizAsignacion = matrizAsignacion;
            removido = new boolean[numEmpleados][numMaquinas * numTurnos];
        }

        // Grasp Construccion
        @Override
        public void run() {
            Solucion solucion;
            // 1. Realizar construccion
            for (int i = 0; i < numIteraciones; i++) {
                solucion = construirSolucion();
                agregarSolucion(solucion);
            }
        }

        //metodo para construir una unica solucion
        private Solucion construirSolucion() {

            Random ran = new Random();
            Requerimiento req;
            Asignacion asig, asigElegida;
            int indiceElegido;

            // 1. Copiar posiblesAsignaciones
            ArrayList<Asignacion> posiblesAsignaciones = (ArrayList<Asignacion>) posiblesAsignacionesOriginal.clone();
            removido = new boolean[numEmpleados][numTurnos * numMaquinas];

            // 2. Inicializar solucion vacia
            Solucion solucion = new Solucion();

            // 3. while (posibles asignaciones != null)        
            while (!posiblesAsignaciones.isEmpty()) {

                // 3.1 generar RCL (alfa)
                double max = posiblesAsignaciones.get(0).fObjetivo;
                double min = posiblesAsignaciones.get(posiblesAsignaciones.size() - 1).fObjetivo;
                double limInf = max - (max - min) * alfa;
                //rcl = new ArrayList<>();
                int indiceLimite = 0;
                while ((indiceLimite < posiblesAsignaciones.size())
                        && (posiblesAsignaciones.get(indiceLimite).fObjetivo >= limInf)) {
                    indiceLimite++;
                }

                // 3.2 elegir asignacion  
                indiceElegido = ran.nextInt(indiceLimite);
                asigElegida = posiblesAsignaciones.get(indiceElegido);
                solucion.add(asigElegida);
                solucion.produccionXnumPersonas += asigElegida.produccionXnumPersonas;
                solucion.costoXmerma += asigElegida.costoXmerma;

                // 3.3 actualizar posibles asignaciones
                int i, j;
                for (i = 0; i < numEmpleados; i++) {
                    j = asigElegida.desempeno.turno * numMaquinas + asigElegida.maquina;
                    if (removido[i][j] == false) {
                        asig = matrizAsignacion[i][j];
                        posiblesAsignaciones.remove(asig);
                        removido[i][j] = true;
                    }
                }
                for (j = 0; j < (numTurnos * numMaquinas); j++) {
                    i = asigElegida.desempeno.empleado;
                    if (removido[i][j] == false) {
                        asig = matrizAsignacion[i][j];
                        posiblesAsignaciones.remove(asig);
                        removido[i][j] = true;
                    }
                }

                // 3.4 actualizar produccion total de la solucion
                solucion.listaProduccionTotal[asigElegida.desempeno.actividad] +=
                        asigElegida.desempeno.produccion - asigElegida.desempeno.merma;
            }

//            // 4. Verificar cumplimiento de requerimientos
//            for (int i = 0; i < numActividades; i++) {
//                if (requerimientos[i].requerimiento > solucion.listaProduccionTotal[i]) {
//                    return null;
//                }
//            }

            // 5. Devolver Solucion
            solucion.fObjetivo = solucion.produccionXnumPersonas / solucion.costoXmerma;
            return solucion;
        }
    }

    //metodo comparar la ultima solucion generada y la mejor encontrada hasta el momento
    private synchronized void agregarSolucion(Solucion solucion) {
        if (solucion != null) {
            numSolucionesGeneradas++;
            if (listaSoluciones.size() < numSoluciones) {
                int i = 0;
                while ((i < listaSoluciones.size())
                        && (listaSoluciones.get(i).fObjetivo > solucion.fObjetivo)) {
                    i++;
                }
                listaSoluciones.add(i, solucion);
            } else if (solucion.fObjetivo > listaSoluciones.get(numSoluciones - 1).fObjetivo) {
                int i = 0;
                while ((i < listaSoluciones.size())
                        && (listaSoluciones.get(i).fObjetivo > solucion.fObjetivo)) {
                    i++;
                }
                if (i == listaSoluciones.size() - 1) {
                    listaSoluciones.set(i, solucion);
                } else {
                    listaSoluciones.add(i, solucion);
                    listaSoluciones.remove(numSoluciones - 1);
                }
            }
        }
        iteracionesPendientes--;
        if (iteracionesPendientes == 0) {
            notify();
        }

    }

    //metodo para mostrar en pantalla el resultado del grasp
    public void imprimirSolucion(Solucion solucion) {
        if (solucion != null) {
            System.out.println("\nMejor Solucion:\nFuncion Objetivo: "
                    + solucion.produccionXnumPersonas + " / " + solucion.costoXmerma + " = " + solucion.fObjetivo + "\n");
            for (int i = 0; i < numActividades; i++) {
                System.out.println("Proceso " + i + ": " + requerimientos[i].requerimiento + " -> "
                        + solucion.listaProduccionTotal[i]);
            }
            System.out.println("Numero de soluciones generadas: " + numSolucionesGeneradas);
//            Asignacion asig;
//            for (int i = 0; i < solucion.size(); i++) {
//                asig = solucion.get(i);
//                System.out.println("Empleado " + asig.empleado + "->\tMáquina " + asig.maquina + "; Turno " + asig.turno);
//            }
        }
    }

    // Método para crear el archivo de datos iniciales
    public static void crearDatosArchivo() {
        int tempNumGalletas = 600;
        int tempNumEmpleados = 85;
        int tempNumTurnos = 2;
        double tempCosto = 3;

        int[] factorProcProd = {65, 65, 25, 25};
        int[] factorProcMerm = {5, 5, 2, 2};
        try {
            Random ran = new Random();
            BufferedWriter archivo = new BufferedWriter(new FileWriter("datos.txt"));

            archivo.write(tempNumEmpleados + " empleados\n");
            for (int i = 0; i < numActividades; i++) {
                int maquinas = 8 + ran.nextInt(3);
                archivo.write(maquinas + "\t");
            }
            archivo.write(" maquinas por proceso\n");
            for (int i = 0; i < numActividades; i++) {
                tempCosto += 0.2 + ran.nextDouble() * 0.8;
                archivo.write(tempCosto + "\t");
            }
            archivo.write(" costos por proceso\n");
            archivo.write(tempNumTurnos + " turnos\n");
            archivo.write(tempNumGalletas + " galletas\n");

            for (int i = 0; i < tempNumEmpleados; i++) {
                int factorEmpProd = ran.nextInt(20) + 20;
                int factorEmpMerm = ran.nextInt(4);
                for (int k = 0; k < tempNumTurnos; k++) {
                    int factorTurnoProd = ran.nextInt(10) - 5;
                    int factorTurnoMerm = ran.nextInt(2);
                    for (int j = 0; j < numActividades; j++) {
                        int prod = factorEmpProd + factorTurnoProd + ran.nextInt(factorProcProd[j]);
                        int merm = factorEmpMerm + factorTurnoMerm + ran.nextInt(factorProcMerm[j]);
                        archivo.write(prod + " " + merm + "\t");
                    }
                }
                archivo.write("\n");
            }
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }
}
