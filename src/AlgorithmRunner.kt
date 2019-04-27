class AlgorithmRunner {

    companion object {
        fun runGeneticAlgorithm() {
            val geneticAlgorithm = GeneticAlgorithm(
                populationSize = 100,
                crossingType = CrossingType.MULTI_POINT,
                crossingProbability = 75,
                mutationProbability = 8
            )
            geneticAlgorithm.run()
        }

        fun runSimulatedAnnealing() {
            val simulatedAnnealing = SimulatedAnnealing(
                startTemperature = 10000000.0,
                stopTemperature = 100.0,
                coolingRate = 0.999,
                iterations = 10000000
            )
            simulatedAnnealing.run()
        }
    }
}