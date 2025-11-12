package safa.fresacalatrava.controladores;

import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.servicio.ServiFinca;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/finca")
@AllArgsConstructor
public class ContFinca
{
    private final ServiFinca _serviFinca;

    //@GetMapping("/consultaTodo")
    public List<DtoFinca> listarFinca()
    {
        var _fallo = new DtoFallo();
        var datos = _serviFinca.DarmeTodo();
        return datos;
    }

    @GetMapping("/consultaUno")
    public DtoFinca DarUno(@RequestParam int eId)
    {
        var _fallo = new DtoFallo();
        var _finca = _serviFinca.DarmeUnoDto(eId, _fallo);
        // TODO: Gestionar fallos.
        return _finca;
    }

    @PostMapping("/creaUpdate")
    public DtoFallo CrearUpdate(@RequestBody DtoFinca eFinca)
    {
        var fallo = new DtoFallo();
        var datos = _serviFinca.CrearUpdate(eFinca, fallo);
        // TODO: Gestionar fallos.
        return datos;
    }
}
