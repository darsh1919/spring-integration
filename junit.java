import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

public class PayloadHeadersTest {

    @Test
    public void testDefaultValues() {
        // Arrange
        PayloadHeaders payloadHeaders = new PayloadHeaders();

        // Assert default values
        assertEquals("Default", payloadHeaders.getDomain(), "Default domain should be 'Default'");
        assertNull(payloadHeaders.getRequestId(), "Request ID should be null by default");
        assertEquals("UFO", payloadHeaders.getSourceSystem(), "Default source system should be 'UFO'");
        assertFalse(payloadHeaders.isDomainSchemaValidation(), "Default domainSchemaValidation should be false");
        assertEquals(Collections.singletonList(""), payloadHeaders.getTags(), "Default tags should be a list with an empty string");
        assertFalse(payloadHeaders.isEnrichFact(), "Default enrichFact should be false");
        assertNull(payloadHeaders.getRuleSetName(), "RuleSetName should be null by default");
    }

    @Test
    public void testCustomValues() {
        // Arrange
        PayloadHeaders payloadHeaders = new PayloadHeaders();
        payloadHeaders.setDomain("CustomDomain");
        payloadHeaders.setRequestId("12345");
        payloadHeaders.setSourceSystem("CustomSystem");
        payloadHeaders.setDomainSchemaValidation(true);
        payloadHeaders.setTags(Collections.singletonList("Tag1"));
        payloadHeaders.setEnrichFact(true);
        payloadHeaders.setRuleSetName("CustomRuleSet");

        // Assert custom values
        assertEquals("CustomDomain", payloadHeaders.getDomain(), "Domain should be 'CustomDomain'");
        assertEquals("12345", payloadHeaders.getRequestId(), "Request ID should be '12345'");
        assertEquals("CustomSystem", payloadHeaders.getSourceSystem(), "Source system should be 'CustomSystem'");
        assertTrue(payloadHeaders.isDomainSchemaValidation(), "domainSchemaValidation should be true");
        assertEquals(Collections.singletonList("Tag1"), payloadHeaders.getTags(), "Tags should be a list with 'Tag1'");
        assertTrue(payloadHeaders.isEnrichFact(), "enrichFact should be true");
        assertEquals("CustomRuleSet", payloadHeaders.getRuleSetName(), "RuleSetName should be 'CustomRuleSet'");
    }
}
