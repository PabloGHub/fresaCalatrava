package safa.fresacalatrava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.dto.DtoFresa;
import safa.fresacalatrava.modelo.Finca;
import safa.fresacalatrava.modelo.Fresa;
import safa.fresacalatrava.servicio.ServiGeneral;

@SpringBootTest
public class TestGeneral
{
    @Autowired
    private ServiGeneral<Finca> _serviGeneral;

    @Test
    void testDarUno()
    {
        DtoFallo fallo = new DtoFallo();
        var finca = _serviGeneral.DarmeUno(Finca.class, 1, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUno: " + finca);
        System.out.println("Error: " + fallo + "\n");
    }

    @Test
    void testEmpaquetar()
    {
        DtoFallo fallo = new DtoFallo();
        var finca = _serviGeneral.DarmeUno( Finca.class, 1, fallo);
        DtoFinca dtoFinca = (DtoFinca) _serviGeneral.Empaquetar(DtoFinca.class, finca, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUno: " + finca);
        System.out.println("Empaquetar: " + dtoFinca);
        System.out.println("Error: " + fallo + "\n");
    }
    @Test
    void testDarUnoDto()
    {
        DtoFallo fallo = new DtoFallo();
        var finca = _serviGeneral.DarmeUnoDto(DtoFresa.class, 1, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUnoDto: " + finca);
        System.out.println("Error: " + fallo + "\n");
    }

    @Test
    void testDesempaquetar()
    {
        DtoFallo fallo = new DtoFallo();
        DtoFresa dtofinca = _serviGeneral.DarmeUnoDto(DtoFresa.class, 1, fallo);
        Fresa finca = (Fresa) _serviGeneral.Empaquetar(Fresa.class, dtofinca, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUnoDto: " + dtofinca);
        System.out.println("Empaquetar: " + finca);
        System.out.println("Error: " + fallo + "\n");
    }

    @Test
    void testCrear()
    {
        DtoFallo fallo = new DtoFallo();
        DtoFinca dtofinca = new DtoFinca();
        dtofinca.setNombre("Finca de Prueba");
        dtofinca.setLatitud(40.12345f);
        dtofinca.setLongitud(-3.12345f);
        dtofinca.setSupercie(150);
        var dtoVuelta = _serviGeneral.Create(dtofinca, fallo);

        System.out.println("====================================");
        System.out.println("Crear: " + dtoVuelta);
        System.out.println("Error: " + fallo + "\n");
    }

    @Test
    void testActualizar()
    {
        DtoFallo fallo = new DtoFallo();
        var finca = _serviGeneral.DarmeUno(Finca.class, 4, fallo);
        DtoFinca dtoFinca = (DtoFinca) _serviGeneral.<DtoFinca>Empaquetar(DtoFinca.class, (IGetterSetter) finca, fallo);

        dtoFinca.setSupercie(dtoFinca.getSupercie() + 100);

        var dtoVuelta = _serviGeneral.Update(dtoFinca, fallo);

        System.out.println("====================================");
        System.out.println("Update: " + dtoVuelta);
        System.out.println("Error: " + fallo + "\n");
    }
}
