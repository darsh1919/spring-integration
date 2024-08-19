
public class RuleEngineRequestTest {

    @Mock
    private Map<String, Map<String, Pair<String, String>>> appConfigMappings;

    @InjectMocks
    private RuleEngineRequest ruleEngineRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRuleEnginePayloadCreation() {
        // Mock data
        UfoDto data = mock(UfoDto.class);
        when(data.getId()).thenReturn("123");
        when(data.getSourceSystem()).thenReturn("SYSTEM1");

        // Sample inputs
        String ruleSetName = "TestRuleSet";
        List<String> driverList = List.of("Driver1", "Driver2");

        // Call the method to create payload
        PayloadRequest result = ruleEngineRequest.ruleEnginePayload(ruleSetName, driverList, data);

        assertNotNull(result, "Payload should not be null");
        assertEquals("123", result.getPayLoadRequest().get("id"), "ID should match");
        assertEquals("SYSTEM1", result.getPayLoadRequest().get("sourceSystem"), "Source system should match");
    }

    @Test
    public void testDateTransformation_CurrentDate() {
        // Simulate current date transformation
        String factData = "CURRENT";
        LocalDate transformedDate = ruleEngineRequest.transformDate(factData);

        assertEquals(LocalDate.now(), transformedDate, "Transformed date should be the current date");
    }

    @Test
    public void testDateTransformation_PreviousDate() {
        // Simulate previous date transformation
        String factData = "PREVIOUS";
        LocalDate transformedDate = ruleEngineRequest.transformDate(factData);

        assertEquals(LocalDate.now().minusDays(1), transformedDate, "Transformed date should be the previous day");
    }

    @Test
    public void testBusinessDaysCalculation_NoWeekends() {
        // Simulate a date range without weekends
        LocalDate start = LocalDate.of(2023, 8, 14);  // Monday
        LocalDate end = LocalDate.of(2023, 8, 18);    // Friday

        long businessDays = ruleEngineRequest.getBusinessDays(start, end);
        assertEquals(5, businessDays, "Should return 5 business days");
    }

    @Test
    public void testBusinessDaysCalculation_WithWeekend() {
        // Simulate a date range with a weekend in between
        LocalDate start = LocalDate.of(2023, 8, 14);  // Monday
        LocalDate end = LocalDate.of(2023, 8, 21);    // Next Monday

        long businessDays = ruleEngineRequest.getBusinessDays(start, end);
        assertEquals(6, businessDays, "Should return 6 business days (excluding weekend)");
    }
}
