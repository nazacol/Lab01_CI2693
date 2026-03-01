import java.io.File
import java.util.*

// Nombre del archivo HARD CODED 
const val ARCHIVO_AMISTADES = "input.txt"

fun main(args: Array<String>) {
    // Verificar argumentos
    if (args.size < 2) {
        println("Error: Debe proporcionar dos nombres")
        println("Uso: java -jar DegreesOfSeparation.jar nombre1 nombre2")
        return
    }
    
    val persona1 = args[0]
    val persona2 = args[1]
    
    // Usamos la constante con el nombre hard coded
    val grados = calcularGradosSeparacion(persona1, persona2)
    println(grados)
}

fun calcularGradosSeparacion(persona1: String, persona2: String): Int {
    val grafo = leerAmistades()
    return bfs(grafo, persona1, persona2)
}

fun leerAmistades(): MutableMap<String, MutableSet<String>> {
    val grafo = mutableMapOf<String, MutableSet<String>>()
    
    try {
        // Aquí está HARD CODED el nombre del archivo
        File(ARCHIVO_AMISTADES).forEachLine { linea ->
            val amigos = linea.trim().split(" ")
            if (amigos.size == 2) {
                val amigo1 = amigos[0]
                val amigo2 = amigos[1]
                
                grafo.computeIfAbsent(amigo1) { mutableSetOf() }.add(amigo2)
                grafo.computeIfAbsent(amigo2) { mutableSetOf() }.add(amigo1)
            }
        }
    } catch (e: Exception) {
        println("Error al leer el archivo $ARCHIVO_AMISTADES: ${e.message}")
    }
    
    return grafo
}

fun bfs(grafo: Map<String, Set<String>>, inicio: String, destino: String): Int {
    if (inicio == destino) {
        return 0
    }
    
    if (!grafo.containsKey(inicio) || !grafo.containsKey(destino)) {
        return -1
    }
    
    val visitados = mutableSetOf<String>()
    val cola: Queue<Pair<String, Int>> = LinkedList()
    
    cola.add(Pair(inicio, 0))
    visitados.add(inicio)
    
    while (cola.isNotEmpty()) {
        val (personaActual, distancia) = cola.poll()
        
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
    
    return -1
}
