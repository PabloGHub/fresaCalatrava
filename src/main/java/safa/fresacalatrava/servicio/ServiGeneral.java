package safa.fresacalatrava.servicio;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.dto.*;
import safa.fresacalatrava.modelo.*;
import safa.fresacalatrava.repositorio.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
public class ServiGeneral// <M> // TODO: Revisar genéricos.
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

    private Map<Class<?>, Class<?>> modelos = Map.of(
            DtoFinca.class, Finca.class,
            DtoInvenadero.class, Invernadero.class,
            DtoInvernadero_SinRela.class, Invernadero.class,
            DtoFresa.class, Fresa.class
    );

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

    public static Class<?> box(Class<?> c)
    {
        if (!c.isPrimitive()) return c;
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

    public static boolean isCompatible(Class<?> paramType, Class<?> valueType)
    {
        if (valueType == null)
        {
            return !paramType.isPrimitive();
        }

        if (paramType.isAssignableFrom(valueType)) return true;

        Class<?> boxedParam = box(paramType);
        Class<?> boxedValue = box(valueType);

        return boxedParam != null
                && boxedValue != null
                && boxedParam.isAssignableFrom(boxedValue);
    }


    // TODO: Revisar.
    public static Method findCompatibleMethod(
            Class<?> dtoClass,
            String methodName,
            Class<?>[] argumentTypes)
    {
        Method bestMatch = null;
        int bestScore = -1;

        for (Method m : dtoClass.getMethods())
        {
            if (!m.getName().equals(methodName)) continue;
            if (m.getParameterCount() != argumentTypes.length) continue;

            Class<?>[] methodParams = m.getParameterTypes();
            int score = 0;
            boolean compatible = true;

            for (int i = 0; i < argumentTypes.length; i++)
            {
                if (!isCompatible(methodParams[i], argumentTypes[i]))
                {
                    compatible = false;
                    break;
                }

                // Más puntos si es exactamente el mismo tipo
                if (argumentTypes[i] != null && methodParams[i].equals(argumentTypes[i]))
                {
                    score += 2;
                }
                else
                {
                    score += 1;
                }
            }

            if (compatible && score > bestScore)
            {
                bestScore = score;
                bestMatch = m;
            }
        }

        return bestMatch;
    }

    public static Method findCompatibleMethod(Class<?> tipo, String methodName)
    {
        for (Method m : tipo.getMethods())
        {
            if (!m.getName().equals(methodName)) continue;
            return m;
        }
        return null;
    }

    public <D, M> D Empaquetar(Class<?> eTipoDestino, M eOrigen, DtoFallo eFallo)
    {
        try
        {
            D novoDto = (D) eTipoDestino.getDeclaredConstructor().newInstance();

            if (!(eOrigen instanceof GetterSetter gsOrigen))
            {
                eFallo.setExito(false);
                eFallo.AddError("no_implement_gs:Empaquetar");
                return null;
            }

            for (Method getter : gsOrigen.getAllGetters())
            {
                String setterNombre = getter.getName().replace("get", "set");

                Object value = getter.invoke(eOrigen);
                Method setter = findCompatibleMethod(eTipoDestino, setterNombre);
                if (setter != null)
                    setter.invoke(novoDto, value);

            }

            return novoDto;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        return  null;
    }

    @SuppressWarnings("unchecked")
    public <M> M DarmeUno(Class<?> eTipo, int eId, DtoFallo eFallo)
    {
        JpaRepository<M, Integer> repo = (JpaRepository<M, Integer>) repos.get(eTipo);
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

    public <M, D> D DarmeUnoDto(Class<D> eTipoDto, int eId, DtoFallo eFallo)
    {
        var eTipoModelo = modelos.get(eTipoDto);
        if (eTipoModelo == null)
        {
            // TODO: eFallo.
            return null;
        }

        M modelo = DarmeUno(eTipoModelo, eId, eFallo);

        return this.<D, M>Empaquetar(eTipoDto, modelo, eFallo);
    }

    public <M> List<M> DarmeTodo(Class<M> eTipo, DtoFallo eFallo)
    {
        var repo = (JpaRepository<M, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            // TODO: eFallo.
            return null;
        }

        return repo.findAll();
    }

    public <M, D> List<D> DarmeTodoDto(Class<D> eTipoDto, DtoFallo eFallo)
    {
        var tipoModelo = modelos.get(eTipoDto);
        if (tipoModelo == null)
        {
            // TODO: eFallo.
            return null;
        }

        return DarmeTodo((Class<M>) tipoModelo, eFallo)
                .stream()
                .map(m -> this.<D, M>Empaquetar(eTipoDto, m, eFallo))
                .toList();
    }

    public <D> D CrearUpdate(D eDto, DtoFallo eFallo)
    {
        var tipoModelo = modelos.get(eDto.getClass());
        if (tipoModelo == null)
        {
            // TODO: eFallo.
            return null;
        }

        var repo = (JpaRepository<Object, Integer>) repos.get(tipoModelo);
        if (repo == null)
        {
            // TODO: eFallo.
            return null;
        }

        var modelo = this.<D, Object>Empaquetar(tipoModelo, eDto, eFallo);
        var f = repo.save(modelo);

        if  (f == null)
        {
            eFallo.AddError("no_guardado:CrearUpdate");
            eFallo.setExito(false);
            return null;
        }

        return Empaquetar(eDto.getClass(), f, eFallo);
    }
}


/*
public <Dto, Origin> Dto Empaquetar(Class<Dto> eTipoDto, Origin eModelo, DtoFallo eFallo) {
    if (eModelo == null) return null;
    try {
        Dto dto = eTipoDto.getDeclaredConstructor().newInstance();
        Method[] origenMethods = eModelo.getClass().getMethods();

        for (Method getter : origenMethods) {
            String name = getter.getName();
            if (getter.getParameterCount() != 0) continue;

            String propSuffix = null;
            if (name.startsWith("get") && name.length() > 3) {
                propSuffix = name.substring(3);
            } else if (name.startsWith("is") && name.length() > 2
                       && (getter.getReturnType() == boolean.class || getter.getReturnType() == Boolean.class)) {
                propSuffix = name.substring(2);
            }
            if (propSuffix == null) continue;

            Object value = getter.invoke(eModelo);
            String setterName = "set" + propSuffix;
            Method setter = findCompatibleSetter(eTipoDto, setterName, getter.getReturnType());
            if (setter != null) {
                setter.invoke(dto, value);
            }
        }

        return dto;
    } catch (Exception ex) {
        // aquí podrías rellenar eFallo con información del error
        return null;
    }
}
 */