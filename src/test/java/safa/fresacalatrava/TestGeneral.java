package safa.fresacalatrava;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import safa.fresacalatrava.dto.DtoFallo;
import safa.fresacalatrava.dto.DtoFinca;
import safa.fresacalatrava.modelo.Finca;
import safa.fresacalatrava.repositorio.RepFinca;
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
        Finca finca = _serviGeneral.DarmeUno(1, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUno: " + finca + "\n");
    }

    @Test
    void testEmpaquetar()
    {
        DtoFallo fallo = new DtoFallo();
        Finca finca = _serviGeneral.DarmeUno( 1, fallo);
        DtoFinca dtoFinca = _serviGeneral.<Finca, DtoFinca>Empaquetar(DtoFinca.class, finca, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUno: " + finca);
        System.out.println("Empaquetar: " + dtoFinca + "\n");
    }
    @Test
    void testDarUnoDto()
    {
        DtoFallo fallo = new DtoFallo();
        var finca = _serviGeneral.DarmeUnoDtoEspecifico(DtoFinca.class, 1, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUnoDto: " + finca + "\n");
    }

    @Test
    void testDesempaquetar()
    {
        DtoFallo fallo = new DtoFallo();
        DtoFinca dtofinca = _serviGeneral.DarmeUnoDtoEspecifico(DtoFinca.class, 1, fallo);
        Finca finca = _serviGeneral.<DtoFinca, Finca>Empaquetar(Finca.class, dtofinca, fallo);

        System.out.println("====================================");
        System.out.println("DarmeUnoDto: " + dtofinca);
        System.out.println("Empaquetar: " + finca + "\n");
    }
}
