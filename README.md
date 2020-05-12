# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 8

## Objetivos

* Identificar los diferentes eventos disponibles con el Mouse y las diferentes características de activación y acción de cada uno.
* Reconocer las diferentes interfaces encargadas de escuchar eventos del Mouse y cada uno de sus métodos de implementación para activar y accionar cada uno de estos.
* Aprender la manera de utilizar cada uno de estos eventos y los casos en que estos podrían proporcionarle más interactividad a nuestras interfaces gráficas.

# Antes de Comenzar

Recordando un poco nuestro recorrido tenemos la vista principal con una integración de varios componentes, hemos construido nuestro componente **inicio** a bas de la reutilización de otros componentes encargados de encapsular la estructura de ina plantilla y se utilizo ademas varios enfoques para realizar dicha reutilización.

<div align='center'>
    <img  src='https://i.imgur.com/HQUhJfh.png'>
    <p>Resultado de construcción de componente Inicio</p>
</div>

# Eventos de Mouse

A continuación se mostrara  el uso de los eventos activados desde el mouse y estos están divididos en 3 grandes grupos, estos grupos se derivan de las diferentes interfaces que se ocupan de la escucha de eventos por Mouse:

* **Eventos MouseListener**
* **Eventos MouseMotionListener**
* **MouseWheelListener**

Primero nos concentraremos en identificar las características de todos los métodos implementados por cada uno de los 3 grandes grupos para lo cual vamos a construir el componente **configuraciones**. Dentro de este experimentaremos con cada uno de los eventos. 
Una vez reconocida la función de cada tipo de evento vamos a identificar las partes en nuestro proyecto donde sera util implementar estos eventos.

**Estos eventos tienen la particularidad de que son usados comúnmente para una alta variedad de objetos gráficos a diferencia de los eventos por acción donde casi siempre suele usarse exclusivamente a los botones. Los eventos de Mouse buscan darle interactividad a más objetos gráficos.**

# Construcción Componente Gráfico Configuraciones

En esta sección vamos a ver de manera rápida la construcción del componente **configuraciones** que nos va a servir como medio para poder reconocer las características de cada uno de los eventos. Cuando hablamos de eventos por Mouse un aspecto importante a resaltar es el posicionamiento del puntero, este sera el enfoque con el cual nos concentraremos para identificar las características de cada tipo de evento.

Para lo anterior nuestro componente **Configuraciones** va tomar el papel de una especie de *Paint* donde el usuario podrá interactuar a manera de prueba con el mouse y comprobar que este funciona adecuadamente.

Entonces el componente **Configuraciones** contendrá:

* **Panel de interacción:** Un panel vacío que tendrá las propiedades de interactividad con las que el usuario podrá probar su mouse.
* **Panel de control:** Un panel que contiene una serie de **JLabels** y que le darán al usuario información importante relacionada con el posicionamiento del puntero del mouse y entre varios de sus datos esta:
    * Posición (x,y) una vez se realiza un click
    * Posición (x,y) una vez se presiona el botón de click del mouse.
    * Posición (x,y) una vez se suelta el botón de click del mouse.
    * Posición (x,y) en tiempo real del puntero del Mouse.
    * Posición (x,y) en tiempo real del puntero del Mouse una vez se tiene presionado el botón de click del mouse.
    * La cantidad de Zoom hacia el panel una vez se mueve el scroll del mouse.
    * El estado de **Adentro o Afuera** del mouse con el panel de interacción.

## Construcción del componente

Nos vamos a ubicar en la clase **configuracionesTemplate** ya que el componente previamente se alisto para ser visible a traves de **enrutamiento**. Primero vamos a traer el servicio de **ObjGraficosService** y **RecursosService** para crear nuestros objetos gráficos: 

* **Declaración:**

```javascript
private ObjGraficosService sObjGraficos;
private RecursosService sRecursosService;
```

* **Obtención de servicios:**
```javascript
// Dentro del constructor
sObjGraficos = ObjGraficosService.getService();
sRecursosService = RecursosService.getService();
```

Ahora vamos a darle un cambio de color a nuestro componente: 

```javascript
// Dentro del constructor
this.setBackground(sRecursosService.getColorGrisClaro());
```

## Objetos Decoradores

A continuación vemos la creación de los objetos decoradores necesarios, en este caso solo se creara un **Cursor**  

* **Declaración:**
```javascript
private Cursor cCruz;
```

* **método CrearObjetosDecoradores:**
```javascript
public void crearObjetosDecoradores(){
    this.cCruz = new Cursor(Cursor.CROSSHAIR_CURSOR);
}
```

* **llamada del método dentro del constructor:**
```javascript
// Dentro del constructor
this.crearObjetosDecoradores();
```

## Paneles

A continuación vemos la creación de los paneles necesarios para realizar la funcionalidad.

