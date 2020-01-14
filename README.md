### Processing and Sorting Flights Log Entries
##### The [flights.csv][identifier] file contains entries between certain dates with different fields, the program reads from the csv file to accomplish three tasks:
- The user picks a date interval, and [T1.java][new-identifier] shows a summary report for the entiries between these dates, listing and calculating total number of entiries, minimum departure delay, maksimum departure delay and average departure delay for each flight ordered by number of entiries decending.
- The user picks an airline and a date interval, and [T2.java][new-identifier] shows a report for the entiries between these dates listing, origin airport name, destination airport name, flight number and statistics such as total number of entiries, minimum departure delay, maksimum departure delay, average departure delay ordered by departure delay descending.
- [T3.java][new-identifier] splits [flights.csv][identifier] file into small files. Each file will contain entries regarding only one flight. All files are named as AirlineCode-FlightNumber.csv . Ex. TK-2345.csv.
<!-- Identifiers, in alphabetical order -->
[identifier]: https://drive.google.com/file/d/1Ev7v9P3r05nozA9hLe6w5RLt55sj35Tq/view?usp=sharing
[new-identifier]: https://github.com/MoAbas/FlightDelays/tree/master/flightreport
