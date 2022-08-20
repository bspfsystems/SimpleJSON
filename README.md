# SimpleJSON

A simple library for working with JSON Objects. This is based on Clifton Labs' version (https://github.com/cliftonlabs/json-simple), which is a fork of Yidong Fang's version (https://github.com/fangyidong/json-simple).

## Obtaining SimpleJSON

You can obtain a copy of SimpleJSON via the following methods:
- Download a pre-built copy from the [Releases page](https://github.com/bspfsystems/SimpleJSON/releases/latest/). The latest version is release 1.1.4.
- Build from source (see below).
- Include it as a dependency in your project (see the Development API section).
-
### Build from Source

SimpleJSON uses [Apache Maven](https://maven.apache.org/) to build and handle dependencies.

#### Requirements

- Java Development Kit (JDK) 8 or higher
- Git
- Apache Maven

#### Compile / Build

Run the following commands to build the library `.jar` file:
```
git clone https://github.com/bspfsystems/SimpleJSON.git
cd SimpleJSON/
mvn clean install
```

The `.jar` file will be located in the `target/` folder.

## Developer API

### Add SimpleJSON as a Dependency

To add SimpleJSON as a dependency to your project, use one of the following common methods (you may use others that exist, these are the common ones):

**Maven:**<br />
Include the following in your `pom.xml` file:<br />
```
<repositories>
    <repository>
        <id>sonatype-repo</id>
        <url>https://oss.sonatype.org/content/repositories/releases/</url>
    </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>org.bspfsystems</groupId>
    <artifactId>simple-json</artifactId>
    <version>1.1.4</version>
    <scope>compile</scope>
  </dependency>
</dependencies>
```

**Gradle:**<br />
Include the following in your `build.gradle` file:<br />
```
repositories {
    maven {
        url "https://oss.sonatype.org/content/repositories/releases/"
    }
}

dependencies {
    implementation "org.bspfsystems:simple-json:1.1.4"
}
```

### API Examples

These are some basic usages of SimpleJSON; for a full scope of what the library offers, please see the Javadocs section below.
```
// A fair number of serialization and deserialization methods throw a JSONException
try {
    
    // Deserialize a file into a JSONObject
    JSONObject object = JSONParser.deserialize(new FileReader(new File("data.json")));
    
    // Add a few items to the object
    object.set("new_id", UUID.randomUUID().toString());
    object.set("new_number", (new Random()).nextInt());
    
    // Get a formatted String to print out to a file (with proper indents and newlines)
    String formatted = JSONParser.format(JSONParser.serialize(object));
    
} catch (JSONException e) {
    e.printStackTrace();
}
```

### Javadocs

The API Javadocs can be found [here](https://bspfsystems.org/docs/simplejson/), kindly hosted by [javadoc.io](https://javadoc.io/).

## Contributing, Support, and Issues

Please check out [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## Licensing

SimpleJSON uses the following licenses:
- [The Apache License, Version 2.0](https://apache.org/licenses/LICENSE-2.0.html)

### Contributions & Licensing

Contributions to the project will remain licensed under the respective license, as defined by the particular license. Copyright/ownership of the contributions shall be governed by the license. The use of an open source license in the hopes that contributions to the project will have better clarity on legal rights of those contributions.

_Please Note: This is not legal advice. If you are unsure on what your rights are, please consult a lawyer._
