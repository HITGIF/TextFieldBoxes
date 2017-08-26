# TextFieldBoxes
[![](https://jitpack.io/v/HITGIF/TextFieldBoxes.svg)](https://jitpack.io/#HITGIF/TextFieldBoxes)

A new Material Design text field that comes in a box.

![Animation](/images/tfb1.gif)

​
## Requirements
- Android 4.0.3 IceCreamSandwich (API lv 15) or greater
- Your favorite IDE

​
## Installation
In order to use it, you need to include it in your project.

### Gradle:
```groovy
allprojects {
    repositories {
      ...
      maven { url 'https://jitpack.io' }
    }
}
```
```groovy
dependencies {
    compile 'com.github.HITGIF:TextFieldBoxes:-SNAPSHOT'
}
```

### Maven:
```xml
<repositories>
    <repository>
         <id>jitpack.io</id>
         <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
  <groupId>com.github.HITGIF</groupId>
  <artifactId>TextFieldBoxes</artifactId>
  <version>-SNAPSHOT</version>
</dependency>
```

### SBT:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
```scala
libraryDependencies += "com.github.HITGIF" % "TextFieldBoxes" % "-SNAPSHOT"
```


### Leiningen:
```scala
:repositories [["jitpack" "https://jitpack.io"]]
```
```scala
:dependencies [[com.github.hitgif/textfieldboxes "-SNAPSHOT"]]	
```

​
## Documentation
### Attributes
app:accentColor: the color for the underline and the hint label. Current theme accent color by default.
app:errorColor: the color that is used to indicate error (e.g. exceeding max characters, setError()).

app:hasFocus: whether the EditText is having the focus. False by default.

app:helperText: the text that is shown below the textbox.

app:helperTextColor: the color of helper text.

app:hint: the text that is shown above the textbox.

app:maxCharacters: max characters count limit. 0 means no limit. 0 by default.

app:minCharacters: min characters count limit. 0 means no limit. 0 by default.

app:maxLines: the number of maximum lines allowed in the text field.

app:singleLine: whether the EditText is single-lined. False by default.

app:text: the text of the EditText.

​
## License

    Copyright 2017 Carbonylgroup Studio

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
