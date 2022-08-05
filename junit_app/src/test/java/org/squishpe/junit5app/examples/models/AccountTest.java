package org.squishpe.junit5app.examples.models;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.squishpe.junit5app.examples.exceptions.InsufficientMoneyExeption;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assumptions.*;

class AccountTest {

    Account account;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeEach
    void initMethodTest(TestInfo testInfo, TestReporter testReporter) {
        this.account =  new Account("Stalyn", new BigDecimal(10));
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        // for print info of test

        System.out.println("Init-Test");
        System.out.println("Executing: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().orElse(null).getName());
        testReporter.publishEntry("print in log");
    }

    @Test
    @DisplayName("testing account name")
    void testCountName() {

        System.out.println(testInfo.getDisplayName());
        String expectedValue = "Stalyn";
        String realValue = account.getPerson();
        assertNotNull(realValue, () -> "Person of account cant null value"); // in expresion lambda will crear method thar build message error
        assertEquals(expectedValue, realValue, "The name od accoun is not expected");
    }


    @Test
    @DisplayName("testing account name")
    void testCountNameInfo() {

        String expectedValue = "Stalyn";
        String realValue = account.getPerson();
        assertNotNull(realValue, () -> "Person of account cant null value"); // in expresion lambda will crear method thar build message error
        assertEquals(expectedValue, realValue, "The name od accoun is not expected");
    }

    @Test
    void testBalanceAccount() {
        assertNotNull(account.getBalance());
        assertEquals(10, account.getBalance().doubleValue());
    }

    @Test
    void testReferenceAccount() {
        account = new Account("Stalyn", new BigDecimal("89.997"));
        Account account2 = new Account("Stalyn", new BigDecimal("89.997"));

        //assertNotEquals(account2, account);
        //tomo con comparación el equal de la clase
        assertEquals(account2, account);
    }

    @Test
    void testDebitAccount() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.debit(new BigDecimal(100));
        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.123456", account.getBalance().toPlainString());
    }

    @Test
    void testCreditAccount() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.credit(new BigDecimal(100));
        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.123456", account.getBalance().toPlainString());
    }

    @Test
    void testInsufficientMoneyException() {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));

        Exception exception =  assertThrows(InsufficientMoneyExeption.class, ()-> {
            account.debit(new BigDecimal(2000));
        });

        String messageException  = exception.getMessage();
        String messageExpected = "Insufficient money";

        assertEquals(messageExpected, messageException);

    }

    @Test
    void testRelationAccountBank() {
        Account account = new Account("Stalyn", new BigDecimal("2500"));
        Account account2 = new Account("Ricardo", new BigDecimal("1500.8989"));

        Bank bank = new Bank();
        bank.setName("Pichincha");
        bank.addAccount(account);
        bank.addAccount(account2);

        assertEquals("Pichincha", account.getBank().getName());
    }

    @Tag("testTagExample")
    @Nested
    @DisplayName("Test operating System")
    class operatingSystemTest {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testEnvironmentWindows() {

        }

        @Test
        @EnabledOnOs(OS.LINUX)
        void testEnvironmentLinux() {

        }


        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testEnvironmentLinuxAndMac() {

        }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testEnvironmentNoWindows() {

        }
    }


    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testOnlyJdk8() {

    }


    @Test
    void printSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v) -> System.out.println(k + ":" + v) );
    }

    @Test
    @EnabledIfSystemProperty(named = "java.version", matches = "15.0.1")
    void testIfSystemProperties() {

    }

    // exec test only if server external is up
    // exec test only if environment is DEV

    @Test
    void testBalanceAccountDev() {
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(isDev);
        assertNotNull(account.getBalance());
        assertEquals(10, account.getBalance().doubleValue());
    }

    @Test
    void testBalanceAccountDevTwo() {
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(isDev, () -> {
            assertNotNull(account.getBalance());
            assertEquals(10, account.getBalance().doubleValue());
        });
    }


    // Make sense only values inside method change
    @RepeatedTest(value = 5, name = "Number repetition {currentRepetitions}")
    void testBalanceAccountDevTwoRepeat(RepetitionInfo info) {
        if(info.getCurrentRepetition() == 3) {
            System.out.println("We is in the repetition "+ info.getCurrentRepetition());
        }

        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(isDev, () -> {
            assertNotNull(account.getBalance());
            assertEquals(10, account.getBalance().doubleValue());
        });
    }

    // Make sense if pass parameters to method


    @ParameterizedTest(name = "number {index} executed with value {argumentsWithNames}")
    @ValueSource(strings = {"100", "200", "500", "1000.123456"})
    void testDebitAccounBalancetParamsValueSource(String money) {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.debit(new BigDecimal(money));
        assertNotNull(account.getBalance());
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO )> 0);
    }


    @ParameterizedTest(name = "number {index} executed with value {argumentsWithNames}")
    @CsvSource({"1,100", "2,200", "3,500", "4,999.123456"})
    void testDebitAccounBalancetParamsCsvSource(String index, String money) {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.debit(new BigDecimal(money));
        assertNotNull(account.getBalance());
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO )> 0);
    }

    @ParameterizedTest(name = "number {index} executed with value {argumentsWithNames}")
    @MethodSource("amountList")
    void testDebitAccounBalancetParamsMethodSource(String amoun) {
        Account account = new Account("Stalyn", new BigDecimal("1000.123456"));
        account.debit(new BigDecimal(amoun));
        assertNotNull(account.getBalance());
        assertTrue(account.getBalance().compareTo(BigDecimal.ZERO )> 0);
    }

    static List<String> amountList() {
        return Arrays.asList("100", "200", "500", "999.123456");
    }

    @Test
    @Timeout(5) // in seconds
    void testTimeOut() throws  InterruptedException{
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS) // in seconds
    void testTimeOutMilliseconds() throws  InterruptedException{
        TimeUnit.SECONDS.sleep(5);
    }
}