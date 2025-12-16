package safa.fresacalatrava.servicio;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFresa;
import safa.fresacalatrava.modelo.Fresa;
import safa.fresacalatrava.repositorio.RepFresa;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiFresa
{
    private final RepFresa _repFresa;

    public DtoFresa Empaquetar(Fresa eFresa, DtoFallo eFallo)
    {
        var _dto = new DtoFresa();
        if (eFresa == null)
        {
            eFallo.AddError("no_encontrado:fresa");
            eFallo.setExito(false);
            return _dto;
        }

        _dto.setId(eFresa.getId());
        _dto.setCodigo(eFresa.getCodigo());
        _dto.setNombre(eFresa.getNombre());
        _dto.setTipo(eFresa.getTipo());
        _dto.setTemporada(eFresa.getTemporada());
        _dto.setRendimiento(eFresa.getRendimiento());
        return _dto;
    }

    public Fresa Desempaquetar(DtoFresa eDtoFresa, DtoFallo eFallo)
    {
        Fresa _novoFresa = (eDtoFresa.getId() == null) ? new Fresa() : _repFresa.findById(eDtoFresa.getId()).orElse(null);

        if (_novoFresa == null)
        {
            eFallo.AddError("no_encontrado:fresa");
            eFallo.setExito(false);
            return null;
        }

        _novoFresa.setId((eDtoFresa.getId() != null) ? eDtoFresa.getId() : _novoFresa.getId());
        _novoFresa.setCodigo((eDtoFresa.getCodigo() != null) ? eDtoFresa.getCodigo() : _novoFresa.getCodigo());
        _novoFresa.setNombre((eDtoFresa.getNombre() != null) ? eDtoFresa.getNombre() : _novoFresa.getNombre());
        _novoFresa.setTipo((eDtoFresa.getTipo() != null) ? eDtoFresa.getTipo() : _novoFresa.getTipo());
        _novoFresa.setTemporada((eDtoFresa.getTemporada() != null) ? eDtoFresa.getTemporada() : _novoFresa.getTemporada());
        _novoFresa.setRendimiento((eDtoFresa.getRendimiento() != null) ? eDtoFresa.getRendimiento() : _novoFresa.getRendimiento());

        return _novoFresa;
    }

    public Fresa DarmeUno(int eId, DtoFallo eFallo)
    {
        var _fresa = _repFresa.findById(eId).orElse(null);
        if (_fresa == null)
        {
            eFallo.AddError("no_encontrado:fresa");
            eFallo.setExito(false);
        }
        return _fresa;
    }

    public DtoFresa DarmeUnoDto(int eId, DtoFallo eFallo)
    {
        var _fresa = DarmeUno(eId, eFallo);
        if (!eFallo.getExito())
        {
            return new DtoFresa();
        }
        return Empaquetar(_fresa, eFallo);
    }

    public List<DtoFresa> DarmeTodo(DtoFallo eFallo)
    {
        var _todas = _repFresa.findAll();
        return _todas.stream()
                .map(f -> Empaquetar(f, eFallo))
                .toList();
    }

    public DtoFallo CrearUpdate(DtoFresa eFresa)
    {
        var eFallo = new DtoFallo();
        return CrearUpdate(eFresa, eFallo);
    }

    public DtoFallo CrearUpdate(DtoFresa eFresa, DtoFallo eFallo)
    {
        var _novo = Desempaquetar(eFresa, eFallo);

        if (_novo == null)
        {
            eFallo.AddError("no_desempaquetado:fresa");
            eFallo.setExito(false);
            return eFallo;
        }

        _repFresa.save(_novo);
        eFallo.setExito(true);
        return eFallo;
    }

    public DtoFallo Eliminar(int eId)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eId, eFallo);
    }

    public DtoFallo Eliminar(DtoFresa eFresa)
    {
        var eFallo = new DtoFallo();
        return Eliminar(eFresa.getId(), eFallo);
    }

    public DtoFallo Eliminar(int eId, DtoFallo eFallo)
    {
        var _fresa = DarmeUno(eId, eFallo);
        if (!eFallo.getExito())
        {
            eFallo.AddError("no_encontrado:fresa");
            return eFallo;
        }

        try
        {
            _repFresa.deleteById(_fresa.getId());
        }
        catch (Exception ex)
        {
            eFallo.setExito(false);
            eFallo.AddError("no_eliminado:fresa");
            return eFallo;
        }
        return eFallo;
    }
}
