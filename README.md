# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 8

## Objetivos

* Identificar los diferentes eventos de Mouse disponibles con sus respectivas características, forma de activación, limites y casos particulares.
* Reconocer las diferentes interfaces encargadas de escuchar eventos del Mouse y cada uno de sus métodos de implementación para activar y manipular los eventos.
* Identificar algunos métodos auxiliares relacionados con la posición del puntero del mouse o el movimiento del Scroll que ayudan con la manipulación de eventos.

# Antes de Comenzar

### **Recordatorio**
Recordando el recorrido, se tiene la vista principal con una integración de varios componentes, se ha construido el componente **inicio** a base de la **reutilización de otros componentes** encargados de encapsular la estructura de una plantilla y se utilizó ademas varios enfoques para realizar dicha reutilización.

<div align='center'>
    <img  src='https://i.imgur.com/6LcW9rb.png'>
    <p>Resultado de construcción de componente Inicio</p>
</div>

# Eventos de Mouse

A continuación se mostrará  el uso de los eventos del Mouse y estos están divididos en 3 grandes grupos, estos grupos se derivan de las diferentes interfaces que se ocupan de la escucha de eventos por Mouse:

* **Eventos MouseListener**
* **Eventos MouseMotionListener**
* **Eventos MouseWheelListener**

Esta sesión se concentra en identificar las características de todos los métodos implementados por cada uno de los 3 grandes grupos para lo cual se va a construir el componente **configuraciones**. Dentro de este, se experimentará con cada uno de los eventos y asi reconocer cuando usar cada evento, como se activan, en que momento dejan de funcionar, casos particulares etc. 
Una vez reconocida la función de cada tipo de evento en la siguiente lección se identificarán las partes en el proyecto donde será util implementar estos eventos para darle más interactividad a la aplicación.

***Nota:** Estos eventos tienen la particularidad de ser usados comúnmente para una alta variedad de objetos gráficos a diferencia de los eventos por acción donde casi siempre suele usarse exclusivamente a los botones. Los eventos de Mouse buscan darle interactividad a más objetos gráficos.*

# Construcción Componente Gráfico Configuraciones

En esta sección se va a ver de manera rápida la construcción del componente **configuraciones** que va a servir como medio para poder reconocer las características de cada uno de los eventos. Cuando hablamos de eventos por Mouse un aspecto importante a resaltar es el **posicionamiento del puntero**, este sera el enfoque con el cual se va a identificar las características de cada tipo de evento.

Para lo anterior el componente **Configuraciones** va tomar el papel de una herramienta para configuración de la sensibilidad y funcionamiento del Mouse, donde el usuario podrá interactuar a manera de prueba con el mouse sobre un panel y comprobar que este funciona adecuadamente.

El componente **Configuraciones** contendrá:

* **Panel de interacción:** Un panel vacío que tendrá las propiedades de interactividad con las que el usuario podrá probar su Mouse.
* **Panel de control:** Un panel que contiene una serie de **JLabels** y que le darán al usuario información importante relacionada con el posicionamiento del puntero del mouse y entre varios de sus datos esta:
    * Posición (x,y) una vez se realiza un click.
    * Posición (x,y) una vez se presiona el botón izquierdo del mouse.
    * Posición (x,y) una vez se suelta el botón izquierdo del mouse.
    * Posición (x,y) en tiempo real del puntero del Mouse.
    * Posición (x,y) en tiempo real del puntero del Mouse una vez se tiene presionado el botón izquierdo del mouse.
    * La cantidad de Zoom hacia el panel una vez se mueve el scroll del mouse.
    * El estado de **Adentro o Afuera** del mouse con respecto al panel de interacción.

## Construcción de objetos gráficos dentro de Configuraciones

El componente **Configuraciones** puede ser visible en pantalla gracias a la gestión de **navegación** realizada en previas lecciones por lo que se puede directamente iniciar con la construcción del componente, en la clase **configuracionesTemplate** primero se van a obtener los servicios **ObjGraficosService** y **RecursosService** para la creación los objetos gráficos: 

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

Ahora se da un cambio de color al componente: 
```javascript
// Dentro del constructor
this.setBackground(sRecursosService.getColorGrisClaro());
```

## Objetos Decoradores

A continuación se muestra la creación de los objetos decoradores necesarios, en este caso solo se creara un **Cursor**:

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

A continuación se muestra la creación de los paneles necesarios para realizar la funcionalidad del componente.

* **Declaración:**
```javascript
private JPanel pDibujo, pDatos;
```

