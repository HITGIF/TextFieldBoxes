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

#### Gradle:
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

#### Maven:
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

#### SBT:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
```scala
libraryDependencies += "com.github.HITGIF" % "TextFieldBoxes" % "-SNAPSHOT"
```


#### Leiningen:
```scala
:repositories [["jitpack" "https://jitpack.io"]]
```
```scala
:dependencies [[com.github.hitgif/textfieldboxes "-SNAPSHOT"]]	
```

​
## Usage

#### 1. Basic

Add `studio.carbonylgroup.textfieldboxes.TextFieldBoxes` to your layout:

```xml
...
<studio.carbonylgroup.textfieldboxes.TextFieldBoxes
    android:id="@+id/text_field_boxes"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Hint" />
...
```

![](/images/hint.png)![](/images/input.png)

#### 2. Disable

`app:enabled="false"` in xml or `setEnable(false)` in Java.

![](/images/basic_disabled.png)

#### 3. SingleLine

Use `app:singleLine="true"` in xml or `setSingleLine(true)` in Java to set the EditText to be single-lined, that scrolls horizontally.

![Animation](/images/singleline.gif)

#### 4. Helper Text and Error Text

helper text: `app:helperText="Helper is here"` in xml or `setHelperText("Helper is here")` in Java.

![](/images/helper.png)

error text: `setError("Error message")` in Java.

*NOTE: Error will be removed when the text changes (input or delete).*

![](/images/error.png)


​
## Documentation
### All Attributes

`app:enabled` Whether the text field is enabled. `True` by default.

`app:text` EditText text.

`app:hint` Hint text at the top.

`app:helperText` Helper text at the bottom.

`app:singleLine` Whether the EditText is single-lined. `False` by default.

`app:maxLines` The number of maximum lines allowed in the text field. `Integer.MAX_VALUE` by default.

`app:maxCharacters` Max characters count limit. `0` means no limit. `0` by default.

`app:minCharacters` Min characters count limit. `0` means no limit. `0` by default.

`app:helperTextColor` Helper text color.

`app:errorColor` The color that is used to indicate error (e.g. exceeding max characters, `setError()`).

`app:primaryColor` The color for the underline and the hint label. Current theme `Primary Color` by default.

`app:hasFocus`Whether the EditText is having the focus. `False` by default.

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