* **Declaración:**
```javascript
private JPanel pDibujo, pDatos;
```

* **método crearJPanels:**

```javascript
public void crearJPanels(){
    pDibujo = sObjGraficos.construirJPanel(15, 15, 500, 570, Color.WHITE, sRecursosService.getBorderGris());
    pDibujo.setCursor(cCruz);
    this.add(pDibujo);

    pDatos = sObjGraficos.construirJPanel(530, 15, 305, 570, Color.WHITE, sRecursosService.getBorderGris());
    this.add(pDatos);
}
```

* **llamada del método dentro del constructor:**
```javascript
// Dentro del constructor
this.crearJPanels();
```

Hasta el momento nuestro componente se ve asi:

<div align='center'>
    <img  src='https://i.imgur.com/0S3JVNy.png'>
    <p>Componente Configuraciones con los paneles creados</p>
</div>

Se puede notar que el **panel de interacción** es el que contiene el cursor que en este caso sera un cursor de Cruz.

## Labels

A continuación se muestra la creación de los **JLabel** necesarios para la funcionalidad del componente, en este caso como vamos a tener muchos JLabel por crear vamos a dividirlos en 2 categorías:

* **Labels Estáticos:** Son aquellos que representan información que no va a cambiar con el tiempo, pueden ser títulos, indicaciones etc.
* **Labels Dinamicos:** Son aquellos que representan valores y que con seguridad va a cambiar su contenido a medida que el usuarió interactué.

Es decir que vamos a realizar la modularizacion de código con los JLabel a traves de 2 métodos **crearJLabelsEstaticos** y **crearJLabelsDinamicos**.

* **Declaración:**
```javascript
// Declaración Labels Estáticos
private JLabel lTitulo, lTituloPosicion, lTituloEstado;
private JLabel lPOnClickX, lPOnClickY, lPInicialX, lPInicialY, lPFinalX, lPFinalY;
private JLabel lPActualX, lPActualY, lLadoX, lLadoY, lZoom, lEstado; 

// Declaración Labels Dinámicos
private JLabel lPOnClickXValor, lPOnClickYValor, lPInicialXValor, lPInicialYValor;
private JLabel lPFinalXValor, lPFinalYValor, lPActualXValor, lPActualYValor;
private JLabel lLadoXValor, lLadoYValor, lZoomValor, lEstadoValor;
```

* **Método crearJLabelsEstaticos:**
```javascript
public void crearJLabelsEstaticos(){

    // LABEL TITULO -------------------------------------------------------------------------
    lTitulo = sObjGraficos.construirJLabel(
        "Datos de Eventos", (this.pDatos.getWidth() - 200) / 2, 15, 200, 40, null, 
        sRecursosService.getColorAzul(), null, sRecursosService.getFontTitulo(), "c"
    );
    pDatos.add(lTitulo);

    // LABEL TITULO POSICIÓN-----------------------------------------------------------------
    lTituloPosicion = sObjGraficos.construirJLabel(
        "Datos de Posiciones", 20, 60, 180, 40, null, sRecursosService.getColorMorado(), 
        null, sRecursosService.getFontTitulo(), "l"
    );
    pDatos.add(lTituloPosicion);

    // LABEL TITULO ESTADO--------------------------------------------------------------------
    lTituloEstado = sObjGraficos.construirJLabel(
        "Datos de Estado", 20, 440, 180, 40, null, sRecursosService.getColorMorado(), 
        null, sRecursosService.getFontTitulo(), "l"
    );
    pDatos.add(lTituloEstado);

    // LABEL POSICIÓN EN X CON CLICK----------------------------------------------------------
    lPOnClickX = sObjGraficos.construirJLabel(
        "Posición en X al dar click: ", 40, 100, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPOnClickX);

    // LABEL POSICIÓN EN Y CON CLICK----------------------------------------------------------
    lPOnClickY = sObjGraficos.construirJLabel(
        "Posición en Y al dar click: ", 40, 120, 160, 40, null, sRecursosService.getColorAzul(),
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPOnClickY);

    // LABEL POSICIÓN EN X INICIAL----------------------------------------------------------
    lPInicialX = sObjGraficos.construirJLabel(
        "Posición en X inicial: ", 40, 160, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPInicialX);

    // LABEL POSICIÓN EN Y INICIAL----------------------------------------------------------
    lPInicialY = sObjGraficos.construirJLabel(
        "Posición en Y inicial: ", 40, 180, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPInicialY);

    // LABEL POSICIÓN EN X FINAL----------------------------------------------------------
    lPFinalX = sObjGraficos.construirJLabel(
        "Posición en X final: ", 40, 220, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPFinalX);

    // LABEL POSICIÓN EN Y FINAL----------------------------------------------------------
    lPFinalY = sObjGraficos.construirJLabel(
        "Posición en Y final: ", 40, 240, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPFinalY);

    // LABEL POSICIÓN EN X ACTUAL----------------------------------------------------------
    lPActualX = sObjGraficos.construirJLabel(
        "Posición en X actual: ", 40, 280, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPActualX);

    // LABEL POSICIÓN EN Y ACTUAL----------------------------------------------------------
    lPActualY = sObjGraficos.construirJLabel(
        "Posición en Y actual: ", 40, 300, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPActualY);

    // LABEL LADO EN X -------------------------------------------------------------------
    lLadoX = sObjGraficos.construirJLabel(
        "Valor lado X: ", 40, 340, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lLadoX);

    // LABEL LADO EN Y --------------------------------------------------------------------
    lLadoY = sObjGraficos.construirJLabel(
        "Valor lado Y: ", 40, 360, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lLadoY);

    // LABEL ZOOM --------------------------------------------------------------------------
    lZoom = sObjGraficos.construirJLabel(
        "Zoom en el panel: ", 40, 400, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lZoom);

    // LABEL ESTADO ------------------------------------------------------------------------
    lEstado = sObjGraficos.construirJLabel(
        "Estado: ", 40, 480, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lEstado);
}
```

