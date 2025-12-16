package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.IGetterSetter;
import safa.fresacalatrava.IOperable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSalidaList<D> implements IDTO
{
    private String token;
    private Iterable<D> dtoList;

    public <D> Iterable<D> Convertir(Class<D> type)
    {
        return (Iterable<D>) dtoList;
    }

    public DtoSalidaList(Iterable<D> dtoList)
    {
        this.token = "PlaceHolderToken";
        this.dtoList = dtoList;
    }
}
