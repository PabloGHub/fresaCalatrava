package safa.fresacalatrava;

import java.lang.reflect.ParameterizedType;

public abstract class TipoCapturado<D>
{
    private final Class<D> tipo;

    @SuppressWarnings("unchecked")
    protected TipoCapturado()
    {
        this.tipo = (Class<D>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public Class<D> getTipo()
    {
        return tipo;
    }
}