* **Método crearJLabelsDinamicos:**
```javascript
public void crearJLabelsDinamicos(){

    // LABEL POSICIÓN EN X CON CLICK VALOR -----------------------------------------------------
    lPOnClickXValor = sObjGraficos.construirJLabel(
        "0", 200, 100, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPOnClickXValor);

    // LABEL POSICIÓN EN Y CON CLICK VALOR -----------------------------------------------------
    lPOnClickYValor = sObjGraficos.construirJLabel(
        "0", 200, 120, 160, 40, null, sRecursosService.getColorAzul(),
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPOnClickYValor);

    // LABEL POSICIÓN EN X INICIAL VALOR -----------------------------------------------------
    lPInicialXValor = sObjGraficos.construirJLabel(
        "0", 200, 160, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPInicialXValor);

    // LABEL POSICIÓN EN Y INICIAL VALOR -----------------------------------------------------
    lPInicialYValor = sObjGraficos.construirJLabel(
        "0", 200, 180, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPInicialYValor);

    // LABEL POSICIÓN EN X FINAL VALOR -----------------------------------------------------
    lPFinalXValor = sObjGraficos.construirJLabel(
        "0", 200, 220, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPFinalXValor);

    // LABEL POSICIÓN EN Y FINAL VALOR -----------------------------------------------------
    lPFinalYValor = sObjGraficos.construirJLabel(
        "0", 200, 240, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPFinalYValor);

    // LABEL POSICIÓN EN X ACTUAL VALOR -----------------------------------------------------
    lPActualXValor = sObjGraficos.construirJLabel(
        "0", 200, 280, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPActualXValor);

    // LABEL POSICIÓN EN Y ACTUAL VALOR -----------------------------------------------------
    lPActualYValor = sObjGraficos.construirJLabel(
        "0", 200, 300, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lPActualYValor);

    // LABEL LADO EN X VALOR --------------------------------------------------------------
    lLadoXValor = sObjGraficos.construirJLabel(
        "0", 200, 340, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lLadoXValor);

    // LABEL LADO EN Y VALOR ---------------------------------------------------------------
    lLadoYValor = sObjGraficos.construirJLabel(
        "0", 200, 360, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lLadoYValor);

    // LABEL ZOOM VALOR ----------------------------------------------------------------------
    lZoomValor = sObjGraficos.construirJLabel(
        "0", 200, 400, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontPequeña(), "l"
    );
    pDatos.add(lZoomValor);

    // LABEL ESTADO VALOR -------------------------------------------------------------------
    lEstadoValor = sObjGraficos.construirJLabel(
        "NINGUNO", 80, 520, 160, 40, null, sRecursosService.getColorAzul(), 
        null, sRecursosService.getFontTitulo(), "l"
    );
    pDatos.add(lEstadoValor);
}
```

* **llamada de métodos dentro del constructor:**
```javascript
// Dentro del Constructor
this.crearJLabelsEstaticos();
this.crearJLabelsDinamicos();
```

Ahora cuando ejecutamos nuestra aplicación se vera de la siguiente manera:

<div align='center'>
    <img  src='https://i.imgur.com/pSaRPIA.png'>
    <p>Componente Configuraciones con la creación de todos los JLabels</p>
</div>  

Por ultimo y para acabar con la clase **Template** del componente **Configuraciones** vamos a crear los métodos **get** necesarios, en este caso se crearan unicamente para los **JLabel dinámicos**:

