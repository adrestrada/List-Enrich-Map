package assignament2.project.adriana.scala

object Main extends App{

  import scala.io.Source
  val bufferedLocation = "/Users/Valeria/IdeaProjects/project-adriana/.idea/dataAdri/gtfs_stm/"
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Trips
  val bufferedTrips = Source.fromFile(s"${bufferedLocation}trips.txt")
  val tripList: List[Trips] = bufferedTrips
      .getLines()
      .toList
      .tail
      .map(_.split(",", -1))
      .map(n => Trips(n(0), n(1), n(2), n(3), if (n(6) =="1") true else false))
  bufferedTrips.close()
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Stops
  val bufferedStops = Source.fromFile(bufferedLocation + "stops.txt")
  val stopsList: List[Stops] = bufferedStops
    .getLines()
    .toList
    .tail
    .map(_.split(",", -1))
    .map(n => Stops(n(0), n(2), n(3).toDouble, n(4).toDouble, n(8).toInt))
  bufferedStops.close()
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~StopTimes
  val bufferedStopsTimes = Source.fromFile(bufferedLocation + "stop_times.txt")
  val stopsTimesIterator= bufferedStopsTimes
    .getLines()
    .drop(1)
    while (stopsTimesIterator.hasNext) {
      val n = stopsTimesIterator.next().split(",", -1)
      val stopTimes = StopTimes(n(0), n(3), n(4).toInt)
      println(stopTimes)
    }
  bufferedStopsTimes.close()
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Routes
  val bufferedRoutes = Source.fromFile(bufferedLocation + "routes.txt")
  val routesList: List[Routes] = bufferedRoutes
    .getLines()
    .toList
    .tail
    .map(_.split(",", -1))
    .map(n => Routes(n(0), n(3), n(6)))
  bufferedRoutes.close()
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CalendarDates
  val bufferedCalendarDate = Source.fromFile(bufferedLocation + "calendar_dates.txt")
  val calendarDateList: List[CalendarDates] = bufferedCalendarDate
    .getLines()
    .toList
    .tail
    .map(_.split(",", -1))
    .map(n => CalendarDates(n(0), n(1), n(2).toInt))
  bufferedCalendarDate.close()
 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TripsRoute
  val lookupRoutes: Map [String, Routes] =
    routesList.map(route => route.routeId -> route).toMap
  val tripsRoute: List[TripsRoute] =
    tripList.map { trip => TripsRoute( trip, lookupRoutes(trip.routeId))}
    tripsRoute.foreach{println}
 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~EnrichedTrip
  val lookupCalendarDates: Map [String, CalendarDates] =
    calendarDateList.map(calendar => calendar.serviceId -> calendar).toMap
  val enrichedTrip: List[EnrichedTrip] =
    tripsRoute.map(tripsRoute => EnrichedTrip( tripsRoute, lookupCalendarDates.getOrElse(tripsRoute.trips.serviceId, null)))
    enrichedTrip.foreach{println}
}

