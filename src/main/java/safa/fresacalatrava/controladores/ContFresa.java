package safa.fresacalatrava.controladores;


import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.*;
import safa.fresacalatrava.modelo.Fresa;
import safa.fresacalatrava.servicio.ServiGeneral;

@RestController
@RequestMapping("/fresa")
public class ContFresa // CRUD: Create, Read, Update, Delete
{
    private final ServiGeneral<Fresa> _serviGeneral;

    public ContFresa(ServiGeneral<Fresa> serviGeneral)
    {
        _serviGeneral = serviGeneral;
        _serviGeneral.setTipoModelo(Fresa.class);
    }

    @GetMapping
    public IDTO DarmeTodo()
    {
        var _fallo = new DtoFallo();
        var _fresa = _serviGeneral.DarmeTodoDto(DtoFresa.class, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }

    @GetMapping("/{id}")
    public IDTO DarmeUno(@PathVariable int id)
    {
        var _fallo = new DtoFallo();
        var _fresa = _serviGeneral.DarmeUnoDto(DtoFresa.class, id, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }

    @PostMapping("/crear")
    public IDTO Crear(@RequestBody DtoFresa eFresa)
    {
        var _fallo = new DtoFallo();
        var _fresa = _serviGeneral.Create(eFresa, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }

    @PostMapping("/act")
    public IDTO Actualizar(@RequestBody DtoFresa eFresa)
    {
        var _fallo = new DtoFallo();
        var _fresa = _serviGeneral.Update(eFresa, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }

    @PostMapping("/eli/{id}")
    public IDTO Eliminar(@PathVariable int id)
    {
        var _fallo = new DtoFallo();
        _serviGeneral.Eliminar(Fresa.class, id, _fallo);
        return _fallo;
    }
}
