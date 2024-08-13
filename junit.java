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


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleEngineConfigTest {

    @Test
    public void testConstructor() {
        // Test different constructors
        RuleEngineConfig config1 = new RuleEngineConfig("source1", "upstream1", "rule1", "type1");
        RuleEngineConfig config2 = RuleEngineConfig.builder()
                .sourceSystem("source2")
                .upstreamFactName("upstream2")
                .ruleEngineFactName("rule2")
                .factType("type2")
                .build();

        assertNotNull(config1);
        assertNotNull(config2);

        assertEquals("source1", config1.getSourceSystem());
        assertEquals("upstream1", config1.getUpstreamFactName());
        assertEquals("rule1", config1.getRuleEngineFactName());
        assertEquals("type1", config1.getFactType());

        assertEquals("source2", config2.getSourceSystem());
        assertEquals("upstream2", config2.getUpstreamFactName());
        assertEquals("rule2", config2.getRuleEngineFactName());
        assertEquals("type2", config2.getFactType());
    }

    @Test
    public void testEqualsAndHashCode() {
        RuleEngineConfig config1 = new RuleEngineConfig("same", "same", "same", "same");
        RuleEngineConfig config2 = new RuleEngineConfig("same", "same", "same", "same");
        RuleEngineConfig config3 = new RuleEngineConfig("diff", "same", "same", "same");

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
        assertNotEquals(config1, config3);
        assertNotEquals(config1.hashCode(), config3.hashCode());
    }

    @Test
    public void testToString() {
        RuleEngineConfig config = new RuleEngineConfig("source", "upstream", "rule", "type");
        assertNotNull(config.toString());
        assertTrue(config.toString().contains("source"));
        assertTrue(config.toString().contains("upstream"));
        assertTrue(config.toString().contains("rule"));
        assertTrue(config.toString().contains("type"));
    }
}
