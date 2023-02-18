# Prime Numbers

This project is a simple exercise in working with basic algorithms, but also
exploring optimizations in the determination of primality for any given number.

The [Prime Numbers article on Wikipedia](https://en.wikipedia.org/wiki/Prime_number)
is a great place to start for those unfamiliar with the detail around prime
numbers.

## Quick Start

1. Make a configuration to run the `home.safrin.PrimeNumbers` program
2. Set program arguments in the configuration with a lower bound value and an
upper bound value
    * Both values must be positive integers greater than 0
    * The lower bound value must be less than the upper bound value
3. Run the configuration and observe the output in the log file

## Optimizations in this implementation

The particular algorithm used in the current implementation at the initial
release of this project is the
[trial division](https://en.wikipedia.org/wiki/Trial_division) algorithm.
It is an admittedly simple, but slow, algorithm that introduces no
opportunity for error.

### The _commutability_ property

Since divisors of a non-prime number are commutable, we don't need to test
for divisors from $2$ to $n - 1$ for a prime candidate $n$. If $x$ and $y$
are divisors of $n$, then $x * y$ is really the same thing as $y * x$.

Therefore, the commutability property allows us to look for divisors only
from $2$ to $n / 2$. That eliminates all computations from $n / 2$ to
$n - 1$, which can be a nice time savings for large $n$.

We can actually do even better than iterating from $2$ to $n / 2$ for
potential divisors. The <em>trial division</em> algorithm says we can
actually stop looking for potential divisors once we get to $\sqrt{n}$.
The reduction from $n / 2$ to $\sqrt{n}$ is an exponential time savings
and becomes very significant for large $n$.

No computations are done for $n < 4$, though this is a virtually
meaningless reduction in processing time. Any value less than $4$
automatically returns a `true` value, so computations start only for
values greater than $3$.

## Future possibilities

* In addition to determining prime values for `int` values, expand the
API to include determining primality for other numeric types such as:
    * `long`
    * `BigInteger`
* More optimizations on the primality test algorithm may be implemented
in the future, but are not yet identified. Future development might
explore algorithms other than the <em>trial division</em> algorithm
implemented in the initial release of this project.
    * [Miller–Rabin primality test](https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test)
    * [AKS primality test](https://en.wikipedia.org/wiki/AKS_primality_test)
    * [Lucas–Lehmer primality test](https://en.wikipedia.org/wiki/Lucas%E2%80%93Lehmer_primality_test)
