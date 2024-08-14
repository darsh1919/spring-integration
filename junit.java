import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class RuleProcessorDtoTransformerTest {

    @Mock
    private ApiService apiService;

    @Mock
    private DriverApiService driverApiService;

    @Mock
    private RuleEngineRequest ruleEngineRequest;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Map<String, String> rulsetNameConfigMappings;

    @InjectMocks
    private RuleProcessorDtoTransformer ruleProcessorDtoTransformer;

    private Message<String> mockMessage;
    private UfOto mockUfOto;
    private RuleEngineResponse mockResponse;
    private List<String> mockDriverList;

    @BeforeEach
    public void setUp() throws Exception {
        // Set up common mock objects, including the expected response structure
        mockMessage = mock(Message.class);
        mockUfOto = mock(UfOto.class);
        mockResponse = mock(RuleEngineResponse.class);
        mockDriverList = new ArrayList<>();

        // Configure mock behavior
        when(objectMapper.readValue(anyString(), eq(UfOto.class))).thenReturn(mockUfOto);
        when(mockUfOto.getSourceSystem()).thenReturn("sourceSystem");
        when(rulsetNameConfigMappings.get("sourceSystem")).thenReturn("ruleSetName");
        when(driverApiService.getRuleEngineResponse(anyString())).thenReturn(mockDriverList);

        // Mock the RuleEngineResponse and its internals as required
        // You need to adapt this part based on what exactly you want to test
    }

    @Test
    public void testTransform_SuccessfulCase() throws Exception {
        // Arrange
        ResponseEntity<RuleEngineResponse> summaryResponseEntity = mock(ResponseEntity.class);
        when(apiService.ruleEngineSummaryResult(any())).thenReturn(summaryResponseEntity);
        when(summaryResponseEntity.getStatusCodeValue()).thenReturn(200);
        when(summaryResponseEntity.getBody()).thenReturn(mockResponse);

        // Act
        RuleProcessorDto result = ruleProcessorDtoTransformer.transform(mockMessage);

        // Assert
        assertNotNull(result);
        // Add more assertions based on the expected result structure
    }

    @Test
    public void testTransform_ExceptionInDriverApiService() throws Exception {
        // Arrange
        when(driverApiService.getRuleEngineResponse(anyString())).thenThrow(new RuntimeException("Driver API failure"));

        // Act & Assert
        assertThrows(Exception.class, () -> ruleProcessorDtoTransformer.transform(mockMessage));
    }

    @Test
    public void testTransform_InvalidResponseFromApiService() throws Exception {
        // Arrange
        ResponseEntity<RuleEngineResponse> summaryResponseEntity = mock(ResponseEntity.class);
        when(apiService.ruleEngineSummaryResult(any())).thenReturn(summaryResponseEntity);
        when(summaryResponseEntity.getStatusCodeValue()).thenReturn(500);

        // Act & Assert
        assertThrows(Exception.class, () -> ruleProcessorDtoTransformer.transform(mockMessage));
    }
}


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverApiServiceTest {

    @InjectMocks
    private DriverApiService driverApiService;

    @Mock
    private ApiService apiService;

    @Test
    public void testGetRuleEngineResponse_Success() {
        String rulesetName = "testRuleset";
        List<String> expectedDriverDetails = List.of("driver1", "driver2");

        when(apiService.getDriverDetails(rulesetName)).thenReturn(expectedDriverDetails);

        List<String> actualDriverDetails = driverApiService.getRuleEngineResponse(rulesetName);

        assertEquals(expectedDriverDetails, actualDriverDetails);
    }

    // Add more test cases for different scenarios like:
    // - Handling exceptions from ApiService
    // - Testing cache behavior (hit and miss)
}
