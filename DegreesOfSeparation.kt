import java.io.File
import java.util.*

fun main(args: Array<String>) {
    // Verificar argumentos
    if (args.size < 2) {
        println("Error: Debe proporcionar dos nombres")
        println("Uso: java -jar DegreesOfSeparation.jar nombre1 nombre2")
        return
    }
    
    val persona1 = args[0]
    val persona2 = args[1]
    
    val grados = calcularGradosSeparacion("input.txt", persona1, persona2)
    println(grados)
}

fun calcularGradosSeparacion(archivo: String, persona1: String, persona2: String): Int {
    val grafo = leerAmistades(archivo)
    return bfs(grafo, persona1, persona2)
}

fun leerAmistades(nombreArchivo: String): MutableMap<String, MutableSet<String>> {
    val grafo = mutableMapOf<String, MutableSet<String>>()
    
    try {
        File(nombreArchivo).forEachLine { linea ->
            val amigos = linea.trim().split(" ")
            if (amigos.size == 2) {
                val amigo1 = amigos[0]
                val amigo2 = amigos[1]
                
                // Agregar relación en ambos sentidos (es simétrica)
                grafo.computeIfAbsent(amigo1) { mutableSetOf() }.add(amigo2)
                grafo.computeIfAbsent(amigo2) { mutableSetOf() }.add(amigo1)
            }
        }
    } catch (e: Exception) {
        println("Error al leer el archivo: ${e.message}")
    }
    
    return grafo
}

fun bfs(grafo: Map<String, Set<String>>, inicio: String, destino: String): Int {
    // Caso especial: misma persona
    if (inicio == destino) {
        return 0
    }
    
    // Si alguna de las personas no está en el grafo
    if (!grafo.containsKey(inicio) || !grafo.containsKey(destino)) {
        return -1
    }
    
    val visitados = mutableSetOf<String>()
    val cola: Queue<Pair<String, Int>> = LinkedList()
    
    // Agregar el nodo inicial con distancia 0
    cola.add(Pair(inicio, 0))
    visitados.add(inicio)
    
    while (cola.isNotEmpty()) {
        val (personaActual, distancia) = cola.poll()
        
        // Explorar todos los amigos de la persona actual
        grafo[personaActual]?.forEach { amigo ->
            if (amigo == destino) {
                return distancia + 1
            }
            
            if (!visitados.contains(amigo)) {
                visitados.add(amigo)
                cola.add(Pair(amigo, distancia + 1))
            }
        }
    }
    
    // No hay conexión
    return -1
}