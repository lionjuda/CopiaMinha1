import org.example.entities.FormaPagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class FormaPagamentoTest {

    private FormaPagamento formaPagamento;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        formaPagamento = new FormaPagamento(1L, "Cartão de Crédito", "S", true, 12, new BigDecimal("1.50"));
    }

    @Test
    void testConstrutorEGetters() {
        assertEquals(1L, formaPagamento.getFpgId());
        assertEquals("Cartão de Crédito", formaPagamento.getFpgDescricao());
        assertEquals("S", formaPagamento.getFpgTipo());
        assertTrue(formaPagamento.getFpgPermiteParcelamento());
        assertEquals(12, formaPagamento.getFpgNumMaxParcelas());
        assertEquals(new BigDecimal("1.50"), formaPagamento.getFpgTaxaAdicional());
    }

    @Test
    void testSettersValidos() {
        formaPagamento.setFpgDescricao("Dinheiro");
        formaPagamento.setFpgTipo("N");
        formaPagamento.setFpgPermiteParcelamento(false);
        formaPagamento.setFpgNumMaxParcelas(6);
        formaPagamento.setFpgTaxaAdicional(new BigDecimal("0.75"));

        assertEquals("Dinheiro", formaPagamento.getFpgDescricao());
        assertEquals("N", formaPagamento.getFpgTipo());
        assertFalse(formaPagamento.getFpgPermiteParcelamento());
        assertEquals(6, formaPagamento.getFpgNumMaxParcelas());
        assertEquals(new BigDecimal("0.75"), formaPagamento.getFpgTaxaAdicional());
    }

    @Test
    void testDescricaoNula() {
        formaPagamento.setFpgDescricao(null);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Descrição é obrigatório", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgDescricao"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgTipoNulo() {
        formaPagamento.setFpgTipo(null);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Tipo é obrigatório", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgTipo"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgTipoInvalido() {
        formaPagamento.setFpgTipo("X");
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Tipo inválido (use S ou N)", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgTipo"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgPermiteParcelamentoNulo() {
        formaPagamento.setFpgPermiteParcelamento(null);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Permite Parcelamento é obrigatório", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgPermiteParcelamento"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgNumMaxParcelasNulo() {
        formaPagamento.setFpgNumMaxParcelas(null);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Número máximo de parcelas é obrigatório", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgNumMaxParcelas"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgNumMaxParcelasInvalido() {
        formaPagamento.setFpgNumMaxParcelas(0);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Número máximo de parcelas deve ser pelo menos 1", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgNumMaxParcelas"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgTaxaAdicionalNula() {
        formaPagamento.setFpgTaxaAdicional(null);
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Taxa adicional é obrigatória", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgTaxaAdicional"))
                .findFirst().get().getMessage());
    }

    @Test
    void testFpgTaxaAdicionalNegativa() {
        formaPagamento.setFpgTaxaAdicional(new BigDecimal("-1.00"));
        Set<ConstraintViolation<FormaPagamento>> violations = validator.validate(formaPagamento);
        assertFalse(violations.isEmpty());
        assertEquals("Taxa adicional não pode ser negativa", violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fpgTaxaAdicional"))
                .findFirst().get().getMessage());
    }
}