```javascript
public JLabel getLPOnClickXValor (){
    return lPOnClickXValor;
}

public JLabel getLPOnClickYValor (){
    return lPOnClickYValor;
}

public JLabel getLPInicialXValor (){
    return lPInicialXValor;
}

public JLabel getLPInicialYValor (){
    return lPInicialYValor;
}

public JLabel getLPFinalXValor (){
    return lPFinalXValor;
}

public JLabel getLPFinalYValor (){
    return lPFinalYValor;
}

public JLabel getLPActualXValor (){
    return lPActualXValor;
}

public JLabel getLPActualYValor (){
    return lPActualYValor;
}

public JLabel getLLadoXValor (){
    return lLadoXValor;
}

public JLabel getLLadoYValor (){
    return lLadoYValor;
}

public JLabel getLZoomValor (){
    return lZoomValor;
}

public JLabel getLEstadoValor (){
    return lEstadoValor;
}
```

# Eventos MouseListener

Vamos a ir al código de nuestra clase **ConfiguracionesComponent** y en la declaración de esta vamos a implementar de la interfaz **MouseListener**:

<div align='center'>
    <img  src='https://i.imgur.com/TELY6J2.png'>
    <p>Implementación de la interfaz MouseListener en la clase ConfiguracionesComponent</p>
</div>

***Nota:** Se debe tener en cuenta la importación de las librerías que soporta la implementación de la interfaz.

Al igual que con la implementación de la interfaz **ActionListener** esta nos va a pedir que se importe ademas los métodos por defecto, por lo general el mismo editor de código va a mostrar el error y va a sugerir la importación automática de estos. 

<div align='center'>
    <img  src='https://i.imgur.com/w02G7EW.png'>
    <p>Métodos implementados por defecto de la interfaz MouseListener</p>
</div>

Se puede observar que esta vez la interfaz **MouseListener** implementa un total de 5 métodos por defectos y cada uno de ellos sera explicado en esta sección.

