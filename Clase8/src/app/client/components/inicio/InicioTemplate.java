package app.client.components.inicio;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.client.components.accion.AccionComponent;
import app.client.components.accion.AccionTemplate;
import app.client.components.tarjeta.TarjetaComponent;
import app.services.ObjGraficosService;
import app.services.RecursosService;

public class InicioTemplate extends JPanel {
  private static final long serialVersionUID = 1L;

  // Declaración Servicios y dependencias
  private InicioComponent inicioComponent;
  private ObjGraficosService sObjGraficos;
  private RecursosService sRecursos;

  // Declaración Objetos Gráficos
  private JPanel pMision, pVision, pNosotros, pAcciones;
  private JLabel lAcciones;

  // Declaración Objetos Decoradores
  private ImageIcon iTarjeta1, iTarjeta2, iTarjeta3;
  private ImageIcon iClase, iPantalla, iIdea, iCelular, iEstadistica, iDireccion;

  public InicioTemplate(InicioComponent inicioComponent) {
    this.inicioComponent = inicioComponent;
    this.inicioComponent.getClass();
    sObjGraficos = ObjGraficosService.getService();
    sRecursos = RecursosService.getService();

    this.crearObjetosDecoradores();
    this.crearJPanels();
    this.crearContenidoPMision();
    this.crearContenidoPVision();
    this.crearContenidoPNosotros();
    this.crearContenidoPAcciones();

    this.setSize(850, 600);
    this.setBackground(sRecursos.getColorGrisClaro());
    this.setLayout(null);
    this.setVisible(true);
  }

  public void crearJPanels() {
    this.pMision = sObjGraficos.construirJPanel(20, 15, 256, 230, Color.WHITE, null);
    this.add(pMision);

    this.pVision = sObjGraficos.construirJPanel(296, 15, 256, 230, Color.WHITE, null);
    this.add(pVision);

    this.pNosotros = sObjGraficos.construirJPanel(572, 15, 256, 230, Color.WHITE, null);
    this.add(pNosotros);

    this.pAcciones = sObjGraficos.construirJPanel(20, 260, 810, 330, Color.WHITE, null);
    this.add(pAcciones);
  }

  public void crearObjetosDecoradores() {
    this.iTarjeta1 = new ImageIcon("Clase8/resources/images/tarjetas/tarjeta1.jpg");
    this.iTarjeta2 = new ImageIcon("Clase8/resources/images/tarjetas/tarjeta2.jpg");
    this.iTarjeta3 = new ImageIcon("Clase8/resources/images/tarjetas/tarjeta3.jpg");
    this.iClase = new ImageIcon("Clase8/resources/images/acciones/clases.png");
    this.iPantalla = new ImageIcon("Clase8/resources/images/acciones/pantalla.png");
    this.iCelular = new ImageIcon("Clase8/resources/images/acciones/celular.png");
    this.iIdea = new ImageIcon("Clase8/resources/images/acciones/ideas.png");
    this.iEstadistica = new ImageIcon("Clase8/resources/images/acciones/estadisticas.png");
    this.iDireccion = new ImageIcon("Clase8/resources/images/acciones/direccion.png");
  }

  public void crearContenidoPMision() {
    this.pMision.add(
      new TarjetaComponent(
        "Nuestra Misión", 
        iTarjeta1,
        "Brindar cursos a la comunidad académica para" + 
        "complementar habilidades fuera del pensum común."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPVision() {
    this.pVision.add(
      new TarjetaComponent(
        "Nuestra Visión", 
        iTarjeta2,
        "Brindar cursos académicos al 80% de los" + 
        "estudiantes de ingeniería de Sistemas."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPNosotros() {
    this.pNosotros.add(
      new TarjetaComponent(
        "Sobre Nosotros", 
        iTarjeta3,
        "Somos un grupo de trabajo de la Universidad" +
        "distrital Francisco jose de Caldas."
      ).getTarjetaTemplate()
    );
  }

  public void crearContenidoPAcciones() {
    this.lAcciones = sObjGraficos.construirJLabel(
      "Nuestros Servicios",
      10, 10, 160, 30,
      null, null,
      sRecursos.getFontTitulo(),
      null,
      sRecursos.getColorAzul(),
      null,
      "c"
    );
    this.pAcciones.add(lAcciones);

    // COMPONENTE ACCIÓN 1 ------------------------------------
    AccionTemplate p1 = new AccionComponent(
      iClase, 
      "Clases", 
      "Clases a la comunidad que complementan el pensum."
    ).getAccionTemplate();
    p1.setLocation(15, 50);
    this.pAcciones.add(p1);

    // COMPONENTE ACCIÓN 2 ------------------------------------
    AccionTemplate p2 = new AccionComponent(
      iPantalla, 
      "Clases Virtuales", 
      "Cursos virtuales como medio de enseñanza."
    ).getAccionTemplate();
    p2.setLocation(30 + p2.getWidth(), 50);
    this.pAcciones.add(p2);

    // COMPONENTE ACCIÓN 3 ------------------------------------
    AccionTemplate p3 = new AccionComponent(
      iIdea, 
      "Generación de ideas",
      "Desarrollo de ideas con tecnologías actuales."
    ).getAccionTemplate();
    p3.setLocation(45 + p3.getWidth() * 2, 50);
    this.pAcciones.add(p3);

    // COMPONENTE ACCIÓN 4 ------------------------------------
    AccionTemplate p4 = new AccionComponent(
      iCelular, 
      "Notificaciones",
      "Notificaión el estado de tus cursos y actividades."
    ).getAccionTemplate();
    p4.setLocation(15, 65 + p4.getHeight());
    this.pAcciones.add(p4);

    // COMPONENTE ACCIÓN 5 ------------------------------------
    AccionTemplate p5 = new AccionComponent(
      iEstadistica, 
      "Estadisticas",
      "Gestión de participación en nuestros cursos."
    ).getAccionTemplate();
    p5.setLocation(30 + p5.getWidth(), 65 + p5.getHeight());
    this.pAcciones.add(p5);

    // COMPONENTE ACCIÓN 6 ------------------------------------
    AccionTemplate p6 = new AccionComponent(
      iDireccion, 
      "Dirección", 
      "Damos direcciónamiento a nuestros estudiantes."
    ).getAccionTemplate();
    p6.setLocation(45 + p6.getWidth() * 2, 65 + p6.getHeight());
    this.pAcciones.add(p6);
  }
}