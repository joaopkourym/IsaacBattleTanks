package battletanks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.isaactanks.model.ClasseTanque;
import com.isaactanks.model.Tanque;
import com.isaactanks.util.CSVHandler;

public class CSVHandlerTest {
    
    @Test
    public void testExportarImportarTanques() throws Exception {
        List<Tanque> tanquesOriginais = new ArrayList<>();
        tanquesOriginais.add(new Tanque("Test1", ClasseTanque.LEVE));
        
        String arquivo = "test_tanques.csv";
        CSVHandler.exportarTanques(tanquesOriginais, arquivo);
        
        File file = new File(arquivo);
        assertTrue(file.exists());
        
        List<Tanque> tanquesImportados = CSVHandler.importarTanques(arquivo);
        assertEquals(1, tanquesImportados.size());
        
        file.delete();
    }
}