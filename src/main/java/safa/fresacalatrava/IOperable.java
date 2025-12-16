package safa.fresacalatrava;

public interface IOperable
{
    default <T> T castear(Class<T> type)
    {
        if (type.isInstance(this))
            return type.cast(this);
        return null;
    }

    default boolean sum(IGetterSetter eOtro)
    {
        if (!(this instanceof IGetterSetter gsThis) || eOtro == null)
            return false;

        try
        {
            for (var metodoGetter : gsThis.darmeAllGetters())
            {
                var valorThis = metodoGetter.invoke(gsThis);

                if (valorThis == null || (valorThis instanceof String && ((String) valorThis).isEmpty()))
                {
                    var nombreVariable = metodoGetter.getName().substring(3);
                    var valorOtro = metodoGetter.invoke(eOtro);
                    eOtro.set(nombreVariable, valorOtro);
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
