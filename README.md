## Swagger-ui path
http://localhost:8081/v1/swagger-ui/index.html

## H2 database information
http://localhost:8081/h2

## App Health check
http://localhost:8081/actuator/health

## Sample Request and Response

Method: - 'GET'
	Request - 'http://localhost:8081/airline/v1/search?origin=AMS&destination=BLR'

	Response:
			{
    "airlineNumber": "C103",
    "origin": "AMS",
    "destination": "BLR",
    "departureTime": "10:30:00",
    "arrivalTime": "17:00:00",
    "fare": 730
	},
	{
	"airlineNumber": "C101",
	"origin": "AMS",
	"destination": "BLR",
	"departureTime": "13:00:00",
	"arrivalTime": "18:30:00",
	"fare": 800
	},
	{
	"airlineNumber": "C102",
	"origin": "AMS",
	"destination": "BLR",
	"departureTime": "12:00:00",
	"arrivalTime": "18:00:00",
	"fare": 850
	}

Method: - 'GET'
	Request - 'http://localhost:8081/airline/v1/search?origin=AMS&destination=BLR&sortColumn=fare&sortType=desc'

	Response:
	[
	{
	"airlineNumber": "C102",
	"origin": "AMS",
	"destination": "BLR",
	"departureTime": "12:00:00",
	"arrivalTime": "18:00:00",
	"fare": 850.0
	},
	{
	"airlineNumber": "C101",
	"origin": "AMS",
	"destination": "BLR",
	"departureTime": "13:00:00",
	"arrivalTime": "18:30:00",
	"fare": 800.0
	},
	{
	"airlineNumber": "C103",
	"origin": "AMS",
	"destination": "BLR",
	"departureTime": "10:30:00",
	"arrivalTime": "17:00:00",
	"fare": 730.0
	}
	]

Method: - 'GET'
      	Request - 'http://localhost:8081/airline/v1/search?origin=AMS&destination=BLR&sortColumn=fare&sortType=test

	{
	"timestamp": "2023-08-22T10:56:22.723+00:00",
	"status": 500,
	"error": "Internal Server Error",
	"path": "/airline/v1/search"
	}

Method: - 'GET'
      	Request - 'http://localhost:8081/airline/v1/search?origin=AMS&destination=ABD'

      	Response:
      	response status is 404

        Response body
        No airlines found in this route.