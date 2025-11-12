package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFallo
{
    private Boolean _exito = true;
    private List<String> _mensaje;

    public void AddError(String eMensaje)
    {
        if (this._mensaje == null)
            this._mensaje = new ArrayList<String>();

        this._mensaje.add(eMensaje);
    }
}
