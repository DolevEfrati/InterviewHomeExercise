# InterviewHomeExercise

## Usage
Java8, maven 3.3.9

## Installation
To run, you need to open project, run 'mvn clean install' and run the main function of the Bootstrapper class.
To ask your queries, open your browser with localhost:8081

## Queries
1. "localhost:8081/sites_number/{visitorId}"  -> Num of unique sites for visitor
2. "localhost:8081/sessions_number/{siteUrl}" -> Num sessions for site
3. "localhost:8081/median/{siteUrl}"          -> Median session length

## Input files
The input files are in the project under the "resources/csvFiles" path. For the run, need to change the filesList parameter in the main function (in the Bootstrapper class).

## Description of the solution
The input files are scanned line by line. 
For each new pageView it is inserted into the correct ArrayList(PageView).
After insertion, the ArrayList(PageView) is converted to ArrayList(Session).
  
## Complexity
The time complexity is O(nlog(n)).
