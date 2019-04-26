fun List<List<Int>>.getBestRouteValue(cities: List<City>): Double {
    var theShortestDistance = Double.POSITIVE_INFINITY

    this.forEach { individual ->
        val currentIndividualDistance = individual.getIndividualWholeDistance(cities)
        if (currentIndividualDistance < theShortestDistance) {
            theShortestDistance = currentIndividualDistance
        }
    }

    return theShortestDistance
}

fun List<List<Int>>.getBestRoute(cities: List<City>): List<Int> {
    var theShortestDistance = Double.POSITIVE_INFINITY
    var bestIndividual = emptyList<Int>()

    this.forEach { individual ->
        val currentIndividualDistance = individual.getIndividualWholeDistance(cities)
        if (currentIndividualDistance < theShortestDistance) {
            theShortestDistance = currentIndividualDistance
            bestIndividual = individual
        }
    }

    return bestIndividual
}

fun List<List<Int>>.getWorstRoute(cities: List<City>): List<Int> {
    var theLongesttDistance = Double.NEGATIVE_INFINITY
    var worstIndividual = emptyList<Int>()

    this.forEach { individual ->
        val currentIndividualDistance = individual.getIndividualWholeDistance(cities)
        if (currentIndividualDistance > theLongesttDistance) {
            theLongesttDistance = currentIndividualDistance
            worstIndividual = individual
        }
    }

    return worstIndividual
}

fun List<List<Int>>.getWorstRouteValue(cities: List<City>): Double {
    var theShortestDistance = Double.POSITIVE_INFINITY

    this.forEach { individual ->
        val currentIndividualDistance = individual.getIndividualWholeDistance(cities)
        if (currentIndividualDistance > theShortestDistance) {
            theShortestDistance = currentIndividualDistance
        }
    }

    return theShortestDistance
}

fun List<List<Int>>.calculate(cities: List<City>): List<CalculatedIndividual> {
    val calculatedIndividuals = mutableListOf<CalculatedIndividual>()
    var increasedIndividualsValue = 0.0

    this.forEach { individual ->
        val currentIndividualDistance = individual.getIndividualWholeDistance(cities)
        calculatedIndividuals.add(CalculatedIndividual(
            individual,
            currentIndividualDistance,
            increasedIndividualsValue + currentIndividualDistance
        ))
        increasedIndividualsValue += currentIndividualDistance
    }

    return calculatedIndividuals
}

fun List<List<Int>>.getPairsOfIndividuals(): List<IndividualsCouple> {
    var firstIndividual: List<Int> = emptyList()
    var secondIndividual: List<Int> = emptyList()
    val listOfCouples = mutableListOf<IndividualsCouple>()

    this.forEach { currentIndividual ->
        if (firstIndividual.isEmpty()) {
            firstIndividual = currentIndividual
        } else if (secondIndividual.isEmpty()) {
            secondIndividual = currentIndividual
            val couple = IndividualsCouple(firstIndividual, secondIndividual)
            listOfCouples.add(couple)
            firstIndividual = emptyList()
            secondIndividual = emptyList()
        }
    }

    return listOfCouples
}