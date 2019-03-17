import kotlin.random.Random

private const val DATA_SET_PATH = "data/quatar_points_data.txt"

class GeneticAlgorithm(
    private val populationSize: Int,
    private val crossingProbability: Int,
    private val mutationProbability: Int
) {

    private var loopNumber = 0

    fun run() {
        val fileReader = FileReader()
        val dataExtractor = DataExtractor()

        val readLines = fileReader.readFileLineByLine(DATA_SET_PATH)
        val cities = dataExtractor.extractListOfCities(readLines)

        println(cities.toString())
        val population = Population(cities, populationSize)
        val firstPopulation = population.generateRandomPopulation()

        runPopulationLifeCycle(firstPopulation, cities)
    }

    private fun runPopulationLifeCycle(population: List<List<Int>>, cities: List<City>): Double {
        loopNumber++
        val theShortestRoute = population.getBestRouteValue(cities)
        println("POPULATION SHORTEST ROUTE FOR LOOP nr " + loopNumber + " --> " + theShortestRoute)
        if(isStopConditionExecuted()) {
            return theShortestRoute
        } else {
            val calculatedIndividuals = population.calculate(cities)
            val selectedIndividuals = IndividualsSelector.selectWithRouletteMethod(calculatedIndividuals, populationSize)

            val individualsCouples = selectedIndividuals.getPairsOfIndividuals()
            val nextGeneration = mutableListOf<List<Int>>()
            individualsCouples.forEach {  individualsCouple ->
                val newGenerationIndividuals = mutableListOf<List<Int>>()

                if(shouldBeCrossed()) {
                    val kids = individualsCouple.cross()
                    newGenerationIndividuals.add(kids.firstKid)
                    newGenerationIndividuals.add(kids.secondKid)
                } else {
                    newGenerationIndividuals.add(individualsCouple.firstIndividual)
                    newGenerationIndividuals.add(individualsCouple.secondIndividual)
                }

                newGenerationIndividuals.forEach { individual ->
                    if(shouldBeMutated()) {
                        nextGeneration.add(individual.mutate())
                    } else {
                        nextGeneration.add(individual)
                    }
                }
            }

            return runPopulationLifeCycle(nextGeneration, cities)
        }
    }

    private fun shouldBeCrossed(): Boolean {
        val randomValue = Random.nextInt(0, 100)
        return randomValue <= crossingProbability
    }

    private fun shouldBeMutated(): Boolean {
        val randomValue = Random.nextInt(0, 100)
        return randomValue <= mutationProbability
    }

    private fun isStopConditionExecuted(): Boolean {
        return loopNumber == 4
    }
}