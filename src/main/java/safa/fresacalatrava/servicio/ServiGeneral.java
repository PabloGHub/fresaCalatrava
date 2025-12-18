package safa.fresacalatrava.servicio;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import safa.fresacalatrava.IGetterSetter;
import safa.fresacalatrava.dto.*;
import safa.fresacalatrava.modelo.*;
import safa.fresacalatrava.repositorio.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
public class ServiGeneral<M> // TODO: Revisar genéricos.
{
    private Class<?> _tipoModelo; // Pasar por constructor. // No se como pasarlo por constructor, :D.

    //private final jakarta.validation.Validator validator;

    private static final Map<String, String> CONSTRAINT_FIELD_MAP = Map.of(
            "duplicado_codigo", "codigo",
            "error", "error"
    );

    private final RepCliente repCliente;
    private final RepFinca repFinca;
    private final RepFresa repFresa;
    private final RepInvernadero repInvernadero;
    private final RepPedido repPedido;
    private final RepPedidoFresa repPedidoFresa;
    private final RepRecoleccion repRecoleccion;
    private final RepRecoleccionFresa repRecoleccionFresa;
    private final RepValoracion repValoracion;

    private Map<Class<? extends IModelo>,
                JpaRepository<?, Integer>> repos;

    public static final Map<Class<?>, Class<?>> modelos = Map.of(
            DtoFinca.class, Finca.class,
            DtoInvenadero.class, Invernadero.class,
            DtoInvernadero_SinRela.class, Invernadero.class,
            DtoFresa.class, Fresa.class
    );