* **método crearJPanels:**
```javascript
public void crearJPanels(){
  // PANEL P-DIBUJO---------------------------------
  pDibujo = sObjGraficos.construirJPanel(
    15, 15, 500, 570, 
    Color.WHITE, 
    sRecursosService.getBorderGris()
  );
  pDibujo.setCursor(cCruz);
  this.add(pDibujo);

  // PANEL P-DATOS---------------------------------
  pDatos = sObjGraficos.construirJPanel(
    530, 15, 305, 570, 
    Color.WHITE, 
    sRecursosService.getBorderGris()
  );
  this.add(pDatos);
}
```
Note que al panel de interacción pDibujo se le agrega el cursor previamente creado mediante el método de configuración **setCursor**.

* **llamada del método dentro del constructor:**
```javascript
// Dentro del constructor
this.crearJPanels();
```

Hasta el momento el componente se ve asi:

<div align='center'>
    <img  src='https://i.imgur.com/NSsrIgq.png'>
    <p>Componente Configuraciones con los paneles creados</p>
</div>

## Labels

A continuación se muestra la creación de los **JLabel** necesarios para la funcionalidad del componente, en este caso se va crear una gran cantidad de JLabels, así que estos serán divididos en 2 categorías:

* **Labels Estáticos:** Son aquellos que representan información que no va a cambiar con el tiempo, pueden ser títulos, indicaciones etc.
* **Labels Dinámicos:** Son aquellos que representan valores y que con seguridad va a cambiar su contenido a medida que el usuarió interactué.

