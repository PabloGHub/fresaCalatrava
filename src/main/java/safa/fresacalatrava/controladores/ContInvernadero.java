package safa.fresacalatrava.controladores;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.*;
import safa.fresacalatrava.servicio.ServiInvenadero;

import java.util.List;

@RestController
@RequestMapping("/invernaderos")
@AllArgsConstructor
public class ContInvernadero
{
    private final ServiInvenadero _serviInvenadero;

    @GetMapping("/todo")
    public IDTO ListarInvernadero()
    {
        var fallo = new DtoFallo();
        var _todos = _serviInvenadero.DarmeTodo(fallo);
        if (!fallo.getExito())
        {
            return fallo;
        }

        return new DtoSalidaList<>(_todos);
    }

    @GetMapping("/{id}")
    public IDTO DarUno(@PathVariable int id)
    {
        var _fallo = new DtoFallo();
        var _dato = _serviInvenadero.DarmeUnoDto(id, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_dato);
    }

    @PostMapping()
    public IDTO CrearUpdate(@RequestBody DtoInvenadero eInvernadero)
    {
        DtoFallo _fallo = new DtoFallo();
        _serviInvenadero.CrearUpdate(eInvernadero, _fallo);
        // TODO: Gestionar fallos.
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fallo);
    }

    @PostMapping("/eliminar")
    public IDTO Eliminar(@RequestParam int eId)
    {
        return _serviInvenadero.Eliminar(eId);
    }
}
