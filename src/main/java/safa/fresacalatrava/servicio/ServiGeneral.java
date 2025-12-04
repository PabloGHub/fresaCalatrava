package safa.fresacalatrava.servicio;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.modelo.*;
import safa.fresacalatrava.repositorio.*;

import java.lang.reflect.Method;
import java.util.Map;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
public class ServiGeneral
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

    private static Class<?> box(Class<?> c)
    {
        if (!c.isPrimitive()) return c; // Creo que tiene que ser al reves.
        if (c == boolean.class) return Boolean.class;
        if (c == byte.class) return Byte.class;
        if (c == char.class) return Character.class;
        if (c == short.class) return Short.class;
        if (c == int.class) return Integer.class;
        if (c == long.class) return Long.class;
        if (c == float.class) return Float.class;
        if (c == double.class) return Double.class;
        return c;
    }

    private static boolean isCompatible(Class<?> paramType, Class<?> valueType) {
        if (valueType == null) {
            // permite setear null si el parámetro no es primitivo
            return !paramType.isPrimitive();
        }
        if (paramType.isAssignableFrom(valueType)) return true;
        // comprobar boxing/unboxing
        Class<?> boxedParam = box(paramType);
        Class<?> boxedValue = box(valueType);
        if (boxedParam != null && boxedValue != null) {
            return boxedParam.isAssignableFrom(boxedValue);
        }
        return false;
    }

    private static Method findCompatibleSetter(Class<?> dtoClass, String setterName, Class<?> getterReturnType) {
        for (Method m : dtoClass.getMethods()) {
            if (!m.getName().equals(setterName) || m.getParameterCount() != 1) continue;
            Class<?> paramType = m.getParameterTypes()[0];
            if (isCompatible(paramType, getterReturnType)) return m;
        }
        return null;
    }

    public <Dto, Modelo> Dto Empaquetar(Class<Dto> eTipoDto, Modelo eModelo, DtoFallo eFallo)
    {
        try
        {
            Dto novoDto = eTipoDto.getDeclaredConstructor().newInstance();
            Class<?> claseModelo = eModelo.getClass();
            Method[] metodosModelo = eModelo.getClass().getMethods();

            for (Method m : claseModelo.getMethods())
            {
                if (m.getParameterCount() <= 0) continue;

                String nombre = m.getName();

                if (!nombre.contains("get")) continue;

                String setterNombre = nombre.replace("get", "set");

                Method setter = findCompatibleSetter(eTipoDto, setterName, getter.getReturnType());

            }

            for (Method mo : claseModelo.getMethods())
            {
                String nombreMetodoModelo = mo.getName();
                if (nombreMetodoModelo.contains("get"))
                {
                    for (Method mm : claseModelo.getMethods())
                    {
                        String nombreMetodoDto = mm.getName();
                        if (nombreMetodoDto.equals(nombreMetodoModelo.replace("get", "set")))
                        {
                            novoDto.invoke();
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        return  null;
    }

    @SuppressWarnings("unchecked")
    public <D> D DarmeUno(Class<D> eTipo, int eId, DtoFallo eFallo)
    {
        JpaRepository<D, Integer> repo = (JpaRepository<D, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            // TODO: eFallo.
            return null;
        }

        var novoDato = repo.findById(eId).orElse(null);
        if (novoDato == null)
        {
            // TODO: eFallo.
            return null;
        }

        return novoDato;
    }


}
