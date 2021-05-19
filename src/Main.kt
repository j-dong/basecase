import java.util.logging.Logger

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("usage: basecase FILENAME")
        return
    }
    val testCase = TestCase.parseFile(args[0])
    println(testCase)
    val logger = Logger.getLogger("Synthesizer")
    val synth = Synthesizer(testCase.correctnessFactor, logger)
    println(synth.synthesizeTestCase(testCase))
}