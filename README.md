# pandalib
Kotlin-based library for Minecraft plugin development. (Using Spigot API)

### Gradle
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.rentedpandas:pandalib:INSERT-VERSION-HERE'
```

### Maven
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.rentedpandas</groupId>
  <artifactId>pandalib</artifactId>
  <version>INSERT-VERSION-HERE</version>
</dependency>
```
