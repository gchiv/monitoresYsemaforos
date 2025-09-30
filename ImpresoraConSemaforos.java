import java.util.concurrent.Semaphore;

public class ImpresoraConSemaforos {

    private static final int NUM_DOCUMENTOS = 5;
    private static final Semaphore[] semaforos = new Semaphore[NUM_DOCUMENTOS];

    static {
        semaforos[0] = new Semaphore(1);
        for (int i = 1; i < NUM_DOCUMENTOS; i++) {
            semaforos[i] = new Semaphore(0);
        }
    }

    private static void usarImpresora(int documentoId) {
        try {
            semaforos[documentoId - 1].acquire();

            System.out.println("Imprimiendo documento #" + documentoId + "...");
            Thread.sleep(300);
            System.out.println("Documento #" + documentoId + " completado.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (documentoId < NUM_DOCUMENTOS) {
                semaforos[documentoId].release();
            }
        }
    }

    public static void main(String[] args) {
        Thread[] hilos = new Thread[NUM_DOCUMENTOS];

        for (int i = 0; i < NUM_DOCUMENTOS; i++) {
            final int id = i + 1;
            hilos[i] = new Thread(() -> usarImpresora(id));
            hilos[i].start();
        }

        for (Thread thread : hilos) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nTodos los documentos han sido impresos.");
    }
}