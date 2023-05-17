TIMES=$1
mvn clean

for i in $(seq 1 "$TIMES");
do
  mvn test
  if [ -d "./allure-report/history" ]; then
    mkdir -p "./allure-results/history/"
    cp -r "./allure-report/history/" "./allure-results/"
  fi
  allure generate --clean ./allure-results

done
allure open
