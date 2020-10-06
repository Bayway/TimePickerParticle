[![](https://jitpack.io/v/bayway/timepickerparticle.svg)](https://jitpack.io/#bayway/timepickerparticle)

# TimePickerParticle
========

A simple Android TimePickerDialog with hour, minutes and seconds. This widget is an extension of [TimePickerWithSeconds][1].

Is required **Android 6+**

You can simply decide what do you want to show with simple function like:

``` java
mTimePicker.setHourNotVisible();
```


Quick Setup - Gradle
========

``` java
repositories { 
    jcenter()
    maven { url “https://jitpack.io” }
}

dependencies {
    implementation 'com.github.bayway:timepickerparticle:v1.0'
}
```


Usage
--------

Show the Picker with:
   
``` java
TimePickerParticleDialog timePickerParticleDialog = new TimePickerParticleDialog(MainActivity.this, 
  new TimePickerParticleDialog.OnTimeSetListener()
  {
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds)
    {
      setTime(hourOfDay, minute, seconds);
    }
  }, 1, 0, 0, true, true, true, true);
timePickerParticleDialog.show();
```

Enjoy!


License
=======

    Copyright 2020 Massimiliano Fiori

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: https://github.com/IvanKovac/TimePickerWithSeconds
 [2]: https://search.maven.org/remote_content?g=com.squareup.retrofit2&a=retrofit&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
 [proguard file]: https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard/retrofit2.pro
 [okhttp proguard]: https://square.github.io/okhttp/#r8-proguard
 [okio proguard]: https://square.github.io/okio/#r8-proguard
