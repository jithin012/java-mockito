package com.pluralsight.pension.setup;

import static com.pluralsight.pension.setup.AccountOpeningService.UNACCEPTABLE_RISK_PROFILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.pluralsight.pension.AccountRepository;

public class AccountOpeningServiceTest {
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Smith";
	private static final LocalDate DOB = LocalDate.of(1990, 1, 1);
	private static final String TAX_ID = "123XYZ9";

	private AccountOpeningService underTest;
	// mock() allow us to create a mock object of a class or an interface.
	// we cab then use the mock to stub return values for its method and verify if
	// they were called.
	private BackgroundCheckService backgroundCheckService = mock(BackgroundCheckService.class);
	private ReferenceIdsManager referenceIdsManager = mock(ReferenceIdsManager.class);
	private AccountRepository accountRepository = mock(AccountRepository.class);
	private AccountOpeningEventPublisher eventPublisher = mock(AccountOpeningEventPublisher.class);

	@BeforeEach
	void setup() {
		underTest = new AccountOpeningService(backgroundCheckService, referenceIdsManager, accountRepository,
				eventPublisher);
	}
	
	 @Test
	 public void shouldOpenAccount() throws IOException {
			final BackgroundCheckResults okBackgroundCheckResults = new BackgroundCheckResults(
					"something_not_unacceptable", 100);
			when(backgroundCheckService.confirm(FIRST_NAME, LAST_NAME, TAX_ID, DOB))
					.thenReturn(okBackgroundCheckResults);

			when(referenceIdsManager.obtainId(eq(FIRST_NAME), eq(LAST_NAME), eq(TAX_ID), eq(DOB)))
					.thenReturn("some_id");

			final AccountOpeningStatus accountOpeningStatus = underTest.openAccount(FIRST_NAME, LAST_NAME, TAX_ID, DOB);

			assertEquals(AccountOpeningStatus.OPENED, accountOpeningStatus);

			ArgumentCaptor<BackgroundCheckResults> backgroundCheckResultsArgumentCaptor = ArgumentCaptor
					.forClass(BackgroundCheckResults.class);

			verify(accountRepository).save(eq("some_id"), eq(FIRST_NAME), eq(LAST_NAME), eq(TAX_ID), eq(DOB),
					backgroundCheckResultsArgumentCaptor.capture());
			verify(eventPublisher).notify(anyString());

			System.out.println(backgroundCheckResultsArgumentCaptor.getValue().getRiskProfile() + " "
					+ backgroundCheckResultsArgumentCaptor.getValue().getUpperAccountLimit());
			assertEquals(okBackgroundCheckResults.getRiskProfile(),
					backgroundCheckResultsArgumentCaptor.getValue().getRiskProfile());
			assertEquals(okBackgroundCheckResults.getUpperAccountLimit(),
					backgroundCheckResultsArgumentCaptor.getValue().getUpperAccountLimit());

			verifyNoMoreInteractions(ignoreStubs(backgroundCheckService, referenceIdsManager));
			verifyNoMoreInteractions(accountRepository, eventPublisher);
		}

		@Test
		public void shouldDeclineAccountIfUnacceptableRiskProfileBackgroundCheckResponseReceived() throws IOException {
			when(backgroundCheckService.confirm(FIRST_NAME, LAST_NAME, TAX_ID, DOB))
					.thenReturn(new BackgroundCheckResults(UNACCEPTABLE_RISK_PROFILE, 0));
			final AccountOpeningStatus accountOpeningStatus = underTest.openAccount(FIRST_NAME, LAST_NAME, TAX_ID, DOB);
			assertEquals(AccountOpeningStatus.DECLINED, accountOpeningStatus);
		}

		@Test
		public void shouldDeclineAccountIfNullBackgroundCheckResponseReceived() throws IOException {
			when(backgroundCheckService.confirm(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenReturn(null);
			final AccountOpeningStatus accountOpeningStatus = underTest.openAccount(LAST_NAME, FIRST_NAME, TAX_ID, DOB);

			assertEquals(AccountOpeningStatus.DECLINED, accountOpeningStatus);
		}

		@Test
		public void shouldThrowIfBackgroundChecksServiceThrows() throws IOException {
			when(backgroundCheckService.confirm(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenThrow(new IOException());
			assertThrows(IOException.class, () -> underTest.openAccount(LAST_NAME, FIRST_NAME, TAX_ID, DOB));
		}

		@Test
		public void shouldThrowIfReferenceIdManagerThrows() throws IOException {
			when(backgroundCheckService.confirm(LAST_NAME, FIRST_NAME, TAX_ID, DOB))
			.thenReturn(new BackgroundCheckResults("something_not_unacceptable", 100));

			when(referenceIdsManager.obtainId(LAST_NAME, FIRST_NAME, TAX_ID, DOB))
					.thenThrow(new RuntimeException());
			
			assertThrows(RuntimeException.class, () -> underTest.openAccount(LAST_NAME, FIRST_NAME, TAX_ID, DOB));
		}

		@Test
		public void shouldThrowIfAccountRepositoryThrows() throws IOException {
			final BackgroundCheckResults backgroundCheckResults = new BackgroundCheckResults("somwthing_not_acceptable",
					100);

			when(backgroundCheckService.confirm(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenReturn(backgroundCheckResults);

			when(referenceIdsManager.obtainId(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenReturn("someID");
			when(accountRepository.save("someID", LAST_NAME, FIRST_NAME, TAX_ID, DOB, backgroundCheckResults))
					.thenThrow(new RuntimeException());

			assertThrows(RuntimeException.class, () -> underTest.openAccount(LAST_NAME, FIRST_NAME, TAX_ID, DOB));
		}

		@Test
		public void shouldThrowIfEventPublisherThrows() throws IOException {
			final BackgroundCheckResults backgroundCheckResults = new BackgroundCheckResults("somwthing_not_acceptable",
					100);

			when(backgroundCheckService.confirm(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenReturn(backgroundCheckResults);

			when(referenceIdsManager.obtainId(LAST_NAME, FIRST_NAME, TAX_ID, DOB)).thenReturn("someID");
			when(accountRepository.save("someID", LAST_NAME, FIRST_NAME, TAX_ID, DOB, backgroundCheckResults))
					.thenReturn(true);
			doThrow(new RuntimeException()).when(eventPublisher).notify("someID"); // since notify return void, mockito
																					// does not like this. So a change
																					// here
			assertThrows(RuntimeException.class, () -> underTest.openAccount(LAST_NAME, FIRST_NAME, TAX_ID, DOB));

		}
}
