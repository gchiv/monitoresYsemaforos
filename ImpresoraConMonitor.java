public class ImpresoraConMonitor {

    private int turno = 1;
    private final int NUM_DOCUMENTOS;

    public ImpresoraConMonitor(int numDocumentos) {
        this.NUM_DOCUMENTOS = numDocumentos;
    }

    public synchronized void imprimirDocumento(int documentoId) {
        try {
            while (documentoId != turno) {
                wait(); 
            }

            System.out.println("Imprimiendo documento #" + documentoId + "...");
            Thread.sleep(300); 
            System.out.println("Documento #" + documentoId + " completado.");

            turno++;
            notifyAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido.");
        }
    }

    public static void main(String[] args) {
        final int TOTAL_DOCUMENTOS = 5;
        ImpresoraConMonitor impresora = new ImpresoraConMonitor(TOTAL_DOCUMENTOS);
        Thread[] hilos = new Thread[TOTAL_DOCUMENTOS];

        for (int i = 0; i < TOTAL_DOCUMENTOS; i++) {
            final int id = i + 1;
            hilos[i] = new Thread(() -> impresora.imprimirDocumento(id));
            hilos[i].start();
        }

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nTodos los documentos han sido impresos.");
    }
}