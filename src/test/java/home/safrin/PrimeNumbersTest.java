package home.safrin;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrimeNumbersTest {

  private PrimeNumbers tester;

  @BeforeEach
  void setUp() {
    this.tester = new PrimeNumbers();
  }

  @ParameterizedTest(name = "test IllegalArgumentException is thrown for isPrime({0})")
  @ValueSource(ints = {0, -1, -2})
  void testInvalidValues(final int n) {
    final var thrown = assertThrows(
        IllegalArgumentException.class,
        () -> this.tester.isPrime(n),
        "Expected isPrime() to throw an IllegalArgumentException, but it didn't"
    );

    assertEquals("n must be a positive integer value", thrown.getMessage());
  }

  @ParameterizedTest(name = "test for isPrime({0})")
  @MethodSource
  void testIsPrime(final int n, final boolean expectedResult) {
    assertEquals(expectedResult, this.tester.isPrime(n));
  }

  private static Stream<Arguments> testIsPrime() {
    return Stream.of(
        Arguments.of(1, true),
        Arguments.of(2, true),
        Arguments.of(3, true),
        Arguments.of(4, false),
        Arguments.of(5, true),
        Arguments.of(6, false),
        Arguments.of(7, true),
        Arguments.of(8, false),
        Arguments.of(9, false),
        Arguments.of(10, false),
        Arguments.of(11, true),
        Arguments.of(12, false),
        Arguments.of(13, true),
        Arguments.of(14, false),
        Arguments.of(15, false),
        Arguments.of(16, false),
        Arguments.of(17, true),
        Arguments.of(18, false),
        Arguments.of(19, true),
        Arguments.of(283, true),
        Arguments.of(3_113, false),
        Arguments.of(16_697, false),
        Arguments.of(80_089, false)
    );
  }
}