import kotlin.math.pow

fun City.getDistanceToCity(destinationCity: City): Double {
    return Math.sqrt(
        (destinationCity.coordinateY - this.coordinateY).pow(2)
                + (destinationCity.coordinateX - this.coordinateX).pow(2)
    )
}