    public void setTipoModelo(Class<M> eTipoModelo)
    {
        this._tipoModelo = eTipoModelo;
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

    public Object Empaquetar(Class<?> eTipoDestino, IGetterSetter eOrigen, DtoFallo eFallo)
    {
        if (eOrigen == null)
        {
            eFallo.AddError("origen_null:Empaquetar");
            eFallo.setExito(false);
            return null;
        }

        try
        {
            Class<?> novo = (Class<?>) eTipoDestino.getDeclaredConstructor().newInstance();
            if (novo == null)
            {
                eFallo.AddError("no_instanciado:Empaquetar");
                eFallo.setExito(false);
                return null;
            }


            for (Method getter : eOrigen.darmeAllGetters())
            {
                String setterNombre = getter.getName().replace("get", "set");

                Object value = getter.invoke(eOrigen);
                Method setter = findCompatibleMethod(eTipoDestino, setterNombre);
                if (setter != null)
                    setter.invoke(novo, value);

            }

            return novo;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

        return  null;
    }

    @SuppressWarnings("unchecked")
    public IModelo DarmeUno(Class<?> eTipo, Integer eId, DtoFallo eFallo)
    {
        var repo = (JpaRepository<Object, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            eFallo.AddError("no_repo:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        /*
        if (eId == null)
        {
            eFallo.AddError("id_null:DarmeUno");
            eFallo.setExito(false);
            return null;
        }
        */

        var novoDato = repo.findById(eId).orElse(null);
        /*
        if (novoDato == null)
        {
            eFallo.AddError("no_encontrado:DarmeUno");
            eFallo.setExito(false);
            return null;
        }
        */

        return (IModelo) novoDato;
    }


    public <D> D DarmeUnoDto(Class<D> eTipoDto, int eId, DtoFallo eFallo)
    {
        var eTipoModelo = modelos.get(eTipoDto);
        if (eTipoModelo == null)
        {
            eFallo.setExito(false);
            eFallo.AddError("no_encontrado_modelo:" + eTipoDto.getSimpleName());
            return null;
        }

        Object modelo = DarmeUno(eTipoModelo, eId, eFallo);
        if (modelo == null)
        {
            eFallo.setExito(false);
            eFallo.AddError("no_encontrado:" + eTipoDto.getSimpleName());
            return null;
        }

        return (D) this.Empaquetar(eTipoDto, (IGetterSetter) modelo, eFallo);
    }

    @SuppressWarnings("unchecked")
    public List<IModelo> DarmeTodo(Class<?> eTipo, DtoFallo eFallo)
    {
        var repo = (JpaRepository<Object, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            eFallo.AddError("no_repo:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        List<?> raw = repo.findAll();
        return raw.stream()
                .map(o -> (IModelo) o)
                .toList();
    }

    @SuppressWarnings("unchecked")
    public <D> List<D> DarmeTodoDto(Class<D> eTipoDto, DtoFallo eFallo)
    {
        Class<?> tipoModelo = modelos.get(eTipoDto);
        if (tipoModelo == null)
        {
            eFallo.AddError("no_modelo_encontrado:" + eTipoDto.getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        List<IModelo> lista = this.DarmeTodo(tipoModelo, eFallo);
        if (lista == null) return null;

        return (List<D>) lista
                .stream()
                .map(m -> this.Empaquetar(eTipoDto, (IGetterSetter) m, eFallo))
                .toList();
    }



    private <D> D guardar(Class<?> eTipo, D eDto, DtoFallo eFallo)
    {
        var repo = (JpaRepository<Object, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            eFallo.AddError("no_repo:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        Object modelo = this.Empaquetar(eTipo, (IGetterSetter) eDto, eFallo);
        Object f = null;
        try
        {
            f = repo.save(modelo);
        }
        catch (org.springframework.dao.DataIntegrityViolationException e)
        {
            Throwable cause = e;
            boolean handled = false;
            while (cause != null)
            {
                if (cause instanceof org.hibernate.exception.ConstraintViolationException cve)
                {
                    String constraint = cve.getConstraintName();
                    String field = CONSTRAINT_FIELD_MAP.getOrDefault(constraint, constraint != null ? constraint : "constraint_violation");
                    eFallo.AddError("constraint_violation:" + field);
                    handled = true;
                    break;
                }
                else if (cause instanceof java.sql.SQLIntegrityConstraintViolationException sqlEx)
                {
                    String msg = sqlEx.getMessage();
                    String hint = "constraint_violation";
                    if (msg != null)
                    {
                        var m = java.util.regex.Pattern.compile("for key '([^']+)'").matcher(msg);
                        if (m.find()) hint = CONSTRAINT_FIELD_MAP.getOrDefault(m.group(1), m.group(1));
                    }
                    eFallo.AddError("constraint_violation:" + hint);
                    handled = true;
                    break;
                }
                cause = cause.getCause();
            }

            if (!handled)
                eFallo.AddError("data_integrity_error:guardar");

            eFallo.setExito(false);
            return null;
        }
        catch (org.springframework.dao.DataAccessException e)
        {
            eFallo.AddError("data_access_error:guardar");
            eFallo.setExito(false);
            return null;
        }
        catch (Exception e)
        {
            eFallo.AddError("error_grave:guardar");
            eFallo.setExito(false);
            return null;
        }

        if (f == null)
        {
            eFallo.AddError("no_guardado:guardar");
            eFallo.setExito(false);
            return null;
        }

        // devolver DTO resultante
        @SuppressWarnings("unchecked")
        D resultado = (D) this.Empaquetar(eDto.getClass(), (IGetterSetter) f, eFallo);
        return resultado;
    }

    public <D> D Update(D eDto, DtoFallo eFallo)
    {
        Class<?> tipoModelo = modelos.get(eDto.getClass());
        if (tipoModelo == null)
        {
            eFallo.AddError("no_modelo_encontrado:" + eDto.getClass().getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        if (eDto instanceof IDTO gsDto)
        {
            try
            {
                Object existencia = this.DarmeUno(tipoModelo, gsDto.<Integer>get("id"), eFallo);
                if (existencia == null)
                {
                    eFallo.AddError("no_existente:Update");
                    eFallo.setExito(false);
                    return null;
                }

                gsDto.sum((IGetterSetter) existencia);
                eDto = (D) gsDto;
            }
            catch (IllegalAccessException | InvocationTargetException iae)
            {
                eFallo.AddError("no_existente:Update");
                eFallo.setExito(false);
                return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                eFallo.AddError("no_existente:Update");
                eFallo.AddError("error_grave:Update");
                eFallo.setExito(false);
                return null;
            }
        }

        return this.guardar(tipoModelo, eDto, eFallo);
    }

    public <D> D Create(D eDto, DtoFallo eFallo)
    {
        Class<?> tipoModelo = modelos.get(eDto.getClass());
        if (tipoModelo == null)
        {
            eFallo.AddError("no_modelo_encontrado:" + eDto.getClass().getSimpleName());
            eFallo.setExito(false);
            return null;
        }

        if (eDto instanceof IDTO gsDto)
        {
            try
            {
                Integer id = gsDto.<Integer>get("id");
                if (id != null)
                {
                    Object existencia = this.DarmeUno(tipoModelo, id, eFallo);
                    if (existencia != null)
                    {
                        eFallo.AddError("ya_existente:Create");
                        eFallo.setExito(false);
                        return null;
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException iae)
            {
                eFallo.AddError("no_existente:Create");
                eFallo.setExito(false);
                return null;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                eFallo.AddError("no_existente:Create");
                eFallo.AddError("error_grave:Create");
                eFallo.setExito(false);
                return null;
            }
        }

        return this.guardar(tipoModelo, eDto, eFallo);
    }


    public IDTO Eliminar(Class<?> eTipo, Integer eId, DtoFallo eFallo)
    {
        var repo = (JpaRepository<? extends IModelo, Integer>) repos.get(eTipo);
        if (repo == null)
        {
            eFallo.AddError("no_repo:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return eFallo;
        }

        IModelo modelo = this.DarmeUno(eTipo, eId, eFallo);
        if (modelo == null)
        {
            eFallo.AddError("no_encontrado:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return eFallo;
        }

        try
        {
            repo.deleteById(eId);
        }
        catch (Exception e)
        {
            eFallo.AddError("error_grave:" + eTipo.getSimpleName());
            eFallo.setExito(false);
            return eFallo;
        }

        return eFallo;
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