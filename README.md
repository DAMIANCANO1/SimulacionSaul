# SimulacionSaul

Aplicacion Java con Swing para generar numeros pseudoaleatorios y ejecutar pruebas estadisticas. No utiliza Maven, Gradle ni dependencias externas.

La interfaz utiliza una presentacion institucional verde, tablas legibles y paneles separados para datos, operaciones y pruebas.

## Solucion si IntelliJ no muestra el boton de ejecutar

1. Abra el proyecto como proyecto desde la carpeta `SimulacionSau`.
2. Seleccione **Archivo > Configuración del proyecto > SDK** y elija una instalación JDK.
3. Marque `src` como carpeta de fuentes si IntelliJ no lo reconoce.
4. Use la configuración **Ejecutar Simulación** o ejecute la clase `simulacion.Main`.

## Metodos de generacion

- Congruencial aditivo
- Congruencial multiplicativo
- Congruencial cuadratico
- Productos medios
- Cuadrados medios
- Multiplicador constante
- Congruencial lineal

## Pruebas estadisticas

- Prueba de medias
- Prueba de varianza
- Prueba Chi-cuadrada
- Prueba Kolmogorov-Smirnov
- Corridas arriba y abajo
- Corridas arriba y abajo de la media
- Prueba de Poker
- Prueba de huecos

## Requisitos

- JDK 8 o superior
- NetBeans con soporte para proyectos Ant

## Abrir y ejecutar en NetBeans

1. Abra NetBeans.
2. Seleccione **Archivo > Abrir proyecto**.
3. Seleccione la carpeta `SimulacionSaul`.
4. Ejecute el proyecto con **F6** o **Ejecutar > Ejecutar proyecto**.
5. La clase principal es `simulacion.Main`.

## Ejecutar por terminal

```bat
ant clean jar
java -jar dist\SimulacionSaul.jar
```

Tambien puede compilarse solo con `javac`:

```bat
javac -encoding UTF-8 -d build\classes src\simulacion\*.java
java -cp build\classes simulacion.Main
```

## Clonar

```bat
git clone https://github.com/DAMIANCANO1/SimulacionSaul.git
cd SimulacionSaul
```
