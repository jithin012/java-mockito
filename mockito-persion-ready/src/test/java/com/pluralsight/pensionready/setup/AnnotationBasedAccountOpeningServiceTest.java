package com.pluralsight.pensionready.setup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.pluralsight.pensionready.AccountRepository;
import com.pluralsight.pensionready.reporting.GovernmentDataPublisher;

@ExtendWith(EasyMockExtension.class)
public class AnnotationBasedAccountOpeningServiceTest {

	public static final String ACCOUNT_ID = "an account ID";
	public static final BackgroundCheckResults ACCEPTABLE_BACKGROUND_CHECK_RESULTS = new BackgroundCheckResults(
			"an acceptable risk profile", 5000000);
	@TestSubject
	private AccountOpeningService underTest = new AccountOpeningService();
	@Mock
	private BackgroundCheckService backgroundCheckService;
	@Mock
	private ReferenceIdsManager referenceIdsManager;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private GovernmentDataPublisher governmentDataPublisher;

	public static final String FIRST_NAME = "John";
	public static final String LAST_NAME = "Smith";
	public static final String TAX_ID = "ABC9";
	public static final LocalDate DOB = LocalDate.of(1990, 1, 10);

	@Test
	public void shouldDeclineAccountOpeningIfBackgroundCheckResultsRiskProfileUnacceptable() throws IOException {
//		expect(backgroundCheckService.confirm(FIRST_NAME, LAST_NAME, TAX_ID, DOB))
//				.andReturn(new BackgroundCheckResults(AccountOpeningService.UNACCEPTABLE_RISK_PROFILE, 0));
//		replay(backgroundCheckService, referenceIdsManager, accountRepository, governmentDataPublisher);
		final AccountOpeningStatus accountOpeningStatus = underTest.openAccount(FIRST_NAME, LAST_NAME, TAX_ID, DOB);

		assertEquals(AccountOpeningStatus.DECLINED, accountOpeningStatus);
	}
}
