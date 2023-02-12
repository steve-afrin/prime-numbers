package home.safrin;

import java.text.NumberFormat;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Instances of this class are able to test for the <em>primality</em> of a numeric value.
 * <em>Primality</em> is just a fancy word for describing whether a numeric value is prime or not.
 * By definition, a prime number is any number that is evenly divisible only by one and itself. If
 * the number is evenly divisible by any other number greater than one, then it is <strong>not</strong>
 * prime.
 * @see <a href="https://en.wikipedia.org/wiki/Prime_number">Prime number on Wikipedia</a>
 */
public class PrimeNumbers {
  private static final Logger LOG = LoggerFactory.getLogger(PrimeNumbers.class);

  private static final NumberFormat INTEGER_FORMATTER = NumberFormat.getIntegerInstance();

  private static final String INVALID_VALUE_ERR_MSG = "n must be a positive integer value";
  private static final String NO_BOUND_VALUES_ERR_MSG = "No bound values provided as program arguments";
  private static final String NO_UPPER_BOUND_VALUE_ERR_MSG = "No upper bound value provided as a program argument";
  private static final String BOUND_VALUES_OUT_OF_ORDER_ERR_MSG
      = "The lower bound value must be less than the upper bound value";
  private static final String INVALID_BOUND_VALUE_ERR_MSG = "The %s bound value must be a positive integer value";

  private static final Predicate<Boolean> isTrueTestResult = eval -> eval;

  /**
   * Tests whether the {@code n} parameter value is prime or not. A prime number is defined as any number
   * that is evenly divided only by one and itself. Any other number greater than one that evenly divides
   * the {@code n} parameter value means that the {@code n} parameter value is <strong>not</strong> prime.
   *
   * @param n the {@code int} value to test for primality
   * @return {@code true} if the {@code n} parameter value is prime; otherwise return {@code false} to
   * indicate that the {@code n} parameter value is not prime.
   */
  public boolean isPrime(final int n) {
    checkArgument(n > 0, INVALID_VALUE_ERR_MSG);

    if (n < 4) {
      return true;
    }

    return IntStream.rangeClosed(2, (int) Math.ceil(Math.sqrt(n)))
                    .mapToObj(divisor -> this.isEvenlyDivisible(n, divisor))
                    .allMatch(isTrueTestResult.negate());
  }

  /**
   * Tests for whether the {@code n} parameter value is <em>evenly divisible</em> by the {@code divisor}
   * parameter value or not. <em>Evenly divisible</em> means that the {@code n} parameter value can be
   * divided by the {@code divisor} parameter value and the remainder value is zero. If the remainder
   * value is anything greater than zero, then the {@code n} parameter value is <strong>not</strong>
   * evenly divisible by the {@code divisor} parameter value.
   *
   * @param n       the {@code int} value to be divided for this test
   * @param divisor the {@code int} value by which to divide the {@code n} parameter value
   * @return {@code true} when the {@code n} parameter value is evenly divisible by the {code divisor}
   * parameter value; otherwise return {@code false} when the division operation results in a remainder
   * value that is greater than zero.
   * @throws ArithmeticException when the {@code divisor} parameter value is zero regardless of whatever
   * the {@code n} parameter value might be.
   */
  private boolean isEvenlyDivisible(final int n, final int divisor) {
    if (n % divisor == 0) {
      LOG.debug("{} is divisible by {}, so {} is not prime",
          INTEGER_FORMATTER.format(n),
          INTEGER_FORMATTER.format(divisor),
          INTEGER_FORMATTER.format(n));
      return true;
    }

    return false;
  }

  /**
   * Entry point to run this program and find all prime numbers between 1 and the first program
   * argument value.
   *
   * @param args the argument values provided to this program.
   * @throws IllegalArgumentException when the first program argument is an {@code int} value that is
   * less than one. Zero and all negative values don't make sense in the context of what this program
   * is designed to calculate and report.
   * @throws NumberFormatException when the first program argument cannot be properly interpreted as an
   * {@code int} value.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException(NO_BOUND_VALUES_ERR_MSG);
    } else if (args.length == 1) {
      throw new IllegalArgumentException(NO_UPPER_BOUND_VALUE_ERR_MSG);
    }

    final var lowerBound = Integer.valueOf(args[0]);
    final var upperBound = Integer.valueOf(args[1]);

    checkArgument(lowerBound > 0, String.format(INVALID_BOUND_VALUE_ERR_MSG, "lower"));
    checkArgument(upperBound > 0, String.format(INVALID_BOUND_VALUE_ERR_MSG, "upper"));
    checkArgument(upperBound > lowerBound, BOUND_VALUES_OUT_OF_ORDER_ERR_MSG);

    final var tester = new PrimeNumbers();

    final var totalPrimeNumbersFound = IntStream.rangeClosed(lowerBound, upperBound)
                                                .filter(tester::isPrime)
                                                .peek(n -> LOG.info("{} is prime", INTEGER_FORMATTER.format(n)))
                                                .count();

    LOG.info("{} total prime numbers were found between {} and {}.",
        INTEGER_FORMATTER.format(totalPrimeNumbersFound),
        INTEGER_FORMATTER.format(lowerBound),
        INTEGER_FORMATTER.format(upperBound));
  }
}