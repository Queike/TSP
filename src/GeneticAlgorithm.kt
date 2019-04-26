import kotlin.random.Random

//private const val DATA_SET_PATH = "data/western_sahara_points_data.txt"
private const val DATA_SET_PATH = "data/quatar_points_data.txt"

private const val IS_ELITE_SELECTION: Boolean = true
private const val IS_TOURNAMENT_SELECTION: Boolean = true   // if set to false --> roulette selection
private const val IS_INVERSION_MUTATION: Boolean = true     // if set to false --> standard mutation

private const val MAX_GENERATIONS_NUMBER = 10000
private const val TOURNAMENT_SIZE = 3                       // in case of tournament selection
private const val CROSSING_POINTS_NUMBER = 3                // in case of multipoint crossing

class GeneticAlgorithm(
    private val populationSize: Int,
    private val crossingType: CrossingType,
    private val crossingProbability: Int,
    private val mutationProbability: Int
) {

    private var bestDistance = Double.POSITIVE_INFINITY
    private var bestRoute = emptyList<Int>()

    private var alghoritmStartTime: Long = 0
    private var alghoritmEndTime: Long = 0

    private var loopNumber = 0
    private val csvWriter = CSVwriter("3test")
    private val individualsSelector = IndividualsSelector()

    fun run() {
        val fileReader = FileReader()
        val dataExtractor = DataExtractor()

        val readLines = fileReader.readFileLineByLine(DATA_SET_PATH)
        val cities = dataExtractor.extractListOfCities(readLines)

        println(cities.toString())
        val population = Population(cities, populationSize)
        val firstPopulation = population.generateRandomPopulation()

        alghoritmStartTime = System.currentTimeMillis()
        runPopulationLifeCycle(firstPopulation, cities)
    }

    private fun runPopulationLifeCycle(population: List<List<Int>>, cities: List<City>) {
        var currentPopulation = population

        while (!isStopConditionExecuted()) {
            loopNumber++
            val theShortestRoute = rememberBestIndividualFromCurrentPopulation(currentPopulation, cities)

            if(loopNumber % 100 == 0) {
                println("POPULATION SHORTEST ROUTE FOR LOOP nr $loopNumber --> $theShortestRoute")
            }

            val calculatedIndividuals = currentPopulation.calculate(cities)

            var selectionResult: List<List<Int>>
            selectionResult = if(IS_TOURNAMENT_SELECTION) {
                individualsSelector.selectWithTournamentMethod(calculatedIndividuals, populationSize, TOURNAMENT_SIZE)
            } else {
                individualsSelector.selectWithRouletteMethod(calculatedIndividuals, populationSize)
            }

            val selectedIndividuals = selectionResult

            val individualsCouples = selectedIndividuals.getPairsOfIndividuals()
            var nextGeneration = mutableListOf<List<Int>>()
            individualsCouples.forEach { individualsCouple ->
                val newGenerationIndividuals = mutableListOf<List<Int>>()

                if (shouldBeCrossed()) {
                    val kids: IndividualsCoupleKids = if(crossingType == CrossingType.MULTI_POINT) {
                        individualsCouple.multiPointsCross(cities, CROSSING_POINTS_NUMBER)
                    } else if(crossingType == CrossingType.SINGLE_POINT) {
                        individualsCouple.singlePointCross(cities)
                    } else {
                        individualsCouple.monotonousCross(cities)
                    }

                    newGenerationIndividuals.add(kids.firstKid)
                    newGenerationIndividuals.add(kids.secondKid)
                } else {
                    newGenerationIndividuals.add(individualsCouple.firstIndividual)
                    newGenerationIndividuals.add(individualsCouple.secondIndividual)
                }

                newGenerationIndividuals.forEach { individual ->
                    if (shouldBeMutated()) {
                        if(IS_INVERSION_MUTATION) {
                            nextGeneration.add(individual.inverse())
                        } else {
                            nextGeneration.add(individual.mutate())
                        }
                    } else {
                        nextGeneration.add(individual)
                    }
                }
            }

            if(IS_ELITE_SELECTION) {
                val individuals = mutableListOf<List<Int>>()
                val bestRoute = currentPopulation.getBestRoute(cities)
                val worstRoute = nextGeneration.getWorstRoute(cities)
                nextGeneration.forEach { individual ->
                    if(individual == worstRoute) {
                        individuals.add(bestRoute)
                    } else {
                        individuals.add(individual)
                    }
                }

                nextGeneration = individuals
            }

            currentPopulation = nextGeneration
        }

        alghoritmEndTime = System.currentTimeMillis()
        val alghoritmWorkingTimeInSec = (alghoritmEndTime - alghoritmStartTime) / 1000
        csvWriter.nextColumn()
        csvWriter.appendToFile(alghoritmWorkingTimeInSec.toString())
        printBestAndFinalResults(currentPopulation, cities, alghoritmWorkingTimeInSec)
        csvWriter.saveFile()
    }

    private fun rememberBestIndividualFromCurrentPopulation(currentPopulation: List<List<Int>>, cities: List<City>): Double {
        val theShortestRoute = currentPopulation.getBestRouteValue(cities)
        if (theShortestRoute < bestDistance) {
            bestDistance = theShortestRoute
            bestRoute = currentPopulation.getBestRoute(cities)
        }

        csvWriter.appendToFile(theShortestRoute.toInt().toString())
        csvWriter.nextLine()

        return theShortestRoute
    }

    private fun printBestAndFinalResults(currentPopulation: List<List<Int>>, cities: List<City>, time: Long) {
        println("FINAL ROUTE " + currentPopulation.getBestRouteValue(cities))
        println("FINAL ROUTE PATH " + currentPopulation.getBestRoute(cities))

        println("BEST ROUTE ---> $bestRoute")
        println("BEST DISTANCE ---> $bestDistance")
        println("TIME ----> $time seconds")
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
        return loopNumber == MAX_GENERATIONS_NUMBER
    }
}