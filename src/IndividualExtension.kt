import kotlin.random.Random

fun List<Int>.getIndividualWholeDistance(cities: List<City>): Double {
    var previousCityId = -1
    var totalDistance: Double = 0.0

    val mutableCities = cities.sortedBy {
        it.id
    }

    this.forEach { currentCityId ->
        if(previousCityId != -1) {
            val previousCity = mutableCities[previousCityId - 1]
            val currentCity = mutableCities[currentCityId - 1]
            totalDistance += previousCity.getDistanceToCity(currentCity)
        }
        previousCityId = currentCityId
    }

    // to add distance from last to first city (cycle)
    totalDistance += mutableCities.last().getDistanceToCity(mutableCities.first())

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

fun List<Int>.inverse(): List<Int> {
    val generatedBounds = generateInversionBounds(this.size)
    val beginOfSublistToInverse = generatedBounds.first()
    val endOfSublistToInverse = generatedBounds.last()

    val sublistToInverse = this.subList(beginOfSublistToInverse, endOfSublistToInverse)
    val mutatedIndividual = mutableListOf<Int>()
    mutatedIndividual.addAll(this.subList(0, beginOfSublistToInverse))
    mutatedIndividual.addAll(sublistToInverse.reversed())
    mutatedIndividual.addAll(this.subList(endOfSublistToInverse, this.size))

    return mutatedIndividual
}

private fun generateInversionBounds(individualLength: Int): List<Int> {
    val firstBound = randomGenerator.nextInt(individualLength)
    var secondBound = randomGenerator.nextInt(individualLength)

    while (secondBound == firstBound) {
        secondBound = randomGenerator.nextInt(individualLength)
    }

    val sortedBounds = listOf(firstBound, secondBound).sorted()

    return sortedBounds
}