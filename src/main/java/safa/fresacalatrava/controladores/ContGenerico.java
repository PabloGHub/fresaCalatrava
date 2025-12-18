package safa.fresacalatrava.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import safa.fresacalatrava.dto.*;
import safa.fresacalatrava.modelo.Finca;
import safa.fresacalatrava.modelo.Fresa;
import safa.fresacalatrava.modelo.Invernadero;
import safa.fresacalatrava.servicio.ServiGeneral;

import java.util.Map;

@RestController
@RequestMapping("/generico")
public class ContGenerico
{
    private final ServiGeneral _serviGeneral;

    public ContGenerico(ServiGeneral serviGeneral)
    {
        _serviGeneral = serviGeneral;
    }



    private Class<?> getDto(String eTipo, DtoFallo fallo)
    {
        if (eTipo == null || eTipo.trim().isEmpty())
        {
            fallo.setExito(false);
            fallo.AddError("no_tipo:null_or_empty");
            return null;
        }

        String buscar = eTipo.trim();
        String normalized = buscar.replaceAll("(?i)^dto", "").replaceAll("(?i)dto$", "");

        Class<?> found = null;
        for (Class<?> dtoClass : ServiGeneral.modelos.keySet())
        {
            String name = dtoClass.getSimpleName();
            if (name.equalsIgnoreCase(buscar) ||
                    name.equalsIgnoreCase(normalized) ||
                    name.equalsIgnoreCase(normalized + "Dto"))
            {
                found = dtoClass;
                break;
            }
        }

        if (found == null)
        {
            fallo.setExito(false);
            fallo.AddError("no_modelo:" + eTipo);
        }

        return found;
    }

    private Class<?> getModelo(IDTO eDto, DtoFallo fallo)
    {
        var m = ServiGeneral.modelos.get(eDto.getClass());
        if (m == null)
        {
            fallo.setExito(false);
            fallo.AddError("no_modelo:" + eDto.getClass().getSimpleName());
        }
        return m;
    }

    @GetMapping("/{eTipo}/t")
    public IDTO DarmeTodo(@PathVariable String eTipo)
    {
        var _fallo = new DtoFallo();
        var m = getDto(eTipo, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        var _fresa = _serviGeneral.DarmeTodoDto(m, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }



    @PostMapping("/c")
    public IDTO Crear(@RequestBody IDTO eDto)
    {
        var _fallo = new DtoFallo();
        var m = getModelo(eDto, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        var _fresa = _serviGeneral.Create(m, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }

    @PostMapping("/{eTipo}/c")
    public IDTO Crear(@PathVariable String eTipo, Object payload) // @RequestBody Map<String, Object> payload
    {
        var _fallo = new DtoFallo();

        // obtener la clase DTO concreta a partir del tipo recibido
        var dtoClass = getDto(eTipo, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        // convertir el payload genérico al DTO concreto
        ObjectMapper mapper = new ObjectMapper();
        IDTO dto = (IDTO) mapper.convertValue(payload, dtoClass);

        // obtener el modelo mapeado para ese DTO
        //var m = getModelo(dto, _fallo);
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        // llamar al servicio. Si tu servicio espera también el DTO, ajusta la firma de Create.
        var _fresa = _serviGeneral.Create(dto, _fallo); // si no existe esta sobrecarga, usa la que tengas y pásale dto si procede
        if (!_fallo.getExito())
        {
            return _fallo;
        }

        return new DtoSalida<>(_fresa);
    }
}
