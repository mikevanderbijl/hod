package nl.trifork.bank.accountms;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.trifork.bank.accountms.DAO.AccountDAO;
import nl.trifork.bank.accountms.controller.MainController;
import nl.trifork.bank.accountms.exception.AccountException;
import nl.trifork.bank.accountms.model.Account;
import nl.trifork.bank.accountms.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountMsApplicationTests {

    private final static Logger logger = LoggerFactory.getLogger(AccountMsApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService mockService;

    @Mock
    private WebRequest mockRequest;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        Mockito.when(mockRequest.getHeader("If-Match")).thenReturn("0");
        Mockito.when(mockRequest.getHeader("userId")).thenReturn("1");
    }

    @Test
    public void createTestAccount() throws Exception {
        Account mockAccount = new Account("name", "description 1");
        String accJSON = objectMapper.writeValueAsString(mockAccount);

        logger.info(accJSON);

        mockMvc.perform(post("/accounts/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(mockRequest))
                .content(accJSON)
                .header("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}