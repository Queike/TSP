import java.util.*

private const val DATA_SET_PATH = "data/western_sahara_points_data.txt"
//private const val DATA_SET_PATH = "data/quatar_points_data.txt"

class SimulatedAnnealing(
    private val startTemperature: Double,
    private val stopTemperature: Double,
    private val coolingRate: Double,
    private val iterations: Int
) {

    private var bestPhenotype: List<Int>? = null
    private var phenotypes = LinkedList<List<Int>>()
    private var currentPhenotypeIndex = 0
    private val randomGenerator = Random()

    fun run() {
        val fileReader = FileReader()
        val dataExtractor = DataExtractor()

        val readLines = fileReader.readFileLineByLine(DATA_SET_PATH)
        val cities = dataExtractor.extractListOfCities(readLines)

        val firstPhenotype = Individual.generateRandomIndividual(cities.toMutableList())
        val result = runAnnealingSimulation(firstPhenotype, cities)

        printResult(result, cities)
    }

    private fun runAnnealingSimulation(firstPhenotype: List<Int>, cities: List<City>): List<Int> {
        var currentPhenotype = firstPhenotype
        bestPhenotype = currentPhenotype

        var currentTemperature = startTemperature
        var currentIteration = 0
        //addProgress(currentPhenotype)

        while (currentIteration < iterations) {
            currentIteration++
            var SiPhenotype = currentPhenotype.getNeighbour()
            currentTemperature *= coolingRate
            //currentPhenotypeIndex++
            //addProgress(currentPhenotype)
            //val difference = bestPhenotype!!.getIndividualWholeDistance(cities) - currentPhenotype.getIndividualWholeDistance(cities)

            if(SiPhenotype.getIndividualWholeDistance(cities) <= currentPhenotype.getIndividualWholeDistance(cities)) {
                currentPhenotype = SiPhenotype
                if(SiPhenotype.getIndividualWholeDistance(cities) <= bestPhenotype!!.getIndividualWholeDistance(cities)) {
                    bestPhenotype = SiPhenotype
                }

            } else if(Math.exp((currentPhenotype.getIndividualWholeDistance(cities) - SiPhenotype.getIndividualWholeDistance(cities)) / currentTemperature) > randomGenerator.nextDouble()) {
                currentPhenotype = SiPhenotype
            }


        }

        return bestPhenotype!!
    }

    private fun printResult(phenotype: List<Int>, cities: List<City>) {
        println("BEST PHENOTYPE ---> $phenotype")
        val distance = phenotype.getIndividualWholeDistance(cities)
        println("BEST DISTANCE ---> $distance")
    }

    private fun addProgress(phenotype: List<Int>) {
        phenotypes.add(phenotype)
    }

    private fun List<Int>.getNeighbour(): List<Int> {
        return this.inverse()
    }

    private fun List<Int>.getPrevious(): List<Int> {
        return if(currentPhenotypeIndex == 0) {
            phenotypes[0]
        } else {
            currentPhenotypeIndex--
            phenotypes[currentPhenotypeIndex]
        }
    }
}