package mx.friends.amigos;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mx.friends.web.Mensajes;


@Named
@RequestScoped
public class CtrlAmigos {
  @Inject
  private Mensajes mensajes;
  @Inject
  private DaoAmigos dao;
  private List<Amigo> instancias;
  /* @PostConstruct indica que el método se ejecuta después de crear el objeto y
   * realizar todos los inject. Funciona casi como un constructor. */
  @PostConstruct
  void init() {
    try {
      instancias = dao.consulta();
    } catch (Exception ex) {
      mensajes.procesa(ex);
    }
  }
  public List<Amigo> getInstancias() {
    return instancias;
  }
}
