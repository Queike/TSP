import kotlin.random.Random

class IndividualsSelector {

    fun selectWithRouletteMethod(individuals: List<CalculatedIndividual>, populationSize: Int): List<List<Int>> {
        val selectedIndividuals = mutableListOf<List<Int>>()

        var mutableIndividuals = individuals

        mutableIndividuals = mutableIndividuals.sortedBy {
            it.countedValue
        }

        selectedIndividuals.add(mutableIndividuals.first().individual)

        val theWorstIndividualValue = mutableIndividuals.last().countedValue

        mutableIndividuals.forEach { individual ->
            individual.selectorValue = theWorstIndividualValue - individual.countedValue
        }

        val individualsSelectorSum = individuals.sumBy {
            it.selectorValue.toInt()
        }

        while (selectedIndividuals.size < populationSize) {
            selectedIndividuals.add(getRouletteResult(individualsSelectorSum.toLong(), mutableIndividuals))
        }

        return selectedIndividuals
    }

    private fun getRouletteResult(rouletteSum: Long, individuals: List<CalculatedIndividual>): List<Int> {
        if(rouletteSum == 0.toLong()) {
            return individuals.first().individual
        }
        val randomValue = Random.nextLong(rouletteSum)
        var winningIndividual = individuals.first()
        var controlSum = 0

        individuals.forEach {
            controlSum += it.selectorValue.toInt()
            if (controlSum >= randomValue) {
                winningIndividual = it
                return winningIndividual.individual
            }
        }

        return winningIndividual.individual
    }

    fun selectWithTournamentMethod(individuals: List<CalculatedIndividual>, populationSize: Int, tournamentSize: Int): List<List<Int>> {
        val selectedIndividuals = mutableListOf<List<Int>>()

        while (selectedIndividuals.size < populationSize) {
            selectedIndividuals.add(getTournamentResult(individuals, tournamentSize))
        }

        return selectedIndividuals
    }

    private fun getTournamentResult(individuals: List<CalculatedIndividual>, tournamentSize: Int): List<Int> {
        val tournamentGroup = mutableListOf<CalculatedIndividual>()

        while (tournamentGroup.size < tournamentSize) {
            val randomIndividual = individuals[Random.nextInt(individuals.size)]
            tournamentGroup.add(randomIndividual)
        }

        val sortedTournament = tournamentGroup.sortedBy {
            it.countedValue
        }

        return sortedTournament.first().individual
    }

    private fun getIndividualsProbabilities(individuals: List<CalculatedIndividual>) {
        val worstIndividualValue = individuals
    }

}