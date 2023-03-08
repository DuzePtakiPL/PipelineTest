package tools.kot.nk2.softeng2lab1

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.junit5.JUnit5Asserter.assertEquals

@ExtendWith(MockitoExtension::class)
class CalculatorTest {

    private lateinit var calculator: Calculator

    @BeforeEach
    fun `Initialize Calculator`() {
        calculator = DefaultCalculatorImpl()
    }

    @Test
    fun `Calculator returns 0 for empty string input`() {
        assertEquals(
            "Calculator implementation must return 0 for empty string input",
            0,
            calculator.add("")
        )
    }

    @Test
    fun `Calculator returns number for single number input`() {
        assertEquals(
            "Calculator implementation must return number for single number input",
            5,
            calculator.add("5")
        )
    }

    @Test
    fun `Calculator returns sum of numbers for numbers separated by commas input`() {
        assertEquals(
            "Calculator implementation must return sum of numbers for numbers separated by commas input",
            10,
            calculator.add("5,1,4")
        )
    }

    @Test
    fun `Calculator throws an error for non-numeric input`() {
        assertThrows<IllegalStateException> {
            calculator.add("a,b,c")
        }
    }

    @Test
    fun `Calculator returns sum for last line of numbers for multiline input`() {
        assertEquals(
            "Calculator implementation must return sum for last line of numbers for multiline input",
            10,
            calculator.add("1,2\n5,1,4")
        )
    }

    @Test
    fun `Calculator throws an error for non-numeric input on last line`() {
        assertThrows<IllegalStateException> {
            calculator.add("1,2\na,b,c")
        }
    }

    @Test
    fun `Calculator returns sum for numbers split by custom delimiter`() {
        assertEquals(
            "Calculator implementation must return sum for numbers split by custom delimiter",
            10,
            calculator.add("//;\n5;1;4")
        )
    }

    @Test
    fun `Calculator throws an error for non-numeric input on last line with custom splitter`() {
        assertThrows<IllegalStateException> {
            calculator.add("//;\na;b;c")
        }
    }

    @Test
    fun `Calculator throws an error for input with negative numbers`() {
        assertThrows<IllegalStateException> {
            calculator.add("5,-1,2")
        }
    }

    @Test
    fun `Calculator returns sum that ignores numbers more than 1000 for numbers input`() {
        assertEquals(
            "Calculator implementation must return sum that ignores numbers more than 1000 for numbers input",
            5,
            calculator.add("5,1000,1001")
        )
    }

    @Test
    fun `Calculator throws an error for input with malformed single character delimiter`() {
        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed single character delimiter - when there is no characters"
        ) {
            calculator.add("//\n512")
        }

        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed single character delimiter - when there are more than one character"
        ) {
            calculator.add("//abc\n5abc1abc2")
        }
    }

    @Test
    fun `Calculator returns sum for numbers split by custom multi character delimiter`() {
        assertEquals(
            "Calculator implementation must return sum for numbers split by custom multi character delimiter",
            10,
            calculator.add("//[_]\n5_1_4")
        )
    }

    @Test
    fun `Calculator throws an error for input with malformed custom multi character delimiter`() {
        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed custom multi character delimiter - when there is no characters inside"
        ) {
            calculator.add("//[]\n512")
        }

        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed custom multi character delimiter - when splitter is malformed"
        ) {
            calculator.add("//[[]]\n512")
        }
    }

    @Test
    fun `Calculator returns sum for numbers split by multiple custom multi characters delimiters`() {
        assertEquals(
            "Calculator implementation must return sum for numbers split by multiple custom multi characters delimiters",
            10,
            calculator.add("//[_$][%$]\n5%$1_$4")
        )
    }

    @Test
    fun `Calculator throws an error for input with malformed multiple custom multi characters delimiters`() {
        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed multiple custom multi characters delimiters - when there is no characters inside"
        ) {
            calculator.add("//[][2][1]\n512")
        }

        assertThrows<IllegalStateException>(
            "Calculator implementation must throw an error for input with malformed multiple custom multi characters delimiters - when splitter is malformed"
        ) {
            calculator.add("//[1][2]22\n512")
        }
    }
}
