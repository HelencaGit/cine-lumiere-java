import java.util.Scanner;

public class GestorCine {
    Cine cine;

    public GestorCine(Cine cine) {
        this.cine = cine; 
    }

    public void gestionarMenu(){//menu de entrada
        try (Scanner teclado = new Scanner(System.in)) {
            
            System.out.println("Elige una opcion");
            int eleccion = 0;
            do{
                System.out.println("Menu");
                System.out.println("\t1. Mostrar butacas");
                System.out.println("\t2. Comprar entradas");
                System.out.println("\t3. Devolver una entrada");
                System.out.println("\t4. Salir");
                eleccion = teclado.nextInt();
                switch (eleccion) {
                    case 1:
                        cine.mostrarButacas();
                        break;
                    case 2:
                        cine.comprarEntrada();
                        break;
                    case 3:
                        cine.devolverEntrada();
                        break;
                    case 4:
                        System.out.println("Hasta pronto!!");
                        break;
                }
            }while(eleccion!=4);
        }catch(Exception e) {
            System.out.println("Hubo un error con la entrada de datos (Scanner) en menú principal\n"+e);
        }

    }
    

}
