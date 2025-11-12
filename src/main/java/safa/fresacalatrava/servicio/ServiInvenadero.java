package safa.fresacalatrava.servicio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.dto.DtoInvenadero;
import safa.fresacalatrava.modelo.Invernadero;
import safa.fresacalatrava.repositorio.RepInvernadero;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServiInvenadero
{
    private RepInvernadero _repInvernadero;
    private final ServiFinca _serviFinca;

    public DtoInvenadero Empaquetar(Invernadero eInvernadero, DtoFallo eFallo)
    {
        var _novoInvernadero = new DtoInvenadero();
        _novoInvernadero.set_id(eInvernadero.getId());
        _novoInvernadero.set_codigo(eInvernadero.getCodigo());
        _novoInvernadero.set_capacidad(eInvernadero.getCapacidad());
        _novoInvernadero.set_tipo(eInvernadero.getTipo());

        var finca = _serviFinca.Empaquetar(eInvernadero.getFinca(),  eFallo);
        _novoInvernadero.set_finca(finca);

        return _novoInvernadero;
    }
    public Invernadero Desempaquetar(DtoInvenadero eDtoInvernadero, DtoFallo eFallo)
    {
        Invernadero _novoInvernadero = (eDtoInvernadero.get_id() == null) ? new Invernadero() : _repInvernadero.findById(eDtoInvernadero.get_id()).orElse(null);

        if (_novoInvernadero == null)
        {
            eFallo.AddError("No se encontró el invernadero con ID: " + eDtoInvernadero.get_id());
            return null;
        }

        _novoInvernadero.setId
        (
            (eDtoInvernadero.get_id() != null) ? eDtoInvernadero.get_id() : _novoInvernadero.getId()
        );

        _novoInvernadero.setCodigo
        (
            (eDtoInvernadero.get_codigo() != null) ? eDtoInvernadero.get_codigo() : _novoInvernadero.getCodigo()
        );
        _novoInvernadero.setCapacidad
        (
            (eDtoInvernadero.get_capacidad() != null) ? eDtoInvernadero.get_capacidad() : _novoInvernadero.getCapacidad()
        );
        _novoInvernadero.setTipo
        (
            (eDtoInvernadero.get_tipo() != null) ? eDtoInvernadero.get_tipo() : _novoInvernadero.getTipo()
        );

        if (eDtoInvernadero.get_finca() != null)
        {
            var finca = _serviFinca.Desampaquetar(eDtoInvernadero.get_finca());
            if (finca != null)
            {
                _novoInvernadero.setFinca(finca);
            }
            else
            {
                eFallo.AddError("[404:finca]El finca no existe");
            }
        }

        return _novoInvernadero;
    }

    public Invernadero DarmeUno(int eId)
    {
        var _invernadero = new Invernadero();
        _invernadero = _repInvernadero.findById(eId).orElse(null);
        return _invernadero;
    }

    public Invernadero DarmeUno(int eId, DtoFallo eFallo)
    {
        var _invernadero = new Invernadero();
        _invernadero = _repInvernadero.findById(eId).orElse(null);
        if (_invernadero == null)
        {
            eFallo.AddError("[404:Invernadero]");
            eFallo.set_exito(false);
        }
        return _invernadero;
    }

    public DtoInvenadero DarmeUnoDto(int eId, DtoFallo eFallo)
    {
        var _invernadero = DarmeUno(eId, eFallo);
        if (!eFallo.get_exito())
        {
            return new DtoInvenadero();
        }
        var _dtoInvernadero = Empaquetar(_invernadero, eFallo);
        return _dtoInvernadero;
    }

    public List<DtoInvenadero> DarmeTodo(DtoFallo eFallo)
    {
        var _invernaderos = _repInvernadero.findAll();

        return _invernaderos.stream()
                .map(e -> Empaquetar(e, eFallo))
                .toList();
    }

    public DtoFallo CrearUpdate(DtoInvenadero eInvernadero)
    {
        var eFallo = new DtoFallo();
        return CrearUpdate(eInvernadero, eFallo);
    }
    public DtoFallo CrearUpdate(DtoInvenadero eInvernadero, DtoFallo eFallo)
    {
        var _novoInvernadero = Desempaquetar(eInvernadero, eFallo);

        if (_novoInvernadero == null)
        {
            eFallo.AddError("[CrearUpdate: Error ocurrido al desampaquetar]");
            eFallo.set_exito(false);
            return eFallo;
        }

        _repInvernadero.save(_novoInvernadero);

        eFallo.set_exito(true);
        return eFallo;
    }

    public DtoFallo Eliminar(int eId)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eId, eFallo);
    }
    public DtoFallo Eliminar(DtoInvenadero eInvernadero)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eInvernadero.get_id(), eFallo);
    }
    public DtoFallo Eliminar(int eId, DtoFallo eFallo)
    {
        var _invernadero = DarmeUno(eId, eFallo);
        if (!eFallo.get_exito())
        {
            eFallo.AddError("[1001:Invernadero]");
            return eFallo;
        }

        try
        {
            _repInvernadero.deleteById(_invernadero.getId());
        }
        catch (Exception ex)
        {
            eFallo.set_exito(false);
            eFallo.AddError("[1001:Invernadero]");
            return eFallo;
        }
        return eFallo;
    
    }
}
