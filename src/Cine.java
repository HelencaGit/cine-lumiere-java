import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cine {

    private static final String RUTA_FICH = "entradasVendidas.txt";

    String nombre;
    String[][] arrayButacas  = new String[10][15];

    public Cine() {
    }

    public Cine(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    private List<String> leerFicheroEntradasVendidas () {//leemos fichero
        List<String> lineas = new ArrayList<>();
        Path ruta = Paths.get(RUTA_FICH);
        try {
            lineas = Files.readAllLines(ruta);
        } catch (IOException ioe) {
           System.out.println("Ha habido un problema con la entrada o salida de los datos" + ioe);
        }
        return lineas;
    }

    private void escribirFicheroEntradasVendidas (Butaca butacaComprada) {//escribimos en el fichero la butaca que nos piden
        Path ruta = Paths.get(RUTA_FICH);
        try {
            Files.writeString(ruta, "\n"+butacaComprada.toString(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);//\n porque sino las escribe seguidas, y append para que las ponga al final, no las pise las anteriores
        } catch (IOException ioe) {
           System.out.println("Ha habido un problema con la entrada o salida de los datos en escribirFichero\n" + ioe);
        }
    }

    private void iniciarArrayButacas() {//inicializamos el cine con __
        for (int i=0;i<10;i++) {
            for (int j=0;j<15;j++) {
                arrayButacas[i][j] = " __ ";
            }
        }
    }

    private void actualizarArrayCineConDatosDelArchivo () {//actualizamos el cine con los datos del archivo
        iniciarArrayButacas();//inicializamos
        List<String> lineasEntradas = leerFicheroEntradasVendidas();//leemos
        if (lineasEntradas!=null && !lineasEntradas.isEmpty()) {//si no es nula o no esta vacia
            for (String cuadrante:lineasEntradas) {
                if (cuadrante.isBlank()) continue;//si la línea está vacía, la salto y paso a la siguiente
                String[] filaColumna = cuadrante.split(":");//en cada fila del array hacemos split para separar la fila y asiento por el :
                int fila = Integer.parseInt(filaColumna[0]);//parseamos la fila String a int
                int asiento = Integer.parseInt(filaColumna[1]);//parseamos el asiento String a int
                arrayButacas[fila][asiento] = " XX "; //en el arrayButacas de la clase cine lo ponemos a XX
            }
        }
    }

    public void mostrarButacas () {//enseñamos el arrayButacas despues de actualizarlo
        actualizarArrayCineConDatosDelArchivo();
        for (int i=0;i<10;i++) {
            for (int j=0;j<15;j++) {
                System.out.print(arrayButacas[i][j]);
            }
            System.out.println();
        }
    }

    public void comprarEntrada() {//método para comprar la entrada
        System.out.println("Este es el estado actual del cine...");//mostramos el cine como está actualmente
        mostrarButacas();
        try {
            Scanner teclado = new Scanner(System.in);
            boolean hayQueRepetir = false;//por si mete el sitio que ya está ocupado y volvemos a preguntarle
            do{
                System.out.println("Las butacas ocupadas tienen XX");
                System.out.println("¿Qué fila quieres?");
                int fila = teclado.nextInt();
                teclado.nextLine();
                System.out.println("¿Qué asiento quieres?");
                int asiento = teclado.nextInt();
                if (arrayButacas[fila-1][asiento-1].contains("XX")) {//controlamos por si esas butacas-1 están ocupadas
                    System.out.println("Esas butacas están ocupadas! Recuerda que...");
                    hayQueRepetir = true;//asi el bucle vuelve hasta que ponga un asiento libre
                } else {
                    System.out.println("Has comprado: ");
                    System.out.println("--------------------------");
                    System.out.println("Cine "+this.getNombre());
                    System.out.println("fila: "+fila);
                    System.out.println("asiento: "+asiento);
                    System.out.println("--------------------------");
                    Butaca butacaComprada = new Butaca(fila-1,asiento-1);//hago una butaca nueva con la que me han pedido
                    escribirFicheroEntradasVendidas (butacaComprada);//lo escribo en el fichero de entradas vendidas
                    actualizarArrayCineConDatosDelArchivo();//actualizo el array de datos del cine
                    hayQueRepetir = false;
                }
            }while(hayQueRepetir);
            
        }catch(Exception e) {
            System.out.println("Hubo un error con la entrada de datos (Scanner) en comprar entrada\n"+e);
        } 
    }

    public void devolverEntrada () {//método para devolver una entrada
        System.out.println("Este es el estado actual del cine...");
        mostrarButacas();//muestro las butacas actuales
        try {
            Scanner teclado = new Scanner(System.in);
            boolean hayQueRepetir = false;
            do{
                System.out.println("Las butacas ocupadas tienen XX");//realizo lo mismo que para venderlas sólo que alrevés
                System.out.println("¿Qué fila quieres devolver?");
                int fila = teclado.nextInt();
                teclado.nextLine();
                System.out.println("¿Qué asiento quieres devolver?");
                int asiento = teclado.nextInt();
                if (arrayButacas[fila-1][asiento-1].contains("XX")) {//alrevés que en el método de compra
                    System.out.println("Has devuelto: ");
                    System.out.println("--------------------------");
                    System.out.println("Cine "+this.getNombre());
                    System.out.println("fila: "+fila);
                    System.out.println("asiento: "+asiento);
                    System.out.println("--------------------------");
                    Butaca butacaDevolver = new Butaca(fila-1,asiento-1);
                    borrarButacaDelFichero(butacaDevolver);
                    actualizarArrayCineConDatosDelArchivo();
                    hayQueRepetir = false;
                } else {
                    System.out.println("Ese sitio ya está libre, esa no es tu butaca, compruebalo de nuevo");
                    hayQueRepetir = true;
                }
            }while(hayQueRepetir);
            
        }catch(Exception e) {
            System.out.println("Hubo un error con la entrada de datos (Scanner) en devolver entrada\n"+e);
        } 
    }

    private void borrarButacaDelFichero(Butaca butacaDevolver){//método para borrar el asiento que quieren devolver
        Path ruta = Paths.get(RUTA_FICH);
        try {
            List<String> listaEntradas=Files.readAllLines(ruta);//leo la ruta y lo meto en una lista
            List<String> listaNueva = new ArrayList<>();//hago una lista nueva para actualizar la anterior
            for(String entrada : listaEntradas){
                if (!entrada.equals(butacaDevolver.toString())){//si la entrada no es igual a la bucata a devolver
                    listaNueva.add(entrada);//añado en la lista nueva las entradas que no son la butaca a devolver
                }
            }
            Files.write(ruta, listaNueva, StandardOpenOption.TRUNCATE_EXISTING);//escribo en el archuvo la lista nueva y hago un truncate_existing ya que quiero(si existe, y tiene contenido, se ignore su contenido para sobreescribirlo desde el principio)
        } catch (IOException ioe) {
           System.out.println("Ha habido un problema con la entrada o salida de los datos en escribirFichero\n" + ioe);
        }
    }

}
