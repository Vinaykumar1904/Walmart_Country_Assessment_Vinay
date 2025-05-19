# Walmart Country Assessment
This Android application, developed in Kotlin, showcases a list of countries fetched from a public JSON API. It is structured using Clean Architecture principles and integrates Retrofit for networking, ViewModel and StateFlow for state management, and RecyclerView for presenting the data in an interactive user interface.

# Architecture Overview:
* Data Layer : Uses CountryResponse data classes to hold the Api response and uses ApiService with Retrofit to fetch data from the API.
* Model Transformation: Used Mapper to map the Entity data classes to Domain layer data classes to hold the Ui related data.
* View Layer: Includes MainActivity, RecyclerView, CountryAdapter, and CountryItemViewHolder to render the country list.
* ViewModel Layer: CountryViewModel provides a StateFlow<ApiStates<List<Country>>> to observe data changes.
* UseCase Layer: Business logic is encapsulated within CountriesUseCase.
* Repository Layer: CountryRepository implements the ICountryRepository interface to abstract data operations.

# Packages
```
com.vinay.walmartassesment
│
├── state             # ApiStates sealed class
├── data
│   ├── dto             # CountryResponse, Currency, Language
│   ├── mappers         # Mapper, Imapper
│   ├── remote          # ApiClient and ApiService
│   └── repositories    # CountryRepository
│
├── domain
│   ├── data            # Country
│   ├── irepository     # ICountryRepository
│   └── usecase         # CountryUseCase
│
├── presentation
│   ├── view            # MainActivity, CountryAdapter, CountryViewHolder
│   └── viewModel       # CountryViewModel, CountryVMFactory
```

# Screenshots
<img src="https://github.com/user-attachments/assets/fc728f6f-9311-41e7-87e5-efcbd82fedf1" alt="countriesList" width="300"/>

# Screen Recording


https://github.com/user-attachments/assets/403f8406-50be-4433-8c5a-8bdc752e120f


