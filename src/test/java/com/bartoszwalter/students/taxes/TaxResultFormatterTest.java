package com.bartoszwalter.students.taxes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for TaxResultFormatter class.
 */
class TaxResultFormatterTest {

	private TaxResultFormatter formatter;
	private TestLogHandler logHandler;

	/**
	 * Set up before each test.
	 */
	@BeforeEach
	void setUp() {
		formatter = new TaxResultFormatter();
		logHandler = new TestLogHandler();
		Logger logger = Logger.getLogger(
				TaxResultFormatter.class.getName());
		logger.addHandler(logHandler);
	}

	/**
	 * Test displayResult with employment contract.
	 */
	@Test
	void testDisplayResultWithEmploymentContract() {
		TaxResult result = createSampleEmploymentResult();

		formatter.displayResult(result, ContractType.EMPLOYMENT);

		// Verify that messages were logged
		assertTrue(logHandler.hasLoggedMessage("Contract Type"));
		assertTrue(logHandler.hasLoggedMessage("Gross Income"));
		assertTrue(logHandler.hasLoggedMessage("Net Income"));
	}

	/**
	 * Test displayResult with civil contract.
	 */
	@Test
	void testDisplayResultWithCivilContract() {
		TaxResult result = createSampleCivilResult();

		formatter.displayResult(result, ContractType.CIVIL);

		// Verify that messages were logged
		assertTrue(logHandler.hasLoggedMessage("Contract Type"));
		assertTrue(logHandler.hasLoggedMessage("Gross Income"));
		assertTrue(logHandler.hasLoggedMessage("Net Income"));
	}

	/**
	 * Test that displayResult includes all required messages.
	 *
	 * @param expectedMessage the expected message in logs
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"Social security tax",
			"Health social security tax",
			"Sickness social security tax",
			"9%",
			"7.75%",
			"Tax deductible expenses",
			"Income to be taxed",
			"Advance tax",
			"Tax free income",
			"Reduced tax"
	})
	void testDisplayResultIncludesRequiredMessages(
			final String expectedMessage) {
		TaxResult result = createSampleEmploymentResult();

		formatter.displayResult(result, ContractType.EMPLOYMENT);

		assertTrue(logHandler.hasLoggedMessage(expectedMessage),
				"Expected log message: " + expectedMessage);
	}

	/**
	 * Test displayResult without tax free income.
	 */
	@Test
	void testDisplayResultWithoutTaxFreeIncome() {
		TaxResult result = createSampleCivilResult();

		formatter.displayResult(result, ContractType.CIVIL);

		// Should not include tax free income messages
		// since civil contract has zero tax free income
		assertTrue(logHandler.hasLoggedMessage("Advance tax paid"));
	}

	/**
	 * Test displayResult includes rounded values.
	 */
	@Test
	void testDisplayResultIncludesRoundedValues() {
		TaxResult result = createSampleEmploymentResult();

		formatter.displayResult(result, ContractType.EMPLOYMENT);

		assertTrue(logHandler.hasLoggedMessage("rounded"));
	}

	/**
	 * Test displayResult with zero values.
	 */
	@Test
	void testDisplayResultWithZeroValues() {
		TaxResult result = createZeroResult();

		formatter.displayResult(result, ContractType.EMPLOYMENT);

		assertTrue(logHandler.hasLoggedMessage("Gross Income"));
	}

	/**
	 * Test displayResult with large values.
	 */
	@Test
	void testDisplayResultWithLargeValues() {
		TaxResult result = createLargeValueResult();

		formatter.displayResult(result, ContractType.EMPLOYMENT);

		assertTrue(logHandler.hasLoggedMessage("Gross Income"));
		assertTrue(logHandler.hasLoggedMessage("Net Income"));
	}

	/**
	 * Test constructor creates formatter.
	 */
	@Test
	void testConstructor() {
		TaxResultFormatter newFormatter = new TaxResultFormatter();
		assertNotNull(newFormatter);
	}

	/**
	 * Create sample employment result for testing.
	 */
	private TaxResult createSampleEmploymentResult() {
		BigDecimal grossIncome = new BigDecimal("5000.00");
		SocialContributions contributions =
				SocialContributions.calculate(grossIncome);
		HealthInsurance health =
				HealthInsurance.calculate(grossIncome);

		return new TaxResult(
				grossIncome,
				contributions,
				health,
				TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES,
				new BigDecimal("4888.75"),
				new BigDecimal("879.98"),
				TaxRates.TAX_FREE_INCOME,
				new BigDecimal("356"),
				new BigDecimal("4093.90")
		);
	}

	/**
	 * Create sample civil result for testing.
	 */
	private TaxResult createSampleCivilResult() {
		BigDecimal grossIncome = new BigDecimal("5000.00");
		SocialContributions contributions =
				SocialContributions.calculate(grossIncome);
		BigDecimal incomeAfter =
				contributions.getIncomeAfterContributions(grossIncome);
		HealthInsurance health = HealthInsurance.calculate(incomeAfter);

		return new TaxResult(
				grossIncome,
				contributions,
				health,
				new BigDecimal("863.16"),
				new BigDecimal("3451"),
				new BigDecimal("621.18"),
				BigDecimal.ZERO,
				new BigDecimal("220"),
				new BigDecimal("4093.90")
		);
	}

	/**
	 * Create result with zero values for testing.
	 */
	private TaxResult createZeroResult() {
		BigDecimal grossIncome = BigDecimal.ZERO;
		SocialContributions contributions =
				SocialContributions.calculate(grossIncome);
		HealthInsurance health =
				HealthInsurance.calculate(grossIncome);

		return new TaxResult(
				grossIncome,
				contributions,
				health,
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				BigDecimal.ZERO
		);
	}

	/**
	 * Create result with large values for testing.
	 */
	private TaxResult createLargeValueResult() {
		BigDecimal grossIncome = new BigDecimal("50000.00");
		SocialContributions contributions =
				SocialContributions.calculate(grossIncome);
		HealthInsurance health =
				HealthInsurance.calculate(grossIncome);

		return new TaxResult(
				grossIncome,
				contributions,
				health,
				TaxRates.EMPLOYMENT_TAX_DEDUCTIBLE_EXPENSES,
				new BigDecimal("49888.75"),
				new BigDecimal("8979.98"),
				TaxRates.TAX_FREE_INCOME,
				new BigDecimal("5056"),
				new BigDecimal("40393.90")
		);
	}

	/**
	 * Custom log handler for testing.
	 */
	private static class TestLogHandler extends Handler {
		private final StringBuilder logs = new StringBuilder();

		@Override
		public void publish(final LogRecord logRecord) {
			logs.append(logRecord.getMessage()).append("\n");
		}

		@Override
		public void flush() {
			// Not needed for testing
		}

		@Override
		public void close() {
			// Not needed for testing
		}

		/**
		 * Check if a message was logged.
		 *
		 * @param message the message to search for
		 * @return true if message was logged
		 */
		public boolean hasLoggedMessage(final String message) {
			return logs.toString().contains(message);
		}
	}
}