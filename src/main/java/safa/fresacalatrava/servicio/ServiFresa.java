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

        _dto.set_id(eFresa.getId());
        _dto.set_codigo(eFresa.getCodigo());
        _dto.set_nombre(eFresa.getNombre());
        _dto.set_tipo(eFresa.getTipo());
        _dto.set_temporada(eFresa.getTemporada());
        _dto.set_rendimiento(eFresa.getRendimiento());
        return _dto;
    }

    public Fresa Desempaquetar(DtoFresa eDtoFresa, DtoFallo eFallo)
    {
        Fresa _novoFresa = (eDtoFresa.get_id() == null) ? new Fresa() : _repFresa.findById(eDtoFresa.get_id()).orElse(null);

        if (_novoFresa == null)
        {
            eFallo.AddError("no_encontrado:fresa");
            eFallo.setExito(false);
            return null;
        }

        _novoFresa.setId((eDtoFresa.get_id() != null) ? eDtoFresa.get_id() : _novoFresa.getId());
        _novoFresa.setCodigo((eDtoFresa.get_codigo() != null) ? eDtoFresa.get_codigo() : _novoFresa.getCodigo());
        _novoFresa.setNombre((eDtoFresa.get_nombre() != null) ? eDtoFresa.get_nombre() : _novoFresa.getNombre());
        _novoFresa.setTipo((eDtoFresa.get_tipo() != null) ? eDtoFresa.get_tipo() : _novoFresa.getTipo());
        _novoFresa.setTemporada((eDtoFresa.get_temporada() != null) ? eDtoFresa.get_temporada() : _novoFresa.getTemporada());
        _novoFresa.setRendimiento((eDtoFresa.get_rendimiento() != null) ? eDtoFresa.get_rendimiento() : _novoFresa.getRendimiento());

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
        return Eliminar(eFresa.get_id(), eFallo);
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
