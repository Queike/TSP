private const val DATA_SEPARATOR = " "

class DataExtractor {
    fun extractListOfCities(linesWithData: List<String>): List<City> {
        val cities = mutableListOf<City>()

        linesWithData.forEach { dataLine ->
            val cityCoordinates = dataLine.substringAfter(DATA_SEPARATOR)
            val cityCoordinateX = cityCoordinates.substringBefore(DATA_SEPARATOR).toDouble()
            val cityCoordinateY = cityCoordinates.substringAfter(DATA_SEPARATOR).toDouble()
            cities.add(City(id = cities.size + 1, coordinateX = cityCoordinateX, coordinateY = cityCoordinateY))
        }

        return cities
    }
}