package safa.fresacalatrava;

public interface Operable
{
    default boolean sum(GetterSetter eOtro)
    {
        if (!(this instanceof GetterSetter gsThis) || eOtro == null)
            return false;

        try
        {
            for (var metodoGetter : gsThis.getAllGetters())
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
