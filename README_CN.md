# TextFieldBoxes

[![Build Status](https://travis-ci.org/HITGIF/TextFieldBoxes.svg?branch=master)](https://travis-ci.org/HITGIF/TextFieldBoxes)
[![JitPack](https://jitpack.io/v/HITGIF/TextFieldBoxes.svg)](https://jitpack.io/#HITGIF/TextFieldBoxes)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-TextFieldBoxes-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6158)
[![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)
[![GitHub issues](https://img.shields.io/github/issues/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/issues)
[![GitHub forks](https://img.shields.io/github/forks/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/network)
[![GitHub stars](https://img.shields.io/github/stars/HITGIF/TextFieldBoxes.svg)](https://github.com/HITGIF/TextFieldBoxes/stargazers)
[![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/LICENSE)

![Animation](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/tfb1.gif)

新的 Material Design 文本框，遵循 Google Material Design 规范。

<a href='https://ko-fi.com/A3343PAW' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://az743702.vo.msecnd.net/cdn/kofi4.png?v=0' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

​
## 要求
- Android 4.0.3 IceCreamSandwich (API lv 15) 或更高
- 你最喜欢的 IDE

​
## 安装
在你的项目中加入以下依赖：

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
    compile 'com.github.HITGIF:TextFieldBoxes:1.1.0'
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
    <version>1.1.0</version>
</dependency>
```

#### SBT:
```scala
resolvers += "jitpack" at "https://jitpack.io"
```
```scala
libraryDependencies += "com.github.HITGIF" % "TextFieldBoxes" % "1.1.0"
```


#### Leiningen:
```scala
:repositories [["jitpack" "https://jitpack.io"]]
```
```scala
:dependencies [[com.github.hitgif/textfieldboxes "1.1.0"]]
```

​
## 使用

#### 1. 基础

将 `studio.carbonylgroup.textfieldboxes.TextFieldBoxes` 加入你的布局文件:

```xml
...
<studio.carbonylgroup.textfieldboxes.TextFieldBoxes
    android:id="@+id/text_field_boxes"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Hint" />
...
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/hint.png)![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/input.png)

#### 2. 启用 / 禁用

在 xml 中加入 `app:enabled` 或在 Java 代码中使用 `setEnabled(boolean _enabled)`。

```xml
app:enabled="false"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/basic_disabled.png)

#### 3. 单行

在 xml 中加入 `app:singleLine` 或在 Java 代码中使用 `setSingleLine(boolean _singleLine)` 以设置 EditText 是否为单行，即能够横向滚动。

```xml
app:singleLine="true"
```

![Animation](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/singleline.gif)

#### 4. 帮助和错误信息

##### 帮助信息:
在 xml 中加入 `app:helperText` 或在 Java 代码中使用 `setHelperText(String _helperText)`。

```xml
app:helperText="Helper is here"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/helper.png)

##### 错误信息:
在 Java 代码中使用 `setError(String _errorText)`。

*注意: 文本改动 (输入或删除) 时会自动清除错误信息。*

```java
setError("Error message");
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/error.png)

#### 5. 前缀 & 后缀

在 xml 中加入 `app:prefix` 或在 Java 代码中使用 `setPrefix(String _prefix)` 以设置 EditText 前端的前缀。

在 xml 中加入 `app:suffix` 或在 Java 代码中使用 `setSuffix(String _suffix)` 以设置 EditText 尾端的后缀。

```xml
app:prefix="$ "
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/prefix.png)


```xml
app:suffix=" @gmail.com"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/suffix.png)


#### 6. 最大行数

在 xml 中加入 `app:maxLines` 或在 Java 代码中使用 `setMaxLines(Int _maxlines)` 以设置文本框的最大行数。默认值是 `Integer.MAX_VALUE`。

```xml
app:maxLines="3"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/maxlines.gif)

#### 7. 最大和最小字符数

在 xml 中加入 `app:maxCharacters` 或在 Java 代码中使用 `setMaxCharacters(int _maxCharacters)` 以设置最大字符数, 在 xml 中加入 `app:minCharacters` 或在 Java 代码中使用 `setMinCharacters(int _minCharacters)` 以设置最小字符数。当超出字符数限制时底部的线会变成 `errorColor`（默认为红色）。默认值是 `0`, 表示没有限制。

*注意: 空格和换行不计入字符数。*

```xml
app:maxCharacters="10"
app:minCharacters="5"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/maxMinChar.gif)

```xml
app:maxCharacters="5"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/maxChar.gif)

#### 8. 图标

在 xml 中加入 `app:iconSignifier` 或在 Java 代码中使用 `setIconSignifier(Int resourceID)` 以设置 TextFieldBoxes 前边的图标。

```xml
app:iconSignifier="@drawable/ic_vpn_key_black_24dp"
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/icon1.png)![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/icon2.png)

#### 9. 自定义颜色

*Primary Color* 是底部的线和提示文字的颜色。在 xml 中加入 `app:primaryColor` 或在 Java 代码中使用 `setPrimaryColor(int _colorRes)` 以设置。默认值为目前主题的 `Primary Color`。

*Error Color* 是出现错误时显示的颜色 (e.g. 超出字符数限制, `setError()`)。在 xml 中加入 `app:errorColor` 或在 Java 代码中使用 `setErrorColor(int _colorRes)` 以设置。默认值是 `A400 red`。

*Helper Text Color* 是帮助文本的颜色。在 xml 中加入 `app:helperTextColor` 或在 Java 代码中使用 `setHelperTextColor(int _colorRes)` 以设置。默认值是 `54% black`。

*Panel Background Color* 是文本框背板的颜色。在 xml 中加入 `app:panelBackgroundColor` 或在 Java 代码中使用 `setPanelBackgroundColor(int _colorRes)` 以设置。默认值是 `6% black`。*需要注意的是根据规范，默认的颜色是 6% 透明度的黑色 (`#000000`)，所以如果你要自定义颜色并且仍需让其保持透明，则应同样设置一个带透明度的颜色。*

```xml
app:primaryColor="#1B5E20"          <!--绿的-->
app:errorColor="#ddaa00"            <!--黄的-->
app:helperTextColor="#795548"       <!--棕的-->
app:panelBackgroundColor="#ffe8e8"  <!--粉的-->
```

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/customColor1.png) ![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/customColor2.png)

#### 10. 自定义 EditText

如果你想要自定义 `TextFieldBoxes` (其实是一个包含 `EditText` 的 `FrameLayout` 继承) 中的 `EditText` , 在 Java 代码中使用 `getEditText()` 就可以随便改 (e.g. `setOnKeyListener()`, `addTextChangedListener()`)

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

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/edittext.gif)

#### 11. 暗主题

TextFieldBoxes 用目前主题中的颜色属性因此将自动改变颜色以适应暗主题而不需其他设置。

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/lightTheme.gif)

![](https://raw.githubusercontent.com/HITGIF/TextFieldBoxes/master/images/darkTheme.gif)

​
## 全部属性

#### 文本

| 属性 | 描述 |
| --- | --- |
| `app:text` | EditText 文本 |
| `app:hint` | 顶部的提示文本 |
| `app:helperText` | 底部的帮助文本 |
| `app:prefix` | 前缀文本 |
| `app:suffix` | 后缀文本 |

#### 颜色

| 属性 | 描述 | 默认值 |
| --- | --- | --- |
| `app:helperTextColor` | 帮助文本颜色 | 目前主题 `textColorTertiary` |
| `app:errorColor` | 错误时的显示颜色 (e.g. 超出字符限制, `setError()`) | `A400 red` |
| `app:primaryColor` | 底部的线和提示文字的颜色 | 目前主题 `colorPrimary` |
| `app:prefixTextColor` | 前缀文本颜色 | 目前主题 `textColorTertiary` |
| `app:suffixTextColor` | 后缀文本颜色 | 目前主题 `textColorTertiary` |
| `app:panelBackgroundColor` | 文本框背板的颜色 | 6% `colorForeground` |

#### 字符统计

| 属性 | 描述 | 默认值 |
| --- | --- | --- |
| `app:maxCharacters` | 最大字符数。`0` 表示没有限制 | `0` |
| `app:minCharacters` | 最小字符数。`0` 表示没有限制 | `0` |

#### 其他

| 属性 | 描述 | 默认值 |
| --- | --- | --- |
| `app:enabled` | 文本框是否启用 | `True` |
| `app:singleLine` | EditText 是否为单行 | `False` |
| `app:maxLines` | 文本框最大行数 | `Integer.MAX_VALUE` |
| `app:iconSignifier` | TextFieldBoxes 前边的图标的资源 ID | `0` |
| `app:hasFocus` | 文本框是否获得焦点 | `False` |

​
## TODO
+ [X] 前缀 & 后缀
+ [X] 图标
+ [X] 暗主题

​
## 开源许可

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
