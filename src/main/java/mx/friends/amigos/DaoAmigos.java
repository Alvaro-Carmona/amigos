package mx.friends.amigos;

import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/** Se encarga del acceso a la base de datos. La anotación
 * <code>@Stateless</code> indica que los objetos de la clase usan transacciones
 * y no almacenan información de estado, como pueden ser nombre, teléfono, etc.
 * <p>
 * La anotación <code>@Dependent</code> indica que el tiempo de vida de este
 * componente depende de quien lo inyecta. Por ejemplo, la clase
 * <code>CtrlAmigos</code>, con tiempo de vida RequestScoped, inyecta un
 * objeto de esta clase, que por consiguiente dura el tiempo de una solicitud
 * http.
 * </p>
 * <p>
 * Esta clase se crea con los siguientes pasos:
 * </p>
 * <ol>
 * <li>Selecciona el paquete donde quieras colocar la clase.</li>
 * <li>Menú Archivo -> Archivo Nuevo...</li>
 * <li>Categorías: Enterprise Java Beans, Tipos de Archivos: Session Bean,
 * Siguiente.</li>
 * <li>
 * EJB Name: DaoAmigo, Package: net.ramptors.pasa, Session Type:
 * Stateless, Terminar.
 * </li>
 * </ol>*/
@Stateless
@Dependent
public class DaoAmigos{
  /** Busca en el proyecto una clase con el nombre <code>EntityManager</code> o
   * una clase con un atributo o método de tipo <code>EntityManager</code> con
   * la anotación <code>@Produces</code>. Los objetos <code>EntityManager</code>
   * se usan para realizar operaciones sobre la base de datos. */
  @Inject
  private EntityManager em;
  public List<Amigo> consulta() {
    /* Con el resultado de la consulta se llena una lista con objetos de la
     * clase "Amigo". */
    return em.createQuery("SELECT c FROM Amigos c ORDER BY c.nombre",
        Amigo.class).getResultList();
  }
  public Amigo busca(Integer id) {
    return em.find(Amigo.class, id);
  }
  public void agrega(Amigo modelo) {
    em.persist(modelo); // Agrega el modelo a la base de datos.
  }
  public void modifica(Amigo modelo) {
    em.merge(modelo);// Guarda los cambios al modelo.
  }
  public void elimina(Amigo modelo) {
    // Busca el modelo en base a su id.
    final Amigo anterior = em.find(Amigo.class, modelo.getId());
    // Si el resultado es null, el chismoso ya no está registrado.
    if (anterior != null) {
      // Pero si la referencia es diferente de null, hay que eliminar el objeto.
      em.remove(anterior);
    }
  }
}