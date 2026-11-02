public class Butaca {
    private int asiento, fila;

    public Butaca(int f, int a){
        asiento=a;
        fila=f;
    }

    public Butaca(String s){
        String[] b=s.split(":");
        fila=Integer.parseInt(b[0]);
        asiento=Integer.parseInt(b[1]);
    }

    public int getAsiento() {
    return asiento;
    }

    public int getFila() {
    return fila;
    }

    public String toString(){
        return fila+":"+asiento;
}
}