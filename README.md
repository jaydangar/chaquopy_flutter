# chaquopy

This is a chaquopy plugin to run python code on android. This is the simplest version, where you can write you code and run it.

## Configuration Steps : 

Add  `maven { url "https://chaquo.com/maven" }` to your project level `gradle.build` like following :

`
buildscript {
    ...
    repositories {
        google()
        jcenter()
        maven { url "https://chaquo.com/maven" }
    }
    ...
}
`

Now in order to use the chaquopy plugin import the chaquopy package in your flutter app through declaring the package inside pubspec.yaml file.

Now you can run the code easily by calling `Chaquopy.executeCode(code)` function which returns a map. 

The map returned contains two properties and it contains follwing structure:

`{
    "outputText" : "code output",
    "error" : "code error",
}`

you can easily access this code via result['textOutput'] and result['error'], where result is variable of type map and you can name it anything you want. 