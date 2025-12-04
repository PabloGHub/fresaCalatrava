package safa.fresacalatrava.controladores;

import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.dto.DtoSalida;
import safa.fresacalatrava.dto.IDTO;
import safa.fresacalatrava.servicio.ServiFinca;
import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/fincas")
@AllArgsConstructor
public class ContFinca
{
    private final ServiFinca _serviFinca;

    @GetMapping("/todo")
    public IDTO listarFinca()
    {
        var _fallo = new DtoFallo();
        var _datos = _serviFinca.DarmeTodoDto(_fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_datos);
    }

    @GetMapping("/{id}")
    public IDTO DarmeUno(@PathVariable int id)
    {
        var _fallo = new DtoFallo();
        var _finca = _serviFinca.DarmeUnoDto(id, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_finca);
    }

    //@PostMapping("/creaUpdate")
    public IDTO CrearUpdate(@RequestBody DtoFinca eFinca)
    {
        DtoFallo fallo = new DtoFallo();
        _serviFinca.CrearUpdate(eFinca, fallo);
        if (!fallo.getExito())
        {
            return fallo;
        }

        return new DtoSalida<>(fallo);
    }
}
