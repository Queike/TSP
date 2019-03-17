class Population(
    private val cities: List<City>,
    private val populationSize: Int
) {
    fun generateRandomPopulation(): List<List<Int>> {
        val listOfIndividuals = mutableListOf<List<Int>>()

        while (listOfIndividuals.size < populationSize) {
            listOfIndividuals.add(Individual.generateRandomIndividual(cities.toMutableList()))
        }

        return listOfIndividuals
    }
}