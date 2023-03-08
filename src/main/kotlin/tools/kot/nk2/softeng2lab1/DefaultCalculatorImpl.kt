package tools.kot.nk2.softeng2lab1

class DefaultCalculatorImpl: Calculator {

    private val splitterSingleCharRegex = Regex("^[^\\[\\]]\$")

    private val splitterMultiCharRegex = Regex("(\\[[^\\[\\]]+\\])+")

    private val splitterPartialMultiCharRegex = Regex("(\\[[^\\[\\]]+\\])")

    private fun processValueToken(token: String): Int {
        val processedValue = token
            .toIntOrNull()
            ?: error("$token is not an integer")

        if(processedValue <= 0) {
            error("$token is not positive")
        }

        if(processedValue >= 1000) {
            return 0
        }

        return processedValue
    }

    private fun processSplitterToken(token: String): Array<String> {
        if(splitterSingleCharRegex.matches(token)) {
            return arrayOf(token)
        }

        if(splitterMultiCharRegex.matches(token).not()) {
            error("Splitter is malformed")
        }

        return splitterPartialMultiCharRegex
            .findAll(token)
            .map { it.value }
            .map { it.removeSurrounding("[", "]") }
            .toList()
            .toTypedArray()
    }

    override fun add(input: String): Int {
        val inputSplit = input.split("\n")
        val numbers = inputSplit.last()

        val splitters = inputSplit
            .asSequence()
            .filter { it.startsWith("//") }
            .map { it.removePrefix("//") }
            .firstOrNull()
            ?.let { processSplitterToken(it) }
            ?: arrayOf(",")

        if(numbers.isBlank()) {
            return 0
        }

        return numbers
            .split(*splitters)
            .foldRight(0) { it, acc -> acc + processValueToken(it) }
    }
}
