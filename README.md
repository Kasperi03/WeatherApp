**WeatherApp**

Yksinkertainen Android-sääsovellus, joka hakee OpenWeatherMap API:sta säätiedot ja näyttää ne Jetpack Compose UI:ssa.

**Toiminnallisuus**

Syötä kaupungin nimi ja paina Search.

Näyttää: kaupungin, sään kuvauksen, lämpötilan, kosteuden ja tuulen nopeuden.

Loading-indikaattori datan haun aikana ja virheilmoitus virhetilanteessa.

**Tekniset tiedot**

Retrofit hoitaa HTTP-pyynnöt ja Gson muuntaa JSONin Kotlin-dataluokiksi.

Coroutines suorittavat API-kutsut taustalla ilman UI-jäätymistä.

ViewModel & StateFlow hallitsevat UI-tilaa; Compose reagoi muutoksiin automaattisesti.

API-key tallennettu local.properties → BuildConfig → Retrofit.
