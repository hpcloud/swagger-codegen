# Swagger Codegen for Helion Code Engine

This will generate a client library for Helion Code Engine. To modify what is being generated, you will need to update the [api.mustache](javascript-angular/src/main/resources/javascript-angular/api.mustache) template file.

## Build
Build `swagger-codegen` from source by running this command at the top level of the repository:
```
mvn package
```
Then, build `javascript-angular` from source in the `hpe/javascript-angular` folder:
```
mvn package
```

## Download the swagger.yml file
Download the [swagger YAML](https://github.com/hpcloud/hce-rest-service/blob/master/app/v2/swagger.yml) file. Convert it to a `swagger.json` file with this command:
```
mkdir hce-swagger
java -jar modules/swagger-codegen-cli/target/swagger-codegen-cli.jar generate \
  -i hce-swagger/swagger.yml \
  -l swagger \
  -o hce-swagger
```

## Compile generator
At the top level of the repository, run this command:
```
java -cp hpe/javascript-angular/target/javascript-angular-swagger-codegen-1.0.0.jar:modules/swagger-codegen-cli/target/swagger-codegen-cli.jar \
io.swagger.codegen.SwaggerCodegen
```

## Generate client library
At the top level of the repository, run this command:
```
java -cp hpe/javascript-angular/target/javascript-angular-swagger-codegen-1.0.0.jar:modules/swagger-codegen-cli/target/swagger-codegen-cli.jar \
io.swagger.codegen.SwaggerCodegen generate -l javascript-angular \
-i hce-swagger/swagger.json \
-o hce-swagger/v2
```
