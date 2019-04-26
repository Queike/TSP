import java.util.*

val randomGenerator = Random()

fun IndividualsCouple.singlePointCross(cities: List<City>): IndividualsCoupleKids {
    val individualSize = this.firstIndividual.size
    val crossingPoint = getCrossingPoint(individualSize)
    val firstParentFirstPart = this.firstIndividual.subList(0, crossingPoint)
    val firstParentSecondPart = this.firstIndividual.subList(crossingPoint, individualSize)
    val secondParentFirstPart = this.secondIndividual.subList(0, crossingPoint)
    val secondParentSecondPart = this.secondIndividual.subList(crossingPoint, individualSize)

    val firstKid = firstParentFirstPart + secondParentSecondPart
    val firstKidRepaired = firstKid.repair(cities)
    val secondKid = secondParentFirstPart + firstParentSecondPart
    val secondKidRepaired = secondKid.repair(cities)

    return IndividualsCoupleKids(firstKidRepaired, secondKidRepaired)
}

fun IndividualsCouple.multiPointsCross(cities: List<City>, crossingPointsNumber: Int): IndividualsCoupleKids {
    val randomCrossingPoints = getRandomCrossingPoints(cities.size, crossingPointsNumber)
    var lastCrossingPoint = 0
    var iterationNumber = 0

    val firstChild = mutableListOf<Int>()
    val secondChild = mutableListOf<Int>()

    randomCrossingPoints.forEach { currentCrossingPoint ->
        val firstParentSublist = this.firstIndividual.subList(lastCrossingPoint, currentCrossingPoint)
        val secondParentSublist = this.secondIndividual.subList(lastCrossingPoint, currentCrossingPoint)

        if(iterationNumber % 2 == 0) {
            firstChild.addAll(firstParentSublist)
            secondChild.addAll(secondParentSublist)
        } else {
            firstChild.addAll(secondParentSublist)
            secondChild.addAll(firstParentSublist)
        }

        iterationNumber++
        lastCrossingPoint = currentCrossingPoint
    }

    val firstParentSublist = this.firstIndividual.subList(lastCrossingPoint, cities.size)
    val secondParentSublist = this.secondIndividual.subList(lastCrossingPoint, cities.size)

    if(iterationNumber % 2 == 0) {
        firstChild.addAll(firstParentSublist)
        secondChild.addAll(secondParentSublist)
    } else {
        firstChild.addAll(secondParentSublist)
        secondChild.addAll(firstParentSublist)
    }

    val kids = IndividualsCoupleKids(firstChild.repair(cities), secondChild.repair(cities))

    return kids
}

fun IndividualsCouple.monotonousCross(cities: List<City>): IndividualsCoupleKids {
    var currentGenPosition = 0

    val firstChild = mutableListOf<Int>()
    val secondChild = mutableListOf<Int>()

    this.firstIndividual.forEach { currentGen ->
        val firstParentGen = currentGen
        val secondParentGen = this.secondIndividual[currentGenPosition]
        if(shouldGenBeSwitched()) {
            firstChild.add(secondParentGen)
            secondChild.add(firstParentGen)
        } else {
            firstChild.add(firstParentGen)
            secondChild.add(secondParentGen)
        }

        currentGenPosition++
    }

    val kids = IndividualsCoupleKids(firstChild.repair(cities), secondChild.repair(cities))

    return kids
}

private fun shouldGenBeSwitched(): Boolean {
    return randomGenerator.nextBoolean()
}

private fun getCrossingPoint(individualLength: Int): Int {
    return individualLength / 2
}

private fun getRandomCrossingPoint(individualLength: Int): Int {
    return randomGenerator.nextInt(individualLength - 2) + 1
}

private fun getRandomCrossingPoints(individualLength: Int, crossingPointsNumber: Int): List<Int> {
    val crossingPoints = mutableListOf<Int>()

    while (crossingPoints.size < crossingPointsNumber) {
        val randomCrossingPoint = getRandomCrossingPoint(individualLength)
        if(!crossingPoints.contains(randomCrossingPoint)) {
            crossingPoints.add(randomCrossingPoint)
        }
    }

    return crossingPoints.sorted()
}

private fun List<Int>.repair(cities: List<City>): List<Int> {
    val checkedGensList = mutableListOf<Int>()
    val repairedChild = mutableListOf<Int>()

    this.forEach {  currentGen ->
        if(checkedGensList.contains(currentGen)) {
            val randomGen = getRandomCity(checkedGensList, cities)
            repairedChild.add(randomGen)
            checkedGensList.add(randomGen)
        } else {
            repairedChild.add(currentGen)
            checkedGensList.add(currentGen)
        }

    }

    return repairedChild
}

private fun getRandomCity(exceptionList: List<Int>, cities: List<City>): Int {
    val citiesIds = cities.map { it.id }
    val set: List<Int> = citiesIds.minus(exceptionList)
    return set.random()
}