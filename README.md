# Nombre: Gabriel Chi Vidal

# Monitores y Semáforos

# Introduccion al Problema de Solucion de Carrera
En el mundo de la programación, sobre todo cuando una aplicación hace varias cosas a la vez, hay un problema muy común y a veces difícil de ver: las "condiciones de carrera". Suena complicado, pero la idea es bastante sencilla si la piensas con un ejemplo.

Imagina que estás comprando por internet la última entrada que queda para un concierto.  Al mismo tiempo, otra persona también le da clic para comprarla. Como fue casi en el mismo instante, el sistema revisa, ve que hay una entrada disponible y les dice a los dos que pueden comprar. Ambos pagan y, de repente, ¡hay un lío! La misma entrada se vendió dos veces.

Eso, justo eso, es una condición de carrera. Ocurre cuando varios hilos o procesos intentan leer y cambiar el mismo recurso compartido (como una variable o un archivo) al mismo tiempo. El resultado final depende de la suerte, del orden exacto en que se ejecutó cada uno, algo que es imposible de predecir.

A ese pedazo de código donde todos "se pelean" por el recurso se le llama la sección crítica. Si no proteges bien esa parte, acabas con datos corruptos y errores rarísimos que son una pesadilla para depurar. Por eso, entender cómo manejar esto es clave si quieres construir aplicaciones que de verdad sean estables y confiables.


# Como funcionan los Semaforos y Monitores

Para evitar las condiciones de carrera y proteger nuestras "secciones críticas", usamos mecanismos de sincronización. Dos de los más importantes son los semáforos y los monitores.

Un semáforo es como el encargado de una sala con un número limitado de sillas. Funciona con un contador interno cuando un hilo quiere entrar a la sección crítica, le pide permiso al semáforo con la operación wait() o acquire() y si el contador es mayor que cero, el semáforo le da permiso, decrementa el contador y el hilo puede continuar, si el contador es cero, significa que no hay "sillas" disponibles, y el hilo debe esperar afuera hasta que otro hilo que ya está adentro termine, libere su lugar y avise al semáforo (puede ser con la operación signal() o release()), lo que incrementa el contador. Un semáforo con el contador inicializado en 1 se llama semáforo binario y se usa para garantizar que solo un hilo a la vez acceda al recurso, lo que se conoce como exclusión mutua.

Un monitor, por otro lado, es un enfoque de más alto nivel, muy común en lenguajes orientados a objetos como Java. Un monitor es básicamente un "guardián" que protege un objeto o un bloque de código, un ejemplo podria ser un baño público con una sola llave, solo una persona puede tener la llave y entrar a la vez. El monitor funciona igual, este se asegura de que solo un hilo pueda ejecutar un bloque de código con "synchronized" sobre un objeto específico en un momento dado. Cuando un hilo entra a un método o bloque sincronizado, adquiere un "lock" sobre ese objeto. Cualquier otro hilo que intente acceder a cualquier bloque sincronizado del mismo objeto tendrá que esperar hasta que el primer hilo termine y libere el cerrojo. Esto hace todo mas simple la programación, ya que el que esta haciendolo no tiene que manejar las operaciones wait y signal manualmente, y reduce su riesgo de cometer errores.

# Capturas de Pantalla

![Captura de Impresora con Semaforo](/assets/impresoraconsemaforo.png)
![Captura de Impresora con Monitor](/assets/impresoraconmonitor.png)

# Conclusion

Después de ver el problema con la impresora, mi conclusión es que esto de la programación concurrente te obliga a ser súper cuidadoso. Si dejas que los hilos corran a su aire, es garantía de que los datos van a terminar hechos un lío. Quedó clarísimo.

La idea clave para evitar ese desastre es la "exclusión mutua", que no es más que obligar a los hilos a hacer fila y a que solo uno pueda usar el recurso a la vez, para lograrlo vimos dos caminos: los semáforos y los monitores.

La verdad, los semáforos te dan muchísimo control, casi como si manejaras el tráfico a mano. Pero ese es su peligro: toda la responsabilidad cae sobre ti. Si se te olvida dar luz verde, atascas todo para siempre. Es muy fácil cometer un error.

Por otro lado, usar monitores (con synchronized en Java) se siente diferente, mucho más seguro. Es como si el objeto ya supiera que tiene que poner un candado y quitarlo después. Te quita un peso de encima y el código es más fácil de leer.

Al final, aunque con las dos herramientas puedes llegar al mismo resultado, para mí la elección es obvia. Los monitores te dan una tranquilidad que los semáforos no. Si quieres hacer un programa que sea estable de verdad, parece el camino más inteligente.