Sobre o app:
    -O app foi desenvolvido utilizando arquitetura MVP e Kotlin Coroutines

Build:
    -Para buildar é preciso adcionar a chave da api no arquivo local.properties: tmdb_api_key={api_key}

Bibliotecas utilizadas:
    -Bibliotecas divididas em regiões: region Kotlin, region UI e region Networking

Region Kotlin:
    -Kotlin Coroutines: para utilização de chamadas assíncronas através de coroutines
    -Koin: utilizado para injeção de dependências

Region UI:
    -AppCompat, RecyclerView, ConstraintLayout and Design: biblioteca de componentes
    -Glide: utilizado para carregar as imagens através dos links disponibilizados pela API

Region Networking:
    -OkHttp3 e Retrofit: utilizado para fazer requisições para API e consumir os dados
    -Gson: utilizado para converter json para objetos em kotlin