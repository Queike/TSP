import kotlin.random.Random

fun List<Int>.getIndividualWholeDistance(cities: List<City>): Double {
    var previousCityId = -1
    var totalDistance: Double = 0.0

    this.forEach { currentCityId ->
        if(previousCityId != -1) {
            val previousCity = cities[previousCityId - 1]
            val currentCity = cities[currentCityId - 1]
            totalDistance += previousCity.getDistanceToCity(currentCity)
        }
        previousCityId = currentCityId
    }

    return totalDistance
}

fun List<Int>.mutate(): List<Int> {
    val firstGenPositionToSwap = Random.nextInt(0, this.size)
    val secondGenPositionToSwap = Random.nextInt(0, this.size)

    val firstGenToswap = this.get(firstGenPositionToSwap)
    val secondGenToSwap = this.get(secondGenPositionToSwap)

    val mutatedIndividual = this.toMutableList()
    mutatedIndividual.set(firstGenPositionToSwap, secondGenToSwap)
    mutatedIndividual.set(secondGenPositionToSwap, firstGenToswap)

    return mutatedIndividual
}