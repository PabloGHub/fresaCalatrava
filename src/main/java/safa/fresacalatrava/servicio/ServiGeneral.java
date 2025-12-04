package safa.fresacalatrava.servicio;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.InitializingBean;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.modelo.*;
import safa.fresacalatrava.repositorio.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
public class ServiGeneral implements InitializingBean
{
    private final RepCliente repCliente;
    private final RepFinca repFinca;
    private final RepFresa repFresa;
    private final RepInvernadero repInvernadero;
    private final RepPedido repPedido;
    private final RepPedidoFresa repPedidoFresa;
    private final RepRecoleccion repRecoleccion;
    private final RepRecoleccionFresa repRecoleccionFresa;
    private final RepValoracion repValoracion;

    private Map<Class<?>, JpaRepository<?, Integer>> repos;

    private final Map<Class<?>, Function<Integer, ?>> finders = new HashMap<>();

    @Override
    public void afterPropertiesSet()
    {
        finders.put(Finca.class, id -> repFinca.findById(id).orElse(null));
        finders.put(Fresa.class, id -> repFresa.findById(id).orElse(null));
        finders.put(Cliente.class, id -> repCliente.findById(id).orElse(null));
        finders.put(Invernadero.class, id -> repInvernadero.findById(id).orElse(null));
        finders.put(Pedido.class, id -> repPedido.findById(id).orElse(null));
        finders.put(PedidoFresa.class, id -> repPedidoFresa.findById(id).orElse(null));
        finders.put(Recoleccion.class, id -> repRecoleccion.findById(id).orElse(null));
        finders.put(RecoleccionFresa.class, id -> repRecoleccionFresa.findById(id).orElse(null));
        finders.put(Valoracion.class, id -> repValoracion.findById(id).orElse(null));
    }

    @PostConstruct
    void init()
    {
        repos = Map.of(
                Finca.class, repFinca,
                Fresa.class, repFresa,
                Cliente.class, repCliente,
                Invernadero.class, repInvernadero,
                Pedido.class, repPedido,
                PedidoFresa.class, repPedidoFresa,
                Recoleccion.class, repRecoleccion,
                RecoleccionFresa.class, repRecoleccionFresa,
                Valoracion.class, repValoracion
        );
    }

    public <T> T Empaquetar(Class<T> eTipo, Finca eFinca, DtoFallo eFallo)
    {

    }

    public <D> D DarmeUno(Class<D> eTipo, int eId, DtoFallo eFallo)
    {
        Function<Integer, ?> finder = finders.get(eTipo);
        if (finder == null) return null;
        Object result = finder.apply(eId);
        return eTipo.cast(result);
    }

    //@SuppressWarnings("unchecked")
    public <D> D DarmeUno_v2(Class<D> eTipo, int eId, DtoFallo eFallo)
    {
        JpaRepository<D, Integer> repo = (JpaRepository<D, Integer>) repos.get(eTipo);

        if (repo == null)
        {
            // TODO: eFallo.
            return null;
        }

        return repo.findById(eId).orElse(null);
    }


}
