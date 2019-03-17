fun IndividualsCouple.cross(): IndividualsCoupleKids {
    val individualSize = this.firstIndividual.size
    val crossingPoint = getCrossingPoint(individualSize)
    val firstParentFirstPart = this.firstIndividual.subList(0, crossingPoint)
    val firstParentSecondPart = this.firstIndividual.subList(crossingPoint, individualSize)
    val secondParentFirstPart = this.secondIndividual.subList(0, crossingPoint)
    val secondParentSecondPart = this.secondIndividual.subList(crossingPoint, individualSize)

    val firstKid = firstParentFirstPart + secondParentSecondPart
    val secondKid = secondParentFirstPart + firstParentSecondPart

    return IndividualsCoupleKids(firstKid, secondKid)
}

private fun getCrossingPoint(individualLength: Int): Int {
    return individualLength / 2
}