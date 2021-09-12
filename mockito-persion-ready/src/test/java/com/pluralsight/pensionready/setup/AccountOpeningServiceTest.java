package com.pluralsight.pensionready.setup;

import static org.easymock.EasyMock.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pluralsight.pensionready.AccountRepository;

public class AccountOpeningServiceTest {
	/**
	 * Mocking
	 * 
	 * Mocking allows you to focus on the unit you are trying to test by replacing
	 * the unit's real dependencies with test-only collaborators. This allows you to
	 * reason about the unit in isolation, without having to deal with the rest of
	 * the code base at the same time.
	 * 
	 * Double
	 * 
	 * Double is a generic term for any case where you replace a production object
	 * for testing purposes. 1. Dummy 2. Fake 3. Stub 4. Spy 5. Mock
	 * 
	 * Argument matchers
	 * 
	 * Increase flexibility for configuring mock expectations. EasyMock compared
	 * arguments of actual method call with the ones from mock expectations using
	 * equals(and Arrays.equals()
	 *
	 * 
	 */
	private AccountOpeningService underTest;
	private BackgroundCheckService backgroundCheckService = mock(BackgroundCheckService.class);
	private ReferenceIdsManager referenceIdsManager = mock(ReferenceIdsManager.class);
	private AccountRepository accountRepository = mock(AccountRepository.class);

	public static final String FIRST_NAME = "John";
	public static final String LAST_NAME = "Smith";
	public static final String TAX_ID = "ABC9";
	public static final LocalDate DOB = LocalDate.of(1990, 1, 10);

	@BeforeEach
	void setUp() {
		underTest = new AccountOpeningService(backgroundCheckService, referenceIdsManager, accountRepository);
	}

	@Test
	public void shouldDeclinedAccountOpening() throws IOException {

		// record phase
		// expect(backgroundCheckService.confirm(FIRST_NAME, LAST_NAME, TAX_ID,
		// DOB)).andRetrun(null);
		final AccountOpeningStatus accountOpeningStatus = underTest.openAccount(FIRST_NAME, LAST_NAME, "ABC9",
				LocalDate.of(1990, 1, 10));
		assertEquals(AccountOpeningStatus.DECLINED, accountOpeningStatus);

	}
}
