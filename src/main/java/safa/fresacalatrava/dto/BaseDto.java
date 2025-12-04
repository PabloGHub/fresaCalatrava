package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Deprecated // parapau.
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto implements IDTO
{
    private Boolean exito = true;
    private List<String> mensaje;

    public boolean AddError(String eMensaje)
    {
        if (this.mensaje == null)
            this.mensaje = new ArrayList<String>();

        return this.mensaje.add(eMensaje);
    }

    public void Limpiar()
    {
        this.exito = true;
        if (this.mensaje != null)
            this.mensaje.clear();
    }
}