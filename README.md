# TextFieldBoxes

[![Build Status](https://travis-ci.org/HITGIF/TextFieldBoxes.svg?branch=master)](https://travis-ci.org/HITGIF/TextFieldBoxes)
[![JitPack](https://jitpack.io/v/HITGIF/TextFieldBoxes.svg)](https://jitpack.io/#HITGIF/TextFieldBoxes)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![GitHub issues](https://img.shields.io/github/issues/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/issues)
[![GitHub forks](https://img.shields.io/github/forks/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/network)
[![GitHub stars](https://img.shields.io/github/stars/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/stargazers)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/LICENSE)

![Animation](/images/tfb1.gif)

A new Material Design text field that comes in a box, based on Google Material design guidelines. [中文看这里](https://github.com/HITGIF/TextFieldBoxes/blob/master/README_CN.md)

<a href='https://ko-fi.com/A3343PAW' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

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
    compile 'com.github.HITGIF:TextFieldBoxes:1.0.1'
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
    <version>1.0.1</version>
</dependency>
```

#### SBT:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
```scala
libraryDependencies += "com.github.HITGIF" % "TextFieldBoxes" % "1.0.1"
```


#### Leiningen:
```scala
:repositories [["jitpack" "https://jitpack.io"]]
```
```scala
:dependencies [[com.github.hitgif/textfieldboxes "1.0.1"]]	
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

#### 2. Enable / Disable

`app:enabled` in xml or `setEnabled(boolean _enabled)` in Java.

```xml
app:enabled="false"
```

![](/images/basic_disabled.png)

#### 3. SingleLine

Use `app:singleLine` in xml or `setSingleLine(boolean _singleLine)` in Java to set whether the EditText is single-lined, that scrolls horizontally.

```xml
app:singleLine="true"
```

![Animation](/images/singleline.gif)

#### 4. Helper Text and Error Text

helper text: `app:helperText` in xml or `setHelperText(String _helperText)` in Java.

```xml
app:helperText="Helper is here"
```

![](/images/helper.png)

error text: `setError(String _errorText)` in Java.

*NOTE: Error will be removed when the text changes (input or delete).*

```java
setError("Error message");
```

![](/images/error.png)

#### 5. Maxlines

Use `app:maxLines` in xml or `setMaxLines(Int _maxlines)` to set the number of maximum lines allowed in the text field. `Integer.MAX_VALUE` by default.

```xml
app:maxLines="3"
```

![](/images/maxlines.gif)

#### 6. Max & Min Characters

Use `app:maxCharacters` in xml or `setMaxCharacters(int _maxCharacters)` in java code to set the max characters count, and `app:minCharacters` in xml or `setMinCharacters(int _minCharacters)` in java code to set the min characters count. The color of the bottom line will turn to `errorColor` (red by default) when exceeding max or min characters limit. `0`, as default, means no max or min characters. 

*NOTE: Space and line feed will NOT count.*

```xml
app:maxCharacters="10"
app:minCharacters="5"
```

![](/images/maxMinChar.gif)

```xml
app:maxCharacters="5"
```

![](/images/maxChar.gif)

#### 7. Custom Colors

*Primary Color* will be used for the color of the underline and the hint text. You can use `app:primaryColor` in xml or `setPrimaryColor(int _colorRes)` in Java. Current theme `Primary Color` by default. 

*Error Color* will be used for the color that indicates error (e.g. exceeding max characters, `setError()`). You can use `app:errorColor` in xml or `setErrorColor(int _colorRes)` in Java. `A400 red` by default. 

*Helper Text Color* will be used for the color of the helper text. You can use `app:helperTextColor` in xml or `setHelperTextColor(int _colorRes)` in Java. `54% black` by default.

*Panel Background Color* will be used for the color of panel at the back. You can use `app:panelBackgroundColor` in xml or `setPanelBackgroundColor(int _colorRes)` in Java. `6% black` by default.

```xml
app:primaryColor="#1B5E20"          <!--Green-->
app:errorColor="#ddaa00"            <!--Yellow-->
app:helperTextColor="#795548"       <!--Brown-->
app:panelBackgroundColor="#ffe8e8"  <!--Pink-->
```

![](/images/customColor1.png) ![](/images/customColor2.png)

#### 8. Customize EditText

If you want to customize the `EditText` in the `TextFieldBoxes` (which is a inherited `FrameLayout` that contains a `EditText` for input), use the `getEditText()` methond in Java and do whatever you like (e.g. `setOnKeyListener()`, `addTextChangedListener()`)

```java
final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes);
textFieldBoxes.getEditText().addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
    if (editable.toString().equals("wrong"))
        textFieldBoxes.setError("It's wrong");
    }
});
```

![](/images/edittext.gif)

​
## All Attributes

#### Texts

`app:text` EditText text.

`app:hint` Hint text at the top.

`app:helperText` Helper text at the bottom.

#### Colors

`app:helperTextColor` Helper text color.

`app:errorColor` The color that is used to indicate error (e.g. exceeding max characters, `setError()`).

`app:primaryColor` The color for the underline and the hint text. Current theme `Primary Color` by default.

#### Characters counter

`app:maxCharacters` Max characters count limit. `0` means no limit. `0` by default.

`app:minCharacters` Min characters count limit. `0` means no limit. `0` by default.

#### Others

`app:enabled` Whether the text field is enabled. `True` by default.

`app:singleLine` Whether the EditText is single-lined. `False` by default.

`app:maxLines` The number of maximum lines allowed in the text field. `Integer.MAX_VALUE` by default.

`app:hasFocus` Whether the EditText is having the focus. `False` by default.

​
## TODO
- Prefix & Suffix
- Icon signifier
- Dark theme

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