Antes de continuar con la explicación vamos a adicionar la capacidad de escuchar estos eventos a nuestro panel de interacción **pDibujo**, el proceso es muy parecido a cuando se agregaba un **ActionListener** solo que esta vez se usara el método **addMouseListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseListener(this.configuracionesComponent);
```

Como nuestra clase **configuracionesComponent** implementa de la interfaz **MouseListener** es posible pasar como argumento el objeto de esta clase igual como se haría con una clase que implementa un **ActionListener**.

* **Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.

<div align='center'>
    <img  src='https://i.imgur.com/ENN2Acl.png'>
    <p>Método de configuración entre su construcción y su agregación</p>
</div>

## MouseClicked 

Un evento **MouseClicked** se activa una vez el usuario da un "**Click**" en el objeto gráfico al cual fue proporcionado la adición del **MouseListener** en este caso nuestro panel de interacción **pDibujo**. Cuando hablamos de hacer un "*Click*" hacemos referencia a la acción de **Oprimir** el botón del Mouse (por lo general el botón izquierdo) y luego **Soltarlo**. 

Esto tiene varias implicaciones por ejemplo si se **oprime** el botón del Mouse luego se **arrastra** el Mouse manteniendo oprimido el botón y luego se **suelta**, este ya no va a funcionar. Es decir que para que se considere que se ha dado un **click** el Mouse se debe mantener en la misma posición una ves se **presiona** y se **suelta**.

Vamos a configurar nuestro evento, en este caso queremos que cuando el usuario oprima un **Click** sobre el panel de interacción, se muestre las coordenadas en X,Y de donde estaba el puntero al momento de realizar esa acción para lo cual debemos mostrar esos valores en los labels **lPOnClickXValor** y **lPOnClickYValor**. 

Existen dos métodos para encontrar la coordenada del puntero una vez se realiza una acción, debemos usar el objeto recibido como parámetro **MouseEvent** para hacer esto, los métodos en cuestión son: 

* **e.getX() / e.getY():** Que nos dará la coordenada del puntero cuando se activo la acción en X o en Y con respecto al objeto gráfico que activo la función. 
* **e.getXOnScreen() / e.getYOnScreen():** Que nos dará la coordenada del puntero cuando se activo la acción en X o en Y con respecto a la pantalla total del monitor donde esta el usuario.

Hay que tener esto en cuenta ya que ambos métodos arrojaran datos diferentes debido a la perspectiva como se puede evidenciar en la siguiente imagen:

<div align='center'>
    <img  src='https://i.imgur.com/MZlNIHk.png'>
    <p>Comparación perspectivas entre métodos que nos dan la posición del puntero</p>
</div>

Por ahora solo nos interesa la posición con respecto al objeto gráfico que activa la acción (**pDibujo**) por lo que vamos a usar unicamente **e.getX() / e.getY()**.

* Como queremos Cambiar el texto de los Labels **** vamos a obtenerlos a traves del objeto que referencia a la clase **Template** y su método **get** correspondiente:
```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor();
    this.configuracionesTemplate.getLPOnClickYValor();
}
```

* Le indicamos ahora que vamos a cambiar el texto con el método:
    * **setText():** Que recibe como parámetro un String y representa el nuevo texto que tendrá el Label.

```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor().setText();
    this.configuracionesTemplate.getLPOnClickYValor().setText();
}
```

* Ahora dentro de los paréntesis le vamos a pasar las coordenadas correspondientes:

```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor().setText(e.getX());
    this.configuracionesTemplate.getLPOnClickYValor().setText(e.getY());
}
```

* Sin embargo como el método recibe por parámetro un **String** y nosotros le estamos enviando como argumento un **entero** el IDE o editor de código nos indicara un error. Para pasar de un entero a String simplemente podemos poner la notación de: **numeroEntero + ""** de esta forma Java entenderá que estamos haciendo una suma de caracteres y se hará la conversion automáticamente. 

```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor().setText(e.getX()+"");
    this.configuracionesTemplate.getLPOnClickYValor().setText(e.getY()+"");
}
```

Una vez ejecutamos nuestra aplicación, mostramos nuestro componente **Configuraciones** y damos click en cualquier parte del panel de interacción **pDibujo** se cambiara automáticamente la información de la posición de X,Y cuando se da Click:

<div align='center'>
    <img  src='./resources/MouseClick1.gif'>
    <p>Componente Configuraciones con la implementación de MouseClicked</p>
</div>

Ahora bien puede observar que si se oprime click en cualquier otra parte que no sea el panel de interacción **pDibujo** o si se oprime el botón izquierdo del Mouse pero se suelta en otra posición no se vera reflejado el evento:

***Nota:** En el video no se ve reflejado que el botón se mantiene oprimido pero como ya se explico previamente es de esa forma en que el evento **MouseClicked** deja de funcionar.*

<div align='center'>
    <img  src='./resources/MouseClick2.gif'>
    <p>Forma incorrecta de activar el evento MouseClicked</p>
</div>

## MousePressed

Un evento **MousePressed** se activa una vez el usuario presiona el botón izquierdo del Mouse, no ve la necesidad de saber cuando  este es soltado ni si se ha movido el mouse posteriormente a la presionada del botón, este solo se enfoca netamente al momento en que se presiono el botón del Mouse. La única condición de activación es que el puntero se encuentre dentro del objeto gráfico que tiene adicionado el objeto **MouseListener** al momento de presionar el botón del Mouse, en este caso el panel de interacción **pDibujo**.

Vamos a configurar este método y esta vez queremos que al presionar el botón del Mouse cambie la información de los Labels **LPInicialXValor y LPInicialYValor**:

```javascript
public void mousePressed(MouseEvent e) {
    this.configuracionesTemplate.getLPInicialXValor().setText(e.getX()+"");
    this.configuracionesTemplate.getLPInicialYValor().setText(e.getY()+"");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MousePress.gif'>
    <p>Componente Configuraciones con la implementación de MousePressed</p>
</div>

En el anterior video se ven varias particularidades:
* Siempre se va a ver reflejado el evento primero con el **MousePressed** ya que las milésimas de segundo en las que el usuario se demora en soltar el botón del mouse retrasa un poco al **MouseClicked**.
* Si se oprime el botón del Mouse y se espera unos segundos sin mover el mouse se va a ver reflejado primero el evento en el **MousePressed** y hasta que se suelte el botón del mouse se vera en el **MouseClicked**.
* Si se oprime el botón del Mouse y mientras se mantiene presionado se mueve el Mouse solo se vera reflejado el evento en el **MousePressed.**.
* Si se oprime el botón afuera del panel de interacción **pDibujo** no se vera reflejado ningún evento.

## MouseReleased

Un evento **MouseReleased** se activa una vez el usuario suelta el botón del Mouse siempre y cuando este haya sido presionado previamente, no ve la necesidad de saber en que momento se oprimió el botón ni si se ha movido el mouse anteriormente a la hora de soltar el botón, este solo se enfoca netamente al momento en que se ha soltado el botón del Mouse. Si este se suelta en alguna parte afuera del panel de interacción **pDibujo** puede funcionar siempre y cuando se haya presionado previamente dentro de el.

Vamos a configurar este método y esta vez queremos que al soltar el botón del Mouse cambie la información de los Labels **LPFinalXValor y LPFinalYValor**:
```javascript
public void mouseReleased(MouseEvent e) {
    this.configuracionesTemplate.getLPFinalXValor().setText(e.getX()+"");
    this.configuracionesTemplate.getLPFinalYValor().setText(e.getY()+"");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MouseReleased.gif'>
    <p>Componente Configuraciones con la implementación de MouseReleased</p>
</div>

En el anterior video se ven varias particularidades:
* Si se presiona y suelta el botón del Mouse en la misma posición (Hacer un Click), los tres eventos van a reflejar las mismas coordenadas.
* Si se presiona el botón del Mouse y se arrastra el Mouse mientras se tiene presionado y luego se suelta se vera reflejado el evento **MousePressed** y el evento **MouseReleased** pero no el evento **MouseClicked**.
* Si se presiona el botón dentro del panel de interacción **pDibujo** y se suelta el botón del Mouse cuando este esta afuera del panel de interacción se vera reflejado el evento **MouseReleased** aun asi.
* Si se oprime el botón afuera del panel de interacción **pDibujo** no se vera reflejado ningún evento.

## MouseEntered

Este evento se activa una vez el usuario entra al objeto gráfico que agrego al **MouseListener**, en este caso el panel de interacción **pDibujo** a traves del puntero del Mouse. No tiene la necesidad de oprimir ningún botón del Mouse, el simple hecho de que este sobre el objeto gráfico activa este evento.

Vamos a configurar este método y esta vez queremos que al entrar al panel **pDibujo** cambie el valor de **lEstadoValor** para indicar que el estado ahora es que esta *ADENTRO*:

```javascript
public void mouseEntered(MouseEvent e) {
    this.configuracionesTemplate.getLEstadoValor().setText("ADENTRO");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MouseEntered.gif'>
    <p>Componente Configuraciones con la implementación de MouseEntered</p>
</div>

Podemos ver que una vez el usuario ingresa con el puntero del mouse al panel de interacción **pDatos** el estado en el panel de datos pasa de **NINGUNO** a **ADENTRO**, sin embargo este no parece cambiar más, se queda en el ultimo estado incluso si vuelve a salir y entrar de nuevo. Esto es por que aun no se ha configurado el evento en caso de que se salga del objeto gráfico.

## MouseExited

Este evento se activa una vez el usuario sale del objeto gráfico que agrego al **MouseListener**, en este caso el panel de interacción **pDibujo** a traves del puntero del Mouse siempre y cuando haya entrado en el previamente. No tiene la necesidad de oprimir ningún botón del Mouse, el simple hecho de que este salga del objeto gráfico activa este evento.

Vamos a configurar este método y esta vez queremos que al salir del panel **pDibujo** cambie el valor de **lEstadoValor** para indicar que el estado ahora es que esta *AFUERA*:
```javascript
@Override
public void mouseExited(MouseEvent e) {
    this.configuracionesTemplate.getLEstadoValor().setText("AFUERA");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MouseExited.gif'>
    <p>Componente Configuraciones con la implementación de MouseExited</p>
</div>

Como se observa una vez se abre el componente **Configuraciones** el estado esta en **NINGUNO**, cada vez que se entra al panel de interacción **pDibujo** el estado cambia a **Adentro** y una vez se sale del panel el estado ahora es **Afuera**.

# MouseMotionListener

Este grupo de eventos para el Mouse da unas características especiales, ya que este grupo de eventos esta enfocado en la muestra de información en tiempo real de acciónes con el Mouse, este grupo de eventos es util para acciones como las de arrastrar objetos o mostrar dibujar en tiempo real cosas por pantalla.

Vamos a ir al código de nuestra clase **ConfiguracionesComponent** y en la declaración de esta vamos a implementar la interfaz **MouseMotionListener**. Una ventaja con uso de interfaces para implementar es que una clase puede **implementar varias interfaces** al tiempo, esto no es posible con la herencia ya que en java no existe **Herencia Multiple**.

<div align='center'>
    <img  src='https://i.imgur.com/RlbxiCv.png'>
    <p>Implementación de la interfaz MouseMotionListener en la clase ConfiguracionesComponent</p>
</div>

***Nota:** Se debe tener en cuenta la importación de la librería que soporta la implementación de la interfaz.*

Al igual que con la implementación de las demás interfaces esta nos va a pedir que se importe ademas los métodos por defecto, por lo general el mismo editor de código va a mostrar el error y va a sugerir la importación automática de estos. 

<div align='center'>
    <img  src='https://i.imgur.com/SowJIrh.png'>
    <p>Métodos implementados por defecto de la interfaz MouseMotionListener</p>
</div>

Se puede observar que esta vez la interfaz **MouseMotionListener** implementa un total de 2 métodos por defecto y cada uno de ellos sera explicado en esta sección.

Antes de continuar con la explicación vamos a adicionar la capacidad de escuchar estos eventos a nuestro panel de interacción **pDibujo**, esta vez se usara el método **addMouseMotionListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseMotionListener(this.configuracionesComponent);
```

Como nuestra clase **configuracionesComponent** implementa de la interfaz **MouseMotionListener** es posible pasar como argumento el objeto de esta clase.

* **Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.

## MouseMoved

Este evento se activa por cada vez que el usuario mueve el puntero del Mouse y este se encuentra dentro del objeto gráfico al cual se le adiciono el **MouseMotionListener**, en este caso el panel de interacción **pDibujo**. No tiene la necesidad de oprimir el botón del mouse, con el hecho de estar dentro del objeto gráfico y moverse este evento se va a activar.

Vamos a configurar este método y esta vez queremos que cada vez que el usuario este moviendo el Mouse dentro del panel **pDibujo** actualice la posición actual en tiempo real del Mouse y para eso vamos a cambiar la información de los label **lPActualXValor y lPActualYValor**:
```javascript
public void mouseMoved(MouseEvent e) {
    this.configuracionesTemplate.getLPActualXValor().setText(e.getX()+"");
    this.configuracionesTemplate.getLPActualYValor().setText(e.getY()+"");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MouseMoved.gif'>
    <p>Componente Configuraciones con la implementación de MouseMoved</p>
</div>

## MouseDragged

Este evento se activa una vez oprimimos el botón del Mouse dentro del objeto gráfico que tiene agregado el **MouseMotionListener** en este caso el panel de interacción **pDibujo** y este se mantiene oprimido mientras se mueve el puntero del Mouse, y termina de accionarse una vez se suelta el botón del Mouse. Se debe oprimir el botón o de lo contrario no funcionará este evento.

Vamos a configurar este método y esta vez queremos que cada vez que el usuario oprima el botón del Mouse y empiece a mover el Mouse actualice la posición actual en tiempo real del Mouse y para eso vamos a cambiar la información de los label **lLadoXValor y lLadoYValor**:
```javascript
public void mouseDragged(MouseEvent e) {
    this.configuracionesTemplate.getLLadoXValor().setText(e.getX()+"");
    this.configuracionesTemplate.getLLadoYValor().setText(e.getY()+"");
}
```

Una vez ejecutamos nuestra aplicación podemos darnos cuenta la función de este evento:

<div align='center'>
    <img  src='./resources/MouseDragged.gif'>
    <p>Componente Configuraciones con la implementación de mouseDragged</p>
</div>

Del anterior video se puede notar algunas particularidades:
* Una vez el usuario oprime el botón del mouse dentro del panel de interacción **pDibujo** y empieza a arrastrar el mouse sin soltar aun el botón deja de funcionar el evento **MouseMoved** y empieza a funcionar el evento **MouseDragged**.
* Una vez el usuario suelta el botón del Mouse  y esta dentro del panel de interacción **pDibujo** dejara de funcionar el evento **MouseDragged** y empieza a funcionar el evento **MouseMoved**.
* Si el usuario presiono el botón del Mouse dentro del panel de interacción **pDibujo** previamente y se mantiene presionado el evento **MouseDragged** seguirá funcionando incluso si se sale del panel todo mientras se siga manteniendo presionado el botón del mouse.
* Si se presiona desde afuera del panel de interacción **pDibujo** ningún evento tendrán efecto.

# MouseWheelListener

Este grupo de eventos se concentra unicamente en el movimiento del **scroll del Mouse** (la rueda que por lo general tiene el Mouse en medio de los dos botones). Este evento se fija unicamente del movimiento de este Sroll, es decir que no tiene en cuenta cuando se **hace un click** con el Scroll, solo cuando el usuario lo mueve hacia arriba o hacia abajo.

Vamos a ir al código de nuestra clase **ConfiguracionesComponent** y en la declaración de esta vamos a implementar la interfaz **MouseWheelListener**. Recordemos que esto es posible ya que una clase puede **implementar varias interfaces** al tiempo.

<div align='center'>
    <img  src='https://i.imgur.com/RBhh8Co.png'>
    <p>Implementación de la interfaz MouseWheelListener en la clase ConfiguracionesComponent</p>
</div>

Como con todas las demás interfaces debemos implementar los métodos por defecto que trae esta interfaz.

<div align='center'>
    <img  src='https://i.imgur.com/ylbQ4Ao.png'>
    <p>Métodos implementados por defecto de la interfaz MouseWheelListener</p>
</div>

Se puede observar que esta vez la interfaz **MouseWheelListener** implementa un total de 1 método por defecto y este sera explicado en esta sección.

Antes de continuar con la explicación vamos a adicionar la capacidad de escuchar estos eventos a nuestro panel de interacción **pDibujo**, esta vez se usara el método **addMouseWheelListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseWheelListener(this.configuracionesComponent);
```

Como nuestra clase **configuracionesComponent** implementa de la interfaz **MouseWheelListener** es posible pasar como argumento el objeto de esta clase.

* **Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.

## MouseWheelMoved

Este evento se activa una vez el usuario mueve el **Scroll del Mouse**, es decir lo desliza hacia arriba o hacia abajo, para que este evento pueda ser activado debe estar dentro del objeto gráfico que tiene agregado al **MouseWheelListener** en este caso el panel de interacción **pDatos**. Si se realiza afuera de este no tendrá ningún efecto.

Vamos a configurar este método y esta vez queremos que cada vez que el usuario deslice el Scroll hacia arriba o hacia abajo vamos a realizar la simulación de acercamiento y alejamiento, esto lo haremos cambiando el valor del label **lZoomValor**. Cuando el usuario mueva el Scroll hacia arriba va a aumentar el numero del zoom mientras que cuando el usuario mueva hacia abajo este numero va a disminuir.

Para saber si el usuario esta subiendo o bajando el scroll del Mouse debemos usar el método:
* **e.getWheelRotation():** Que nos retorna un numero entero.
    * Si el usuario esta subiendo con el scroll va a retornar el numero **-1**.
    * Si el usuario esta bajando con el scroll va a retornar el numero **1**.

Con este método podemos realizar nuestra configuración:
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    if(e.getWheelRotation() == -1)
        // Acción Cuando el usuario sube con el Scroll
    if(e.getWheelRotation() == 1)
        // Acción Cuando el usuario baja con el Scroll
}
```

* Como queremos cambiar el valor del label **lZoomValor**, vamos a obtenerlo e indicarle que cambiaremos su contenido con el método **setText**:
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText();
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText();
}
```

* Ahora debemos indicarle en cada caso que se le va a sumar o restar el valor de 1, pero para eso debemos obtener el valor actual que tiene el label **lZoomValor**, ya que si solo le decimos que sume o reste 1 el valor  del Label **lZoomValor** solo podría ser 1 o -1.
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(
            this.configuracionesTemplate.getLZoomValor().getText()
        );
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(
            this.configuracionesTemplate.getLZoomValor().getText()
        );
}
```

* Como lo que queremos hacer es sumar o restar un 1 al contenido actual del Label **lZoomValor** debemos pasar el dato a entero ya que el método **getText()** nos retorna el valor en forma de String y de esta forma no se puede realizar una suma o resta entre números:
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(
            Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText())
        );
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(
            Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText()) 
        );
}
```

