class AlgorithmRunner {

    companion object {
        fun runGeneticAlgorithm() {
            val geneticAlgorithm = GeneticAlgorithm(
                10,
                75,
                100
            )
            geneticAlgorithm.run()
        }
    }
}