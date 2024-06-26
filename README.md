## Building the Project:

To build the project, navigate to the directory containing `pom.xml` and run the following command:

```bash
mvn clean install
```
This command will clean the project, compile the source code, run tests, and package the application into a JAR file.
Пo to the directory:
```bash
cd/src/main/java/com/barda_petrenco/shop_electronic
```
## Running the app:
To run Java code, you must have the JDK (Java Development Kit) installed. You can check the JDK installation with the command:

```bash
java -version
javac -version
```

Use the javac command to compile your Java file:
```bash
javac ShopElectronicApplication.java
```
Use the java command to run your compiled class:
```bash
java ShopElectronicApplication
```
Open your browser and paste:
```bash
http://localhost:8012/
```
## Using the app:
## None authorise pages:
- index.html (home page)
- error.html (error page)
- products.html (products page)
- speaker ,1,3.html (product speker page)
- login.html (authirise page)
## Pages with authorization (for authorized users):
- bucket.html (bucket page)
- profile.html (profile user page)
- user.html (registration page)
- userList.html (user list with admin)
