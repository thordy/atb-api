# atb-api
Java implementation of the [AtB](https://www.atb.no/) Sanntids API

## Install
* Clone repository
* Build with `mvn install`
* Add as a dependency to your project
```xml
<dependency>
	<groupId>com.thord</groupId>
	<artifactId>atb-api</artifactId>
	<version>0.0.1</version>
</dependency>
```

## Configuration
To use the API you need credentials obtained from [AtB](https://www.atb.no/sanntid/)

## Example Usage
```java
// Initialize a new instance of the API
AtB api = new AtB("<username>", "<password>", APIMode.SOAP);

// Get a list of all bus stops
BusStopsListResult stops = api.getBusStopsList();

// Get bus schedule for a stop matching the given id
RealTimeForecastByStopResult result = api.getUserRealTimeForecastByStop(<bus_stop_id>);
````
