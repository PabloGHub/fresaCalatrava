package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.Operable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoSalida<D> implements IDTO, GetterSetter, Operable
{
    private String token;
    private D dto;

    public <D> D Convertir(Class<D> type)
    {
        return type.cast(dto);
    }

    public DtoSalida(D dto)
    {
        this.token = "PlaceHolderToken";
        this.dto = dto;
    }
}
