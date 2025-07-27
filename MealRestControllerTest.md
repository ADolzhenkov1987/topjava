## meal REST controller tests

### 1. get all user's meals
curl -s "http://localhost:8080/topjava/rest/user/meals"

### 2. get some meal
curl -s "http://localhost:8080/topjava/rest/user/meals/100011"

### 3. get meals filtered with date or time
#### date without time
curl -s "http://localhost:8080/topjava/rest/user/meals/filter?startDate=2020-01-30&endDate=2020-01-31"
#### date with time
curl -s "http://localhost:8080/topjava/rest/user/meals/filter?startDate=2020-01-30&endDate=2020-01-30&startTime=10:00&endTime=13:00"

### 4. create new meal
curl -X POST "http://localhost:8080/topjava/rest/user/meals" \
  -H "Content-Type: application/json" \
  -d '{"dateTime":"2025-07-27T10:00:00","description":"breakfast","calories":1100}'

### 5. update meal
curl -X PUT "http://localhost:8080/topjava/rest/user/meals/100012" \
  -H "Content-Type: application/json" \
  -d '{"dateTime":"2025-07-27T10:00:00","description":"updated breakfast","calories":1500}'

### 6. delete meal
curl -X DELETE "http://localhost:8080/topjava/rest/user/meals/100012"