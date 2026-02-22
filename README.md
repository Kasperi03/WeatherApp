**Viikkotehtävä 6 - Room**

**Room** Room toimii paikallisena tietokantakerroksena, joka hallinnoi säädataa ja hakuhistoriaa. Projektissa Room koostuu:

**Entity** määrittelee tietokantataulut, kuten WeatherEntity ja SearchHistoryEntity.

**DAO** (Data Access Object) – sisältää metodit tietojen lisäämiseen ja hakemiseen (WeatherDao, SearchHistoryDao).

**Database** yhdistää Entityt ja DAO:t (AppDatabase).

**Repository** tarjoaa ViewModelille puhtaan rajapinnan datan hakemiseen ja tallentamiseen (WeatherRepository, SearchHistoryRepository).

**ViewModel** säilyttää ja hallinnoi UI:lle annettavaa dataa (WeatherViewModel).

**UI/View**  havainnollistaa tiedon käyttäjälle (WeatherScreen) ja reagoi muutoksiin LiveData/Flow:n kautta.

**Projektin rakenne**
MainActivity.kt
|
+---data
|   \---remote
|       |   RetrofitInstance.kt
|       |   WeatherApi.kt
|       |
|       +---local
|       |       AppDatabase.kt
|       |       SearchHistoryDao.kt
|       |       WeatherDao.kt
|       |
|       +---model
|       |       SearchHistoryEntity.kt
|       |       WeatherEntity.kt
|       |       WeatherResponse.kt
|       |
|       \---repository
|               SearchHistoryRepository.kt
|               WeatherRepository.kt
|
+---ui
|   \---theme
|           Color.kt
|           Theme.kt
|           Type.kt
|
+---view
|       WeatherScreen.kt
|
\---viewmodel
        WeatherViewModel.kt
**Datavirran kulku**

UI pyytää tietoa ViewModelilta (WeatherScreen → WeatherViewModel).

ViewModel kysyy Repositoryltä (WeatherRepository tai SearchHistoryRepository).

Repository tarkistaa ensin Roomin välimuistin (WeatherDao / SearchHistoryDao).

Jos data on vanhentunutta tai puuttuu, Repository hakee sen verkosta (WeatherApi via RetrofitInstance).

Saadut tiedot tallennetaan Roomiin (AppDatabase) ja palautetaan ViewModelille.

ViewModel päivittää UI:n LiveData/Flow:n kautta, jolloin käyttäjä näkee aina tuoreimman datan.

**Välimuistilogiikka**

Room toimii paikallisena välimuistina: säädata tallennetaan tietokantaan.

Repository tarkistaa välimuistin ajan:

Jos data on alle esim. 30 minuuttia vanhaa -> palautetaan Roomista.

Jos data on vanhempaa tai puuttuu -> haetaan API:sta ja päivitetään Room.

Näin UI saa nopean vasteajan välimuistista, mutta myös tuoretta tietoa tarvittaessa.
