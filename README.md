# reactive-spring-product-manager
[![Build Status](https://travis-ci.org/pinka117/reactive-spring-product-manager.svg?branch=master)](https://travis-ci.org/pinka117/reactive-spring-product-manager)
[![Coverage Status](https://coveralls.io/repos/github/pinka117/reactive-spring-product-manager/badge.svg?branch=master)](https://coveralls.io/github/pinka117/reactive-spring-product-manager?branch=master)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=com.reactive.spring:product.manager&metric=code_smells)](https://sonarcloud.io/dashboard/index/com.reactive.spring:product.manager)


## Run Tests
Execute from the project's folder:
#### To run unit and integration tests

```
./mvnw clean verify
```
#### To run e2e tests

```
./mvnw verify -Pe2e-tests
```

## Run application
Make sure mongodb (3.4+) is running
```
mongod
```
To run mongodb execute
```
sudo service mongod start
```
Execute from the project's folder to run application:
```
./mvnw spring-boot:run
```
