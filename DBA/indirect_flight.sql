-- A flight with a connection
--
-- If no direct flights are found, look for two flights
-- that make up the desired route with minimal transfer times.
--
-- TODO:
--
--   Enforce minimium connection time (e.g. 2 hours)
--   Order by most efficient route (e.g. shortest total journey time?)
--

SELECT 
  FA.flight_id,
  FA.flight_date,
  FA.flight_code,
  FA.depart,
  FA.depart_at,

  FA.destination AS connect_in,
  FB.depart_at - FA.arrive_at AS connect_duration,

  FB.flight_id,
  FB.flight_date,
  FB.flight_code,
  FB.destination,
  FB.arrive_at

FROM
  Flight as FA,
  Flight as FB

WHERE FA.flight_date = '2014-10-07' 
AND   FA.depart = 'LHR' 
AND   FB.destination = 'BLL' 
AND   FA.destination = FB.depart

AND   FA.flight_date = FB.flight_date 
AND   FA.arrive_at < FB.depart_at

ORDER BY connect_duration ASC
;