public class App {
    public static void main(String[] args) throws Exception {
        Cine cineLumiere = new Cine("Cine Lumiere");//creo un cine nuevo con nombre
        GestorCine gestor= new GestorCine(cineLumiere);//le asigno el gestor a ese cine
        System.out.println("Bienvenido al "+cineLumiere.getNombre()+"!!!");
        gestor.gestionarMenu();//llamo por el gestor al menu
    }
}
