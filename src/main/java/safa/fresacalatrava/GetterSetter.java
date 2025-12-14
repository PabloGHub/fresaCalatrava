package safa.fresacalatrava;

import safa.fresacalatrava.servicio.ServiGeneral;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public interface GetterSetter
{
    public static String getNameVariable(Method metodo)
    {
        if (metodo.getName().startsWith("get") || metodo.getName().startsWith("set"))
        {
            return metodo.getName().substring(3).toLowerCase();
        }
        return null;
    }

    default <T> T get(String nombreVariable) throws InvocationTargetException, IllegalAccessException
    {
        var nombreMetodo = "get" + nombreVariable.substring(0, 1).toUpperCase() + nombreVariable.substring(1);
        var metodo = ServiGeneral.findCompatibleMethod(this.getClass(), nombreMetodo);

        if (metodo == null)
            return null;
        return (T) metodo.invoke(this);
    }

    default <T> void set(String nombreVariable, T valor) throws InvocationTargetException, IllegalAccessException
    {
        var nombreMetodo = "set" + nombreVariable.substring(0, 1).toUpperCase() + nombreVariable.substring(1);
        var metodo = ServiGeneral.findCompatibleMethod(this.getClass(), nombreMetodo);

        if (metodo == null)
            return;
        metodo.invoke(this, valor);
    }

    default List<Method> getAllGetters()
    {
        var metodos = new ArrayList<Method>();
        for (var metodo : this.getClass().getMethods())
        {
            if (metodo.getName().startsWith("get") &&
                metodo.getParameterCount() == 0 &&
                metodo.getName().length() > 3)
            {
                metodos.add(metodo);
            }
        }
        return metodos;
    }

    default List<Method> getAllSetters()
    {
        var metodos = new ArrayList<Method>();
        for (var metodo : this.getClass().getMethods())
        {
            if (metodo.getName().startsWith("set") &&
                metodo.getParameterCount() == 1 &&
                metodo.getName().length() > 3)
            {
                metodos.add(metodo);
            }
        }
        return metodos;
    }
}
