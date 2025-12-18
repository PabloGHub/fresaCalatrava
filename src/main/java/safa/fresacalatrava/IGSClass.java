package safa.fresacalatrava;

public interface IGSClass
{
    default Class<?> getImplementingClass()
    {
        return this.getClass();
    }
}