Es decir que se va a realizar la modularizacion de código con los JLabel a través de 2 métodos **crearJLabelsEstaticos** y **crearJLabelsDinamicos**.

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
    "Datos de Eventos",
    (this.pDatos.getWidth() - 200) / 2, 15, 200, 40,
    null, null,
    sRecursosService.getFontTitulo(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "c"
  );
  pDatos.add(lTitulo);

  // LABEL TITULO POSICIÓN-----------------------------------------------------------------
  lTituloPosicion = sObjGraficos.construirJLabel(
    "Datos de Posiciones",
    20, 60, 180, 40,
    null, null,
    sRecursosService.getFontTitulo(),
    null,
    sRecursosService.getColorMorado(),
    null,
    "l"
  );
  pDatos.add(lTituloPosicion);

  // LABEL TITULO ESTADO--------------------------------------------------------------------
  lTituloEstado = sObjGraficos.construirJLabel(
    "Datos de Estado",
    20, 440, 180, 40,
    null, null,
    sRecursosService.getFontTitulo(),
    null,
    sRecursosService.getColorMorado(),
    null,
    "l"
  );
  pDatos.add(lTituloEstado);

  // LABEL POSICIÓN EN X CON CLICK----------------------------------------------------------
  lPOnClickX = sObjGraficos.construirJLabel(
    "Posición en X al dar click: ",
    40, 100, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPOnClickX);

  // LABEL POSICIÓN EN Y CON CLICK----------------------------------------------------------
  lPOnClickY = sObjGraficos.construirJLabel(
    "Posición en Y al dar click: ",
    40, 120, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPOnClickY);

  // LABEL POSICIÓN EN X INICIAL----------------------------------------------------------
  lPInicialX = sObjGraficos.construirJLabel(
    "Posición en X inicial: ",
    40, 160, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPInicialX);

  // LABEL POSICIÓN EN Y INICIAL----------------------------------------------------------
  lPInicialY = sObjGraficos.construirJLabel(
    "Posición en Y inicial: ",
    40, 180, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPInicialY);

  // LABEL POSICIÓN EN X FINAL----------------------------------------------------------
  lPFinalX = sObjGraficos.construirJLabel(
    "Posición en X final: ",
    40, 220, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPFinalX);

  // LABEL POSICIÓN EN Y FINAL----------------------------------------------------------
  lPFinalY = sObjGraficos.construirJLabel(
    "Posición en Y final: ",
    40, 240, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPFinalY);

  // LABEL POSICIÓN EN X ACTUAL----------------------------------------------------------
  lPActualX = sObjGraficos.construirJLabel(
    "Posición en X actual: ",
    40, 280, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPActualX);

  // LABEL POSICIÓN EN Y ACTUAL----------------------------------------------------------
  lPActualY = sObjGraficos.construirJLabel(
    "Posición en Y actual: ",
    40, 300, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPActualY);

  // LABEL LADO EN X -------------------------------------------------------------------
  lLadoX = sObjGraficos.construirJLabel(
    "Valor lado X: ",
    40, 340, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lLadoX);

  // LABEL LADO EN Y --------------------------------------------------------------------
  lLadoY = sObjGraficos.construirJLabel(
    "Valor lado Y: ",
    40, 360, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lLadoY);

  // LABEL ZOOM --------------------------------------------------------------------------
  lZoom = sObjGraficos.construirJLabel(
    "Zoom en el panel: ",
    40, 400, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lZoom);

  // LABEL ESTADO ------------------------------------------------------------------------
  lEstado = sObjGraficos.construirJLabel(
    "Estado: ",
    40, 480, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lEstado);
}
```

* **Método crearJLabelsDinamicos:**
```javascript
public void crearJLabelsDinamicos() {
  // LABEL POSICIÓN EN X CON CLICK VALOR -----------------------------------------------------
  lPOnClickXValor = sObjGraficos.construirJLabel(
    "0",
    200, 100, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPOnClickXValor);

  // LABEL POSICIÓN EN Y CON CLICK VALOR -----------------------------------------------------
  lPOnClickYValor = sObjGraficos.construirJLabel(
    "0",
    200, 120, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPOnClickYValor);

  // LABEL POSICIÓN EN X INICIAL VALOR -----------------------------------------------------
  lPInicialXValor = sObjGraficos.construirJLabel(
    "0",
    200, 160, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPInicialXValor);

  // LABEL POSICIÓN EN Y INICIAL VALOR -----------------------------------------------------
  lPInicialYValor = sObjGraficos.construirJLabel(
    "0",
    200, 180, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPInicialYValor);

  // LABEL POSICIÓN EN X FINAL VALOR -----------------------------------------------------
  lPFinalXValor = sObjGraficos.construirJLabel(
    "0",
    200, 220, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPFinalXValor);

  // LABEL POSICIÓN EN Y FINAL VALOR -----------------------------------------------------
  lPFinalYValor = sObjGraficos.construirJLabel(
    "0",
    200, 240, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPFinalYValor);

  // LABEL POSICIÓN EN X ACTUAL VALOR -----------------------------------------------------
  lPActualXValor = sObjGraficos.construirJLabel(
    "0",
    200, 280, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPActualXValor);

  // LABEL POSICIÓN EN Y ACTUAL VALOR -----------------------------------------------------
  lPActualYValor = sObjGraficos.construirJLabel(
    "0",
    200, 300, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lPActualYValor);

  // LABEL LADO EN X VALOR --------------------------------------------------------------
  lLadoXValor = sObjGraficos.construirJLabel(
    "0",
    200, 340, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lLadoXValor);

  // LABEL LADO EN Y VALOR ---------------------------------------------------------------
  lLadoYValor = sObjGraficos.construirJLabel(
    "0",
    200, 360, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lLadoYValor);

  // LABEL ZOOM VALOR ----------------------------------------------------------------------
  lZoomValor = sObjGraficos.construirJLabel(
    "0",
    200, 400, 160, 40,
    null, null,
    sRecursosService.getFontLigera(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
  );
  pDatos.add(lZoomValor);

  // LABEL ESTADO VALOR -------------------------------------------------------------------
  lEstadoValor = sObjGraficos.construirJLabel(
    "NINGUNO",
    80, 520, 160, 40,
    null, null,
    sRecursosService.getFontTitulo(),
    null,
    sRecursosService.getColorAzul(),
    null,
    "l"
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

Al ejecutar la aplicación, el componente **Configuraciones** se vera de la siguiente manera:

<div align='center'>
    <img  src='https://i.imgur.com/TZcDfdl.png'>
    <p>Componente Configuraciones con la creación de todos los JLabels</p>
</div>  

Por ultimo y para acabar con la clase **Template** del componente **Configuraciones** se crean los métodos **get** necesarios, en este caso se crean unicamente para los **JLabel dinámicos**:

```javascript
public JLabel getLPOnClickXValor() { return lPOnClickXValor; }

public JLabel getLPOnClickYValor() { return lPOnClickYValor; }

public JLabel getLPInicialXValor() { return lPInicialXValor; }

public JLabel getLPInicialYValor() { return lPInicialYValor; }

public JLabel getLPFinalXValor() { return lPFinalXValor; }

public JLabel getLPFinalYValor() { return lPFinalYValor; }

public JLabel getLPActualXValor() { return lPActualXValor; }

public JLabel getLPActualYValor() { return lPActualYValor; }

public JLabel getLLadoXValor() { return lLadoXValor; }

public JLabel getLLadoYValor() { return lLadoYValor; }

public JLabel getLZoomValor() { return lZoomValor; }

public JLabel getLEstadoValor() { return lEstadoValor; }
```

# Eventos MouseListener

Dentro de la clase **ConfiguracionesComponent** específicamente en la declaración de la clase se va a implementar de la interfaz **MouseListener**:

<div align='center'>
    <img  src='https://i.imgur.com/TELY6J2.png'>
    <p>Implementación de la interfaz MouseListener en la clase ConfiguracionesComponent</p>
</div>

***Nota:** Se debe tener en cuenta la importación de las librerías que soporta la implementación de la interfaz.*

Al igual que con la implementación de la interfaz **ActionListener** esta va a pedir que se importen todos los métodos por defecto, por lo general el mismo editor de código va a mostrar el error y va a sugerir la importación automática de estos. 

<div align='center'>
    <img  src='https://i.imgur.com/w02G7EW.png'>
    <p>Métodos implementados por defecto de la interfaz MouseListener</p>
</div>

Se puede observar que esta vez la interfaz **MouseListener** implementa un total de 5 métodos por defecto y cada uno de ellos será explicado en esta sección.

Antes de continuar con la explicación se debe adicionar la capacidad de escuchar los eventos tipo **MouseListener** al panel de interacción **pDibujo**, el proceso es muy parecido a cuando se agregaba un **ActionListener** solo que esta vez se usa el método **addMouseListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseListener(this.configuracionesComponent);
```

Como la clase **configuracionesComponent** implementa de la interfaz **MouseListener** es posible pasar como argumento el objeto de esta clase igual como se haría con una clase que implementa un **ActionListener**.

***Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.*

<div align='center'>
    <img  src='https://i.imgur.com/GIXOMS5.png'>
    <p>Método de configuración entre su construcción y su agregación</p>
</div>

## MouseClicked 

Un evento **MouseClicked** se activa una vez el usuario da un "**Click**" en el objeto gráfico al cual fue proporcionado la adición del **MouseListener** en este caso el panel de interacción **pDibujo**. Cuando se menciona hacer un "*Click*" se hace referencia a la acción de **Oprimir** el botón del Mouse (por lo general el botón izquierdo) y luego **Soltarlo**. 

Esto tiene varias implicaciones, por ejemplo si se **oprime** el botón del Mouse, luego se **arrastra** el Mouse manteniendo oprimido el botón y luego se **suelta**, este ya no va a funcionar. Es decir que para que se considere que se ha dado un **click** el Mouse se debe mantener en la misma posición una vez se **presiona** y se **suelta**.

Se procede a configurar el evento, en este caso se quiere que cuando el usuario de un **Click** sobre el panel de interacción **pDibujo**, se muestre las coordenadas en X,Y de donde estaba el puntero al momento de realizar esa acción. Para esto se debe mostrar esos valores en los labels **lPOnClickXValor** y **lPOnClickYValor**. 

Existen dos métodos para encontrar la coordenada del puntero una vez se realiza una acción, es necesario usar el objeto recibido como parámetro **MouseEvent** para hacer esto, los métodos en cuestión son: 

* **e.getX() / e.getY():** Que darán la coordenada del puntero cuando se activo la acción en X o en Y con respecto al objeto gráfico que activo la función. 
* **e.getXOnScreen() / e.getYOnScreen():** Que darán la coordenada del puntero cuando se activo la acción en X o en Y con respecto a la pantalla total del monitor del usuario.

Hay que tener esto en cuenta ya que ambos métodos arrojaran datos diferentes debido a la perspectiva como se puede evidenciar en la siguiente imagen:

<div align='center'>
    <img  src='https://i.imgur.com/9U87jsh.png'>
    <p>Comparación perspectivas entre métodos que nos dan la posición del puntero</p>
</div>

Por ahora solo es de interés la posición con respecto al objeto gráfico que activa la acción (**pDibujo**) por lo que se va a usar unicamente los métodos **e.getX() / e.getY()**.

* Como se quiere cambiar el texto de los Labels **lPOnClickXValor y lPOnClickYValor** se obtienen a través del objeto que referencia a la clase **Template** y sus métodos **get** correspondientes:
```javascript
public void mouseClicked(MouseEvent e) {
  this.configuracionesTemplate.getLPOnClickXValor();
  this.configuracionesTemplate.getLPOnClickYValor();
}
```

* Se le indica ahora que va a cambiar el texto con el método:
    * **setText():** Que recibe como parámetro un String y representa el nuevo texto que tendrá el Label.
```javascript
public void mouseClicked(MouseEvent e) {
  this.configuracionesTemplate.getLPOnClickXValor().setText();
  this.configuracionesTemplate.getLPOnClickYValor().setText();
}
```

* Ahora dentro de los paréntesis se pasan las coordenadas correspondientes:
```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor().setText(e.getX());
    this.configuracionesTemplate.getLPOnClickYValor().setText(e.getY());
}
```

* Sin embargo como el método recibe por parámetro un **String** y se esta enviando como argumento un **entero** el IDE o editor de código indicará un error. Para pasar de un entero a String simplemente se puede usar la notación de: **(numeroEntero + "")** de esta forma Java entenderá que se esta haciendo una suma de caracteres y se hará la conversion automáticamente. 

```javascript
public void mouseClicked(MouseEvent e) {
    this.configuracionesTemplate.getLPOnClickXValor().setText(e.getX() + "");
    this.configuracionesTemplate.getLPOnClickYValor().setText(e.getY() + "");
}
```

Una vez se ejecuta la aplicación, se muestra el componente **Configuraciones** y se da click en cualquier parte del panel de interacción **pDibujo** se cambiara automáticamente la información de la posición de X,Y cuando se da Click:

<div align='center'>
    <img  src='./gifs/MouseClick.gif'>
    <p>Componente Configuraciones con la implementación de MouseClicked</p>
</div>

Ahora bien, cabe recalcar que si se oprime click en cualquier otra parte que no sea el panel de interacción **pDibujo** o si se oprime el botón izquierdo del Mouse pero se suelta en otra posición no se verá reflejado el evento:

***Nota:** En las siguientes secuencias se ve reflejado que el botón izquierdo del Mouse se mantiene oprimido cuando este tiene una circunferencia amarilla alrededor. Como ya se explico previamente es de esa forma en que el evento **MouseClicked** deja de funcionar.*

<div align='center'>
    <img  src='./gifs/MouseClickFail.gif'>
    <p>Forma incorrecta de activar el evento MouseClicked</p>
</div>

## MousePressed

Un evento **MousePressed** se activa una vez el usuario presiona el botón izquierdo del Mouse, no ve la necesidad de saber cuando es soltado el botón, ni si se ha movido el Mouse posteriormente a la presión del botón, este solo se enfoca netamente al momento en que se presiono el botón del Mouse. La única condición de activación es que el puntero se encuentre dentro del objeto gráfico que tiene adicionada la escucha del **MouseListener** al momento de presionar el botón del Mouse, en este caso el panel de interacción **pDibujo**.

Se va a configurar este método y esta vez se que al presionar el botón del Mouse cambie la información de los Labels **LPInicialXValor y LPInicialYValor**:

```javascript
public void mousePressed(MouseEvent e) {
  this.configuracionesTemplate.getLPInicialXValor().setText(e.getX() + "");
  this.configuracionesTemplate.getLPInicialYValor().setText(e.getY() + "");
}
```

Una vez se ejecuta la aplicación se evidencia la función de este evento:

<div align='center'>
    <img  src='./gifs/MousePressed.gif'>
    <p>Componente Configuraciones con la implementación de MousePressed</p>
</div>

En el anterior video se ven varias particularidades:
* Siempre se va a ver reflejado primero el evento **MousePressed** ya que las milésimas de segundo en las que el usuario se demora en soltar el botón del mouse retrasa un poco al **MouseClicked**.
* Si se oprime el botón del Mouse y se espera unos segundos sin mover el mouse se va a ver reflejado primero el evento en el **MousePressed** y hasta que se suelte el botón del mouse se vera en el **MouseClicked**.
* Si se oprime el botón del Mouse y mientras se mantiene presionado se mueve el Mouse solo se vera reflejado el evento **MousePressed**.
* Si se oprime el botón afuera del panel de interacción **pDibujo** no se vera reflejado ningún evento.

## MouseReleased

Un evento **MouseReleased** se activa una vez el usuario suelta el botón del Mouse siempre y cuando este haya sido presionado previamente dentro del objeto gráfico que escucha los eventos **MouseListener**, no ve la necesidad de saber en que momento se oprimió el botón ni si se ha movido el mouse previamente de soltar el botón, este solo se enfoca netamente al momento en que se ha soltado el botón del Mouse. Una particularidad a mencionar es que si el botón del Mouse se suelta en alguna parte afuera del panel de interacción **pDibujo** puede funcionar siempre y cuando se haya presionado previamente el botón del Mouse dentro de el objeto gráfico que activo el evento.

Se va a configurar este método y esta vez se quiere que al soltar el botón del Mouse cambie la información de los Labels **LPFinalXValor y LPFinalYValor**:
```javascript
public void mouseReleased(MouseEvent e) {
  this.configuracionesTemplate.getLPFinalXValor().setText(e.getX() + "");
  this.configuracionesTemplate.getLPFinalYValor().setText(e.getY() + "");
}
```

Se ejecuta la aplicación se es posible dar cuenta de la función de este evento:

<div align='center'>
    <img  src='./gifs/MouseReleased.gif'>
    <p>Componente Configuraciones con la implementación de MouseReleased</p>
</div>

En la anterior secuencia se ven varias particularidades:
* Si se presiona y suelta el botón del Mouse en la misma posición (Hacer un Click), los tres eventos van a reflejar las mismas coordenadas.
* Si se presiona el botón del Mouse y se arrastra el Mouse mientras se tiene presionado el botón y luego se suelta se vera reflejado el evento **MousePressed** y el evento **MouseReleased** pero no el evento **MouseClicked**.
* Si se presiona el botón dentro del panel de interacción **pDibujo** y se suelta el botón del Mouse cuando este esta afuera del panel de interacción se vera reflejado el evento **MouseReleased** aun asi.
* Si se oprime el botón afuera del panel de interacción **pDibujo** no se vera reflejado ningún evento.

## MouseEntered

Este evento se activa una vez el usuario entra a través del puntero del Mouse al objeto gráfico que agrego al **MouseListener**, en este caso el panel de interacción **pDibujo**. No tiene la necesidad de oprimir ningún botón del Mouse, el simple hecho de que este sobre el objeto gráfico activa este evento.

Se va a configurar este método y esta vez se quiere que al entrar al panel **pDibujo** cambie el valor de **lEstadoValor** para indicar que el estado ahora es *ADENTRO*:

```javascript
public void mouseEntered(MouseEvent e) {
    this.configuracionesTemplate.getLEstadoValor().setText("ADENTRO");
}
```

Una vez se ejecuta la aplicación se evidencia la función de este evento:

<div align='center'>
    <img  src='./gifs/MouseEntered.gif'>
    <p>Componente Configuraciones con la implementación de MouseEntered</p>
</div>

Es posible ver que una vez el usuario ingresa con el puntero del mouse al panel de interacción **pDatos** el estado en el panel de datos pasa de **NINGUNO** a **ADENTRO**, sin embargo, este no parece cambiar más, se queda en el ultimo estado incluso si vuelve a salir y entrar de nuevo. Esto es por que aun no se ha configurado el evento en caso de que se salga del objeto gráfico.

## MouseExited

Este evento se activa una vez el usuario sale a través del puntero del Mouse del objeto gráfico que agrego al **MouseListener**, en este caso el panel de interacción **pDibujo** siempre y cuando haya entrado en el previamente. No tiene la necesidad de oprimir ningún botón del Mouse, el simple hecho de que este salga del objeto gráfico activa este evento.

Se va a configurar este método y esta vez se quiere que al salir del panel **pDibujo** cambie el valor de **lEstadoValor** para indicar que el estado ahora es *AFUERA*:
```javascript
@Override
public void mouseExited(MouseEvent e) {
  this.configuracionesTemplate.getLEstadoValor().setText("AFUERA");
}
```

Una vez se ejecuta la aplicación se puede dar cuenta la función de este evento:

<div align='center'>
    <img  src='./gifs/MouseExited.gif'>
    <p>Componente Configuraciones con la implementación de MouseExited</p>
</div>

Como se observa una vez se abre el componente **Configuraciones** el estado está en **NINGUNO**, cada vez que se entra al panel de interacción **pDibujo** el estado cambia a **ADENTRO** y una vez se sale del panel el estado ahora es **AFUERA**.

# MouseMotionListener

Este grupo de eventos para el Mouse da unas características especiales, ya que están enfocados en la muestra de información en tiempo real de acciónes con el Mouse. Estos eventos son útiles para acciones como las de arrastrar objetos, dibujar en tiempo real figuras por pantalla o crear efectos con el movimiento del Mouse.

En la clase **ConfiguracionesComponent**, específicamente en la declaración de esta se va a implementar la interfaz **MouseMotionListener**. Una ventaja con uso de interfaces para implementar es que una clase puede **implementar varias interfaces** al tiempo, esto no es posible con la herencia ya que en java no existe **Herencia Multiple**.

<div align='center'>
    <img  src='https://i.imgur.com/RlbxiCv.png'>
    <p>Implementación de la interfaz MouseMotionListener en la clase ConfiguracionesComponent</p>
</div>

***Nota:** Se debe tener en cuenta la importación de la librería que soporta la implementación de la interfaz.*

Al igual que con la implementación de las demás interfaces esta va a pedir que se importen los métodos por defecto, por lo general el mismo IDE o editor de código va a mostrar el error y va a sugerir la importación automática de estos. 

<div align='center'>
    <img  src='https://i.imgur.com/SowJIrh.png'>
    <p>Métodos implementados por defecto de la interfaz MouseMotionListener</p>
</div>

Se puede observar que esta vez la interfaz **MouseMotionListener** implementa un total de 2 métodos por defecto y cada uno de ellos serán explicados en esta sección.

Antes de continuar con la explicación se debe adicionar la capacidad de escuchar los eventos de tipo **MouseMotionListener** a la panel de interacción **pDibujo**, esta vez se usara el método **addMouseMotionListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseMotionListener(this.configuracionesComponent);
```

Como la clase **configuracionesComponent** implementa de la interfaz **MouseMotionListener** es posible pasar como argumento el objeto de esta clase.

***Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.*

## MouseMoved

Este evento se activa cada vez que el usuario mueve el puntero del Mouse y este se encuentra dentro del objeto gráfico al cual se le adiciono el **MouseMotionListener**, en este caso el panel de interacción **pDibujo**. No tiene la necesidad de oprimir el botón del mouse, con el hecho de estar dentro del objeto gráfico y moverse dentro, este evento se va a activar.

Se va a configurar el método y esta vez se quiere que cada vez que el usuario este moviendo el Mouse dentro del panel **pDibujo** actualice la posición actual en tiempo real del Mouse, para eso se va a cambiar la información de los label **lPActualXValor y lPActualYValor**:
```javascript
public void mouseMoved(MouseEvent e) {
  this.configuracionesTemplate.getLPActualXValor().setText(e.getX() + "");
  this.configuracionesTemplate.getLPActualYValor().setText(e.getY() + "");
}
```

Una vez se ejecuta la aplicación se evidencia la función de este evento:

<div align='center'>
    <img  src='./gifs/MouseMoved.gif'>
    <p>Componente Configuraciones con la implementación de MouseMoved</p>
</div>

## MouseDragged

Este evento se activa una vez se oprime el botón del Mouse dentro del objeto gráfico que tiene agregado el **MouseMotionListener** en este caso el panel de interacción **pDibujo** y se mueve el puntero del Mouse mientras se mantiene oprimido el botón del Mouse, por otro lado termina de accionarse una vez se suelta el botón del Mouse. Se debe oprimir el botón del Mouse dentro del objeto gráfico o de lo contrario no funcionará este evento.

Se va a configurar el método y esta vez se quiere que cada vez que el usuario oprima el botón del Mouse y empiece a mover el Mouse actualice la posición actual en tiempo real del Mouse, para eso se va a cambiar la información de los label **lLadoXValor y lLadoYValor**:
```javascript
public void mouseDragged(MouseEvent e) {
    this.configuracionesTemplate.getLLadoXValor().setText(e.getX() + "");
    this.configuracionesTemplate.getLLadoYValor().setText(e.getY() + "");
}
```

Una vez se ejecute la aplicación es posible ver la función de este evento:

<div align='center'>
    <img  src='./gifs/MouseDragged.gif'>
    <p>Componente Configuraciones con la implementación de mouseDragged</p>
</div>

De la anterior secuencia se puede notar algunas particularidades:
* Una vez el usuario oprime el botón del mouse dentro del panel de interacción **pDibujo** y empieza a arrastrar el mouse sin soltar aun el botón, deja de funcionar el evento **MouseMoved** y empieza a funcionar el evento **MouseDragged**.
* Una vez el usuario suelta el botón del Mouse  y está dentro del panel de interacción **pDibujo** dejara de funcionar el evento **MouseDragged** y empieza a funcionar el evento **MouseMoved**.
* Si el usuario presiono el botón del Mouse dentro del panel de interacción **pDibujo** previamente y se mantiene presionado el evento **MouseDragged** seguirá funcionando incluso si se sale del panel todo mientras se siga manteniendo presionado el botón del mouse.
* Si se presiona desde afuera del panel de interacción **pDibujo** ningún evento tendrán efecto.

# MouseWheelListener

Este grupo de eventos se concentran unicamente en el movimiento del **scroll del Mouse** (la rueda que por lo general tiene el Mouse en medio de los dos botones). Este evento se fija unicamente del movimiento de este Scroll, es decir que no tiene en cuenta cuando se **hace un click** con el Scroll, solo cuando el usuario lo mueve hacia arriba o hacia abajo.

En la clase **ConfiguracionesComponent**, específicamente en la declaración de la clase, se va a implementar la interfaz **MouseWheelListener**. Recordar que esto es posible ya que una clase puede **implementar varias interfaces** al tiempo.

<div align='center'>
    <img  src='https://i.imgur.com/RBhh8Co.png'>
    <p>Implementación de la interfaz MouseWheelListener en la clase ConfiguracionesComponent</p>
</div>

Como con todas las demás interfaces, se debe implementar los métodos por defecto que trae esta interfaz.

<div align='center'>
    <img  src='https://i.imgur.com/ylbQ4Ao.png'>
    <p>Métodos implementados por defecto de la interfaz MouseWheelListener</p>
</div>

Se puede observar que la interfaz **MouseWheelListener** implementa un solo método por defecto y este será explicado en esta sección.

Antes de continuar con la explicación se debe adicionar la capacidad de escuchar los eventos tipo **MouseWheelListener** al panel de interacción **pDibujo**, esta vez se usará el método **addMouseWheelListener**:
```javascript
// Dentro del método crearJPanels
pDibujo.addMouseWheelListener(this.configuracionesComponent);
```

Como la clase **configuracionesComponent** implementa de la interfaz **MouseWheelListener** es posible pasar como argumento el objeto de esta clase.

***Nota:** Como este método hace parte de la etapa de configuración debe ir entre la construcción del panel y la adición de este en el componente.*

## MouseWheelMoved

Este evento se activa una vez el usuario mueve el **Scroll del Mouse**, es decir lo desliza hacia arriba o hacia abajo, para que este evento pueda ser activado debe estar dentro del objeto gráfico que tiene agregado la escucha de eventos tipo **MouseWheelListener** en este caso el panel de interacción **pDatos**. Si se realiza afuera de este no tendrá ningún efecto.

Se va a configurar el método y esta vez se quiere que cada vez que el usuario deslice el Scroll hacia arriba o hacia abajo se realice la simulación de acercamiento y alejamiento, esto se realiza cambiando el valor del label **lZoomValor**. Cuando el usuario mueva el Scroll hacia arriba va a aumentar el numero del zoom mientras que cuando el usuario mueva hacia abajo este numero va a disminuir.

Para saber si el usuario esta subiendo o bajando el scroll del Mouse se debe usar el método:
* **e.getWheelRotation():** Que retorna un numero entero.
    * Si el usuario esta subiendo con el scroll va a retornar el numero **-1**.
    * Si el usuario esta bajando con el scroll va a retornar el numero **1**.

Con este método se puede realizar la configuración del zoom explicada anteriormente:
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  if(e.getWheelRotation() == -1)
    // Acción Cuando el usuario sube con el Scroll
  if(e.getWheelRotation() == 1)
    // Acción Cuando el usuario baja con el Scroll
}
```

* Como se quiere cambiar el valor del label **lZoomValor**, se debe obtener a traves del objeto de la clase **Template** y su respectivo método **get**, una vez obtenido se debe indicar que cambiará su contenido con el método **setText**:
```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText();
  if(e.getWheelRotation() == 1)
    this.configuracionesTemplate.getLZoomValor().setText();
}
```

* Ahora se debe indicar en cada caso que se le va a sumar o restar el valor de 1, pero para eso es necesario obtener antes el valor actual que tiene el label **lZoomValor**, ya que si solo se le indica que sume o reste 1 el valor  del Label **lZoomValor** solo podría ser 1 o -1. Para obtener su valor nuevamente se debe llamar al Label a través del objeto de la clase **Template** y su respectivo método **get** seguido del método **getText()** que retorna en un String el contenido del Label:
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

* Como se quiere sumar o restar un 1 al contenido actual del Label **lZoomValor** se debe convertir el dato de String a entero y de esta forma se puede realizar una suma o resta entre números:
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

* Es posible notar que hasta el momento en ambos casos se esta haciendo el mismo proceso, se puede entonces encapsular el proceso de obtención y conversion del zoom actual en una variable antes de que empiecen las condiciones, para que no quede tan confuso el código:

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual);
  if(e.getWheelRotation() == 1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual);
}
```

