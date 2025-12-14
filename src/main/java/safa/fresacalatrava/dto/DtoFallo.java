package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFallo implements IDTO
{

    private Boolean exito = true;
    private List<String> mensaje;

    //private DtoFallo warning;

    public boolean AddError(String eMensaje)
    {
        if (this.mensaje == null)
            this.mensaje = new ArrayList<String>();

        return this.mensaje.add(eMensaje);
    }

    /*
    public boolean AddWarning(String eMensaje)
    {
        return this.warning.AddError(eMensaje);
    }
     */

    public void Limpiar()
    {
        this.exito = true;
        if (this.mensaje != null)
            this.mensaje.clear();
        //if (this.warning != null)
        //    this.warning.Limpiar();
    }

    public DtoFallo(BaseDto eDTO)
    {
        //super();
        setExito(eDTO.getExito());
        setMensaje(eDTO.getMensaje());
    }
}
