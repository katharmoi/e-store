# E-Store

Android app to provide a template app for e-commerce

## Project Structure

This is a multi module, multi flavor android app.
- App is implemented using clean architecture.
- Dagger is used for the DI.
- Retrofit is used for networking
- Room is used for the db
- Rx Java is used for functional & asynchronous programming


App consists of 3 modules

- domain module: contains all business logic entities and use cases.
- data module: contains all local(db) and remote data implementations
- app module: contains view layer objects (activities, view models, fragments, layouts, assets...)


## Getting Started

Instructions to get you a copy of the project up and running on your
local machine for development and testing purposes. See deployment for
notes on CI/CD.

### Prerequisites

[Download android studio](https://developer.android.com/studio/?gclid=CjwKCAjw9vn4BRBaEiwAh0muDEPwbBj97WHCtKOHt1oVexwIiDvDfj1BTt4TWhw50w0leOJfkATtKRoCxa8QAvD_BwE&gclsrc=aw.ds)

If you're using a 64-bit version of Linux you need to install the required libraries for 64-bit machines.

For 64-bit ubuntu
```
$ sudo apt-get install libc6:i386 libncurses5:i386 libstdc++6:i386 lib32z1 libbz2-1.0:i386
```

For 64-bit Fedora

```
$ sudo yum install zlib.i686 ncurses-libs.i686 bzip2-libs.i686
```

### Installing

To install Android Studio on Windows:

```
1.Double click the downloaded exe

2.Follow the setup wizard in Android Studio and install any SDK packages that it recommends
```

To install Android Studio on Linux:

```
1.Unpack the .zip file you downloaded to an appropriate location for your
  applications, such as within /usr/local/ for your user profile, or /opt/
  for shared users.
  
2.To launch Android Studio, open a terminal, navigate to the android-studio/bin/
  directory, and execute studio.sh.
  
3.Follow the setup wizard in Android Studio and install any SDK packages that it recommends
```

Importing app:

1.Launch Android Studio

2.Import project into android studio

![image](https://drive.google.com/uc?export=view&id=1tVy5tCV1krwcoIYUYHBeDMAP2sw9vD3w)

3.If prompted click&load required libraries.

4.Wait until gradle builds the project

### Configurations

  ##### Updating remote credentials

  Under domain module select build.gradle. Change the credentials for the
  corresponding build type(release/debug)

  ```
   buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            buildConfigField("String", "API_TOKEN", "token_to_be_changed")
        }
        debug{
            buildConfigField("String", "API_TOKEN", "token_to_be_changed")
        }
    }
  ```

  ##### Updating app version

  App utilizes semantic versioning. Under app module build.gradle update the
  major/minor/patch field.

  Example for version 2.1.0

  ```
  ext.versionMajor = 2
  ext.versionMinor = 1
  ext.versionPatch = 0
  ```

### Deployment

##### CI/CD

Bitrise is used as CI/CD automation tool.

###### Manual Build

 1.Select the app under APPS menu

 2.Press the Start/Schedule a build btn

 3.Select the branch to build and start build

 4.All the artifacts including the flavor dependant apks and test results
 can be found under the corresponding builds APPS & ARTIFACTS tab