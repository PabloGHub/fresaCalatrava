package safa.fresacalatrava.controladores;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.dto.DtoInvenadero;
import safa.fresacalatrava.servicio.ServiInvenadero;

import java.util.List;

@RestController
@RequestMapping("/invernadero")
@AllArgsConstructor
public class ContInvernadero
{
    private final ServiInvenadero _serviInvenadero;

    public List<DtoInvenadero> ListarInvernadero()
    {
        var fallo = new DtoFallo();
        var _todos = _serviInvenadero.DarmeTodo(fallo);
        // TODO: Gestionar fallos.
        return _todos;
    }

    @GetMapping("/consultaUno")
    public DtoInvenadero DarUno(@RequestParam int eId)
    {
        var _fallo = new DtoFallo();
        var _dato = _serviInvenadero.DarmeUnoDto(eId, _fallo);
        // TODO: Gestionar fallos.
        return _dato;
    }

    @PostMapping("/creaUpdate")
    public DtoFallo CrearUpdate(@RequestBody DtoInvenadero eInvernadero)
    {
        var _fallo = new DtoFallo();
        var datos = _serviInvenadero.CrearUpdate(eInvernadero, _fallo);
        // TODO: Gestionar fallos.
        return datos;
    }

    @PostMapping("/eliminar")
    public DtoFallo Eliminar(@RequestParam int eId)
    {
        return _serviInvenadero.Eliminar(eId);
    }
}
