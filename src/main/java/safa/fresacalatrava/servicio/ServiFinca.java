package safa.fresacalatrava.servicio;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.modelo.Finca;
import safa.fresacalatrava.repositorio.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiFinca
{
    private RepFinca _repFinca;

    public DtoFinca Empaquetar(Finca eFinca, DtoFallo eFallo)
    {
        DtoFinca _novoDtoFinca = new DtoFinca();

        _novoDtoFinca.setId(eFinca.getId());
        _novoDtoFinca.setNombre(eFinca.getNombre());
        _novoDtoFinca.setLatitud(eFinca.getLatitud());
        _novoDtoFinca.setLongitud(eFinca.getLongitud());
        _novoDtoFinca.setSupercie(eFinca.getSupercie());

        return _novoDtoFinca;
    }
    public Finca Desampaquetar(DtoFinca eFinca)
    {
        Finca novoFinca = (eFinca.getId() == null) ? new Finca() : DarmeUno(eFinca.getId());

        novoFinca.setId
        (
            (eFinca.getId() != null) ? eFinca.getId() : novoFinca.getId()
        );

        novoFinca.setNombre
        (
                (eFinca.getNombre() != null) ? eFinca.getNombre() : novoFinca.getNombre()
        );
        novoFinca.setLatitud
        (
                (eFinca.getLatitud() != null) ? eFinca.getLatitud() : novoFinca.getLatitud()
        );
        novoFinca.setLongitud
        (
                (eFinca.getLongitud() != null) ? eFinca.getLongitud() : novoFinca.getLongitud()
        );
        novoFinca.setSupercie
        (
                (eFinca.getSupercie() != null) ? eFinca.getSupercie() : novoFinca.getSupercie()
        );

        return novoFinca;
    }

    public Finca DarmeUno(int eId)
    {
        var _finca = new Finca();
        _finca = _repFinca.findById(eId).orElse(null);

        return _finca;
    }
    public Finca DarmeUno(int eId, DtoFallo eFallo)
    {
        var _finca = new Finca();
        _finca = DarmeUno(eId);

        if (_finca == null)
        {
            _finca = new Finca();
            eFallo.setExito(false);
            eFallo.AddError("no_encontrado:finca");
            return _finca;
        }

        eFallo.setExito(true);
        return _finca;
    }

    public DtoFinca DarmeUnoDto(int eId, DtoFallo eFallo)
    {
        var _finca = DarmeUno(eId, eFallo);
        if (!eFallo.getExito())
        {
            return new DtoFinca();
        }
        return Empaquetar(_finca, eFallo);
    }

    public List<DtoFinca> DarmeTodoDto(DtoFallo eFallo)
    {
        var _fincas = _repFinca.findAll();

        return _fincas.stream()
                .map(f -> Empaquetar(f, eFallo))
                .toList();
    }

    public DtoFallo CrearUpdate(DtoFinca eFinca)
    {
        var eFallo = new DtoFallo();
        return CrearUpdate(eFinca, eFallo);
    }
    public DtoFallo CrearUpdate(DtoFinca eFinca, DtoFallo eFallo)
    {
        var _novoFinca = Desampaquetar(eFinca);

        if (_novoFinca == null)
        {
            eFallo.AddError("no_desempaquetado:finca");
            eFallo.setExito(false);
            return eFallo;
        }

        var f = _repFinca.save(_novoFinca);

        if  (f == null)
        {
            eFallo.AddError("no_guardado:finca");
            eFallo.setExito(false);
            return eFallo;
        }

        return eFallo;
    }

    public DtoFallo Eliminar(int eId)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eId, eFallo);
    }
    public DtoFallo Eliminar(DtoFinca eFinca)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eFinca.getId(), eFallo);
    }
    public DtoFallo Eliminar(int eId, DtoFallo eFallo)
    {
        var _finca = DarmeUno(eId, eFallo);
        if (!eFallo.getExito())
        {
            eFallo.AddError("no_encontrado:finca");
            return eFallo;
        }
        try
        {
            _repFinca.deleteById(_finca.getId());
        }
        catch (Exception ex)
        {
            eFallo.setExito(false);
            eFallo.AddError("no_borrado:finca");
            return eFallo;
        }

        return eFallo;
    }
}
