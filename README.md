## Chaquopy Flutter plugin

This is an unofficial Chaquopy Flutter plugin to run Python code on Android. This is the simplest version, where you can write your code and run it.

## Configuration Steps : 

Add  `maven { url "https://chaquo.com/maven" }` and add chaquopy dependency in dependencies section at project level `gradle.build` like following :
You can find the latest-version of `chaquopy` [here](https://chaquo.com/chaquopy/):

```
buildscript {
    repositories {
        google()
        jcenter()
        maven { url "https://chaquo.com/maven" }
    }
    dependencies {
        ...
        classpath "com.chaquo.python:gradle:latest-version"
    }
}
```

Then, in the module-level build.gradle file (usually in the app directory), apply the Chaquopy plugin at the top of the file, but after the Android plugin:

```
apply plugin: 'com.android.application' 
apply plugin: 'com.chaquo.python' 
```

Apply ABI selection using following code.

```
defaultConfig {
    ndk {
       abiFilters "armeabi-v7a", "arm64-v8a", "x86", "x86_64"
    }
}
```

add this line to application tag in manifest file 
```
android:name="com.chaquo.python.android.PyApplication".
```

After that sync your project.

Now, in your android folder, you'll have additional python folder created. Now, Download [script.py](https://drive.google.com/file/d/1D4Hjt66f0MXkaeAQ8WLX3DEebX3BrFvM/view?usp=sharing) and put it in python directory. (Kindly note that this python file should not be renamed other than script.py and also if your code doesn't work, check the intendations of the downloaded file.)

Now in order to use the chaquopy plugin import the chaquopy package in your flutter app through declaring the package inside pubspec.yaml file.

Now you can run the code easily by calling `Chaquopy.executeCode(code)` function which returns a map. 

The map returned contains two properties and it contains follwing structure:

```
{
    "textOutputOrError" : "code output or error",
}
```

you can easily access this code via `result["textOutputOrError"]` , where result is variable of type map and you can name it anything you want.

## FAQs:

1. Why it shows the notification and also crashes the app after some time limit?

    This plugin uses [Chaquopy SDK](https://chaquo.com/chaquopy/), which uses a license for the unlimited use case. In order to remove the notification and timelimit, you need to contact [here](https://chaquo.com/chaquopy/paid-license/) for the license and after you get your license, you need to follow add follwing two lines in your `local.properties` file.

    1. chaquopy.license=`your_license_key`
    2. chaquopy.applicationId=`package name of the app`

2. Can I use python packages?

    You can use all the python packages by using following configuration.
    
    ```
    defaultConfig {
        python {
            pip {
                install "scipy"
                install "numpy"
                //  specify any other package to install.
            }
        }
    }
    ```

3. Why this package doesn't support OpenCV, Matplotlib and NLTK packages?

    I am writing reasons for individual packages here.

    1. OpenCV and Matplotlib : OpenCV and Matplotlib Requires a special configuration, I am working on this issue to be able to integrate in the package itself.
   
    2. NLTK and Spacy : NLTK and Spacy Packages can be installed and technically run on your device, but most of the NLTK and Spacy functionality relies on it's data that you will be downloading using `nltk.download('all')`. so It increases the size of the app significantly. I am also working on this feature.

4. App Size Reduction:
   
   Using ABI Selection settings you can reduce the app size. The Python interpreter is a native component, so you must use the abiFilters setting to specify which ABIs you want the app to support. The currently available ABIs are:

```
    armeabi-v7a, supported by virtually all Android devices.

    arm64-v8a, supported by most recent Android devices.

    x86, for the Android emulator.

    x86_64, for the Android emulator.
```

During development you’ll probably want to enable them all, i.e.:

```
defaultConfig {
    ndk {
       abiFilters "armeabi-v7a", "arm64-v8a", "x86", "x86_64"
    }
}
```

5. buildPython:

Some features require Python 3.5 or later to be available on the build machine. These features are indicated by a note in their documentation sections.

By default, Chaquopy will try to find Python on the PATH with the standard command for your operating system, first with a matching minor version, and then with a matching major version. For example, if Chaquopy’s own Python version is 3.8.x, then:

```
    On Linux and Mac it will try python3.8, then python3.

    On Windows, it will try py -3.8, then py -3.
```

If this doesn’t work for you, set your Python command using the buildPython setting. For example, on Windows you might use one of the following:

```
defaultConfig {
    python {
        buildPython "C:/path/to/python.exe"
        buildPython "C:/path/to/py.exe", "-3.8"
    }
}
```

6. Future Plans : 
   
    [ ] Add support for opencv and matplotlib

    [ ] Add support for NLTK and Spacy

    [ ] Add support for apple devices as well. If you want to help me out with it, kindly [contact]('jayjaydangar96@gmail.com') me,through mail, and I will be happy make this plugin better.


##  Demo : 
    
![](https://user-images.githubusercontent.com/10520025/113665705-94912600-96cb-11eb-8ebd-3732058e52d0.gif)
    
##  Queries : 

All the configurations of chaquopy will work the same way, it's mentioned on the [chaquopy sdk](https://chaquo.com/chaquopy/doc/current/android.html) home page. if you don't find any solution you can open issue in the repository and I am happy to help. :) 
