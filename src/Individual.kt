class Individual {
    companion object {
        fun generateRandomIndividual(cities: MutableList<City>): List<Int> {
            cities.shuffle()

            return cities.map {  city ->
                city.id
            }
        }
    }
}