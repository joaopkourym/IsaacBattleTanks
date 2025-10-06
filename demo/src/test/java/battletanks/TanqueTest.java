package battletanks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.isaactanks.model.ClasseTanque;
import com.isaactanks.model.Tanque;

public class TanqueTest {
    
    @Test
    public void testCriacaoTanqueLeve() {
        Tanque tanque = new Tanque("Speedy", ClasseTanque.LEVE);
        
        assertEquals("Speedy", tanque.getCodinome());
        assertEquals(ClasseTanque.LEVE, tanque.getClasse());
        assertEquals(100.0, tanque.getIntegridade(), 0.01);
    }
    
    @Test
    public void testAtaqueEDano() {
        Tanque atacante = new Tanque("Atacante", ClasseTanque.MEDIO);
        Tanque alvo = new Tanque("Alvo", ClasseTanque.LEVE);
        
        double integridadeInicial = alvo.getIntegridade();
        atacante.atacar(alvo);
        
        assertTrue(alvo.getIntegridade() < integridadeInicial);
    }
    
    @Test
    public void testTanqueDestruido() {
        Tanque tanque = new Tanque("Fragil", ClasseTanque.LEVE);
        
        for (int i = 0; i < 10; i++) {
            tanque.receberDano(50);
        }
        
        assertEquals(0.0, tanque.getIntegridade(), 0.01);
    }
}