* Podemos notar que hasta el momento en ambos casos estamos haciendo el mismo proceso, podemos entonces encapsular el proceso de obtención y conversion del zoom actual en una variable antes de que empiecen los if para que no quede tan confuso nuestro código:

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual);
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual);
}
```

* Ahora si vamos a sumar y restar un 1 según sea el caso:

 ```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1);
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1);
}
```

* Sin embargo el método **setText** nos exige enviar como argumento un String asi que hacemos la conversion como se menciono antes:
 ```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

Si ejecutamos nuestra aplicación podemos ver nuestro evento funcionando:

<div align='center'>
    <img  src='./resources/MouseWheel.gif'>
    <p>Componente Configuraciones con la implementación de mouseWheelMoved</p>
</div>

Sin embargo vemos que si bajamos demasiado con el scroll este empieza a mostrarnos números negativos y esto en términos de zoom no es correcto, por lo que seria bueno dejar un limite en caso de bajar el scroll para que no pueda pasar a menos de 0:

* primero debemos indicar que vamos a realizar otra condición en el segundo if ya que este nos indica que el usuario esta bajando con el **Scroll** esto se hace con el separador **&&**.

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
    if(
        e.getWheelRotation() == 1 &&
    )
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

* Ahora queremos obtener el valor actual del zoom para poder compararlos posteriormente para obtenerlo recordemos que lo hacemos a traves del objeto de la clase **Template** y su método **get** correspondiente seguido del método **getText()**:

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
    if(
        e.getWheelRotation() == 1 &&
        this.configuracionesTemplate.getLZoomValor().getText()
    )
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```
* Como queremos hacer una comparación numérica (ver que este sea mayor a 0) debemos convertir el texto que nos devuelve el **getText** a entero:

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
    if(
        e.getWheelRotation() == 1 &&
        Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText())
    )
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

* Ahora solo nos hace falta realizar la comparación: 

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
        int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
        if(e.getWheelRotation() == -1)
            this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
        if(
            e.getWheelRotation() == 1 &&
            Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText()) > 0
        )
            this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
    }
```

En la anterior condición le estamos indicando al programa que solo cuando el Zoom sea mayor a 0 este pueda ser restado, de lo contrario este sera 0 y no podrá entrar a la condición.

Si ejecutamos de nuevo nuestra aplicación notamos que se cumple lo que queríamos:

<div align='center'>
    <img  src='./resources/MouseWheelTrue.gif'>
    <p>Restricción en la resta del zoom</p>
</div>

# Resultado 

Si llegaste hasta aquí **!Felicidades!** has aprendido las características principales de los diferentes eventos del mouse. Has aprendido en que momentos se activa y deja de funcionar cada uno de ellos ademas de algunas particularidades que tienen alguno de ellos. En la siguiente clase vamos a implementar alguno de estos eventos al resto de nuestro proyecto para darle una interactividad mayor a nuestra interfaz gráfica de usuario.

# Actividad 

Revisa los diferentes tipos de eventos poniendo comentarios a la funcionalidad, particularidades y en que casos con funciona cada uno de estos.