* Ahora si se va a sumar y restar un 1 según sea el caso:

 ```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
    int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
    if(e.getWheelRotation() == -1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1);
    if(e.getWheelRotation() == 1)
        this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1);
}
```

* Sin embargo el método **setText** exige enviar como argumento un String asi que se realiza otra conversion, esta vez de entero a String como se menciono antes:
 ```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
  if(e.getWheelRotation() == 1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

Si se ejecuta la aplicación se puede ver el evento funcionando:

<div align='center'>
    <img  src='./gifs/MouseWheel.gif'>
    <p>Componente Configuraciones con la implementación de mouseWheelMoved</p>
</div>

Sin embargo, es posible notar que si se baja demasiado con el scroll este empieza a mostrar números negativos y esto en términos de zoom no es correcto, por lo que seria bueno dejar un limite en caso de bajar el scroll para que no pueda pasar a menos de 0:

* Primero se debe indicar en el condicional que controla cuando el Scroll esta bajando que se va a realizar otra condición, esto se hace con el separador **&&**.

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
  if(e.getWheelRotation() == 1 &&)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

* Ahora se debe obtener el valor actual del zoom para poder ser comparado, sin embargo, este proceso de obtención y conversion del Zoom actual ya está encapsulado en la variable **valorZoomActual** por lo que se usa esta:

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
  if(e.getWheelRotation() == 1 && valorZoomActual)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

* Ahora solo nos hace falta realizar la comparación: 

```javascript
public void mouseWheelMoved(MouseWheelEvent e) {
  int valorZoomActual = Integer.parseInt(this.configuracionesTemplate.getLZoomValor().getText());
  if(e.getWheelRotation() == -1)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual + 1 + "");
  if(e.getWheelRotation() == 1 && valorZoomActual > 0)
    this.configuracionesTemplate.getLZoomValor().setText(valorZoomActual - 1 + "");
}
```

En la anterior condición se indicando al programa que solo cuando el valor del Zoom actual sea mayor a 0 este podrá ser restado, de lo contrario este valor será 0 y por ende no podrá entrar a la condición.

Si se ejecuta de nuevo la aplicación se puede notar que se cumple la condición que queríamos:

<div align='center'>
    <img  src='./gifs/MouseWheelFinal.gif'>
    <p>Restricción en la resta del zoom</p>
</div>

# Resultado 

Si llegaste hasta aquí **!Felicidades!**, se ha aprendido las características principales de los diferentes eventos del mouse. Se ha revisado en que momentos se activa y deja de funcionar cada uno de ellos, además de algunas particularidades que tienen los eventos del mouse. En la siguiente clase se van a implementar estos eventos al resto del proyecto para darle una interactividad mayor a la interfaz gráfica de usuario.

# Actividad 

Revisa los diferentes tipos de eventos poniendo comentarios a la funcionalidad, particularidades y en que casos no funciona cada uno de estos.