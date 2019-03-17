import kotlin.random.Random

class IndividualsSelector {
    companion object {
        fun selectWithRouletteMethod(individuals: List<CalculatedIndividual>, populationSize: Int): List<List<Int>> {
            val selectedIndividuals = mutableListOf<List<Int>>()

            var individualsM = individuals

            val individualsWholeSum = individuals.sumBy {
                it.countedValue.toInt()
            }

            individualsM.sortedBy {
                it.countedValue
            }.reversed()

            println("INDIVIDUALS REVERSED !")
            individualsM.forEach {
                println(it.countedValue)
            }

            individualsM.sortedBy {
                it.countedValue
            }

            println("INDIVIDUALS !")
            individualsM.forEach {
                println(it.countedValue)
            }

            selectedIndividuals.add(individualsM.minBy { it.countedValue }!!.individual)

            individualsM.forEach {  calculatedIndividual ->
                calculatedIndividual.increasedValue = calculatedIndividual.countedValue * (10 - individualsM.indexOf(calculatedIndividual))
            }

            val increasedSum = individualsM.sumBy {
                it.increasedValue.toInt()
            }

            individualsM.sortedBy {
                it.increasedValue
            }

            while (selectedIndividuals.size < populationSize) {
                selectedIndividuals.add(getRouletteResult(increasedSum, individualsM))
            }

            return selectedIndividuals
        }

        private fun getRouletteResult(rouletteSum: Int, individuals: List<CalculatedIndividual>): List<Int> {
            val randomValue = Random.nextInt(rouletteSum)
            var winningIndividual = individuals.first()

            individuals.forEach {
                if(it.increasedValue > randomValue) {
                    winningIndividual = it
                    return winningIndividual.individual
                }
            }

            return winningIndividual.individual
        }
    }
}