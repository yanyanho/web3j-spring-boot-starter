package org.web3j.spring.autoconfigure;

import com.github.macdao.moscow.ContractAssertion;
import com.github.macdao.moscow.ContractContainer;
//import org.flywaydb.core.Flyway;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.spring.Application;

import java.nio.file.Paths;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ConfigurationProperties(prefix = "emall.test")
public abstract class ApiTestBase {

    private static final ContractContainer container = new ContractContainer(Paths.get("api-contracts/api"));

    private static boolean firstExecution = true;

    @Rule
    public final TestName name = new TestName();

    @Rule
    public final ExecutionTimeoutRule timeout = new ExecutionTimeoutRule();

    @Value("${local.server.port}")
    protected int port;

    private int executionTimeout;

//    @Autowired
//    private Flyway flyway;

//    @Before
//    public void setUp() throws Exception {
//        flyway.setBaselineOnMigrate(true);
//        flyway.clean();
//        flyway.migrate();
//    }

    protected Map<String, String> assertContract() {
        return assertContract("[hi]" + name.getMethodName());
    }

    protected Map<String, String> assertContract(String description) {
        final boolean necessity = description.contains("_cannot_");
        return new ContractAssertion(container.findContracts(description))
                .setPort(port)
                .setExecutionTimeout(executionTimeout())
                .setNecessity(necessity)
                .assertContract();
    }

    private int executionTimeout() {
        int executionTimeout = this.executionTimeout * timeout.getTimeout();
        if (firstExecution) {
            executionTimeout *= 2;
            firstExecution = false;
        }
        return executionTimeout;
    }

    public void setExecutionTimeout(int executionTimeout) {
        this.executionTimeout = executionTimeout;
    }
}


    
    